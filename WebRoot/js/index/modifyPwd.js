$(document).ready(function(){
	
	//取消修改
	$(".dialog-btn button:eq(1)").click(function(){
		closeLayer();   		                
	});
	
	//确认修改
	$(".dialog-btn button:eq(0)").click(function(){
		var oldPwd = $.trim($(".m div:eq(1) input").val());	 //原登录密码
		var newPwd = $.trim($(".m div:eq(2) input").val());	//新登录密码
		var passwordStr = $.md5(oldPwd).substr(8,16);
		var newPasswordStr = $.md5(newPwd).substr(8,16);
		var pwdLength = newPwd.length;
		var confirmPwd = $.trim($(".m div:eq(3) input").val());	//确认码
		if(xyzIsNull(oldPwd)){
			layer.tips('请输入原登录密码', $(".m div:eq(1) input"),{tips:2});
			$(".m div:eq(1) input").focus();
			return;
		}else if(xyzIsNull(newPwd)){
			layer.tips('请输入新密码', $(".m div:eq(2) input"),{tips:2});
			$(".m div:eq(2) input").focus();
			return;
		}else if(xyzIsNull(confirmPwd)){
			layer.tips('请输入确认密码', $(".m div:eq(3) input"),{tips:2});
			$(".m div:eq(3) input").focus();
			return;
		}else if(oldPwd == newPwd){
			layer.tips('不能与原密码一致', $(".m div:eq(2) input"),{tips:2});
			$(".m div:eq(2) input").val("");	
			$(".m div:eq(2) input").focus();
			return;
		}else if(pwdLength<6 || pwdLength>18){
			layer.tips('密码长度应为6-18位', $(".m div:eq(2) input"),{tips:2});
			$(".m div:eq(2) input").val("");	
			$(".m div:eq(2) input").focus();
			return;
		}else if(newPwd != confirmPwd){
			layer.tips('与新密码不一致', $(".m div:eq(3) input"),{tips:2});
			$(".m div:eq(3) input").val("");	
			$(".m div:eq(3) input").focus();
			return;
		}
		
		$.ajax({
			url:'../../LoginWS/alterPassword.do',
			type:'POST',
			data:{
				username:getCookie("XZTRIP_LOGIN_NAME"),
				oldPassword:passwordStr,
				newPassword:newPasswordStr
			},
			async:false,
			dataType:'json',
			success:function(data){
				if(data.status == 1){
					parent.layer.alert('修改成功，请重新登录',function(index){
						deleteXZTripCookie();
						parent.location.href="../../login.html";
					});
				}else{
					layer.msg(data.msg, {icon: 2}); 
				}
			},
			error:function(data){
				parent.layer.msg('服务器出错，请联系管理员！', {icon: 2}); 
				closeLayer();
			}
		});
	});
});

//关闭当前弹出层
function closeLayer(){
	var index = parent.layer.getFrameIndex(window.name); 
	parent.layer.close(index);  
}
