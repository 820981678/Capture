<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户管理</title>
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/${themes}/easyui.css"/>    
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/icon.css"/>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.easyui.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/locale/easyui-lang-zh_CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${webRoot}static/main.css"/>
	<link rel="stylesheet" type="text/css" href="${webRoot}static/crud.css"/>
	<script type="text/javascript" src="${webRoot}static/main.js"></script>
	
	<script type="text/javascript"> var webRoot = ${webRoot}; </script>
	
</head>
<body class="easyui-layout" >

	<div data-options="region:'north'" style="height:150px; padding:10px; border:none;" >
		<div id="p" class="easyui-panel" title="填写您的筛选条件" data-options="collapsible:false" style="width:100%;height:auto;padding:10px;float:left;">
			<form id="select_form" class="fm" method="post" novalidate>
				<div class="fitem" style="width:300px; float:left;">
	                <label>账户名称:</label>
	                <input name="name" class="easyui-textbox">
	            </div>
	            <div class="fitem" style="width:300px; float:left;">
	                <label>账户职务:</label>
	                <input name="user_title" class="easyui-textbox">
	            </div>
	             <div class="fitem" style="width:300px; float:left;">
	                <label>账户状态:</label>
	                <select class="easyui-combobox" name="status" style="width:165px;">
	                	<option value="-1" selected>全部</option>
	                	<option value="1" >启用</option>
	                	<option value="0">禁用</option>
	                </select>
	            </div>
	            <div class="fitem" style="width:300px; float:left;">
	                <a id="select" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	            </div>
	        </form>
		</div>
	</div>
	
	<div data-options="region:'center',split:true" style="height:auto; border:none; padding:10px;">
   		<table id="dg"></table>
	</div>

    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除</a>
    </div>
    
    <!-- 弹出窗口 -->
    <div id="addUser" class="easyui-dialog" style="width:400px;height:340px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="ftitle">添加</div>
        <form id="addfm" class="fm" method="post" novalidate>
            <div class="fitem">
                <label>账户名称:</label>
                <input name="name" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>账户职务:</label>
                <input name="user_title" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>账户密码:</label>
                <input id="password" name="user_password" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>联系电话:</label>
                <input name="phone" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>报警电话:</label>
                <input name="warn_phone" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>账户状态:</label>
                <select class="easyui-combobox" name="status" style="width:165px;">
                	<option value="1" selected>启用</option>
                	<option value="0">禁用</option>
                </select>
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addUser').dialog('close')" style="width:90px">取消</a>
    </div>
    
</body>
    <script type="text/javascript" src="${webRoot}static/user/userManager.js"></script>
</html>