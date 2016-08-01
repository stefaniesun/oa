$(document).ready(function(){
	
	 tableInit();
	
});


function tableInit(){
	$('#scenicTable').bootstrapTable({
		url:'../../ProductWS/queryProductList.do',
	    columns: [
          {field: 'provider',title: '资源'},  
          {field: 'nameCn',title: '名称'},
          {field: 'nameCn',title: '库存',align:'center', formatter:function(value,row,index){
        	  return '<button type="button" class="btn btn-primary btn-xs" onclick="stockControl(\''+row.numberCode+'\');">库存管理</button>';
          }}
	    ],
	    queryParams:function(params) {
	    	params.page=1;
	     	params.rows=10;
	     	params.userName=getCookie("XZTRIP_LOGIN_NAME");
	    	return params;
	    },
	    responseHandler : function(data){
			if(data.status==1){
				return  data.content.rows;
			}else{
				top.window.layer.alert(data.msg);
			}
		}
	});
}

//库存管理
function stockControl(numberCode){
	parent.layer.open({
	    type: 2,
	    fix: false,
	    title: '库存管理',
	    maxmin: false,
	    shadeClose:true,
	    content: ['../productStock.html?numberCode='+numberCode],
	    area: ['1150px' , '620px']
	});
}