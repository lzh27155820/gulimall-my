package com.liu.xyz.common.myutils;

/**
 * create liu 2022-10-13
 */
public class ProductConstant {

    public   enum AttrEnum{
        ATTR_TYPE_BASE("基本属性",1),ATTR_TYPE_SALE("销售属性",0);
        private String msg;
        private Integer code;

        AttrEnum(String msg, Integer code) {
            this.msg = msg;
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public Integer getCode() {
            return code;
        }
    }
    public enum  StatusEnum{
        DOW_SPU(2,"下架"), UP_SPU(1,"上架"),NEW_SPU(0,"新建");

        private int code;
        private String msg;

        StatusEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
