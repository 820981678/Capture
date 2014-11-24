<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签管理</title>
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/${themes}/easyui.css"/>    
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/icon.css"/>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.easyui.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/locale/easyui-lang-zh_CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${webRoot}static/main.css"/>
	<link rel="stylesheet" type="text/css" href="${webRoot}static/crud.css"/>
	<script type="text/javascript" src="${webRoot}static/main.js"></script>
	
	<script type="text/javascript"> 
		var webRoot = ${webRoot};
		var labelid = ${labelid};
	</script>
	
</head>
<body class="easyui-layout" style="font">

	<table id="dg"></table>
	
	<!-- 右键菜单 -->
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div iconCls="icon-add" onclick="addLabel()">添加标签</div>
		<div iconCls="icon-add" onclick="addGroup()">添加分组</div>
	    <div>退出</div>
	</div>
	
	<!-- 打标签弹出窗口 -->
	<div id="playLabel" class="easyui-dialog" style="width:410px;height:300px;" closed="true" buttons="#dlg-buttons" data-options="modal:true">
	    <table id="labelTab"></table>
	</div>
	<div id="dlg-buttons">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="savePlayLabel()" style="width:90px">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#playLabel').dialog('close')" style="width:90px">取消</a>
	</div>
    
</body>
    <script type="text/javascript" src="${webRoot}static/diy/label/toCommonPage.js"></script>
</html>