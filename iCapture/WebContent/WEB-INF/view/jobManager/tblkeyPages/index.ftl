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
<body style="padding:0;margin:0;">
    <table id="tblkeyPage"></table>
    
    <script type="text/javascript">
    	//加载数据表格
        $('#tblkeyPage').datagrid({
		    url:'${webRoot}tblkeypage/query?id=${id}',
		    
		    //title: '所有文章',
		    //fitColumns: true, //设置自适应宽度
		    fit: true, //设置填充高度,自适应高度
		    sortName: 'id',
		    sortOrder: 'desc',
		    pagination: true, //设置分页
		    rownumbers: true, //显示行号
		    singleSelect: true,//设置为单选行
		    
		    columns:[[
		        {field:'title',title:'标题'},
		        {field:'count1',title:'匹配次数'},
		        {field:'pdate',title:'发布时间'},
		        {
		        	field:'a',
		        	title:'操作',
					formatter: function(val,rec){
						return '<a href="' + rec.item1 + '" target="_blank" >查看原文</a>';
					}		        	
		        }
		    ]],
		    
		    loadFilter: function(data){
		    	return data;
		    }
		    
		});
    </script>
    
</body>
</html>