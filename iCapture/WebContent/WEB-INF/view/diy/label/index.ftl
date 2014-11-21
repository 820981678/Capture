<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签管理</title>
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/jquery-easyui-1.4.1/themes/default/easyui.css"/>    
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/jquery-easyui-1.4.1/themes/icon.css"/>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.4.1/jquery.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${webRoot}static/main.css"/>
	<link rel="stylesheet" type="text/css" href="${webRoot}static/crud.css"/>
	<script type="text/javascript" src="${webRoot}static/main.js"></script>
	
	<script type="text/javascript"> var webRoot = ${webRoot}; </script>
	
</head>
<body class="easyui-layout" style="font">

    <div region="west" style="width:420px;padding1:1px;overflow:hidden;border:none;">
            <table id="dg"></table>
		    <div id="toolbar">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addLabel()">添加标签</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editLabel()">修改标签</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="deleteLabel()">删除标签</a>
		    </div>
    </div>  
    <!-- 正中间panel -->  
    <div region="center" >
        <div class="easyui-tabs" id="centerTab" fit="true" border="false">  
            
        </div>  
    </div>
    
    <!-- 弹出窗口 -->
    <div id="addLabel" class="easyui-dialog" style="width:400px;height:190px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="ftitle">添加标签</div>
        <form id="addfm" class="fm" method="post" novalidate>
            <div class="fitem">
                <label>标签名称:</label>
                <input name="name" class="easyui-textbox" required="true">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveLabel()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addLabel').dialog('close')" style="width:90px">取消</a>
    </div>
    
</body>
    <script type="text/javascript" src="${webRoot}static/diy/label/label_index.js"></script>
</html>