$(document).ready(function(){
	
	   
	 tableInit();
	 
	 $("#queryButton").click(function(){
			loadTable();
		});
	 
});


function tableInit(){
	var toolbar = [];
	if(xyzControlButton("buttonCode_h20151214155701")){
		toolbar[toolbar.length]={
				text: '新增班次',
				icon: 'fa-plus',
				iconType:'primary',
				handler: 'addLesson()'
		};
	}
	if(xyzControlButton("buttonCode_h20151214155702")){
		toolbar[toolbar.length]={
				text: '编辑班次',
				icon: 'fa-edit',
				iconType:'info',
				handler: 'editUser()'
		};
	}

	xyzgrid({
		table : 'lessonManagerTable',
		url : '../LessonWS/queryLessonList.do',
		toolbar : toolbar,
	    columns : [
	      {field:'checkboxTemp',checkbox:'true'},
          {field:'year',title:'年份'},
  		  {field:'type',title:'类型'},
  		  {field:'name',title:'名称'},
		  {field:'price',title:'价格'},
		  {field:'dateInfo',title:'上课时间'},
  		  {field:'addDate',title:'添加时间'},
  		  {field:'temp',title:'设置资源组',width:'90px',
				formatter: function(value,row,index){
					return '<button data-toggle="button" class="btn btn-primary btn-sm btn-outline" type="button"><i class="fa fa-sitemap"></i>&nbsp;资源组</button>';
				}
		  }
  	
	    ]
	});
}

function loadTable(){
	var username = $("#username").val();
	var nickName = $("#nickName").val();
	var position = $("#position").combobox("getValue");
	
	$("#userManagerTable").bootstrapTable('refresh',{query:{
		username : username,
		nickName : nickName,
		position : position
	}});
}


function addLesson(){
	
	xyzdialog({
		id : 'dialog_addLesson',
		title : '新增班次',
		content: '../data/addLesson.html',
	    fit:false,
	    width:'600px',
	    height:'700px',
	    buttons:[{
			text:'确定',
			handler:function(){
				addLessonSubmit();
			}
		},{
			text:'取消'
		}],
		onLoad:function(){
		
		}
	});
	


}


function editUser(){
	
	var users=$('#userManagerTable').bootstrapTable('getSelections');

	if(users.length != 1){
		top.layer.msg('请先选中单个对象',{img: "warning"});
		return;
	}
	
	var row = users[0];
	
	xyzdialog({
		id : 'dialog_editUser',
		title : '新增用户',
		content: '../security/editUser.html',
	    fit:false,
	    width:'600px',
	    height:'400px',
	    buttons:[{
			text:'确定',
			handler:function(){
				editUserSubmit();
			}
		},{
			text:'取消'
		}],
		onLoad:function(){
			$("#usernameForm").val(row.username);
			$("#nickNameForm").val(row.nickName);
		}
	});
}

function editUserEnabled(username){
	xyzAjax({
		url : "../AdminUserWS/editUserEnabled.do",
		data : {
			username : username
		},
		success : function(data) {
			if(data.status==1){
				top.layer.msg('操作成功',{img:"check"});
			}else{
				top.layer.alert(data.msg, {icon: 2});
			}
		}
	});
}

function addLessonSubmit(){
	
	var year = $("#yearForm").val();
	var type = $("#typeForm").val();
	var name = $("#nameForm").val();
	var price = $("#priceForm").val();
	var dateInfo = $("#dateInfoForm").val();
	var teachType = $("#teachTypeForm").val();
	var flagRefund = $("#flagRefundForm").attr("checked")=="checked"?1:0;
	var remark = $("#remarkForm").val();

	xyzAjax({
		url : "../LessonWS/addLesson.do",
		data : {
			year : year,
			type : type,
			name : name,
			price : price,
			dateInfo : dateInfo,
			teachType : teachType,
			flagRefund : flagRefund,
			remark : remark
		},
		success : function(data) {
			if(data.status==1){
				$('#lessonManagerTable').bootstrapTable('refresh');
				top.layer.msg('操作成功',{img:"check"});
				layer.close($("#dialog_addLesson").data("index")); 
			}else{
				top.layer.alert(data.msg, {icon: 2});
			}
		}
	});

}


function editUserSubmit(){
	
	var username = $("#usernameForm").val();
	var nickName = $("#nickNameForm").val();
	
	xyzAjax({
		url : "../AdminUserWS/editUser.do",
		data : {
			username : username,
			nickName : nickName
		},
		success : function(data) {
			if(data.status==1){
				$('#userManagerTable').bootstrapTable('refresh');
				top.layer.msg('操作成功',{img:'check'});
				layer.close($("#dialog_addUser").data("index")); 
			}else{
				top.layer.alert(data.msg, {icon: 2});
			}
		}
	});

}

function editUserPassword(){
	var users=$('#userManagerTable').bootstrapTable('getSelections');

	if(users.length != 1){
		top.layer.msg('请先选中单个对象',{img: "warning"});
		return;
	}
	
	var row = users[0];
	
	xyzdialog({
		id : 'dialog_editUserPassword',
		title : '重设密码',
		content: '../security/editUserPassword.html',
	    fit:false,
	    width:'600px',
	    height:'400px',
	    buttons:[{
			text:'确定',
			handler:function(){
				editUserPasswordSubmit();
			}
		},{
			text:'取消'
		}],
		onLoad:function(){
			$("#usernameForm").val(row.username);
			$("#nickNameForm").val(row.nickName);
		}
	});
}
	
function editUserPasswordSubmit(){
	
	var username = $("#usernameForm").val();
	var password = $("#passwordForm").val();
	var confirmPassword = $("#confirmPasswordForm").val();
	
	if(password!=confirmPassword){
		layer.tips('两次输入的密码不一致', '#passwordForm');
		return;
	}
	
	password=$.md5(password).substr(8,16);
	
	xyzAjax({
		url : "../AdminUserWS/editUserPassword.do",
		data : {
			username : username,
			password : password
		},
		success : function(data) {
			if(data.status==1){
				$('#userManagerTable').bootstrapTable('refresh');
				top.layer.msg('操作成功',{img:'check'});
				layer.close($("#dialog_editUserPassword").data("index"));
			}else{
				top.layer.alert(data.msg, {icon: 2});
			}
		}
	});
}

