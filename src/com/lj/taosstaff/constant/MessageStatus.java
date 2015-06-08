package com.lj.taosstaff.constant;

public enum MessageStatus {
	ORIGINAL_STATUS(0,"³õÊ¼×´Ì¬"),
    READED_STATUS(1,"ÒÑ¶Á");

    public Integer code;
    public String label;
    MessageStatus(Integer code,String label){
        this.code=code;
        this.label=label;
    }
}
