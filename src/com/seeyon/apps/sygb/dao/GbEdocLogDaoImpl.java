package com.seeyon.apps.sygb.dao;

import com.seeyon.apps.cip.util.CIPUtil;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.po.GbEdocLogPo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.log.CtpLogFactory;
import com.seeyon.ctp.util.*;
import org.apache.commons.logging.Log;

import java.text.ParseException;
import java.util.Date;
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
public class GbEdocLogDaoImpl implements GbEdocLogDao {
    private static Log LOGGER = CtpLogFactory.getLog(GbEdocLogDaoImpl.class);
    private static StringBuffer logField = new StringBuffer();

    static {
        logField.append("a.id as id,a.systemCode as systemCode,a.systemName as systemName,a.url as url,a.actionType as actionType,a.resultType as resultType,a.resultInfo as resultInfo,a.moduleType as moduleType,a.memberId as memberId,a.createTime as createTime");
    }

    /**
     * 获取国博日志列表
     *
     * @param flipInfo
     * @param map
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Map<String, Object>> getGbEdocLogList(FlipInfo flipInfo, Map<String, Object> map) throws BusinessException {
        Map<String, Object> params = new HashMap<>(1);
        StringBuffer sb = new StringBuffer();
        sb.append("select new map(").append(logField).append(")");
        sb.append(" from GbEdocLogPo a where 1=1 ");
        // 搜索区域
        if (map != null) {
            if (map.get("condition") != null && CIPUtil.checkNotNull(map)) {
                String condition = String.valueOf(map.get("condition"));
                String value = map.get("value").toString();
                if ("systemCode".equals(condition)) {
                    sb.append(" and a.systemCode like :systemCode");
                    // 特别处理 SQLWildcardUtil
                    params.put("systemCode", "%" + SQLWildcardUtil.escape(value) + "%");
                }
                if ("systemName".equals(condition)) {
                    sb.append(" and a.systemName like :systemName");
                    // 特别处理 SQLWildcardUtil
                    params.put("systemName", "%" + SQLWildcardUtil.escape(value) + "%");
                }
                if ("url".equals(condition)) {
                    sb.append(" and a.url like :url");
                    // 特别处理 SQLWildcardUtil
                    params.put("url", "%" + SQLWildcardUtil.escape(value) + "%");
                }
                if ("actionType".equals(condition) && GbEnum.ACTION_TYPE.getValue(Integer.parseInt(value)) != null) {
                    sb.append(" and a.actionType = :actionType");
                    params.put("actionType", GbEnum.ACTION_TYPE.getValue(Integer.parseInt(value)).getKey());
                }

                if ("moduleType".equals(condition) && GbEnum.MODULE_TYPE.getValue(Integer.parseInt(value)) != null) {
                    sb.append(" and a.moduleType = :moduleType");
                    params.put("moduleType", GbEnum.MODULE_TYPE.getValue(Integer.parseInt(value)).getKey());
                }
                if ("resultType".equals(condition) && GbEnum.HANDLE_RESULT.getValue(Integer.parseInt(value)) != null) {
                    sb.append(" and a.resultType = :resultType");
                    params.put("resultType", GbEnum.HANDLE_RESULT.getValue(Integer.parseInt(value)).getKey());
                }
            }

            if (map.get("memberId") != null) {
                sb.append(" and a.memberId = :memberId");
                params.put("memberId", Long.parseLong(map.get("memberId").toString()));
            }
            try {
                if (map.get("from_createDate") != null && Strings.isNotBlank(map.get("from_createDate").toString())) {
                    sb.append(" and a.createTime >= :startTime");
                    String tiemstr = map.get("from_createDate").toString();
                    Date d = DateUtil.parse(tiemstr + " 00:00:00", DateUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN);
                    params.put("startTime", d);
                }
                if (map.get("to_createDate") != null && Strings.isNotBlank(map.get("to_createDate").toString())) {
                    String tiemstr = map.get("to_createDate").toString();
                    Date d = DateUtil.parse(tiemstr + "  23:59:59", DateUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN);
                    sb.append(" and a.createTime <= :endTime");
                    params.put("endTime", d);
                }
            } catch (ParseException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        sb.append(" order by a.createTime desc");
        return DBAgent.find(sb.toString(), params, flipInfo);
    }

    /**
     * 根据ID获取PO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public GbEdocLogPo getEdocLogPoById(Long id) throws BusinessException {
        if (null == id) {
            return null;
        }
        return DBAgent.get(GbEdocLogPo.class, id);
    }
}
