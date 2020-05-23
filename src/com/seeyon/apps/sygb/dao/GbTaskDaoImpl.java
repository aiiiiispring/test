package com.seeyon.apps.sygb.dao;

import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.JDBCAgent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询待办，已办数据并返回
 */
public class GbTaskDaoImpl implements GbTaskDao {
    FlipInfo result;

    @Override
    public FlipInfo getTaskInformation(Integer pageNum, Integer pageSize, String receiverAccount, Long memberId, String thirdPartyState, String affairState) {
        //创建链接
        JDBCAgent jdbc = new JDBCAgent(false);
        String sql = "select p.id,p.title,p.sender_id,p.create_date,p.pcurl,p.h5url,tr.APP_NAME,p.SENDER_NAME,om.`name`,p.app from (select tp.id,tp.title,tp.sender_id,tp.create_date,tp.pcurl,tp.h5url,tp.REGISTER_ID,tp.SENDER_NAME,tp.classify as app from thirdparty_pending tp where tp.state=? and tp.receiver_id=?  UNION all select ca.id,ca.subject,ca.SENDER_ID,ca.RECEIVE_TIME,'','','','',ca.app from ctp_affair ca where ca.state=? and ca.member_id=?) p  left join thirdparty_register tr on p.REGISTER_ID = tr.ID left join org_member om on p.SENDER_ID=om.id order by p.create_date desc";
        List param = new ArrayList();
        param.add(thirdPartyState);
        param.add(memberId);
        param.add(affairState);
        param.add(memberId);
        // 设置分页查询条件
        FlipInfo flipInfo = new FlipInfo();
        flipInfo.setPage(pageNum);
        flipInfo.setSize(pageSize);
        try {
            //分页查询数据
            result = jdbc.findByPaging(sql, param, flipInfo);
            return result;
        } catch (SQLException | BusinessException e) {
            e.printStackTrace();
        } finally {
            jdbc.close();
        }
        return result;
    }
}
