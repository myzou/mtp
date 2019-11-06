function func3() {
        //询问框
    layer.confirm('您是如何看待前端开发？', {
        btn: ['重要','奇葩'] //按钮
    }, function(){
        layer.msg('的确很重要', {icon: 1});
    }, function(){
        layer.msg('也可以这样', {
            time: 2000, //2s后自动关闭
            btn: ['明白了', '知道了']
        });
    });
}


/** 
 * 设置select控件选中 
 * @param selectId select的id值 
 * @param checkValue 选中option的值 
 * @author 标哥 
*/  
function set_select_checked(selectId, checkValue){  
    var select = document.getElementById(selectId);  
    for (var i = 0; i < select.options.length; i++){  
        if (select.options[i].value == checkValue){  
            select.options[i].selected = true;  
            break;  
        }  
    }  
}


