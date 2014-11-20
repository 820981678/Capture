<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Capture</title>
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/jquery-easyui-1.3.2/themes/default/easyui.css"/>    
	<link rel="stylesheet" type="text/css" href="${webRoot}plug/jquery-easyui-1.3.2/themes/icon.css"/>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>    
	<script type="text/javascript" src="${webRoot}plug/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${webRoot}static/main.css"/>
	<script type="text/javascript" src="${webRoot}static/main.js"></script>
</head>
<body class="easyui-layout" style="font">
    <!-- 正左边panel -->  
    <div region="west" title="全部分类" split="true" style="width:180px;padding1:1px;overflow:hidden;">
        <div class="easyui-accordion" fit="true" border="false">
	            <ul class="easyui-tree" id="iconlist" >
	            	<#list data as classify>
	                	<li style="height:30px; line-height:30px;">
	                		<span><a href="javascript:addTab('tabId_${classify.name}','${classify.name}','${webRoot}common/index?groupid=${classify.id}');">${classify.name}</a></span>
	                	</li>  
	            	</#list>
	            </ul>
        </div>
    </div>  
    <!-- 正中间panel -->  
    <div region="center" >
        <div class="easyui-tabs" id="centerTab" fit="true" border="false">  
            
        </div>  
    </div>  
</body>
</html>