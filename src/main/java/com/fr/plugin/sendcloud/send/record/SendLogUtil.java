package com.fr.plugin.sendcloud.send.record;

import com.fr.file.DatasourceManager;
import com.fr.log.FineLoggerFactory;
import com.fr.plugin.sendcloud.config.PlatformConfig;
import org.apache.commons.lang.StringEscapeUtils;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by yuwh on 2019/4/2
 * Description:none
 */
public class SendLogUtil {
    private com.fr.data.impl.Connection _conn;
    private java.sql.Connection _con = null;
    private Statement _stmt = null;
    private PlatformConfig config= PlatformConfig.getInstance();
    private String _dbname= config.getDBConnect();
    private String _logTable= config.getTableName();
    private String[] fieldsName= new String[]{"type", "entity","tpl","time","paras","code","detail"};
    private StringBuffer sqlString= new StringBuffer();

    public SendLogUtil(){ }

    public SendLogUtil getLogger(HashMap var){
        _conn = DatasourceManager.getInstance().getConnection(_dbname);
        if(_conn != null){
            try {
                _con = _conn.createConnection();
                _stmt = _con.createStatement();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            FineLoggerFactory.getLogger().error(_dbname+" connect error");
        }
        sqlString.append("INSERT INTO "+_logTable);
        int length= fieldsName.length;
        StringBuffer keys= new StringBuffer();
        StringBuffer values= new StringBuffer();
        for(int i= 0; i< length; i++){
            if(i==length-1){
                keys.append(fieldsName[i]);
                values.append("'"+var.get(fieldsName[i])+"'");
            } else {
                keys.append(fieldsName[i]+",");
                values.append("'"+ StringEscapeUtils.escapeSql(String.valueOf(var.get(fieldsName[i])))+"',");
            }
        }
        sqlString.append(" (").append(keys).append(") VALUES (").append(values).append(")");
        return this;
    }

    public synchronized void record(){
        try {
            _stmt.execute(String.valueOf(sqlString));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (_stmt != null) {
                _stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (_con != null) {
                _con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
