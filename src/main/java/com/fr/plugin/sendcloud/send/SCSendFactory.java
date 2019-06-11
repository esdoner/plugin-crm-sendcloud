package com.fr.plugin.sendcloud.send;

import com.fr.general.Inter;
import com.fr.plugin.sendcloud.config.PlatformConfig;
import com.fr.plugin.transform.ExecuteFunctionRecord;
import com.fr.plugin.transform.FunctionRecorder;
import com.fr.script.AbstractFunction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuwh on 2019/4/1
 * Description:none
 */
@FunctionRecorder(localeKey="FS_PLUGIN_FANRUAN-CRM-SENDCLOUD")
public class SCSendFactory<S extends SendBasic> extends AbstractFunction {
    private StringBuffer className= new StringBuffer();
    private StringBuffer result;
    private final int FIRSTPARANUM= 1;
    private PlatformConfig config= PlatformConfig.getInstance();
    private Boolean _switch= config.getSwitchOn();
    private Boolean _testscene= config.getTestOnly();
    private String _inner_m= config.getMobileReg();
    private String _inner_e= config.getEmailReg();

    @Override
    @ExecuteFunctionRecord
    public Object run(Object[] objects) {
        if(objects.length<5){
            return "{'code':'0';'detail':'parameters number error'}";
        }
        String mod= String.valueOf(objects[0]);
        className.append("Send").append(Character.toUpperCase(mod.charAt(0))).append(mod.substring(1)).append("ByTpl");
        List<Object> objects1 = new ArrayList<Object>(objects.length);
        Collections.addAll(objects1, objects);
        //objects1不需要一级参数
        AtomicInteger i= new AtomicInteger();
        while(i.get() <FIRSTPARANUM) {
            objects1.remove(0);
            i.getAndIncrement();
        }
        try {
            Class cls= Class.forName(this.getClass().getPackage().getName()+"."+className.toString());
            Constructor con= cls.getConstructor(List.class );
            Object obj=con.newInstance(objects1);
            S send= (S) obj;
            try {
                if(send.prepare()) {
                    if ( _switch) {
                        if (_testscene) {
                            Pattern pattern;
                            Matcher matcher;
                            if (mod.equals("sms")) {
                                pattern= Pattern.compile(_inner_m);
                                matcher= pattern.matcher(objects[3].toString());
                                if (matcher.find()) {
                                    //开关on+测试场景+短信+满足正则= 真实发送
                                    send.send();
                                } else {
                                    //开关on+测试场景+短信+不满足正则= 虚拟发送
                                    send.send(true);
                                }
                            } else if (mod.equals("email")) {
                                pattern= Pattern.compile(_inner_e);
                                matcher= pattern.matcher(objects[1].toString());
                                if (matcher.find()) {
                                    //开关on+测试场景+短信+满足正则= 真实发送
                                    send.send();
                                } else {
                                    //开关on+测试场景+邮件+不满足正则= 虚拟发送
                                    send.send(true);
                                }
                            }
                        } else {
                            //开关on+非测试场景
                            send.send();
                        }
                        result = new StringBuffer("{'code':'" + send.getResult().getStatusCode() + "','" + "detail':'" + send.getResult().getMessage() + "'}");
                        return result.toString();
                    } else {
                        return "{'code':'0';'detail':'switch off'}";
                    }
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "{'code':'0';'detail':'try failed'}";
    }
}
