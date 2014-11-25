<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Capture</title>
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/${themes}/easyui.css"/>    
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/${easyui}/themes/icon.css"/>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/jquery.easyui.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/${easyui}/locale/easyui-lang-zh_CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${webRoot}static/main.css"/>
	<script type="text/javascript" src="${webRoot}static/main.js"></script>
	<script type="text/javascript" src="${webRoot}static/select.js"></script>
	
	<script type="text/javascript"> var webRoot = ${webRoot}; </script>
	<style>
		ul{
            list-style: none;
            margin: 0;
            padding: 0;
        }
        li{
            line-height: 30px;
            float: left;
            width: auto;
            margin-left: 20px;
        }
        input{
        	vertical-align:middle;
        }
	</style>
</head>
<body>
	<!-- 标签筛选 -->
	<div id="p" class="easyui-panel" title="选取要筛选的标签,支持多选" data-options="collapsible:false" style="width:100%;height:auto;padding:10px;float:left;">
        <ul>
            <li><input type="checkbox" />easyui</li>
            <li><input type="checkbox" />easyui</li>
            <li><input type="checkbox" />using</li>
            <li><input type="checkbox" />complete</li>
            <li><input type="checkbox" />save</li>
            <li><input type="checkbox" />powerful</li>
            <li><input type="checkbox" />easyui</li>
            <li><input type="checkbox" />easyui</li>
            <li><input type="checkbox" />using</li>
            <li><input type="checkbox" />complete</li>
            <li><input type="checkbox" />save</li>
            <li><input type="checkbox" />powerful</li>
            <li><input type="checkbox" />easyui</li>
            <li><input type="checkbox" />easyui</li>
            <li><input type="checkbox" />using</li>
            <li><input type="checkbox" />complete</li>
            <li><input type="checkbox" />save</li>
            <li><input type="checkbox" />powerful</li>
        </ul>
    </div>
    <div style="width:100%; height:5px;"></div>
    <!-- 分组筛选 -->
    <div id="p" class="easyui-panel" title="选取要筛选的分组" data-options="collapsible:false" style="width:100%;height:auto;padding:10px;float:left;">
        <ul>
            <li><input type="radio" />easyui</li>
            <li><input type="radio" />easyui</li>
            <li><input type="radio" />using</li>
            <li><input type="radio" />complete</li>
            <li><input type="radio" />save</li>
            <li><input type="radio" />powerful</li>
            <li><input type="radio" />easyui</li>
            <li><input type="radio" />easyui</li>
            <li><input type="radio" />using</li>
            <li><input type="radio" />complete</li>
            <li><input type="radio" />save</li>
            <li><input type="radio" />powerful</li>
            <li><input type="radio" />easyui</li>
            <li><input type="radio" />easyui</li>
            <li><input type="radio" />using</li>
            <li><input type="radio" />complete</li>
            <li><input type="radio" />save</li>
            <li><input type="radio" />powerful</li>
        </ul>
    </div>
    
    <div class="easyui-panel" style="width:100%;height:0px;" border="false">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" style="width:50%;padding:10px">
            </div>
        </div>
    </div>
    
    <!-- 查询 -->
    <div style="width:100%; height:10px;"></div>
	    <div style="width:100%; height:26px; text-align:center;">
	    	<a id="select" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	    </div>
    <div style="width:100%; height:10px;"></div>
    
    <!-- 查询结果 -->
    <div id="centerTab" class="easyui-tabs" style="height:340px;">
	    
	</div>
	
</body>
</html>