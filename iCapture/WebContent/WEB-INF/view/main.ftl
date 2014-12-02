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

    <div data-options="region:'north'" style="height:50px" href="${webRoot}main/top"></div>

    <div data-options="region:'west',split:true" title="菜单栏" style="width:200px;">
        <div class="easyui-accordion" data-options="fit:true,border:false">
            
            <#list menus as m>
        	<div title="${m.name}">
        		<ul class="easyui-tree" id="iconlist" >
        		<#list m.menuList as menu>
        			<li style="height:30px; line-height:30px;">
        				<a href="javascript:addTab('tabId_1_${menu.tabName}','${menu.name}','${webRoot}${menu.url}');">
        					${menu.name}
        				</a>
        			</li>
        		</#list>
            	</ul>
        	</div>
            </#list>
            
            <!-- 用于占位 -->
            <div title="      ">  
            </div>
        </div>
    </div>

    <div data-options="region:'center'">
    	
        <div id="centerTab" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
            
        </div>
                
    </div>

    <!-- 正下方panel 
    <div data-options="region:'south',split:true" style="height:50px;"></div>
    -->       
</body>
	<script type="text/javascript">
		$(function(){
			addTab('tabId_selete','欢迎页','${webRoot}main/select');
		});
	</script>
</html>