package com.yonyou.mcloud.model;

/**
 * Created by hubo on 2016/2/29
 */
public interface IBaseVO {

    short DR_NORMAL = 0;
    short DR_DEL = 1;

    String DR_FEILD = "dr";
    String TS_FEILD = "ts";

    String getId();

    void setId(String id);

    Long getTs();

    void setTs(Long ts);

    Short getDr();

    void setDr(Short dr);
}
