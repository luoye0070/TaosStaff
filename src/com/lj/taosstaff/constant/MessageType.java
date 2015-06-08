package com.lj.taosstaff.constant;

public enum MessageType {
	ORDER_HANDLE_TYPE(0,"��������"),
    CHECKOUT_REQUEST_TYPE(1,"��������"),
    TEAWATER_TYPE(2,"��ˮ"),
    OTHER_TYPE(3,"����"),
    PACKAGE(4,"���"),
    CLEAR_AWAY(5,"������ʰ"),
    UPDATE_DISH_LIST(6,"���µ���б�"),
    SERVED_FOOD(7,"�ϲ�");
    public Integer code;
    public String label;
    MessageType(Integer code,String label){
        this.code=code;
        this.label=label;
    }
}
