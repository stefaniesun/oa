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
				text: '新增学员',
				icon: 'fa-plus',
				iconType:'primary',
				handler: 'addStudent()'
		};
	}
	if(xyzControlButton("buttonCode_h20151214155702")){
		toolbar[toolbar.length]={
				text: '编辑学员',
				icon: 'fa-edit',
				iconType:'info',
				handler: 'editUser()'
		};
	}
	if(xyzControlButton("buttonCode_h20151214155704")){
		toolbar[toolbar.length]={
				text: '重设密码',
				icon: 'fa-lock',
				iconType:'danger',
				handler: 'editUserPassword()'
		};
	}
	xyzgrid({
		table : 'studentManagerTable',
		url : '../StudentWS/queryStudentList.do',
		toolbar : toolbar,
	    columns : [
	      {field:'checkboxTemp',checkbox:'true'},
          {field:'name',title:'姓名'},
  		  {field:'phone',title:'手机号'},
		  {field:'enabled',title:'可用',
				formatter: function(value,row,index){
					if (value == 1){
						return '<input type="checkbox" class="js-switch" onChange="editUserEnabled(\''+row.username+'\')" checked/>';
					} else {
						return '<input type="checkbox" class="js-switch" onChange="editUserEnabled(\''+row.username+'\')" />';
					}
				}
		  },
  		  {field:'idCard',title:'身份证号码'},
  		  {field:'addDate',title:'添加时间'},
  		  {field:'temp',title:'设置资源组',width:'90px',
				formatter: function(value,row,index){
					return '<button data-toggle="button" class="btn btn-primary btn-sm btn-outline" type="button"><i class="fa fa-sitemap"></i>&nbsp;资源组</button>';
				}
		  },
  		  {field:'temp',title:'绑定手持终端',width:'90px',
				formatter: function(value,row,index){
					return '<button data-toggle="button" class="btn btn-primary btn-sm btn-outline" type="button"><i class="fa fa-mobile-phone"></i>&nbsp;手持终端</button>';
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


function addStudent(){
	
	xyzdialog({
		id : 'dialog_addStudent',
		title : '新增学员',
		content: '../data/addStudent.html',
	    fit:false,
	    width:'600px',
	    height:'500px',
	    buttons:[{
			text:'确定',
			handler:function(){
				addStudentSubmit();
			}
		},{
			text:'取消'
		}]
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

function addStudentSubmit(){
	
	var name = $("#nameForm").val();
	var phone = $("#phoneForm").val();
	var idCard = $("#idCardForm").val();
	
	xyzAjax({
		url : "../StudentWS/addStudent.do",
		data : {
			name : name,
			phone : phone,
			idCard : idCard
		},
		success : function(data) {
			if(data.status==1){
				$('#studentManagerTable').bootstrapTable('refresh');
				top.layer.msg('操作成功',{img:"check"});
				layer.close($("#dialog_addStudent").data("index")); 
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

