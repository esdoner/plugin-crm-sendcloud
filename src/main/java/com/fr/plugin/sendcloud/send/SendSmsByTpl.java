package com.fr.plugin.sendcloud.send;

import com.fr.general.Inter;
import com.fr.plugin.sendcloud.builder.SendCloudBuilder;
import com.fr.plugin.sendcloud.core.SendCloud;
import com.fr.plugin.sendcloud.model.SendCloudSms;
import com.fr.plugin.sendcloud.send.record.SendLogUtil;
import com.fr.plugin.sendcloud.util.ResponseData;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yuwh on 2019/4/1
 * Description:none
 */
public final class SendSmsByTpl implements SendBasic {
    private int type;
    private int tplid;
    private String number;
    private StringBuffer parasJsonString;
    private SendCloudSms sms = new SendCloudSms();
    private SendCloud sc = SendCloudBuilder.build();
    private ResponseData res= new ResponseData();

    public SendSmsByTpl(List objects){
        type= Integer.valueOf(String.valueOf(objects.get(0)));
        tplid= Integer.valueOf(String.valueOf(objects.get(1)));
        number= String.valueOf(objects.get(2));
        parasJsonString= new StringBuffer(String.valueOf(objects.get(3)));
    }

    @Override
    public boolean prepare() throws Throwable {
        sms.setMsgType(type);
        sms.setTemplateId(tplid);
        sms.addPhone(number);
        JSONObject jsonObj= JSONObject.fromObject(parasJsonString.toString());
        StringBuffer tempKey;
        for(Object var:jsonObj.keySet()){
            tempKey= new StringBuffer(String.valueOf(var));
            sms.addVars(tempKey.toString(), String.valueOf(jsonObj.get(tempKey.toString())));
        }
        return true;
    }

    @Override
    public boolean send() throws Throwable {
        res = sc.sendSms(sms);
        //log收集 start
        setLoggerInfo();
        //end
        return true;
    }

    @Override
    public boolean send(boolean var) throws Throwable {
        res.setStatusCode(200);
        res.setMessage(Inter.getLocText("logdb_detail_sms_test_susccess"));
        setLoggerInfo();
        return true;
    }

    public void setLoggerInfo(){
        HashMap<String, String> info= new HashMap<String,String>();
        info.put("type","sms");
        info.put("entity",number);
        info.put("tpl",String.valueOf(tplid));
        info.put("time",String.valueOf(System.currentTimeMillis()/1000));
        info.put("paras", parasJsonString.toString());
        info.put("code", String.valueOf(res.getStatusCode()));
        info.put("detail", String.valueOf(res.getMessage()));
        SendLogUtil util= new SendLogUtil();
        util.getLogger(info).record();
    }

    @Override
    public String errTrans(String var) {
        return null;
    }

    @Override
    public ResponseData getResult() {
        return this.res;
    }
}
