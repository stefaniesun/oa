function xyzgrid(d){
	
	//构建toolbar
	{
		var toolbar=d.toolbar;
		var toolbarHtml="<div id='toolbar'>";
		for(var i=0;i<toolbar.length;i++){
			if(xyzIsNull(toolbar[i].text)){
				return;
			}
			toolbarHtml+="<button style='margin-right:5px' onclick='"+toolbar[i].handler+"' class='btn btn-"+toolbar[i].iconType+" ' type='button'><i class='fa "+toolbar[i].icon+"'></i>&nbsp;"+toolbar[i].text+"</button>";
		}
		toolbarHtml+="</div>";
		$("#userManagerTable").before(toolbarHtml);
		
	}
	
	
	$("#"+d.table).bootstrapTable({
		
		url : d.url==undefined?undefined:d.url,//表格数据请求URL
		columns : d.columns,//表格显示列
		toolbar : '#toolbar',//一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
		pagination : true,//设置为 true 会在表格底部显示 分页条
		sidePagination : 'server',//分页模式，服务端进行分页
		pageList : d.pageList==undefined?[5,10,15,30,50]:d.pageList,//分页
		iconsPrefix : 'glyphicon',//定义字体库
		showRefresh : true,//是否显示 刷新按钮
		showColumns : true,//是否显示 内容列下拉框
		cardView : false,//设置为 true将显示card视图，适用于移动设备。否则为table试图，适用于pc
		undefinedText : '',//当数据为 undefined 时显示的字符
		striped : true,//设置为 true 会有隔行变色效果
		iconsPrefix : 'fa',//定义字体库
		detailView : false,//设置为 true 可以显示详细页面模式。
		paginationVAlign :'bottom',
		paginationDetailHAlign : 'left',
		singleSelect : 'true',//设置True 将禁止多选
		smartDisplay:'true',//根据浏览设备自动切换浏览模式
		//查询条件
		queryParams : d.queryParams==undefined?function(params){
			params.page=parseInt(params.offset/params.limit)+parseInt(1);//分页首页页码
			params.rows=params.limit;//分页页面数据条数
	    	return params;
		}:d.queryParams,
		//返回数据回调
		responseHandler : d.responseHandler==undefined?function(data){
			if(data.status==1){
				return  data.content;
			}else{
				top.window.layer.alert(data.msg);
			}
		}:d.responseHandler,
		//表格dom重绘
		onResetView : d.onResetView==undefined?function(data){
			//初始化IOS滑动button效果
			var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
			elems.forEach(function(elem) {
			  var switchery = new Switchery(elem, { size: 'small'});
			});
		}:d.onResetView
		/*
		pageNumber : d.pageNumber==undefined?1:d.pageNumber,
		pageSize : d.pageSize==undefined?10:d.pageSize,
		title : d.title==undefined?'数据表':d.title,
		collapsible : d.collapsible==undefined?true:d.collapsible,//默认有折叠按钮
		collapsed : d.collapsed==undefined?false:d.collapsed,//定义初始化的时候是否折叠
		url : d.url==undefined?undefined:d.url,
		data : d.data==undefined?undefined:d.data,
		toolbar : d.toolbar==undefined?'':$.isArray(d.toolbar)?d.toolbar:"#"+d.toolbar,
		loadFilter : d.loadFilter==undefined?function(data){
				if(data.status==1){
					return data.content;
				}else{
					top.$.messager.alert("警告",data.msg,"warning");
					return {total : 0 , rows : []};
				}
			}:d.loadFilter,
		nowrap : d.nowrap==undefined?true:d.nowrap,//是否不换行，true不换行
		border : d.border==undefined?true:d.border,//边框
		height : d.height==undefined?xyzGetHeight($("#"+d.table).position().top+42):d.height,//高度
		singleSelect : d.singleSelect==undefined?true:d.singleSelect,//单选
		fitColumns : d.fitColumns==undefined?false:d.fitColumns,//自适应
		striped : d.striped==undefined?false:d.striped,//斑马线
		pagination : d.pagination==undefined?true:d.pagination,//分页
		pagePosition : d.pagePosition==undefined?"top":d.pagePosition,//分页条文职
		rownumbers : d.rownumbers==undefined?true:d.rownumbers,//行号
		checkOnSelect : d.checkOnSelect==undefined?true:d.checkOnSelect,//点行选框
		selectOnCheck : d.selectOnCheck==undefined?true:d.selectOnCheck,//点框选行
		pageList : d.pageList==undefined?[5,10,15,30,50]:d.pageList,//分页
		idField : d.idField==undefined?'numberCode':d.idField,
		columns : d.columns,
		frozenColumns : d.frozenColumns==undefined?undefined:d.frozenColumns,//冻结列
		queryParams : d.queryParams==undefined?undefined:d.queryParams,//查询条件
		onCheck : d.onCheck==undefined?undefined:d.onCheck,
		tools:[{
			iconCls:'icon-add',
			handler:function(e){
				e.preventDefault();
				var tableId = d.table;
				if($('#columnMenuAdd_'+tableId).attr("id")){
					$('#columnMenuAdd_'+tableId).menu("destroy");
				}
				$('body').append('<div id="columnMenuAdd_'+tableId+'"/>');
				var cmenu = $('#columnMenuAdd_'+tableId);
				cmenu.menu({
					onClick: function(item){
						$('#'+tableId).datagrid('showColumn', item.name);
						cmenu.menu("destroy");
					}
				});
				var fields = $('#'+tableId).datagrid('getColumnFields');
				for(var i=1; i<fields.length; i++){
					var field = fields[i];
					var col = $('#'+tableId).datagrid('getColumnOption', field);
					if(col.hidden==true){
						cmenu.menu('appendItem', {
							text: col.title,
							name: field,
							iconCls: 'icon-empty'
						});
					}
				}
				$('#columnMenuAdd_'+tableId).menu('show', {    
					left:e.pageX,
					top:e.pageY
				});
			}
		},{
			iconCls:'icon-remove',
			handler:function(e){
				e.preventDefault();
				var tableId = d.table;
				if($('#columnMenuSub_'+tableId).attr("id")){
					$('#columnMenuSub_'+tableId).menu("destroy");
				}
				$('body').append('<div id="columnMenuSub_'+tableId+'"/>');
				var cmenu = $('#columnMenuSub_'+tableId);
				cmenu.menu({
					onClick: function(item){
						$('#'+tableId).datagrid('hideColumn', item.name);
						cmenu.menu("destroy");
					}
				});
				var fields = $('#'+tableId).datagrid('getColumnFields');
				for(var i=1; i<fields.length; i++){
					var field = fields[i];
					var col = $('#'+tableId).datagrid('getColumnOption', field);
					if(!(col.hidden==true)){
						cmenu.menu('appendItem', {
							text: col.title,
							name: field,
							iconCls:'icon-ok'
						});
					}
				}
				$('#columnMenuSub_'+tableId).menu('show', {    
					left:e.pageX,
					top:e.pageY 
				});
			}
		}],
		onLoadSuccess : d.onLoadSuccess==undefined?undefined:d.onLoadSuccess,
		onBeforeLoad : function(param){
			$("#"+d.table).datagrid("clearChecked");
			$("#"+d.table).datagrid("clearSelections");
		},
		onSelect : d.onSelect==undefined?function(rowIndex,rowData){
			if(rowData.remark!=undefined){
				top.$("#remarkTop").text(rowData.remark);
			}
			if(!$("#remarkTopDialogDivOrder",top.document).is(":hidden")){
				top.$('#remarkTopDialogDivOrder').window("close");
			}
		}:d.onSelect,
		onHeaderContextMenu : function(e, field){
			e.preventDefault();
			var tableId = d.table;
			if($('#columnMenu_'+tableId).attr("id")){
				$('#columnMenu_'+tableId).menu("destroy");
			}
			$('body').append('<div id="columnMenu_'+tableId+'"/>');
			var cmenu = $('#columnMenu_'+tableId);
			cmenu.menu({
				onClick: function(item){
					if(item.iconCls == 'icon-ok'){
						$('#'+tableId).datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					}else {
						$('#'+tableId).datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
					}
					cmenu.menu("destroy");
				}
			});
			var fields = $('#'+tableId).datagrid('getColumnFields');
			for(var i=1; i<fields.length; i++){
				var field = fields[i];
				//console.log("field="+field);
				var col = $('#'+tableId).datagrid('getColumnOption', field);
				if(col.hidden==true){
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-empty'
					});
				}else{
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-ok'
					});
				}
			}
			$('#columnMenu_'+tableId).menu('show',{
				left:e.pageX,
				top:e.pageY
			});
		},
		onClickCell:d.onClickCell==undefined?undefined:d.onClickCell
	*/});
};


