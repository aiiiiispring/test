package com.seeyon.apps.sygb.manager;

import com.seeyon.apps.sygb.bo.GbBpResultDataBo;
import com.seeyon.ctp.common.exceptions.BusinessException;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.manager </p>
 * <p> File: ThirdPartyManager.java </p>
 * <p> Module: 调用第三方接口 </p>
 * <p> Description: [调用第三方接口类 ] </p>
 * <p> Date: 2020-4-13 16:53</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 16:53
 * @since jdk1.8
 */

public interface ThirdPartyManager {

    /**
     * 调用第三方接口推送数据
     *
     * @param gbBpResultDataBo 组装好的公文数据
     * @param memberId         处理人
     * @return void
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-16 11:15
     */
    void transferThirdInterface(GbBpResultDataBo gbBpResultDataBo, Long memberId);
}
