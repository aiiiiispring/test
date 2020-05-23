package com.seeyon.apps.sygb.manager;

import com.seeyon.apps.govdoc.bo.SendGovdocResult;
import com.seeyon.apps.sygb.bo.GbFileBo;
import com.seeyon.apps.sygb.bo.GbFileIdBo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.workflow.event.WorkflowEventData;
import com.seeyon.v3x.services.form.bean.FormExport;

import java.util.List;
import java.util.Map;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.manager </p>
 * <p> File: GbEDocManager.java </p>
 * <p> Module: 国博公文管理接口 </p>
 * <p> Description: [ ] </p>
 * <p> Date: 2020-3-30 14:56</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-3-30 14:56
 * @since jdk1.8
 */
public interface GbDocManager {

    /**
     * 根据模板自动触发公文
     *
     * @param templateCode  模板代码
     * @param param         参数：<br />
     *                      transfertype:数据类型，默认（或者不传）为xml，还可以为json<br />
     *                      senderLoginName:发起者在OA中的登陆名<br />
     *                      data：表单数据（map），形式为：文单控件名：值<br />
     * @param contentFileId 正文附件ID
     * @param content       正文内容
     * @return com.seeyon.apps.govdoc.bo.SendGovdocResult
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-3-30 14:58
     */
    SendGovdocResult sendEDoc(String templateCode, Map<String, Object> param, Long contentFileId, String content) throws BusinessException;

    /**
     * 根据公文模板编号发起
     *
     * @param senderLoginName 发起者登录名
     * @param templateCode    公文模板编号
     * @param data
     * @return com.seeyon.apps.govdoc.bo.SendGovdocResult
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-3-30 14:59
     */
    SendGovdocResult sendEDocByTemplate(String senderLoginName, String templateCode, FormExport data, Long contentFileId, String content) throws BusinessException;

    /**
     * 根据传递的附件信息，从FTP服务器下载附件,并创建本地File文件，返回fileId集合
     *
     * @param files
     * @return com.seeyon.apps.sygb.bo.GbFileIdBo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-10 18:48
     */
    GbFileIdBo downloadFileByFtp(List<GbFileBo> files) throws BusinessException;

    /**
     * 给已有公文添加附件（ftp上下载）
     *
     * @param summaryId      公文id
     * @param subReferenceId 附件关联关系Id
     * @param gbFileIdBo     ftp文件集
     * @throws BusinessException
     */
    void addEDocAttachment(Long summaryId, Long subReferenceId, GbFileIdBo gbFileIdBo) throws BusinessException;

    /**
     * 获取文单HTML
     *
     * @param affairId
     * @return
     * @throws BusinessException
     */
    String getFormHtmlByAffairId(Long affairId) throws BusinessException;

    /**
     * 国博公文流程结束后事件
     *
     * @param data
     * @return void
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-3 14:11
     */
    void onGbProcessFinished(WorkflowEventData data);
}