function xyzdialog(d){
	
	layer.open({
		  id : d.id==undefined?'':d.id,
		  type : 1,
		  shift : 3,
		  title : d.title==undefined?'对话框':d.title,
		  closeBtn: 1, //不显示关闭按钮
		  area: [d.width==undefined?'auto':d.width, d.height==undefined?'auto':d.height],
		  shadeClose: true, //开启遮罩关闭
		  fix: false, //不固定
		  content: d.content==undefined?'':d.content,
		    success: function(layero, index){
		    	//关联索引
		    	$("#"+d.id).data("index",index);
		    	//生成html
		    	if($("d.content$=html")){
		    		$.get(d.content,function(data){
			    		$(".layui-layer-content").html(data);
		    		});
		    	}
		    	//生成底部button
				if(d.buttons!=undefined){
		    		var footButtons="<div class='dialog-btn'>";
		    		if(d.buttons[0]!=undefined){
		    			d.buttons[0].text=d.buttons[0].text==undefined?"确定":d.buttons[0].text;
		    			footButtons+= "<button id='submitBtn' type='button' class='btn btn-w-m btn-primary'>"+d.buttons[0].text+"</button>";
		    		}
		    		 if(d.buttons[1]!=undefined){
		    			d.buttons[1].text=d.buttons[1].text==undefined?"取消":d.buttons[1].text;
		    			footButtons+= "<button id='cancelBtn' type='button' class='btn btn-w-m btn-white'>"+d.buttons[1].text+"</button>";
		    		}
		    		 footButtons+="</div>";
		    			
		    		$(layero).append(footButtons);
		    		 $(layero).on('click','#submitBtn',d.buttons[0].handler);
		    		 $(layero).on('click','#cancelBtn',d.buttons[1].handler==undefined?function(){
		    			 layer.close(index);
		    		 }:d.buttons[1].handler);
		    	}
				//执行加载完成后回调
				if(d.onLoad!=undefined){
					d.onLoad.call();
				}
		     }
		});

	
	
	/*parent.layer.open({
		id:d.id==undefined?'dialog':d.id,
		title:d.title==undefined?'对话框':d.title,
	    type: 2,
	    area: [d.width==undefined?'auto':d.width, d.height==undefined?'auto':d.height],
	    fix: true, //不固定
	    maxmin: false,
	    closeBtn: true,
	    shadeClose:true,
	    content: [d.content, 'no'],
	    success: function(layero, index){
			if(d.buttons!=undefined){
	    		var footButtons="<div class='dialog-btn'>";
	    		if(d.buttons[0]!=undefined){
	    			d.buttons[0].text=d.buttons[0].text==undefined?"确定":d.buttons[0].text;
	    			footButtons+= "<button id='submitBtn' type='button' class='btn btn-w-m btn-primary'>"+d.buttons[0].text+"</button>";
	    		}
	    		 if(d.buttons[1]!=undefined){
	    			d.buttons[1].text=d.buttons[1].text==undefined?"取消":d.buttons[1].text;
	    			footButtons+= "<button id='cancelBtn' type='button' class='btn btn-w-m btn-white'>"+d.buttons[1].text+"</button>";
	    		}
	    		 footButtons+="</div>";
	    			
	    		$(layero).append(footButtons);
	    		 $(layero).on('click','#submitBtn',d.buttons[0].handler);
	    		 $(layero).on('click','#cancelBtn',d.buttons[1].handler==undefined?function(){
	    			 parent.layer.close(index);
	    		 }:d.buttons[1].handler);
	    	}
			
			
			 callback.call(this,$(layero));
			//表单验证
	     }
	});*/
	
	

	
	/*$("body").append("<div id='"+d.dialog+"'></div>");
	$("#"+d.dialog).dialog({
		title : d.title==undefined?'对话框':d.title,
		height : d.height==undefined?200:d.height,
		width : d.width==undefined?300:d.width,
		modal : d.modal==undefined?true:d.modal,//锁住当前页面
		closable : d.closable==undefined?false:d.closable,//关闭
	    cache : d.cache==undefined?false:d.cache,//缓存
	    fit : d.fit==undefined?true:d.fit,//全屏
	    href : d.href==undefined?undefined:d.href,
	    content : d.content==undefined?undefined:d.content,
	    buttons : d.buttons==undefined?undefined:d.buttons,
		onLoad : d.onLoad==undefined?undefined:d.onLoad,
		onOpen : d.onOpen==undefined?undefined:d.onOpen,
		resizable : d.resizable==undefined?false:d.resizable,
		draggable : d.draggable==undefined?false:d.draggable
	});
	//set center
	if(d.center != undefined || d.center == true){
		$("#"+d.dialog).dialog("center");
	}*/
};//cdialog end




