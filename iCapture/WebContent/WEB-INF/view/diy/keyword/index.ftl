<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关键字管理</title>
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
<body class="easyui-layout" style="font">

    <table id="dg"></table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addKeyword()">添加</a>
    </div>
    
    <!-- 弹出窗口 -->
    <div id="addKeyword" class="easyui-dialog" style="width:400px;height:290px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="ftitle">添加关键字</div>
        <form id="addfm" class="fm" method="post" novalidate>
            <div class="fitem">
                <label>分组名称:</label>
                <input name="name" class="easyui-textbox" required="true">
                <label>分组名称:</label>
                <input name="name" class="easyui-textbox" required="true">
                <label>分组名称:</label>
                <input name="name" class="easyui-textbox" required="true">
                <label>分组名称:</label>
                <input name="name" class="easyui-textbox" required="true">
                <label>分组名称:</label>
                <input name="name" class="easyui-textbox" required="true">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveKeyword()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addKeyword').dialog('close')" style="width:90px">取消</a>
    </div>
    
</body>
    <script type="text/javascript" src="${webRoot}static/diy/keyword/keyword.js"></script>
</html>