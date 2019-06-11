package com.fr.plugin.sendcloud.config;

import com.fr.config.*;
import com.fr.config.holder.Conf;
import com.fr.config.holder.factory.Holders;
import com.fr.stable.StringUtils;

@Visualization(category = "Plugin-Crm-Sendcloud_Group")
public class PlatformConfig extends DefaultConfiguration {

    private static volatile PlatformConfig config = null;

    public static PlatformConfig getInstance() {
        if (config == null) {
            config = ConfigContext.getConfigInstance(PlatformConfig.class);
        }
        return config;
    }

    //userkey
    @Identifier(value = "Api_User", name = "Plugin-Crm-Sendcloud_Api_User", description = "Plugin-Crm-Sendcloud_Api_User_Description", status = Status.SHOW)
    private Conf<String> api_user = Holders.simple(StringUtils.EMPTY);

    @Identifier(value = "Api_Key", name = "Plugin-Crm-Sendcloud_Api_Key", description = "Plugin-Crm-Sendcloud_Api_Key_Description", status = Status.SHOW)
    private Conf<String> api_key = Holders.simple(StringUtils.EMPTY);

    @Identifier(value = "Sms_User", name = "Plugin-Crm-Sendcloud_Sms_User", description = "Plugin-Crm-Sendcloud_Sms_User_Description", status = Status.SHOW)
    private Conf<String> sms_user = Holders.simple(StringUtils.EMPTY);

    @Identifier(value = "Sms_Key", name = "Plugin-Crm-Sendcloud_Sms_Key", description = "Plugin-Crm-Sendcloud_Sms_Key_Description", status = Status.SHOW)
    private Conf<String> sms_key = Holders.simple(StringUtils.EMPTY);

    //switch condition
    @Identifier(value = "SwitchOn", name = "Plugin-Crm-Sendcloud_SwitchOn", description = "Plugin-Crm-Sendcloud_SwitchOn_Description", status = Status.SHOW)
    private Conf<Boolean> switchon = Holders.simple(true);

    @Identifier(value = "TestOnly", name = "Plugin-Crm-Sendcloud_TestOnly", description = "Plugin-Crm-Sendcloud_TestOnly_Description", status = Status.SHOW)
    private Conf<Boolean> testonly = Holders.simple(true);

    @Identifier(value = "EmailReg", name = "Plugin-Crm-Sendcloud_EmailReg", description = "Plugin-Crm-Sendcloud_EmailReg_Description", status = Status.SHOW)
    private Conf<String> emailreg = Holders.simple(StringUtils.EMPTY);

    @Identifier(value = "MobileReg", name = "Plugin-Crm-Sendcloud_MobileReg", description = "Plugin-Crm-Sendcloud_MobileReg_Description", status = Status.SHOW)
    private Conf<String> mobilereg = Holders.simple(StringUtils.EMPTY);

    //email register
    @Identifier(value = "RegisterEmail", name = "Plugin-Crm-Sendcloud_RegisterEmail", description = "Plugin-Crm-Sendcloud_RegisterEmail_Description", status = Status.SHOW)
    private Conf<String> registeremail = Holders.simple(StringUtils.EMPTY);

    //logdb
    @Identifier(value = "DBConnect", name = "Plugin-Crm-Sendcloud_DBConnect", description = "Plugin-Crm-Sendcloud_DBConnect_Description", status = Status.SHOW)
    private Conf<String> dbconnect = Holders.simple(StringUtils.EMPTY);

    @Identifier(value = "TableName", name = "Plugin-Crm-Sendcloud_TableName", description = "Plugin-Crm-Sendcloud_TableName_Description", status = Status.SHOW)
    private Conf<String> tablename = Holders.simple(StringUtils.EMPTY);

    //这里的getter和setter名字要和上面的conf保持一致
    public String getApi_User() {
        return api_user.get();
    }

    public void setApi_User(String var) {
        this.api_user.set(var);
    }

    public String getApi_Key() {
        return api_key.get();
    }

    public void setApi_Key(String var) {
        this.api_key.set(var);
    }

    public String getSms_User() {
        return sms_user.get();
    }

    public void setSms_User(String var) {
        this.sms_user.set(var);
    }

    public String getSms_Key() {
        return sms_key.get();
    }

    public void setSms_Key(String var) {
        this.sms_key.set(var);
    }

    public Boolean getSwitchOn(){
        return switchon.get();
    }

    public void setSwitchOn(Boolean var){
        this.switchon.set(var);
    }

    public Boolean getTestOnly(){
        return testonly.get();
    }

    public void setTestOnly(Boolean var){
        this.testonly.set(var);
    }

    public String getEmailReg() {
        return emailreg.get();
    }

    public void setEmailReg(String var) {
        this.emailreg.set(var);
    }

    public String getMobileReg() {
        return mobilereg.get();
    }

    public void setMobileReg(String var) {
        this.mobilereg.set(var);
    }

    public String getRegisterEmail() {
        return registeremail.get();
    }

    public void setRegisterEmail(String var) {
        this.registeremail.set(var);
    }

    public String getDBConnect() {
        return dbconnect.get();
    }

    public void setDBConnect(String var) {
        this.dbconnect.set(var);
    }

    public String getTableName() {
        return tablename.get();
    }

    public void setTableName(String var) {
        this.tablename.set(var);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        PlatformConfig cloned = (PlatformConfig) super.clone();
        cloned.api_user = (Conf<String>) api_user.clone();
        cloned.api_key = (Conf<String>) api_key.clone();
        cloned.sms_user = (Conf<String>) sms_user.clone();
        cloned.sms_key = (Conf<String>) sms_key.clone();
        cloned.switchon = (Conf<Boolean>) switchon.clone();
        cloned.switchon = (Conf<Boolean>) testonly.clone();
        cloned.emailreg = (Conf<String>) emailreg.clone();
        cloned.mobilereg = (Conf<String>) mobilereg.clone();
        cloned.registeremail = (Conf<String>) registeremail.clone();
        cloned.dbconnect = (Conf<String>) dbconnect.clone();
        cloned.tablename = (Conf<String>) tablename.clone();
        return cloned;
    }
}
