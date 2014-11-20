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
<body style="padding:0;margin:0;">

    <table id="dg"></table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">Add</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove</a>
    </div>
    
	<script type="text/javascript">
		//加载数据表格
        $('#dg').datagrid({
		    url:'${webRoot}common/query?groupid=${groupid}',
		    
		    //title: '所有文章',
		    //fitColumns: true, //设置自适应宽度
		    fit: true, //设置自适应高度
		    sortName: 'id',
		    sortOrder: 'desc',
		    pagination: true, //设置分页
		    rownumbers: true, //显示行号
		    singleSelect: true,//设置为单选行
		    
		    toolbar:"#toolbar",
		    
		    columns:[[
		        {field:'item0',title:'标题'},
		        {field:'item1',title:'原文链接'},
		        {field:'item2',title:'这是什么'},
		        {
		        	field:'a',
		        	title:'操作',
					formatter: function(val,rec){
						return '<a href="' + rec.item1 + '" target="_blank" >查看原文</a>';
					}		        	
		        }
		    ]],
		    
		});
			
	</script>
</body>
</html>