layui.config({
    base: "../../../common/"
})/*.extend({
    index: "lib/index"
});*/
layui.define(['index','form', 'layer', 'table'], function (exports) {
	var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        table = layui.table,
        setter = layui.setter,
        view = layui.view,
    	admin = layui.admin;
    	
    (function($,doc,o){
      var tk;
      var obj;
      var otd;
      var callback;
      o.show = function(h,e,c){
        callback = c;
        var d =  doc.getElementById('table_type_select');
        if(d){
          d.remove();
        }
        otd = e;
        var s = '<div id="table_type_select" class="table-select"><dl class="layui-anim layui-anim-upbit" style="padding: 0px;top: 0px">';
        for(var k in h.data){
          if(h.value == h.data[k].value){
            s += '<dd lay-value="'+ h.data[k].value + '" class="layui-this">'+ h.data[k].text + '</dd>';
          }else{
            s += '<dd lay-value="'+ h.data[k].value + '" >'+ h.data[k].text + '</dd>';
          }
        }
        s += '</dl></div>';
        otd.innerHTML = s + otd.innerHTML;
        obj = doc.getElementById('table_type_select');
        obj.onmouseout = function(){
          tk = 1;
          setTimeout(function() {
            if(tk){
              if(obj){
                ke = 0;
                obj.remove();
              }
            }
          }, 200);
        }
        obj.onmouseover = function(){
          tk = 0;
        }
        obj.addEventListener('click', function(e){
          var value = $(e.srcElement).attr('lay-value');
          var text = e.srcElement.innerHTML;
          obj.remove();
          callback({value:value,text:text});
        });
      }
    })($,document, window.type_select = {});
    
    
    
    
	//监听表格事件
//	table.on('tool(dataList)',function(obj){
//		var cols = dataTableObj.parentTableCols;
//		console.log(cols)
//  	if(obj.event === 'type'){
//  		var arr = [];
//  		var _this = this;
//		    admin.req({
//				url:"http://127.0.0.1:8020/layui_jerei2.0/modules/testdemo/sex.json",
//				success:function(r){
//					for(var i=0; i<r.length; i++){
//						let o = new Object();
//						o['value'] = r[i].id;
//						o['text'] = r[i].value;
//						arr.push(o);
//					}
//					
//					//显示下拉框框
//			        type_select.show({
//						//设置当前选择的id
//						value:obj.data.sexvalue,
//						//下拉选择数据
//						data:arr
//			        }, _this, function(e){
//					    //回调函数 返回结果
//					    obj.data.sexvalue = e.value;
//			            obj.data.sextext = e.text;
//			            obj.update(obj.data);
//			            form.render();
//					});
//				}
//			})
//  		
//  		
//      }
//  })
        
	var dataTableObj = {
		parentTableData : [],
		tableIns : [],
		parentTableCols : [],
		parentTable:{
			compareKey:"",
			compareTitle:""
		},
		detailTable:{
			compareKey:"",
			compareTitle:""
		},
		parentTableDelRows:[],
		parentTableId:"",
		
		
		renderParentTable:function(obj){
			var _this = this;
			this.parentTable.compareKey = obj.compareKey;
			this.parentTableCols = obj.cols;
			this.parentTableId = obj.id.substring(1);
			var hideNum = [];
			for(var i=0; i<obj.cols[0].length; i++){
				if(obj.compareKey == obj.cols[0][i].field){
					this.parentTable.compareTitle = obj.cols[0][i].title;
				}
				// 设置隐藏列（cols中设置style:'display:none;'）的表头同时被隐藏
				if(obj.cols[0][i].style){
					hideNum.push(i);
				}
			}
			_this.parentTableData = obj.data.list;

			_this.tableIns = table.render({
				elem:obj.id,
				data:_this.parentTableData,
//				count:obj.data.totalCount,
				height: obj.height,
				id:"parentTable",
				cols: obj.cols,
				page:obj.page,
//				done:obj.done?obj.done:function(){},
				limits: obj.limits?obj.limits:[10, 15, 20, 25],
            	limit: obj.limit?obj.limit:10,
            	done:function(){
            		// 设置隐藏列（cols中设置style:'display:none;'）的表头同时被隐藏
            		for(var i = 0; i<hideNum.length; i++){
            			$('.layui-table-box .layui-table-header table.layui-table thead tr th:eq('+hideNum[i]+')').addClass('layui-hide');
            		}
            	}
			});
			
		},
		
		renderTableSelect:function(obj,_this,param){
			var arr = [];
		    for(var i=0; i<param.data.length; i++){
				let o = new Object();
				o['value'] = param.data[i][param.value];
				o['text'] = param.data[i][param.text];
				arr.push(o);
			}
			
			//显示下拉框框
	        type_select.show({
				//设置当前选择的id
				value:obj.data[param.valueField],
				//下拉选择数据
				data:arr
	        }, _this, function(e){
			    //回调函数 返回结果
			    obj.data[param.valueField] = e.value;
	            obj.data[param.textField] = e.text;
	            obj.update(obj.data);
	            form.render();
			});
		},
		
		delParentRow:function(){
			var chooseRow = table.checkStatus('parentTable').data;
			if(chooseRow.length==0){
				layer.msg("您未选中数据", {icon: 2,time: 2000 });
				return;
			}
			for(var i=0; i<chooseRow.length; i++){
				this.parentTableDelRows.push(chooseRow[i]);
			}
			var newArr = [];
			for(var i=0; i<this.parentTableData.length; i++){
				var isPush=true;
				for(var j=0; j<chooseRow.length; j++){
					var pKey=this.parentTable.compareKey;
					if(this.parentTableData[i][pKey] == chooseRow[j][pKey]){
						isPush = false;
					}
				}
				if(isPush) newArr.push(this.parentTableData[i]);
			}
			this.parentTableData = newArr.concat();
			this.tableIns.reload({
            	data:this.parentTableData
            });
            return this.parentTableDelRows;
		},
		getParentDelRows:function(){
			return this.parentTableDelRows;
		},
		addParentRow:function(){
			for(var i=0; i<this.parentTableData.length; i++){
				if(this.parentTableData[i][this.parentTable.compareKey] == null || this.parentTableData[i][this.parentTable.compareKey]==""){
					layer.msg("您有未编辑数据！请确保所有【"+this.parentTable.compareTitle+"】非空！", {icon: 2,time: 2000 });
					return;
				}
			}
			this.parentTableData.unshift(this.getEmptyRow());
			this.reloadParentTable(this.parentTableData);
		},
		
		updateParentTableRow:function(obj,_this,chooseData,param){
			if(param.tableField == this.parentTable.compareKey){
				var rows = this.parentTableData;
				console.log(rows)
				for(var i=0; i<rows.length; i++){
					if(chooseData[param.chooseField] == rows[i][param.tableField]){
						layer.msg("所选"+this.parentTable.compareTitle+"已存在！", {icon: 2,time: 2000 });
						return;
					}
				}
			}
			obj.data.username = chooseData.username;
			obj.update(obj.data);
		},
		
		searchParentTable:function(obj){
			var arr=[];
			for(var i=0; i<this.parentTableData.length; i++){
				var count1=0,count2=0;
				for(var key in obj){
					count1++;
					if(this.parentTableData[i][key] == obj[key] || !obj[key]){
						count2++;
					}
				}
				if(count1 == count2) arr.push(this.parentTableData[i]);
			}
			this.reloadParentTable(arr);
		},
		
		reloadParentTable:function(data){
			var _this = this;
			console.log(_this.parentTableCols)
			this.tableIns.reload({
				cols:_this.parentTableCols,
            	data:data
            });
		},
		
		getParentTableIns:function(){
			return this.tableIns;
		},
		
		getParentTableRows:function(){
			return this.parentTableData;
		},
		
		getEmptyRow:function(){
			var obj = {}, cols=this.parentTableCols;
			for(var i=0; i<cols[0].length; i++){
				if(cols[0][i].field){
					var key = cols[0][i].field;
					obj[key] = null;
				}
			}
			return obj;
		},
		
		
		renderDetaiTable:function(obj){
			var _this = this;
			this.detailTable.compareKey = obj.table.compareKey;
			for(var i=0; i<obj.table.cols[0].length; i++){
				if(obj.table.compareKey == obj.table.cols[0][i].field){
					this.detailTable.compareTitle = obj.table.cols[0][i].title;
				}
			}
			layer.open({
	          type: 1
	          ,title: obj.title
	          ,content: $(obj.detailId)
	          ,area: [obj.width, obj.height]
	          ,btn: ['确定', '取消']
	          ,success: function(layero, index){
			    table.render({
					elem:obj.table.tableId,
					url:obj.table.url,
					id:"detailTable",
					where:obj.table.where?obj.table.where:{},
					page:obj.table.page,
					limits: obj.table.limits?obj.table.limits:[10, 15, 20, 25],
	            	limit: obj.table.limit?obj.table.limit:10,
					responseHandler: function (res) {
		                return {
		                    "count": res.data.totalCount,
		                    "data": res.data.list,
		                    "code": res.code==200 ? 0 : -1 //code值为200表示成功
		                };
		           },
					cols: obj.table.cols
				});
			  }
	          ,yes: function(index, layero){
	            var chooseRow = table.checkStatus('detailTable').data;
	            var arr = [];
	            for(var i=0; i<chooseRow.length; i++){
	            	var isPush=true;
	            	for(var j=0; j<_this.parentTableData.length; j++){
	            		if(chooseRow[i][_this.detailTable.compareKey] == _this.parentTableData[j][_this.parentTable.compareKey]){
	            			isPush = false;
	            		}
	            		if(!isPush) break;
	            	}
	            	if(isPush){
	            		var row = _this.getEmptyRow();
		            	for(var k=0; k<obj.table.map.length; k++){
		            		var pKey=obj.table.map[k].parent,dKey=obj.table.map[k].detail;
			            	row[pKey] = chooseRow[i][dKey];
			            }
	            		_this.parentTableData.unshift(row);
	            	}
	            }
	              _this.reloadParentTable(_this.parentTableData);
	              layer.close(index); //关闭弹层
	          }
	        }); 
		}
		
		
		
	};
	
	table.on('edit()', function(obj) { //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
		var parentTable = dataTableObj.parentTable;
		var cols = dataTableObj.parentTableCols;
		var format;
		for(var i=0; i<cols[0].length; i++){
			if(cols[0][i].field == obj.field){
				format = cols[0][i].format;
				break;
			}
		}
		if(format == 'int' && !/^-?\d+$/.test(obj.value)){
			layer.msg("只能输入整数类型！", {icon: 2,time: 2000 });
			dataTableObj.parentTableData[obj.data.LAY_TABLE_INDEX][obj.field] = $(this).prev().text();
		}if(format == 'num' && !/^(-?\d+)(\.\d+)?$/.test(obj.value)){
			layer.msg("只能输入数值类型！", {icon: 2,time: 2000 });
			dataTableObj.parentTableData[obj.data.LAY_TABLE_INDEX][obj.field] = $(this).prev().text();
		}else if(format == 'tel' && !/^[1][3,4,5,7,8][0-9]{9}$/.test(obj.value)){
			layer.msg("手机号码格式不正确！", {icon: 2,time: 2000 });
			dataTableObj.parentTableData[obj.data.LAY_TABLE_INDEX][obj.field] = $(this).prev().text();
		}else if(format == 'money'){
			if(!/^(\d+)(\.\d+)?$/.test(obj.value)){
				layer.msg("金额输入不合法！", {icon: 2,time: 2000 });
				dataTableObj.parentTableData[obj.data.LAY_TABLE_INDEX][obj.field] = $(this).prev().text();
			}else{
				var str = (obj.value*1).toFixed(2) + '';
				var intSum = str.substring(0,str.indexOf(".")).replace( /\B(?=(?:\d{3})+$)/g, ',' );//取到整数部分
				var dot = str.substring(str.length,str.indexOf("."))//取到小数部分搜索
				var ret = intSum + dot;
				dataTableObj.parentTableData[obj.data.LAY_TABLE_INDEX][obj.field] = ret;
			}
		}else if(format == 'mail' && !/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(obj.value)){
			layer.msg("邮箱格式不合法！", {icon: 2,time: 2000 });
			dataTableObj.parentTableData[obj.data.LAY_TABLE_INDEX][obj.field] = $(this).prev().text();
		}
		
		if(obj.field == parentTable.compareKey){
			if(obj.value == "" || obj.value == undefined) {
				layer.msg(parentTable.compareTitle+"不可为空！", {icon: 2,time: 2000 });
				dataTableObj.parentTableData[obj.data.LAY_TABLE_INDEX][parentTable.compareKey] = $(this).prev().text();
			}else{
				for(var i=0; i<dataTableObj.parentTableData.length; i++){
					if(obj.value==dataTableObj.parentTableData[i][parentTable.compareKey] && i!=obj.data.LAY_TABLE_INDEX){
						layer.msg(parentTable.compareTitle+"不可与已有"+parentTable.compareTitle+"重复！", {icon: 2,time: 2000 });
						dataTableObj.parentTableData[obj.data.LAY_TABLE_INDEX][parentTable.compareKey] = $(this).prev().text();
					}
				}
			}
		}
		dataTableObj.reloadParentTable(dataTableObj.parentTableData);
	});
	
	exports('dataTable', dataTableObj);
});