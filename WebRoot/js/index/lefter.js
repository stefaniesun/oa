$(document).ready(function(){
	var username = getCookie("XZTRIP_LOGIN_NAME");
	$("#username").html(username);
	
	$("#modifyPwd").click(function(){
		parent.layer.open({
			title:'修改密码',
		    type: 2,
		    area: ['600px', '430px'],
		    fix: true, //不固定
		    maxmin: false,
		    closeBtn: true,
		    shadeClose:true,
		    content: ['../../index/modifyPassword.html', 'no']  	
		});
	});
	
	$("#logoutLink").click(function(){
		deleteXZTripCookie();
		location.href="../login.html";
	});
	
	$("#logoutHeaderLink").click(function(){
		deleteXZTripCookie();
		location.href="../login.html";
	});
});