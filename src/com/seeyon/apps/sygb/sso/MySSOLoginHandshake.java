package com.seeyon.apps.sygb.sso;

import com.seeyon.apps.sygb.util.TaskBusinessUtil;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.organization.principal.NoSuchPrincipalException;
import com.seeyon.ctp.organization.principal.PrincipalManager;
import com.seeyon.ctp.portal.sso.SSOLoginHandshakeAbstract;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 单点登录校验拦截类
 */
public class MySSOLoginHandshake extends SSOLoginHandshakeAbstract {

    private PrincipalManager principalManager= (PrincipalManager) AppContext.getBean("principalManager");

    @Override
    public String handshake(String ticket) {
        //解析ticket
        String ticketInfo = TaskBusinessUtil.decode(ticket);
        String[] info= TaskBusinessUtil.spliteTicket(ticketInfo);
        //规则校验
        if (info!=null){
            if (null==principalManager){
            }
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            return info[0];
        }

        return null;
      }



    @Override
    public void logoutNotify(String ticket) {

    }
}
