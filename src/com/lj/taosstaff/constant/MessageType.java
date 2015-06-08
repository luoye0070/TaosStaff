package com.lj.taosstaff.constant;

public enum MessageType {
	ORDER_HANDLE_TYPE(0,"订单处理"),
    CHECKOUT_REQUEST_TYPE(1,"结账请求"),
    TEAWATER_TYPE(2,"茶水"),
    OTHER_TYPE(3,"其它"),
    PACKAGE(4,"打包"),
    CLEAR_AWAY(5,"餐桌收拾"),
    UPDATE_DISH_LIST(6,"更新点菜列表"),
    SERVED_FOOD(7,"上菜");
    public Integer code;
    public String label;
    MessageType(Integer code,String label){
        this.code=code;
        this.label=label;
    }
}
