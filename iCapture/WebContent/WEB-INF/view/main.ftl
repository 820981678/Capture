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

    <div data-options="region:'west',split:true" title="菜单栏" style="width:300px;">
        <div class="easyui-accordion" data-options="fit:true,border:false">
            <div title="任务管理" selected="true">  
                <ul class="easyui-tree" id="iconlist" >
                    <li style="height:30px; line-height:30px;"><a href="javascript:addTab('tabId_1_loginInfo','分类','${webRoot}classify/index');">分类</a></li>  
                    <li style="height:30px; line-height:30px;"><a href="javascript:addTab('tabId_1_privilege','关键字','${webRoot}tblkey/index');">关键字</a></li>
                </ul>
            </div>
            <div title="标签分组管理" selected="true">  
                <ul class="easyui-tree" id="iconlist" >
                    <li style="height:30px; line-height:30px;"><a href="javascript:addTab('tabId_2_label','标签管理','${webRoot}label/index');">标签管理</a></li>  
                    <li style="height:30px; line-height:30px;"><a href="javascript:addTab('tabId_2_group','分组管理','${webRoot}group/index');">分组管理</a></li>  
                </ul>
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