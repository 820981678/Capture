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

	<div data-options="region:'north'" style="height:150px; padding:10px; border:none;" >
		<div id="p" class="easyui-panel" title="填写您的筛选条件" data-options="collapsible:false" style="width:100%;height:auto;padding:10px;float:left;">
			<form id="select_form" class="fm" method="post" novalidate>
	            <div class="fitem" style="width:300px; float:left;">
	                <label>关键字:</label>
	                <input name="name" class="easyui-textbox">
	            </div>
	            <div class="fitem" style="width:300px; float:left;">
	                <label>正负面:</label>
	                <select class="easyui-combobox" name="wtype" style="width:165px;">
	                	<option selected>全部</option>
				        <option value="0">不定</option>
	                	<option value="1">正面</option>
				        <option value="2">负面</option>
	                </select>
	            </div>
	            <div class="fitem" style="width:300px; float:left;">
	                <label>处理方式:</label>
	                <select class="easyui-combobox" name="stype" style="width:165px;">
                		<option selected>全部</option>
				        <option value="0">全词匹配</option>
	                	<option value="1">分隔后全部匹配</option>
				        <option value="2">分隔后匹配任何一个</option>
	                </select>
	            </div>
	            <div class="fitem" style="width:300px; float:left;">
	                <label>状态:</label>
	                <select class="easyui-combobox" name="status" style="width:165px;">
	                	<option selected>全部</option>
				        <option value="0">有效</option>
	                	<option value="1">无效</option>
	                </select>
	            </div>
	            <div class="fitem" style="width:300px; float:left;">
	            	<label>权重:</label>
	            	<select id="site_rate_select" class="easyui-combobox" name="site_rate" style="width:165px;">
	            	</select>
	            </div>
	        </form>
	        <div class="fitem" style="width:300px; float:left;">
	        	<a id="select" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	        </div>
		</div>
	</div>

    <div data-options="region:'center',split:true" style="height:auto; border:none; padding:10px;">
   		<table id="dg"></table>
	</div>
	
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addKeyword()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editKeyword()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteKeyword()">删除</a>
    </div>
    
    <!-- 弹出窗口 -->
    <div id="addKeyword" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="ftitle">添加关键字</div>
        <form id="addfm" class="fm" method="post" novalidate>
            <div class="fitem">
                <label>关键字:</label>
                <input name="name" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>正负面:</label>
                <select class="easyui-combobox" name="wtype" style="width:165px;">
			        <option value="0" selected>不定</option>
                	<option value="1">正面</option>
			        <option value="2">负面</option>
                </select>
            </div>
            <div class="fitem">
                <label>处理方式:</label>
                <select class="easyui-combobox" name="stype" style="width:165px;">
			        <option value="0" >全词匹配</option>
                	<option value="1" selected>分隔后全部匹配</option>
			        <option value="2">分隔后匹配任何一个</option>
                </select>
            </div>
            <div class="fitem">
                <label>状态:</label>
                <select class="easyui-combobox" name="status" style="width:165px;">
			        <option value="0" selected>有效</option>
                	<option value="1">无效</option>
                </select>
            </div>
            <div class="fitem">
                <label>默认分组:</label>
                <select id="groupid" class="easyui-combobox" name="catalog_id" required="true" style="width:165px;">
                </select>
            </div>
            <div class="fitem">
            	<label>权重:</label>
            	<select id="site_rate_form" class="easyui-combobox" name="site_rate" required="true" style="width:165px;">
            	</select>
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveKeyword()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addKeyword').dialog('close')" style="width:90px">取消</a>
    </div>
    
</body>
    <script type="text/javascript" src="${webRoot}static/diy/keyword.js"></script>
</html>