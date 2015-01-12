<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>重庆市民政局舆情管理系统</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.min.js"></script>
  <!--  
  <link rel="alternate icon" type="image/png" href="assets/i/favicon.png">
  -->
  <link rel="stylesheet" href="../plug/amaze/css/amazeui.min.css"/>
  <style>
    .header {
      text-align: center;
    }
    .header h1 {
      font-size: 200%;
      color: #333;
      margin-top: 30px;
    }
    .header p {
      font-size: 14px;
    }
  </style>
</head>
<body>
<div class="header">
  <div class="am-g">
    <h1>重庆市民政局舆情管理系统</h1>
    <p>做困难群众贴心人<br/>建立人们群众满意民政</p>
  </div>
  <hr />
</div>
<div class="am-g">
  <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
    <h3>登录</h3>
    <hr>
    <!--
    <div class="am-btn-group">
      <a href="#" class="am-btn am-btn-secondary am-btn-sm"><i class="am-icon-github am-icon-sm"></i> Github</a>
      <a href="#" class="am-btn am-btn-success am-btn-sm"><i class="am-icon-google-plus-square am-icon-sm"></i> Google+</a>
      <a href="#" class="am-btn am-btn-primary am-btn-sm"><i class="am-icon-stack-overflow am-icon-sm"></i> stackOverflow</a>
    </div>
    <br>
    
    <br>
    -->

    <form id="login_form" method="post" class="am-form">
      <label for="email">账号:</label>
      <input type="text" name="name" id="name" value="admin">
      <br>
      <label for="password">密码:</label>
      <input type="password" name="user_password" id="password" value="admin">
      <br>
      <label for="remember-me">
        <input id="remember-me" type="checkbox">
        	记住密码
      </label>
      <br />
      <div class="am-cf">
        <input id="login" type="button" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl">
        <input type="button" value="忘记密码 ^_^? " class="am-btn am-btn-default am-btn-sm am-fr">
      </div>
    </form>
    <hr>
    <p>© 2014 重庆市民政局</p>
  </div>
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
	
</script>
</html>