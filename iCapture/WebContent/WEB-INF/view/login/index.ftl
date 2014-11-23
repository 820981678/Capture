<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Capture Manager</title>
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/${themes}/easyui.css"/>    
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/icon.css"/>
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.easyui.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="win" class="easyui-window" title="Login" style="width:300px;height:180px;">
		<form id="login_form" style="padding:10px 20px 10px 40px;" action="${webRoot}login/doLogin" method="post">
			<p>Name: <input type="text" name="name" value="admin"></p>
			<p>Pass: <input type="password" name="password" value="admin"></p>
			<!--
			<p><input type="checkbox" name="isSave" value="true" />保存一周 </p>
			-->
			<div style="padding:5px;text-align:center;">
				<a id="login" class="easyui-linkbutton" icon="icon-ok">Login</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$("#login").click(function(){
			$.ajax({
				url: '${webRoot}login/doLogin',
				type: 'post',
				data: $("#login_form").serialize(),
				dataType: 'json',
				success: function(data){
					if(data.code == 0){
						location.href = '${webRoot}';
					} else {
						$.messager.alert('提示信息','用户名或密码错误!','error');
					}
				}
			});
		});
	});
	
	$('#win').window('open');
</script>
</html>