<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>舆情级别配置</title>
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

    <table id="dg"></table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addWarn()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editWarn()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteWarn()">删除</a>
    </div>
    
    <!-- 弹出窗口 -->
    <div id="addWarn" class="easyui-dialog" style="width:400px;height:340px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="ftitle">添加</div>
        <form id="addfm" class="fm" method="post" novalidate>
            <div class="fitem">
                <label>舆情级别:</label>
                <input name="name" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>权重下线:</label>
                <input name="min_rate" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>权重上线:</label>
                <input name="max_rate" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>处理描述:</label>
                <input name="description" class="easyui-textbox">
            </div>
            <div class="fitem">
                <label>发送信息:</label>
                <select class="easyui-combobox" name="send_msg" style="width:165px;">
                	<option value="1" selected>发送</option>
                	<option value="0">不发送</option>
                </select>
            </div>
            <div class="fitem">
                <label>人工处理:</label>
                <select class="easyui-combobox" name="need_handle" style="width:165px;">
                	<option value="1" selected>处理</option>
                	<option value="0">不处理</option>
                </select>
            </div>
            <div class="fitem">
                <label>短信模板:</label>
                <input name="msg_tpl" class="easyui-textbox" required="true">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveWarn()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addWarn').dialog('close')" style="width:90px">取消</a>
    </div>
    
</body>
    <script type="text/javascript" src="${webRoot}static/diy/warnLevel.js"></script>
</html>