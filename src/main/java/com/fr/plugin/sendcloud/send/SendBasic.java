package com.fr.plugin.sendcloud.send;

import com.fr.plugin.sendcloud.util.ResponseData;

/**
 * Created by yuwh on 2019/3/26
 * Description:none
 */
public interface SendBasic {
    // 参数准备阶段
    boolean prepare() throws Throwable;

    // 发送
    boolean send() throws Throwable;

    //虚拟发送
    boolean send(boolean var) throws Throwable;

    // 返回码分析和转换
    String errTrans(String var);

    //日志生成
    void setLoggerInfo();

    //get发送结果
    ResponseData getResult();
}
