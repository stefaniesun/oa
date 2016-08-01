$(document).ready(function() {
	$("#productButton").click(function(){
		loadTable();
	});
	
	initTable();
	
});

function initTable(){

	xyzgrid({
		table : 'exceptionLogManagerTable',
		title : '异常日志列表',
		url:'../ExceptionLogWS/queryExceptionLogList.do',
		pageList : [5,10,15,30,50],
		pageSize : 15,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns : [[
		    {field:'checkboxTemp',checkbox:true},
			{field:'numberCode',title:'编号',hidden:true},
			{field:'orderNum',title:'订单编号'},
			{field:'productName',title:'订单信息'},
			{field:'type',title:'异常类型',width:100},
			{field:'content',title:'异常信息',width:500},
			{field:'addDate',title:'添加时间',width:100},
			{field:'handleFlag',title:'处理状态',
				formatter: function(value,row){
					if(value=="0"){
						return "<a href='javascript:void(0);' onclick='handle(\""+row.numberCode+"\")'>处理确认</a>";
					}else{
						return "<span>已处理</span>";
					}
				}		
			}
		]]
	});
	
}

function handle(numberCode){
	xyzAjax({
		url : "../ExceptionLogWS/handleExceptionLogOper.do",
		data : {
			numberCode : numberCode
		},
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#exceptionLogManagerTable").datagrid("reload");
		    }else{
		    	top.$.messager.alert("警告",data.msg,"warning");
		    }
		}
	});
}

function loadTable(){
	var type = $("#type").val();
	var orderNum = $("#orderNum").val();
	var dateStart=$("#dateStart").datebox("getValue");
	var dateEnd=$("#dateEnd").datebox("getValue");
	var handleType=$("#handleType").combobox("getValue");
	$("#exceptionLogManagerTable").datagrid("load",{
		orderNum:orderNum,
		type:type,
		dateStart:dateStart,
		dateEnd:dateEnd,
		handleType:handleType
	});
}
