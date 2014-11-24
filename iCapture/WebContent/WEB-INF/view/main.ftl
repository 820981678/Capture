<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Capture</title>
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/jquery-easyui-1.3.2/themes/${themes}/easyui.css"/>    
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/jquery-easyui-1.3.2/themes/icon.css"/>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.3.2/jquery.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${webRoot}static/main.css"/>
	<script type="text/javascript" src="${webRoot}static/main.js"></script>
</head>
<body class="easyui-layout" style="font">
    <!-- 正上方panel -->  
    <div region="north" style="height:50px; border:none; background-color:#E0ECFF;" href="${webRoot}main/top"></div>
    <!-- 正左边panel -->  
    <div region="west" title="菜单栏" split="true" style="width:280px;padding1:1px;overflow:hidden;">  
        <div class="easyui-accordion" fit="true" border="false">
            <!-- selected -->  
            <div title="任务管理" selected="true">  
	            <ul class="easyui-tree" id="iconlist" >
	                <li style="height:30px; line-height:30px;"><a href="javascript:addTab('tabId_1_loginInfo','分类','${webRoot}classify/index');">分类</a></li>  
	                <li style="height:30px; line-height:30px;"><a href="javascript:addTab('tabId_1_privilege','关键字','${webRoot}tblkey/index');">关键字</a></li>
	                
	                <!--
	                <li style="height:30px; line-height:30px;"><a href="javascript:addTab('tabId_1_label','标签','${webRoot}classify/index');">标签</a></li>  
	                <li style="height:30px; line-height:30px;"><a href="javascript:addTab('tabId_1_group','分组','${webRoot}tblkey/index');">分组</a></li>  
	            	-->
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
    <!-- 正中间panel -->  
    <div region="center" >
        <div class="easyui-tabs" id="centerTab" fit="true" border="false">  
            <div title="欢迎页" style="padding:5px;">   
                <div style="margin-top:0px;">  
                    <h3>你好，欢迎来到Capture</h3>
                </div>  
            </div>  
        </div>  
    </div>  
    <!-- 正下方panel -->
    <!--
    <div region="south" style="height:30px;line-height:28px; border:none;" align="center">  
        <label>Hello EasyUI 1.3.2</label>  
    </div>  
    -->
</body>
</html>