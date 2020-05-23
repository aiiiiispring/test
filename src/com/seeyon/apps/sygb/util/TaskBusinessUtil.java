package com.seeyon.apps.sygb.util;

import nc.vo.framework.rsa.Encode;

/**
 * 加秘解密ticket工具类
 */
public class TaskBusinessUtil {

    //ticket分割标记
    private static final String TICKET_FLAG="::";

    /**
     * 解码ticket密文
     * @param ticket
     * @return
     */
    public static String decode(String ticket){
        return new Encode().decode(ticket);
    }

    public static String encode(String MemberId,String addairId,String registercode){
              String ticket=MemberId+"::"+addairId+"::"+registercode;

        return new Encode().encode(ticket);
    }

    /**
     * 分割明文
     * @param ticket
     * @return
     */
    public static String[] spliteTicket(String ticket){
        if(ticket.contains(TICKET_FLAG)){
            return ticket.split(TICKET_FLAG);
        }else{

            String[] tickects = new String[]{ticket};
                   return tickects;
        }

    }
}
