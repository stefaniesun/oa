$(document).ready(function() {
	$("#scenicButton").click(function(){
		loadTable();
	});
	
	xyzCombobox({
		combobox : 'regionForm',
		url : '../ListWS/getRegionList.do',
	});
	
	initTable();
	
});

function initTable(){

	var toolbar = [];
	if(xyzControlButton('buttonCode_h20160503100200')){
		toolbar[toolbar.length]={
				text: '更新景区数据',
				border:'1px solid #bbb',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();updateScenicButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	
	
	xyzgrid({
		table : 'scenicManagerTable',
		title : '西藏景区列表',
		url:'../ScenicWS/queryScenicList.do',
		pageList : [5,10,15,30,50],
		pageSize : 15,
		singleSelect : false,
		toolbar:toolbar,
		idField : 'numberCode',
		height:'auto',
		columns : [[
		    {field:'checkboxTemp',checkbox:true},
			{field:'numberCode',title:'编号',hidden:true},
			{field:'nameCn',title:'名称'},
			{field:'lat',title:'经度'},
			{field:'lng',title:'纬度'},
			{field:'address',title:'地址'},
			{field:'regionName',title:'行政区'}
		]]
	});
	
}

function updateScenicButton(numberCode){
	xyzAjax({
		url : "../ScenicWS/updateScenicList.do",
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#scenicManagerTable").datagrid("reload");
		    }else{
		    	top.$.messager.alert("警告",data.msg,"warning");
		    }
		}
	});
}

function loadTable(){
	var nameCn = $("#nameCn").val();
	var region= $("#regionForm").combobox("getValue");
	$("#scenicManagerTable").datagrid("load",{
		nameCn : nameCn,
		region:region
	});
}