/*
 * combobox必须
 * url必须 
 * lazy可选:延迟加载,默认延迟
 * valueField可选
 * textField可选
 */
function xyzCombobox(c){
	
	
	$("#"+c.combobox).bsSuggest('init', {
        /*url: "/rest/sys/getuserlist?keyword=",
        effectiveFields: ["userName", "email"],
        searchFields: [ "shortAccount"],
        effectiveFieldsAlias:{userName: "姓名"},*/
		getDataMethod: "url",  
		url : c.url,
		idField: 'numberCode',
		keyField : 'nameCn',
    }).on('onDataRequestSuccess', function (e, result) {
    	result=result.content;
        console.log(result);
    }).on('onSetSelectValue', function (e, keyword, data) {
        console.log('onSetSelectValue: ');
    }).on('onUnsetSelectValue', function () {
        console.log('onUnsetSelectValue');
    });
	
	/*var xyzComboboxData = {};
	xyzComboboxData.valueField = c.valueField==undefined?'value':c.valueField;
	xyzComboboxData.textField = c.textField==undefined?'text':c.textField;

	//20160622 huying 修改  由：方便动态传入 url data 数据 自动分析显示
	if(c.url != null && c.url != undefined && c.url != ""){
		xyzComboboxData.url = c.url;
		xyzComboboxData.loadFilter = function(data){
			if(data instanceof Array){
				return data;
			}else{
				if(data.status==1){
					return data.content;
				}else{
					return [];
				}
			}
		};
	}else{
		xyzComboboxData.data = c.data;
		xyzComboboxData.loadFilter=function(data){
			return c.data;
		};
	}
	
	var xyzComboboxLazy = c.lazy==undefined?true:c.lazy;
	if(xyzComboboxLazy){
		xyzComboboxData.onShowPanel = function(){
			if($(this).combobox("getData").length==0){
				$(this).combobox("reload",c.url);
			}
		};
	}else{
		xyzComboboxData.url = c.url;
	}
	xyzComboboxData.data=c.data==undefined?'':c.data;
	xyzComboboxData.onBeforeLoad = c.onBeforeLoad==undefined?undefined:c.onBeforeLoad;
	xyzComboboxData.onLoadSuccess = c.onLoadSuccess==undefined?undefined:c.onLoadSuccess;
	xyzComboboxData.onSelect = c.onSelect==undefined?undefined:c.onSelect;
	xyzComboboxData.onChange = c.onChange==undefined?undefined:c.onChange;
	xyzComboboxData.onHidePanel = c.onHidePanel==undefined?undefined:c.onHidePanel;
	xyzComboboxData.required = c.required==undefined?false:c.required;
	xyzComboboxData.editable = c.editable==undefined?true:c.editable;
	xyzComboboxData.multiple = c.multiple==undefined?false:c.multiple;
	xyzComboboxData.mode = c.mode==undefined?'local':c.mode;
	$('#'+c.combobox).combobox(xyzComboboxData);*/
}


