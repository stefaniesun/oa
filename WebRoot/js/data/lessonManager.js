$(document).ready(function(){
	
	   
	 tableInit();
	 
	 $("#queryButton").click(function(){
			loadTable();
		});
	 
});


function tableInit(){
	var toolbar = [];
	if(xyzControlButton("buttonCode_h20151214155701")){
		toolbar[toolbar.length]={
				text: '新增班次',
				icon: 'fa-plus',
				iconType:'primary',
				handler: 'addLesson()'
		};
	}
	if(xyzControlButton("buttonCode_h20151214155702")){
		toolbar[toolbar.length]={
				text: '编辑班次',
				icon: 'fa-edit',
				iconType:'info',
				handler: 'editLesson()'
		};
	}
	if(xyzControlButton("buttonCode_h20151214155703")){
		toolbar[toolbar.length]={
				text: '删除班次',
				icon: 'fa-close',
				iconType:'danger',
				handler: 'deleteLesson()'
		};
	}

	xyzgrid({
		table : 'lessonManagerTable',
		url : '../LessonWS/queryLessonList.do',
		toolbar : toolbar,
	    columns : [
	      {field:'checkboxTemp',checkbox:'true'},
          {field:'year',title:'年份'},
  		  {field:'type',title:'类型'},
  		  {field:'name',title:'名称'},
		  {field:'price',title:'价格'},
		  {field:'dateInfo',title:'上课时间',
				formatter: function(value,row,index){
					return row.startDate.substring(0,10)+'&nbsp;&nbsp;-&nbsp;&nbsp;'+row.endDate.substring(0,10);
				}
		  },
		  {field:'remark',title:'说明'},
  		  {field:'addDate',title:'添加时间'},
  		  {field:'temp',title:'设置资源组',width:'90px',
				formatter: function(value,row,index){
					return '<button data-toggle="button" class="btn btn-primary btn-sm btn-outline" type="button"><i class="fa fa-sitemap"></i>&nbsp;资源组</button>';
				}
		  }
  	
	    ]
	});
}

function loadTable(){
	var username = $("#username").val();
	var nickName = $("#nickName").val();
	var position = $("#position").combobox("getValue");
	
	$("#userManagerTable").bootstrapTable('refresh',{query:{
		username : username,
		nickName : nickName,
		position : position
	}});
}


function addLesson(){
	
	xyzdialog({
		id : 'dialog_addLesson',
		title : '新增班次',
		content: '../data/addLesson.html',
	    fit:false,
	    width:'600px',
	    height:'700px',
	    buttons:[{
			text:'确定',
			handler:function(){
				addLessonSubmit();
			}
		},{
			text:'取消'
		}],
		onLoad:function(){
		
		}
	});
	


}


function editLesson(){
	
	var lessons=$('#lessonManagerTable').bootstrapTable('getSelections');

	if(lessons.length != 1){
		top.layer.msg('请先选中单个对象',{img: "warning"});
		return;
	}
	
	var row = lessons[0];
	
	xyzdialog({
		id : 'dialog_editLesson',
		title : '编辑班次',
		content: '../data/editLesson.html',
	    fit:false,
	    width:'600px',
	    height:'700px',
	    buttons:[{
			text:'确定',
			handler:function(){
				editLessonSubmit(row.numberCode);
			}
		},{
			text:'取消'
		}],
		onLoad:function(){
			$("#yearForm").val(row.year);
			$("#nameForm").val(row.name);
			$("#priceForm").val(row.price);
			$("#remarkForm").val(row.remark);
			$("#startDateForm").val(row.startDate.substring(0,10));
			$("#endDateForm").val(row.endDate.substring(0,10));
		}
	});
}



function addLessonSubmit(){
	
	var year = $("#yearForm").val();
	var type = $("#typeForm").val();
	var name = $("#nameForm").val();
	var price = $("#priceForm").val();
	var startDate = $("#startDateForm").val();
	var endDate = $("#endDateForm").val();
	var teachType = $("#teachTypeForm").val();
	var flagRefund = $("#flagRefundForm").attr("checked")=="checked"?1:0;
	var remark = $("#remarkForm").val();

	xyzAjax({
		url : "../LessonWS/addLesson.do",
		data : {
			year : year,
			type : type,
			name : name,
			price : price,
			startDate : startDate,
			endDate : endDate,
			teachType : teachType,
			flagRefund : flagRefund,
			remark : remark
		},
		success : function(data) {
			if(data.status==1){
				$('#lessonManagerTable').bootstrapTable('refresh');
				top.layer.msg('操作成功',{img:"check"});
				layer.close($("#dialog_addLesson").data("index")); 
			}else{
				top.layer.alert(data.msg, {icon: 2});
			}
		}
	});

}


function editLessonSubmit(numberCode){
	
	var year = $("#yearForm").val();
	var type = $("#typeForm").val();
	var name = $("#nameForm").val();
	var price = $("#priceForm").val();
	var startDate = $("#startDateForm").val();
	var endDate = $("#endDateForm").val();
	var teachType = $("#teachTypeForm").val();
	var flagRefund = $("#flagRefundForm").attr("checked")=="checked"?1:0;
	var remark = $("#remarkForm").val();
	
	xyzAjax({
		url : "../LessonWS/editLesson.do",
		data : {
			numberCode : numberCode,
			year : year,
			type : type,
			name : name,
			price : price,
			startDate : startDate,
			endDate : endDate,
			teachType : teachType,
			flagRefund : flagRefund,
			remark : remark
		},
		success : function(data) {
			if(data.status==1){
				$('#lessonManagerTable').bootstrapTable('refresh');
				top.layer.msg('操作成功',{img:"check"});
				layer.close($("#dialog_editLesson").data("index")); 
			}else{
				top.layer.alert(data.msg, {icon: 2});
			}
		}
	});

}

function deleteLesson(){
	var lessons=$('#lessonManagerTable').bootstrapTable('getSelections');

	if(lessons.length != 1){
		top.layer.msg('请先选中单个对象',{img: "warning"});
		return;
	}
	
	var row = lessons[0];
	
	top.layer.confirm('确定删除班次？', {
		  btn: ['确定','取消'] //按钮
		}, function(index){
			xyzAjax({
				url : "../LessonWS/deleteLesson.do",
				data : {
					numberCode : row.numberCode
				},
				success : function(data) {
					if(data.status==1){
						$('#lessonManagerTable').bootstrapTable('refresh');
						top.layer.msg('删除成功',{img:'check'});
					}else{
						top.layer.alert(data.msg, {icon: 2});
					}
				}
			});
			layer.close(index);
		});
}

