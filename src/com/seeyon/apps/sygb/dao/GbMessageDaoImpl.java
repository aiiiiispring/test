package com.seeyon.apps.sygb.dao;

import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.JDBCAgent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取消息推送数据实现
 */
public class GbMessageDaoImpl implements GbMessageDao {
    @Override
    public FlipInfo getMessage(Integer pageNum, Integer pageSize, Long memberId, Map<String, Object> params) {
        //设置sql注入参数
        List sqlParams = new ArrayList();
        sqlParams.add(memberId);
        //sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("select " +
                "ctp_user_history_message.LINK_PARAM_0 as messageId," +
                "ctp_user_history_message.MESSAGE_CONTENT as title," +
                "ctp_user_history_message.SENDER_ID as senderId," +
                "ctp_user_history_message.RECEIVER_ID as receiverId," +
                "ctp_user_history_message.CREATION_DATE as creation_date," +
                "ctp_user_history_message.MESSAGE_CATEGORY as thirdpartyRegisterCode," +
                "cip_message_extend.PCURL as messageURL," +
                "cip_message_extend.H5URL as messageH5URL " +
                "from  " +
                "ctp_user_history_message LEFT JOIN cip_message_extend  " +
                "ON " +
                "ctp_user_history_message.LINK_PARAM_0 = cip_message_extend.THIRD_MESSAGE_ID "+
                "where ctp_user_history_message.RECEIVER_ID = ? ");
        //判断消息是否已读，没有参数默认查询所有状态，有状态进行SQL拼接。
         Object isReadTemp = params.get("isRead");
         Double isRead = null;
        if (isReadTemp != null && !String.valueOf(isReadTemp).isEmpty()) {
            isRead = Double.parseDouble(String.valueOf(isReadTemp));
            sql = sql.append("and ctp_user_history_message.IS_READ = ? ");
            sqlParams.add(isRead);
        }
        sql = sql.append(" ORDER BY creation_date DESC");
        //条件查询
        JDBCAgent jdbcAgent = new JDBCAgent();
        try {
            FlipInfo flipInfo = new FlipInfo();
            flipInfo.setPage(pageNum);
            flipInfo.setSize(pageSize);
            FlipInfo result = jdbcAgent.findByPaging(sql.toString(), sqlParams, flipInfo);
            return result;
        }catch (SQLException | BusinessException e){
                   e.printStackTrace();
        }finally {
            jdbcAgent.close();
        }
          return null;
    }
}
