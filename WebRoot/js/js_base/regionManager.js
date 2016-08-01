$(document).ready(function() {
	$("#regionButton").click(function(){
		loadTable();
	});
	
	initTable();
	
});

function initTable(){

	var toolbar = [];
	if(xyzControlButton('buttonCode_h20160429154900')){
		toolbar[toolbar.length]={
				text: '更新行政区数据',
				border:'1px solid #bbb',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();updateRegionButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	
	
	xyzgrid({
		table : 'regionManagerTable',
		title : '西藏行政区划列表',
		url:'../RegionWS/queryRegionList.do',
		pageList : [5,10,15,30,50],
		pageSize : 15,
		singleSelect : false,
		toolbar:toolbar,
		idField : 'numberCode',
		height:'auto',
		columns : [[
		    {field:'checkboxTemp',checkbox:true},
			{field:'numberCode',title:'编号'},
			{field:'nameCn',title:'名称'}
		]]
	});
	
}

function updateRegionButton(numberCode){
	xyzAjax({
		url : "../RegionWS/updateRegionList.do",
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#regionManagerTable").datagrid("reload");
		    }else{
		    	top.$.messager.alert("警告",data.msg,"warning");
		    }
		}
	});
}

function loadTable(){
	var name=$("#name").val();
	$("#regionManagerTable").datagrid("load",{
		name:name
	});
}
