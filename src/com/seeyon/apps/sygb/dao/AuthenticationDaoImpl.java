package com.seeyon.apps.sygb.dao;

import com.seeyon.apps.cip.util.CIPUtil;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.po.AuthenticationPo;
import com.seeyon.apps.sygb.po.SummaryAuthenticationPo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.log.CtpLogFactory;
import com.seeyon.ctp.util.*;
import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.dao </p>
 * <p> File: AuthenticationDaoImpl.java </p>
 * <p> Module: 系统认证注册 </p>
 * <p> Description: 系统认证注册数据持久层dao实现类 </p>
 * <p> Date: 2020-4-13 15:46</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 15:46
 * @since jdk1.8
 */
public class AuthenticationDaoImpl implements AuthenticationDao {
    private static Log LOGGER = CtpLogFactory.getLog(AuthenticationDaoImpl.class);

    /**
     * 获取认证系统列表
     *
     * @param flipInfo
     * @param params
     * @return com.seeyon.ctp.util.FlipInfo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-21 18:00
     */
    @Override
    public FlipInfo getAuthenticationList(FlipInfo flipInfo, Map<String, Object> params) throws BusinessException {
        StringBuilder sb = new StringBuilder();
        sb.append("select a.id,register.id as registerId,register.app_name,register.app_code,product.product_code, a.url,a.module_type,a.way,a.is_enable,a.create_time,a.update_time");
        sb.append(" from cip_product_register product");
        sb.append(" right join thirdparty_register register on register.product_id=product.id ");
        sb.append(" inner join authentication a on a.register_id = register.id ");
        List<Object> param = new ArrayList();
        if (params != null) {
            sb.append(" where 1=1 ");
            if (params.get("condition") != null && CIPUtil.checkNotNull(params)) {
                String condition = String.valueOf(params.get("condition"));
                if ("app_name".equals(condition) || "introduction".equals(condition) || "app_code".equals(condition)) {
                    sb.append(" and register." + condition + " LIKE ?");
                    param.add("%" + SQLWildcardUtil.escape(params.get("value").toString()) + "%");
                }
                if ("product_code".equals(condition) || "product_name".equals(condition)) {
                    sb.append(" and product." + condition + " LIKE ?");
                    param.add("%" + SQLWildcardUtil.escape(params.get("value").toString()) + "%");
                }
            }
        }
        sb.append(" order by a.update_time desc");
        JDBCAgent jdbc = new JDBCAgent();

        try {
            FlipInfo var6 = jdbc.findByPaging(sb.toString(), param, flipInfo);
            return var6;
        } catch (Exception var10) {
            LOGGER.error(var10.getMessage(), var10);
        } finally {
            jdbc.close();
        }
        flipInfo.setData(new ArrayList(0));
        return flipInfo;
    }

    /**
     * 检查是否存在重名的系统PO
     *
     * @param registerId 注册系统ID
     * @param moduleType 模块类型
     * @param id         注册ID
     * @param isNew      是否新增的
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean getIsExistByRegisterId(Long registerId, GbEnum.MODULE_TYPE moduleType, String id, boolean isNew) throws BusinessException {
        Map<String, Object> params = new HashMap<>(3);
        String hql = "";
        params.put("registerId", registerId);
        params.put("moduleType", moduleType.getKey());
        if (isNew) {
            hql = "from AuthenticationPo a where a.registerId=:registerId and a.moduleType = :moduleType";
        } else {
            hql = "from AuthenticationPo a where a.registerId=:registerId and a.moduleType = :moduleType and a.id<>:id";
            params.put("id", Long.valueOf(id));
        }
        List list = DBAgent.find(hql, params);
        if (Strings.isEmpty(list)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据id获取注册系统数据
     *
     * @param id
     * @return com.seeyon.apps.sygb.po.AuthenticationPo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-15 14:50
     */
    @Override
    public AuthenticationPo getAuthenticationPoById(Long id) throws BusinessException {
        if (null == id) {
            return null;
        }
        return DBAgent.get(AuthenticationPo.class, id);
    }

    /**
     * 根据注册ID和模块类型获取系统信息
     *
     * @param registerId CIP注册ID
     * @param moduleType 模块类型
     * @return com.seeyon.apps.sygb.po.AuthenticationPo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-21 18:43
     */

    @Override
    public AuthenticationPo getAuthenticationPoByRegisterIdAndModuleType(Long registerId, Integer moduleType) throws BusinessException {
        String hql = "from AuthenticationPo a where a.registerId = :registerId and a.moduleType = :moduleType";
        Map<String, Object> params = new HashMap<>(2);
        params.put("registerId", registerId);
        params.put("moduleType", moduleType);
        List<AuthenticationPo> find = DBAgent.find(hql, params);
        if (Strings.isNotEmpty(find)) {
            return find.get(0);
        }
        return null;
    }


    /**
     * 根据summaryId获取注册系统关系表
     *
     * @param summaryId
     * @return com.seeyon.apps.sygb.po.SummaryAuthenticationPo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-15 14:56
     */
    @Override
    public SummaryAuthenticationPo getSummaryAuthenticationPoBySummaryId(Long summaryId) throws BusinessException {
        String hql = "from SummaryAuthenticationPo a where a.summaryId = :summaryId";
        Map<String, Object> params = new HashMap<>(1);
        params.put("summaryId", summaryId);
        List<SummaryAuthenticationPo> find = DBAgent.find(hql, params);
        if (Strings.isNotEmpty(find)) {
            return find.get(0);
        }
        return null;
    }

}
