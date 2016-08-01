$(document).ready(function(){
	
	
	xyzCombobox({
		combobox : 'test',
		url : '../../AdminUserWS/getAllPosition.do',
		valueField: 'numberCode',
		textField : 'nameCn'
	});
	
	   
	 tableInit();
	 
});


function tableInit(){
	var toolbar = [];
	if(xyzControlButton("buttonCode_h20151214155701")){
		toolbar[toolbar.length]={
				text: '新增用户',
				icon: 'fa-plus',
				iconType:'primary',
				handler: 'addUser()'
		};
	}
	if(xyzControlButton("buttonCode_h20151214155702")){
		toolbar[toolbar.length]={
				text: '编辑用户',
				icon: 'fa-edit',
				iconType:'info',
				handler: 'editUser()'
		};
	}
	xyzgrid({
		table : 'userManagerTable',
		url : '../../AdminUserWS/queryUserList.do',
		toolbar : toolbar,
	    columns : [
	      {field:'checkboxTemp',checkbox:'true'},
          {field:'nickName',title:'昵称'},
  		  {field:'username',title:'账号'},
		  {field:'enabled',title:'可用',
				formatter: function(value,row,index){
					if (value == 1){
						return '<input type="checkbox" class="js-switch" onChange="editUserEnabled(\''+row.username+'\')" checked/>';
					} else {
						return '<input type="checkbox" class="js-switch" onChange="editUserEnabled(\''+row.username+'\')" />';
					}
				}
		  },
  		  {field:'positionNameCn',title:'岗位'},
  		  {field:'possessorNameCn',title:'资源组'},
  		  {field:'addDate',title:'添加时间'}
	    ]
	});
}


function addUser(){
	
	xyzdialog({
		id : 'dialog_addUser',
		title : '新增用户',
		content: '../security/addUser.html',
	    fit:false,
	    width:'600px',
	    height:'400px',
	    buttons:[{
			text:'确定',
			handler:function(){
				addUserSubmit();
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
		url : "../../AdminUserWS/editUserEnabled.do",
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

function addUserSubmit(){
	
	var username = $("#usernameForm").val();
	var nickName = $("#nickNameForm").val();
	
	xyzAjax({
		url : "../../AdminUserWS/addUser.do",
		data : {
			username : username,
			nickName : nickName
		},
		success : function(data) {
			if(data.status==1){
				$('#userManagerTable').bootstrapTable('refresh');
				top.layer.msg('操作成功',{img:"check"});
				layer.close($("#dialog_editUser").data("index")); 
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
		url : "../../AdminUserWS/editUser.do",
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
	

