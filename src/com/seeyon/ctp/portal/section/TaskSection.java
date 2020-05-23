package com.seeyon.ctp.portal.section;

import com.seeyon.apps.cip.po.RegisterPO;
import com.seeyon.apps.cip.po.ThirdPendingPO;
import com.seeyon.apps.sygb.dao.GbThirdPendingDao;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.authenticate.domain.User;
import com.seeyon.ctp.common.i18n.ResourceUtil;
import com.seeyon.ctp.portal.section.templete.BaseSectionTemplete;
import com.seeyon.ctp.portal.section.templete.MultiRowVariableColumnTemplete;
import com.seeyon.ctp.util.Base64;
import com.seeyon.ctp.util.Datetimes;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.Strings;
import org.apache.commons.collections.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class TaskSection extends BaseSectionImpl {

    private GbThirdPendingDao gbThirdPendingDao;

    public void setGbThirdPendingDao(GbThirdPendingDao gbThirdPendingDao) {
        this.gbThirdPendingDao = gbThirdPendingDao;
    }

    @Override
    public String getId() {
        return "taskSection";
    }

    @Override
    public String getName(Map<String, String> map) {
        return "第三方待办";
    }

    @Override
    public Integer getTotal(Map<String, String> map) {

        return null;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public BaseSectionTemplete projection(Map<String, String> map) {
        MultiRowVariableColumnTemplete c = new MultiRowVariableColumnTemplete();
        User user = AppContext.getCurrentUser();
        long userId = user.getId();
        int count = 8;
        c.setDataNum(count);
        return null;
        try {
            FlipInfo fi = new FlipInfo();
            fi.setNeedTotal(false);
            fi.setSize(count);
            List<ThirdPendingPO> ps = .findPendingsByReceiverId( userId, fi);

            for(int i = 0; i < count; ++i) {
                MultiRowVariableColumnTemplete.Row row = c.addRow();
                if (!CollectionUtils.isEmpty(ps) && i < ps.size()) {
                    MultiRowVariableColumnTemplete.Cell cell = row.addCell();
                    ThirdPendingPO p = (ThirdPendingPO)ps.get(i);
                    cell.setAlt(p.getTitle());
                    cell.setCellContent(p.getTitle());
                    StringBuilder sb = new StringBuilder("/cip/message/cipMessage.do?url=");
                    sb.append(Base64.encodeString(p.getUrl()));
                    sb.append("&registerCode=");
                    sb.append("&from=P");
                    cell.setLinkURL(sb.toString());
                    MultiRowVariableColumnTemplete.Cell createDate = row.addCell();
                    createDate.setAlt(Datetimes.formatDate(p.getCreationDate()));
                    createDate.setCellContent(Datetimes.formatDate(p.getCreationDate()));
                    MultiRowVariableColumnTemplete.Cell createName = row.addCell();
                    createName.setAlt(p.getSenderName());
                    createName.setCellContent(p.getSenderName());
                    MultiRowVariableColumnTemplete.Cell classification = row.addCell();
                    classification.setAlt(Strings.isBlank(p.getClassify()) ? ResourceUtil.getString("common.toolbar.state.pending.label") : p.getClassify());
                    classification.setCellContent(p.getClassify());
                }
            }
        } catch (Exception var22) {

        }

        c.setShowBottomButton(true);

        try {
            c.addBottomButton("common_more_label", "/thirdPendingController.do?method=morePending&registerId=" + providerId + "&sectionName=" + URLEncoder.encode(this.getSectionName(map), "UTF-8"));
        } catch (UnsupportedEncodingException var21) {
        }

        return c;
    }
}