function xyzGetHeight(height){
	var heightT = parseInt(top.$("#centerContentTabs").css("height").split("px")[0]);
	return heightT-height;
}

function xyzGetCurrentRow(table,field,value){
	var rows = $("#"+table).datagrid("getRows");
	var rowT = [];
	for(var i=0;i<rows.length;i++){
		var row = rows[i];
		if(row[field]==value){
			rowT [rowT.length] = row;
		}
	}
	if(rowT.length!=1){
		return null;
	}else{
		return rowT[0];
	}
}

//针对Onchange事件 传div和value取中文 
function xyzOnChangeGetText(div,value){
	var result = "";
	var dataT = $("#"+div).combobox("getData");
	for(var ppp in dataT){
		if(dataT[ppp].value==value){
			result = dataT[ppp].text;
		}
	}
	return result;
}

function xyzOnChangeGetValue(div,value){
	var result = "";
	var dataT = $("#"+div).combobox("getData");
	for(var ppp in dataT){
		if(dataT[ppp].value==value){
			result = dataT[ppp].value;
		}
	}
	return result;
}

/**
 * Author:huying
 * 两级联动下拉列表 
 * 20160621
 * @param d
 */
function HyInterlink(d){
	var parent=d.parent==null?"":d.parent;//父级下拉框id
	var sublevel=d.sublevel==null?"":d.sublevel;//子级下拉框id
	var data=d.data==null?[{}]:d.data;
	var searchTypeDate=new Array();
	for(var i=0;i<data.length;i++){
		searchTypeDate[searchTypeDate.length]={"id":data[i].key,"text":data[i].value};
	}
	$("#"+parent).combobox({
		valueField: 'id',    
        textField: 'text',
        data:searchTypeDate,
        onChange:function(newValue,oldValue){
        	var list=new Array();
        	for(var i=0;i<data.length;i++){
        		var obj=data[i];
        		if(obj.key==newValue){
        			list[list.length]={"type":obj.type,"data":obj.data};
        			break;
        		}
        	}
        	
        	if(list[0].type=='combobox'){
        		xyzCombobox({
        			combobox : sublevel,
    				valueField: 'value',    
    		        textField: 'text',
    		        data:list[0].data,
    		        url:list[0].data.url,
    		        lazy:false
        		});
        	}
        }
	});
	//将下拉框第一列设为默认值
	$("#"+parent).combobox("setValue",searchTypeDate[0].id);
} 

