<!-- 右键菜单 -->
<div id="mm" class="easyui-menu" style="width:120px;">
	<div iconCls="icon-add" onclick="addLabel()">添加标签</div>
	<div iconCls="icon-add" onclick="addGroup()">添加分组</div>
    <div>退出</div>
</div>

<!-- 打标签弹出窗口 -->
<div id="playLabel" class="easyui-dialog" style="width:410px;height:300px;" closed="true" buttons="#dlg-buttons">
    <table id="labelTab"></table>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="savePlayLabel()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#playLabel').dialog('close')" style="width:90px">取消</a>
</div>
