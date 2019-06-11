package com.fr.plugin.sendcloud.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by yuwh on 2019/3/27
 * Description:none
 */
public class ObjectVolume {
    private static boolean  isNull(Object var){
        return var==null;
    }

    public static boolean collectionIsEmpty(Collection var){
        if( !isNull(var) ){
            return var.isEmpty();
        }
        return true;
    }

    public static boolean mapIsEmpty(Map var){
        if( !isNull(var) ){
            return var.isEmpty();
        }
        return true;
    }
}
