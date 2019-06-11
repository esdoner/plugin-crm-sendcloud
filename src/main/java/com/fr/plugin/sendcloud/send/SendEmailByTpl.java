package com.fr.plugin.sendcloud.send;

import com.fr.general.Inter;
import com.fr.plugin.sendcloud.builder.SendCloudBuilder;
import com.fr.plugin.sendcloud.config.PlatformConfig;
import com.fr.plugin.sendcloud.core.SendCloud;
import com.fr.plugin.sendcloud.model.MailBody;
import com.fr.plugin.sendcloud.model.SendCloudMail;
import com.fr.plugin.sendcloud.model.TemplateContent;
import com.fr.plugin.sendcloud.send.record.SendLogUtil;
import com.fr.plugin.sendcloud.util.ResponseData;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by yuwh on 2019/3/26
 * Description:none
 */
public final class SendEmailByTpl implements SendBasic{
    private String address;
    private String tplid;
    private String subject;
    private String fromname;
    private StringBuffer parasJsonString;
    private Map<String, List<String>> sub = new HashMap<String, List<String>>();
    private SendCloudMail mail = new SendCloudMail();
    private ResponseData res= new ResponseData();

    public SendEmailByTpl(List objects) {
        address= String.valueOf(objects.get(0));
        tplid= String.valueOf(objects.get(1));
        subject= String.valueOf(objects.get(2));
        fromname= String.valueOf(objects.get(3));
        parasJsonString= new StringBuffer(String.valueOf(objects.get(4)));
    }

    @Override
    public boolean prepare() throws Throwable {
        MailBody body= new MailBody();
        PlatformConfig config= PlatformConfig.getInstance();
        // 设置 From
        body.setFrom(config.getRegisterEmail());
        // 设置 FromName
        body.setFromName(fromname);
        // 设置 ReplyTo
        body.setReplyTo(config.getRegisterEmail());
        // 设置subject
        body.setSubject(subject);
        //TODO 这里的前三项项要改成配置文件里的

        //收件人列表
        List<String> toList = new ArrayList<String>();
        toList.add(address);
        //模板参数准备
        generateParas();

        // 此时, receiver 中添加的 to, cc, bcc 均会失效
        body.addXsmtpapi("to", toList);
        body.addXsmtpapi("sub", sub);
        body.addHeader("SC-Custom-test_key1", "test1");
        body.addHeader("NO-SC-Custom-test_key1", "test2");

        TemplateContent content = new TemplateContent();
        content.setTemplateInvokeName(tplid);

        mail.setBody(body);
        mail.setContent(content);

        return true;
    }

    private void generateParas(){
        JSONObject jsonObj= JSONObject.fromObject(parasJsonString.toString());
        StringBuffer tempKey;
        List tempValues= new ArrayList();
        for(Object var:jsonObj.keySet()){
            tempValues.clear();
            tempKey= new StringBuffer(String.valueOf(var));
            tempValues.add(jsonObj.get(tempKey.toString()));
            sub.put("%"+tempKey.toString()+"%", (List<String>) ((ArrayList) tempValues).clone());
        }
    }

    @Override
    public boolean send() throws Throwable {
        SendCloud sc = SendCloudBuilder.build();
        res = sc.sendMail(mail);
        //log收集 start
        setLoggerInfo();
        //end
        return true;
    }

    @Override
    public boolean send(boolean var) throws Throwable {
        SendCloud sc = SendCloudBuilder.build();
        res.setStatusCode(200);
        res.setMessage(Inter.getLocText("logdb_detail_email_test_susccess"));
        setLoggerInfo();
        return true;
    }

    public void setLoggerInfo(){
        HashMap<String, String> info= new HashMap<String, String>();
        info.put("type","email");
        info.put("entity",address);
        info.put("tpl",tplid);
        info.put("time",String.valueOf(System.currentTimeMillis()/1000));
        info.put("paras", parasJsonString.toString());
        info.put("code", String.valueOf(res.getStatusCode()));
        info.put("detail", String.valueOf(res.getMessage()));
        SendLogUtil util= new SendLogUtil();
        util.getLogger(info).record();
    }

    @Override
    public String errTrans(String var) { return null; }

    @Override
    public ResponseData getResult() {
        return this.res;
    }
}
