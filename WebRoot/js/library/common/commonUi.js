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
		$("#"+d.table).before(toolbarHtml);
		
	}
	
	
	$("#"+d.table).bootstrapTable({
		
		url : d.url==undefined?undefined:d.url,//表格数据请求URL
		columns : d.columns,//表格显示列
		method:'get',//请求方式
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
		ajax:function(params){
			$.ajax({
				  type: 'post',
				  url : d.url==undefined?undefined:d.url,//表格数据请求URL
				  data: params.data,
				  success: params.success,
				  dataType: 'json'
				});
		},
		queryParamsType:'limit',
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
	});
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
		    		 $(layero).on('click','#submitBtn',function(){
		    			 if(validateForm()){
		    				 d.buttons[0].handler.call();
		    			 }
	    			 });
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

};




/*
 * combobox必须
 * url必须 
 * lazy可选:延迟加载,默认延迟
 * valueField可选
 * textField可选
 */
function xyzCombobox(c){
	

 	var  toggle='<div class="input-group-btn">';
 	toggle+='<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">';
 	toggle+='<span class="caret"></span>';
	toggle+='</button>';
	toggle+='<ul class="dropdown-menu dropdown-menu-right" role="menu">';
	toggle+='</ul>';
	toggle+='</div>';
	 $("#"+c.combobox).after(toggle);
 	
	 var resultList = {};
	 $("#"+c.combobox).bsSuggest('init', {
		 getDataMethod: "url",    //获取数据的方式，总是从 URL 获取
         url: c.url,
         keyField: "text",               //有效显示于列表中的字段，非有效字段都会过滤，默认全部，对自定义getData方法无效
         fnProcessData: function (data) {    // url 获取数据时，对数据的处理，作为 fnGetData 的回调函数
        	 var dataList = {value: []};
             data=data.content;
             for(var i=0;i<data.length;i++){
            	  dataList.value.push({
            		  id: eval("data[i]."+c.valueField),
            		  text: eval("data[i]."+c.textField)
                  });
             }
             resultList=dataList;
             return dataList;
         }
     }).on('onDataRequestSuccess', function (e, result) {
         console.log('onDataRequestSuccess: ', result);
     }).on('onSetSelectValue', function (e, keyword, data) {
         console.log(keyword);
     }).on('onUnsetSelectValue', function () {
    	 $(this).attr("data-id","");
         console.log('onUnsetSelectValue');
     });
	 
	
	 $.fn.extend({
		 combobox:function(method){
			 if(method=="getValue"){
				for(var i=0;i<resultList.length;i++){
					alert(resultList[i].text);
					alert($(this).val());
					if(resultList[i].text==$(this).val()){
						 return resultList[i].id;
					}
				}
				 return "";
			 }
		 }
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

