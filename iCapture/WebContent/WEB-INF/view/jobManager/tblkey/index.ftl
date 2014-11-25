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
</head>
<body class="easyui-layout">
    <!-- 正左边panel -->
    <div data-options="region:'west',split:true" title="全部关键字" style="width:180px;" border="false">  
        <div class="easyui-accordion" fit="true" border="false">
	            <ul class="easyui-tree" id="iconlist" >
	            	<#list data as tblkey>
	                	<li style="height:30px; line-height:30px;">
	                		<span><a href="javascript:addTab('tabId_${tblkey.name}','${tblkey.name}','${webRoot}tblkeypage/index?id=${tblkey.id}');">${tblkey.name}</a></span>
	                	</li>
	            	</#list>
	            </ul>
        </div>
    </div>  
    <!-- 正中间panel -->  
    <div data-options="region:'center'" border="false">
        <div id="centerTab" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
            
        </div>  
    </div>  
</body>
</html>