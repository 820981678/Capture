<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>舆情处理人</title>
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/${themes}/easyui.css"/>    
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/icon.css"/>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.easyui.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/locale/easyui-lang-zh_CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${webRoot}static/main.css"/>
	<link rel="stylesheet" type="text/css" href="${webRoot}static/crud.css"/>
	<script type="text/javascript" src="${webRoot}static/main.js"></script>
	
	<script type="text/javascript"> var webRoot = ${webRoot}; </script>
	
	<style>
		ul{
			list-style:none; margin:0px; padding:0px;
		}
		li{
			width:100%; height:30px; padding:0px; margin:0px; border-bottom:1px solid #E0ECFF;
		}
		li .d1{
			width:230px; height:30px; line-height:30px; float:left; padding-left:10px;
		}
		li .d2{
			height:30px; line-height:30px; float:left;
		}
	</style>
	
</head>
<body class="easyui-layout">

	<div data-options="region:'west',split:false" style="width:300px; height:100%; padding:10px;" border="false"> 
		<div class="easyui-panel" title="全部舆情级别" data-options="collapsible:false" style="width:100%;height:100%;padding:0px;float:left;">
			<ul id="warnLevel_sum">
			</ul>
		</div>
    </div>
	 <div data-options="region:'center'" border="false" style=" padding:10px;">
	 	<div class="easyui-panel" title="舆情处理人" data-options="collapsible:false" style="width:100%;height:100%;float:left;">
			<table id="dg"></table>
		</div>
	</div>

    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddWarnUser()">配置处理人</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteWarnUser()">删除处理人</a>
    </div>
    
    <!-- 配置处理人窗口 -->
	<div id="warnUserWindow" class="easyui-dialog" style="width:500px;height:300px;" closed="true" buttons="#dlg-buttons" data-options="modal:true">
	    <table id="warnUserTab" border="false"></table>
	</div>
	<div id="dlg-buttons">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveWarnUser()" style="width:90px">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#warnUserWindow').dialog('close')" style="width:90px">取消</a>
	</div>
    
</body>
    <script type="text/javascript" src="${webRoot}static/diy/warnUser.js"></script>
</html>