function xyzProgressBar(id, time){
	xyzdialog({
		dialog : 'dialogFormDiv_xyzProgressBar_'+id,
		title : '进度',
		fit:false,
		width:400,
		height:70,
	    content : '<div id="xyzProgressBar_'+id+'" style="width:400px;"></div>',
	    onOpen:function(){
	    	$('#xyzProgressBar_'+id).progressbar({
	    		width:384,
	    		height:34,
	    		value: 0
	    	});
	    	
	    	var functionTimeout = null;
	    	functionTimeout = function(){
	    		try{ //防止dialog已摧毁, 获取值时 报错!
	    			var value = $('#xyzProgressBar_'+id).progressbar('getValue');
		    		value += Math.floor(Math.random() * (100/time)/5);
		    		if (value <= 99){
			    		$('#xyzProgressBar_'+id).progressbar('setValue', value);
			    		setTimeout(functionTimeout,100);
			    	}
	    		}catch(e){
	    			;
	    		}
	    	};
	    	functionTimeout();
	    }
	});
}


function xyzCloseProgressBar(id){
	$('#xyzProgressBar_'+id).progressbar('setValue', 100);
	setTimeout(function(){
		$("#dialogFormDiv_xyzProgressBar_"+id).dialog("destroy");
	},200);
}

