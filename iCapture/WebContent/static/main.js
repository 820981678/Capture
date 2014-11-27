/**
 * tab的添加跳转
 */

$(function(){
	$("#centerTab").tabs({
//		//注册选中事件,用于选中刷新tab
//	    onSelect: function(title,index){
//	    	alert(title);
//	    	refreshTab({"tabTitle":title});
//	    }
	});
});

/**  
 * 创建新选项卡  
 * @param tabId    选项卡id  
 * @param title    选项卡标题  
 * @param url      选项卡远程调用路径  
 */  
function addTab(tabId,title,url){
    //如果当前id的tab不存在则创建一个tab  
    if($("#"+tabId).html()==null){  
        var name = 'iframe_'+tabId;  
        $('#centerTab').tabs('add',{  
            title: title,           
            closable:true,  
            cache : false,
            //注：使用iframe即可防止同一个页面出现js和css冲突的问题  
            content : '<iframe name="'+name+'"id="'+tabId+'"src="'+url+'" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>', 
        });  
    } else {
    	var tt = $("#centerTab");
    	tt.tabs('select', title);
    	refreshTab({"tabTitle":title,"url":url});
    }
}

/**     
 * 刷新tab 
 * @cfg  
 *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
 *如果tabTitle为空，则默认刷新当前选中的tab 
 *如果url为空，则默认以原来的url进行reload 
 */  
function refreshTab(cfg){  
    var refresh_tab = cfg.tabTitle?$('#centerTab').tabs('getTab',cfg.tabTitle):$('#centerTab').tabs('getSelected');  
    if(refresh_tab && refresh_tab.find('iframe').length > 0){  
	    var _refresh_ifram = refresh_tab.find('iframe')[0];  
	    var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;
	    //_refresh_ifram.src = refresh_url;  
	    _refresh_ifram.contentWindow.location.href=refresh_url;
    }
}

/**
 * datagrid数据过滤器
 */
function loadfilter(data){
	if(data.code != 0){
		$.messager.alert("提示信息",data.message);
	} else {
		return data;
	}
}