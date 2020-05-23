package com.seeyon.apps.sygb.manager;

import DBstep.iMsgServer2000;
import com.seeyon.apps.edoc.constants.EdocConstant.SendType;
import com.seeyon.apps.govdoc.bo.SendGovdocResult;
import com.seeyon.apps.govdoc.helper.GovdocContentHelper;
import com.seeyon.apps.govdoc.manager.GovdocContentManager;
import com.seeyon.apps.govdoc.manager.GovdocFormManager;
import com.seeyon.apps.govdoc.manager.GovdocPubManager;
import com.seeyon.apps.govdoc.manager.GovdocSummaryManager;
import com.seeyon.apps.govdoc.option.util.EdocOptionDisplayUtil;
import com.seeyon.apps.govdoc.po.FormOptionExtend;
import com.seeyon.apps.govdoc.vo.GovdocBodyVO;
import com.seeyon.apps.govdoc.vo.GovdocNewVO;
import com.seeyon.apps.sygb.bo.GbBpDataBo;
import com.seeyon.apps.sygb.bo.GbBpResultDataBo;
import com.seeyon.apps.sygb.bo.GbFileBo;
import com.seeyon.apps.sygb.bo.GbFileIdBo;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.mapping.GbFieldMapping;
import com.seeyon.apps.sygb.po.SummaryAuthenticationPo;
import com.seeyon.apps.sygb.util.GbUtils;
import com.seeyon.apps.sygb.util.ThreadTaskUtils;
import com.seeyon.apps.sygb.vo.AuthenticationVo;
import com.seeyon.cap4.form.api.FormApi4Cap4;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.GlobalNames;
import com.seeyon.ctp.common.ModuleType;
import com.seeyon.ctp.common.affair.manager.AffairManager;
import com.seeyon.ctp.common.authenticate.domain.User;
import com.seeyon.ctp.common.constants.ApplicationCategoryEnum;
import com.seeyon.ctp.common.content.affair.constants.StateEnum;
import com.seeyon.ctp.common.content.comment.Comment;
import com.seeyon.ctp.common.content.comment.CommentManager;
import com.seeyon.ctp.common.content.mainbody.CtpContentAllBean;
import com.seeyon.ctp.common.content.mainbody.MainbodyManager;
import com.seeyon.ctp.common.content.mainbody.MainbodyType;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.filemanager.Constants;
import com.seeyon.ctp.common.filemanager.manager.AttachmentManager;
import com.seeyon.ctp.common.filemanager.manager.FileManager;
import com.seeyon.ctp.common.i18n.LocaleContext;
import com.seeyon.ctp.common.po.affair.CtpAffair;
import com.seeyon.ctp.common.po.content.CtpContentAll;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.ctp.common.po.filemanager.Attachment;
import com.seeyon.ctp.common.po.filemanager.V3XFile;
import com.seeyon.ctp.common.po.template.CtpTemplate;
import com.seeyon.ctp.common.template.manager.TemplateManager;
import com.seeyon.ctp.form.api.FormApi4Cap3;
import com.seeyon.ctp.form.bean.*;
import com.seeyon.ctp.form.util.Enums.FieldAccessType;
import com.seeyon.ctp.form.util.Enums.FormType;
import com.seeyon.ctp.form.util.Enums.SubTableField;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.services.CTPLocator;
import com.seeyon.ctp.services.ErrorServiceMessage;
import com.seeyon.ctp.services.ServiceException;
import com.seeyon.ctp.util.DateUtil;
import com.seeyon.ctp.util.FileUtil;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.json.JSONUtil;
import com.seeyon.ctp.workflow.event.WorkflowEventData;
import com.seeyon.ctp.workflow.wapi.WorkflowApiManager;
import com.seeyon.oainterface.common.PropertyList;
import com.seeyon.ocip.common.ftp.FTPUtil;
import com.seeyon.v3x.edoc.domain.EdocSummary;
import com.seeyon.v3x.edoc.manager.EdocManager;
import com.seeyon.v3x.edoc.webmodel.EdocOpinionModel;
import com.seeyon.v3x.edoc.webmodel.FormOpinionConfig;
import com.seeyon.v3x.services.flow.FlowUtil;
import com.seeyon.v3x.services.form.FormUtils;
import com.seeyon.v3x.services.form.bean.*;
import com.seeyon.v3x.services.util.SaveFormToXml;
import com.seeyon.v3x.system.signet.domain.V3xHtmDocumentSignature;
import com.seeyon.v3x.system.signet.enums.V3xHtmSignatureEnum;
import com.seeyon.v3x.system.signet.manager.V3xHtmDocumentSignatManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.manager </p>
 * <p> File: GbEDocManagerImpl.java </p>
 * <p> Module: 国博公文管理实现 </p>
 * <p> Description: [公文发起接口实现类] </p>
 * <p> Date: 2020-3-30 14:56</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-3-30 14:56
 * @since jdk1.8
 */
public class GbDocManagerImpl implements GbDocManager {

    private OrgManager orgManager;
    private TemplateManager templateManager = null;
    private FormApi4Cap3 formApi4Cap3 = null;
    private FormApi4Cap4 formApi4Cap4 = null;
    private WorkflowApiManager wapi = null;
    private FileManager fileManager = (FileManager) AppContext.getBean("fileManager");
    private final static Log logger = LogFactory.getLog(GbDocManagerImpl.class);
    private GovdocSummaryManager govdocSummaryManager = (GovdocSummaryManager) AppContext.getBean("govdocSummaryManager");
    private AttachmentManager attachmentManager = (AttachmentManager) AppContext.getBean("attachmentManager");

    public OrgManager getOrgManager() {
        return orgManager;
    }

    public void setOrgManager(OrgManager orgManager) {
        this.orgManager = orgManager;
    }

    public TemplateManager getTemplateManager() {
        if (templateManager == null) {
            templateManager = (TemplateManager) AppContext.getBean("templateManager");
        }
        return templateManager;
    }

    public FormApi4Cap3 getFormApi4Cap3() {
        if (formApi4Cap3 == null) {
            formApi4Cap3 = (FormApi4Cap3) AppContext.getBean("formApi4Cap3");
        }
        return formApi4Cap3;
    }

    public FormApi4Cap4 getFormApi4Cap4() {
        if (formApi4Cap4 == null) {
            formApi4Cap4 = (FormApi4Cap4) AppContext.getBean("formApi4Cap4");
        }
        return formApi4Cap4;
    }

    public WorkflowApiManager getWorkflowApiManager() {
        if (wapi == null) {
            wapi = (WorkflowApiManager) AppContext.getBean("wapi");
        }
        return wapi;
    }

    /**
     * 根据模板代码发起公文流程，转换数据格式
     *
     * @param templateCode  模板代码
     * @param param         参数：<br />
     *                      transfertype:数据类型，默认（或者不传）为xml，还可以为json<br />
     *                      senderLoginName:发起者在OA中的登陆名<br />
     *                      data：表单数据（map），形式为：文单控件名：值<br />
     * @param contentFileId 正文附件ID
     * @param content       正文内容
     * @return
     * @throws BusinessException
     */
    @Override
    public SendGovdocResult sendEDoc(String templateCode, Map<String, Object> param, Long contentFileId, String content) throws BusinessException {
        //默认为xml(data为XML格式)
        String transfertype = "xml";
        transfertype = (String) param.get("transfertype");
        if ("json".equals(transfertype)) {
            //将param中data的JSON 转换为XML
            param = jsonValueToXml(param, templateCode);
            if (param == null) {
                return null;
            }
        }
        String senderLoginName = (String) param.get("senderLoginName");
        //是否草稿状态
        String data = (String) param.get("data");
        //如果后续还要增加参数 都通过 Map<String,Object> relevantParam 来传递
        FormExport formExportData = null;
        //枚举值转ID
        try {
            data = enumValueToId(data, templateCode);
            if ("2.0".equals(Double.toString(FormUtils.getXmlVersion(data)))) {
                formExportData = FormUtils.xmlTransformFormExport(data);
            } else {
                formExportData = new FormExport();
                PropertyList propertyList = new PropertyList("FormExport", 1);
                propertyList.loadFromXml(data);
                formExportData.loadFromPropertyList(propertyList);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return sendEDocByTemplate(senderLoginName, templateCode, formExportData, contentFileId, content);
    }


    /**
     * 发起公文流程
     *
     * @param senderLoginName 发起者登录名
     * @param templateCode    公文模板编号
     * @param data            数据
     * @return
     * @throws BusinessException
     */
    @Override
    public SendGovdocResult sendEDocByTemplate(String senderLoginName, String templateCode, FormExport data, Long contentFileId, String content) throws BusinessException {
        CtpTemplate template = getTemplateManager().getTempleteByTemplateNumber(templateCode);
        if (template == null) {
            throw new BusinessException(ErrorServiceMessage.flowTempleExist.getErroCode() + ErrorServiceMessage.flowTempleExist.getValue() + ":" + templateCode);
        }
        //构建第一个表单字段内容
        FormExport newData = null;
        FormBean formBean = null;
        com.seeyon.cap4.form.bean.FormBean formBean4 = null;
        boolean isForm = template.getModuleType() == ModuleType.govdocSend.getKey() && Integer.valueOf(template.getBodyType()) == 20;
        if (isForm) {
            newData = data;
            formBean = getFormApi4Cap3().getFormByFormCode(template);
            formBean4 = getFormApi4Cap4().getFormByFormCode(template);
        }
        V3xOrgMember member = null;
        try {
            member = getOrgManager().getMemberByLoginName(senderLoginName);
        } catch (Exception e2) {
            logger.error("获取人员信息异常！");
            throw new BusinessException(e2);
        }
        if (member == null) {
            throw new BusinessException(ErrorServiceMessage.formImportServiceMember.getErroCode() + ErrorServiceMessage.formImportServiceMember.getValue() + ":" + senderLoginName);
        }
        long senderId = member.getId();
        //设置当前线程的当前人，为了避免后续方法取值错误
        User user = new User();
        user.setId(senderId);
        user.setName(member.getName());
        user.setLoginAccount(member.getOrgAccountId());
        user.setAccountId(user.getLoginAccount());
        user.setLocale(LocaleContext.getAllLocales().get(0));
        AppContext.putThreadContext(GlobalNames.SESSION_CONTEXT_USERINFO_KEY, user);
        if (AppContext.getRawSession() != null) {
            Object raw = AppContext.getSessionContext(com.seeyon.ctp.common.constants.Constants.SESSION_CURRENT_USER);
            if (raw != null) {
                User u = (User) raw;
                logger.error("操作被阻止，试图将会话中的当前用户" + u.getName() + "替换为" + user.getName());
            } else {
                AppContext.putSessionContext(com.seeyon.ctp.common.constants.Constants.SESSION_CURRENT_USER, user);
            }
        }
        if (isForm) {
            //检查权限检查（人员，表单，模板权限）
            if (formBean4 != null) {
                checkIsExist(newData, senderLoginName, templateCode, template, formBean4, member);
            } else {
                checkIsExist(newData, senderLoginName, templateCode, template, formBean, member);
            }
        }
        //正文类型HTML&Form
        //10HTML20FORM
        Long templateId = template.getId();
        long masterId = -1L;
        if (isForm) {
            //保存表单数据
            try {
                if (formBean4 != null) {
                    masterId = saveMasterAndSubForm(template, newData, formBean4, user);
                } else {
                    masterId = saveMasterAndSubForm(template, newData, formBean, user);
                }
            } catch (SQLException e1) {
                throw new BusinessException(ErrorServiceMessage.formExportServiceDateInfoError.getErroCode() + ErrorServiceMessage.formExportServiceDateInfoError.getValue() + ":" + e1.getMessage());
            }
        }
        GovdocPubManager govdocPubManager = (GovdocPubManager) AppContext.getBean("govdocPubManager");
        SendGovdocResult result = govdocPubManager.transSendColl(SendType.immediate, templateId, senderId, masterId, null, null, null, null, null, null, null);

        //构建第二个ctp-content_all(正文内容，如传输的正文内容为空，则默认取模板内的正文)
        CtpContentAll bodyContent = null;
        GovdocBodyVO bodyVo = new GovdocBodyVO();
        GovdocNewVO newVo = new GovdocNewVO();
        newVo.setBodyVo(bodyVo);
        newVo.setSummary(result.getSummary());
        newVo.setCurrentUser(user);
        CtpContentAll transContent = GovdocContentHelper.getTransBodyContentByModuleId(templateId);
        if (transContent != null) {
            try {
                bodyContent = (CtpContentAll) transContent.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            bodyContent.setSort(1);
        }

        if (bodyContent != null) {
            bodyContent.setIdIfNew();
            // 如果不是标准正文，以附件形式传输
            if (bodyContent.getContentType().intValue() != MainbodyType.HTML.getKey()) {
                Long fileId = null;
                // 如果第三方传递了正文的附件，则使用第三方的正文，否则，使用模板正文
                if (null != contentFileId) {
                    fileId = contentFileId;
                } else {
                    try {
                        fileId = fileManager.copyFileBeforeModify(Long.valueOf(bodyContent.getContent()));
                    } catch (Exception e) {
                        logger.info("复制正文或签章错误:" + bodyContent.getTitle(), e);
                    }
                }
                bodyContent.setContent(String.valueOf(fileId));
            } else {
                //保存新公文正文
                if (Strings.isNotBlank(content)) {
                    bodyContent.setContent(content);
                }
            }
            bodyVo.setBodyContent(bodyContent);
            GovdocContentManager govdocContentManager = (GovdocContentManager) AppContext.getBean("govdocContentManager");
            govdocContentManager.saveExchangeBodyContent(newVo);
        }
        return result;
    }

    /**
     * 权限检查（人员，表单，模板权限）
     *
     * @param templateCode
     * @param template
     * @param formBean
     * @param member
     * @throws BusinessException
     */
    private void checkIsExist(FormExport data, String loginName, String templateCode, CtpTemplate template, FormBean formBean, V3xOrgMember member) throws BusinessException {
        if (template.getWorkflowId() == null) {
            throw new BusinessException(ErrorServiceMessage.flowTempleExist.getErroCode() + ErrorServiceMessage.flowTempleExist.getValue() + ":" + templateCode);
        }

        if (formBean == null) {
            //表单模板不存在
            throw new BusinessException(ErrorServiceMessage.formImportServiceIsexit.getErroCode() + ErrorServiceMessage.formImportServiceIsexit.getValue() + ":" + templateCode);
        }
        if (formBean.getFormType() == FormType.baseInfo.getKey() || formBean.getFormType() == FormType.manageInfo.getKey()) {
            throw new BusinessException(ErrorServiceMessage.flowImportException.getErroCode() +
                    ErrorServiceMessage.flowImportException.getValue());
        }
        if (member == null) {
            throw new BusinessException(ErrorServiceMessage.formImportServiceMember.getErroCode() + ErrorServiceMessage.formImportServiceMember.getValue() + ":" + loginName);
        }
        //null退出，非表单正文退出
        String bodyType = template.getBodyType();
        if (!String.valueOf(MainbodyType.FORM.getKey()).equals(bodyType)) {
            throw new BusinessException(ErrorServiceMessage.flowValidContent.getErroCode() + ErrorServiceMessage.flowValidContent.getValue());
        }
        try {
            boolean auth = getTemplateManager().isTemplateEnabled(template.getId(), member.getId());
            if (!auth) {
                throw new BusinessException(ErrorServiceMessage.flowTempleAuth.getErroCode() + ErrorServiceMessage.flowTempleAuth.getValue() + ":" + templateCode + " " + member.getName());
            }
        } catch (BusinessException e) {
            throw new BusinessException(ErrorServiceMessage.flowTempleAuth.getErroCode() + ErrorServiceMessage.flowTempleAuth.getValue() + ":" + templateCode + " " + member.getName() + e.getMessage());
        }
        //校验数据
        //主表
        Map<String, String> masterNameValues = new HashMap<String, String>();
        for (ValueExport value : data.getValues()) {
            masterNameValues.put(value.getDisplayName(), value.getValue());
        }
        //从表
        List<Map<String, String>> slaveNameValues = new ArrayList<Map<String, String>>();
        for (SubordinateFormExport subFormExport : data.getSubordinateForms()) {

            for (RecordExport recordExport : subFormExport.getValues()) {
                Map<String, String> subDataMap = new HashMap<String, String>();
                for (ValueExport v : recordExport.getRecord()) {
                    subDataMap.put(v.getDisplayName(), v.getValue());
                }
                slaveNameValues.add(subDataMap);
            }
        }
        try {
            String viewAndOperation = getWorkflowApiManager().getNodeFormOperationName(template.getWorkflowId(), null);
            //工作流改造：试图ID.权限ID_试图ID.权限ID
            String operstionId = viewAndOperation.split("[_]")[0].split("[.]")[1];
            getFormApi4Cap3().checkInputData(formBean.getId(), -1L, Long.valueOf(operstionId), masterNameValues, slaveNameValues, -1L);
        } catch (Exception e2) {
            throw new BusinessException(ErrorServiceMessage.formExportServiceDateInfoError.getErroCode() + ErrorServiceMessage.formExportServiceDateInfoError.getValue() + ":" + e2.getMessage());
            //throw new BusinessException("校验数据出错:"+e2.getMessage());
        }
    }

    /**
     * 权限检查（人员，表单，模板权限）
     *
     * @param templateCode
     * @param template
     * @param formBean
     * @param member
     * @throws BusinessException
     */
    private void checkIsExist(FormExport data, String loginName, String templateCode, CtpTemplate template, com.seeyon.cap4.form.bean.FormBean formBean, V3xOrgMember member) throws BusinessException {
        if (template.getWorkflowId() == null) {
            throw new BusinessException(ErrorServiceMessage.flowTempleExist.getErroCode() + ErrorServiceMessage.flowTempleExist.getValue() + ":" + templateCode);
        }

        if (formBean == null) {
            //表单模板不存在
            throw new BusinessException(ErrorServiceMessage.formImportServiceIsexit.getErroCode() + ErrorServiceMessage.formImportServiceIsexit.getValue() + ":" + templateCode);
        }
        if (formBean.getFormType() == FormType.baseInfo.getKey() || formBean.getFormType() == FormType.manageInfo.getKey()) {
            throw new BusinessException(ErrorServiceMessage.flowImportException.getErroCode() +
                    ErrorServiceMessage.flowImportException.getValue());
        }
        if (member == null) {
            throw new BusinessException(ErrorServiceMessage.formImportServiceMember.getErroCode() + ErrorServiceMessage.formImportServiceMember.getValue() + ":" + loginName);
        }
        //null退出，非表单正文退出
        String bodyType = template.getBodyType();
        if (!String.valueOf(MainbodyType.FORM.getKey()).equals(bodyType)) {
            throw new BusinessException(ErrorServiceMessage.flowValidContent.getErroCode() + ErrorServiceMessage.flowValidContent.getValue());
        }
        try {
            boolean auth = getTemplateManager().isTemplateEnabled(template.getId(), member.getId());
            if (!auth) {
                throw new BusinessException(ErrorServiceMessage.flowTempleAuth.getErroCode() + ErrorServiceMessage.flowTempleAuth.getValue() + ":" + templateCode + " " + member.getName());
            }
        } catch (BusinessException e) {
            throw new BusinessException(ErrorServiceMessage.flowTempleAuth.getErroCode() + ErrorServiceMessage.flowTempleAuth.getValue() + ":" + templateCode + " " + member.getName() + e.getMessage());
        }
        //校验数据
        //主表
        Map<String, String> masterNameValues = new HashMap<String, String>();
        for (ValueExport value : data.getValues()) {
            masterNameValues.put(value.getDisplayName(), value.getValue());
        }
        //从表
        List<Map<String, String>> slaveNameValues = new ArrayList<Map<String, String>>();
        for (SubordinateFormExport subFormExport : data.getSubordinateForms()) {

            for (RecordExport recordExport : subFormExport.getValues()) {
                Map<String, String> subDataMap = new HashMap<String, String>();
                for (ValueExport v : recordExport.getRecord()) {
                    subDataMap.put(v.getDisplayName(), v.getValue());
                }
                slaveNameValues.add(subDataMap);
            }
        }
        try {
            String viewAndOperation = getWorkflowApiManager().getNodeFormOperationName(template.getWorkflowId(), null);
            //工作流改造：试图ID.权限ID_试图ID.权限ID
            String operstionId = viewAndOperation.split("[_]")[0].split("[.]")[1];
            getFormApi4Cap4().checkInputData(formBean.getId(), -1L, Long.valueOf(operstionId), masterNameValues, slaveNameValues, -1L);
        } catch (Exception e2) {
            throw new BusinessException(ErrorServiceMessage.formExportServiceDateInfoError.getErroCode() + ErrorServiceMessage.formExportServiceDateInfoError.getValue() + ":" + e2.getMessage());
            //throw new BusinessException("校验数据出错:"+e2.getMessage());
        }
    }


    private long saveMasterAndSubForm(CtpTemplate template, FormExport data,
                                      com.seeyon.cap4.form.bean.FormBean formBean, User user) throws BusinessException, SQLException {
        String operationId = getWorkflowApiManager().getNodeFormOperationName(template.getWorkflowId(), null);
        //工作流改造：试图ID.权限ID_试图ID.权限ID
        operationId = operationId.split("[_]")[0].split("[.]")[1];
        com.seeyon.cap4.form.bean.FormAuthViewBean viewBean = getFormApi4Cap4().getAuth(Long.parseLong(operationId));

        long masterid = 0;

        com.seeyon.cap4.form.bean.FormTableBean masterTableBean = formBean.getMasterTableBean();
        //处理主表数据
        Map<String, Object> mainDataMap = new LinkedHashMap<String, Object>();

        //webservice接口BEAN
        for (ValueExport value : data.getValues()) {
            if (StringUtils.isNotBlank(value.getValue())) {
                com.seeyon.cap4.form.bean.FormFieldBean fieldBean = masterTableBean.getFieldBeanByDisplay(value.getDisplayName());
                if (fieldBean != null) {
                    mainDataMap.put(fieldBean.getName(), value.getValue());
                }
            }
        }
        com.seeyon.cap4.form.bean.FormDataMasterBean masterData = com.seeyon.cap4.form.bean.FormDataMasterBean.newInstance(formBean);
        masterData.addFieldValue(mainDataMap);
        masterData.putExtraAttr("needProduceValue", "true");
        masterid = masterData.getId();
        List<com.seeyon.cap4.form.bean.FormTableBean> tableList = formBean.getSubTableBean();

        //循环该表单下所有重复表(子表)
        for (com.seeyon.cap4.form.bean.FormTableBean table : tableList) {
            String tableName = table.getTableName();
            //处理外部数据为A8bean，如果是重复项表，分区分组提交
            //            List<Map<String, Object>> subTableData = new ArrayList<Map<String, Object>>();
            for (SubordinateFormExport subFormExport : data.getSubordinateForms()) {

                for (RecordExport recordExport : subFormExport.getValues()) {
                    Map<String, Object> subDataMap = null;
                    for (ValueExport v : recordExport.getRecord()) {
                        com.seeyon.cap4.form.bean.FormFieldBean fieldBean = table.getFieldBeanByDisplay(v.getDisplayName());
                        if (fieldBean == null) {
                            continue;
                        }
//                            if (StringUtils.isNotBlank(v.getValue())) {
                        com.seeyon.cap4.form.bean.FormAuthViewFieldBean filedBean = viewBean.getFormAuthorizationField(fieldBean.getName());
                        if (subDataMap == null) {
                            subDataMap = new LinkedHashMap<String, Object>();
                        }
                        if (filedBean.getAccess().equals(FieldAccessType.add.name()) || filedBean.getAccess().equals(FieldAccessType.edit.name())) {
                            subDataMap.put(fieldBean.getName(), v.getValue());
                        }
//                            }
                    }
                    //                    subTableData.add(subDataMap);
                    if (subDataMap != null) {
                        subDataMap.put(SubTableField.formmain_id.getKey(), masterid);
                        List<com.seeyon.cap4.form.bean.FormDataSubBean> subDatas = masterData.getSubData(table.getTableName());
                        if (subDatas != null && subDatas.size() == 1 && subDatas.get(0).isEmpty()) {
                            subDatas.get(0).addFieldValue(subDataMap);
                        } else {
                            com.seeyon.cap4.form.bean.FormDataSubBean subData = new com.seeyon.cap4.form.bean.FormDataSubBean(subDataMap, table, masterData, true);
                            subData.fillNullValue();
                            masterData.addSubData(tableName, subData);
                        }
                    }

                }
            }

        }
        getFormApi4Cap4().putSessioMasterDataBean(formBean, masterData, true, true);
        getFormApi4Cap4().calcAll(formBean, masterData, viewBean, false, false, true, true);
        //getFormManager().calcAll(formBean, masterData, viewBean, false, false, true);
        getFormApi4Cap4().saveOrUpdateFormData(masterData, formBean.getId(), true);
        return masterid;
    }

    private long saveMasterAndSubForm(CtpTemplate template, FormExport data,
                                      FormBean formBean, User user) throws BusinessException, SQLException {
        String operationId = getWorkflowApiManager().getNodeFormOperationName(template.getWorkflowId(), null);
        //工作流改造：试图ID.权限ID_试图ID.权限ID
        operationId = operationId.split("[_]")[0].split("[.]")[1];
        FormAuthViewBean viewBean = getFormApi4Cap3().getAuth(Long.parseLong(operationId));

        long masterid = 0;

        FormTableBean masterTableBean = formBean.getMasterTableBean();
        //处理主表数据
        Map<String, Object> mainDataMap = new LinkedHashMap<String, Object>();

        //webservice接口BEAN
        for (ValueExport value : data.getValues()) {
            if (StringUtils.isNotBlank(value.getValue())) {
                FormFieldBean fieldBean = masterTableBean.getFieldBeanByDisplay(value.getDisplayName());
                if (fieldBean != null) {
                    mainDataMap.put(fieldBean.getName(), value.getValue());
                }
            }
        }
        FormDataMasterBean masterData = FormDataMasterBean.newInstance(formBean, viewBean);
        masterData.addFieldValue(mainDataMap);
        masterData.putExtraAttr("needProduceValue", "true");
        masterid = masterData.getId();
        List<FormTableBean> tableList = formBean.getSubTableBean();

        //循环该表单下所有重复表(子表)
        for (FormTableBean table : tableList) {
            String tableName = table.getTableName();
            //处理外部数据为A8bean，如果是重复项表，分区分组提交
            //            List<Map<String, Object>> subTableData = new ArrayList<Map<String, Object>>();
            for (SubordinateFormExport subFormExport : data.getSubordinateForms()) {

                for (RecordExport recordExport : subFormExport.getValues()) {
                    Map<String, Object> subDataMap = null;
                    for (ValueExport v : recordExport.getRecord()) {
                        FormFieldBean fieldBean = table.getFieldBeanByDisplay(v.getDisplayName());
                        if (fieldBean == null) {
                            continue;
                        }
//                            if (StringUtils.isNotBlank(v.getValue())) {
                        FormAuthViewFieldBean filedBean = viewBean.getFormAuthorizationField(fieldBean.getName());
                        if (subDataMap == null) {
                            subDataMap = new LinkedHashMap<String, Object>();
                        }
                        if (filedBean.getAccess().equals(FieldAccessType.add.name()) || filedBean.getAccess().equals(FieldAccessType.edit.name())) {
                            subDataMap.put(fieldBean.getName(), v.getValue());
                        }
//                            }
                    }
                    //                    subTableData.add(subDataMap);
                    if (subDataMap != null) {
                        subDataMap.put(SubTableField.formmain_id.getKey(), masterid);
                        List<FormDataSubBean> subDatas = masterData.getSubData(table.getTableName());
                        if (subDatas != null && subDatas.size() == 1 && subDatas.get(0).isEmpty()) {
                            subDatas.get(0).addFieldValue(subDataMap);
                        } else {
                            FormDataSubBean subData = new FormDataSubBean(subDataMap, table, masterData, true);
                            subData.fillNullValue();
                            masterData.addSubData(tableName, subData);
                        }
                    }

                }
            }

        }
        getFormApi4Cap3().putSessioMasterDataBean(formBean, masterData, true, true);
        getFormApi4Cap3().calcAll(formBean, masterData, viewBean, false, false, true);
        getFormApi4Cap3().saveOrUpdateFormData(masterData, formBean.getId());
        return masterid;
    }


    /**
     * 将枚举值转为ID
     *
     * @param data
     * @param templateCode
     * @return java.lang.String
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-3-30 16:06
     */
    private String enumValueToId(String data, String templateCode) {
        CtpTemplate template = templateManager.getTempleteByTemplateNumber(templateCode);
        if (template == null) {
            return data;
        }
        try {
            boolean isForm = template.getModuleType() == ModuleType.govdocSend.getKey() && Integer.valueOf(template.getBodyType()) == 20;

            if (isForm) {
                FormBean formBean = getFormApi4Cap3().getFormByFormCode(template);
                // 根据模板编号、表示名称 找出对应的字段
                List<FormFieldBean> allFields = formBean.getAllFieldBeans();
                Map<String, FormFieldBean> columnMap = new LinkedHashMap<String, FormFieldBean>();
                Map<String, FormFieldBean> parentFieldMap = new LinkedHashMap<String, FormFieldBean>();
                Map<String, String> enumFieldValueMap = new LinkedHashMap<String, String>();
                for (FormFieldBean field : allFields) {
                    if (field.getEnumId() != 0) {
                        if (StringUtils.isNotEmpty(field.getEnumParent())) {
                            for (FormFieldBean bean : allFields) {
                                if (field.getEnumParent().equals(bean.getName())) {
                                    parentFieldMap.put(field.getDisplay(), bean);
                                    break;
                                }
                            }
                        }
                        columnMap.put(field.getDisplay(), field);
                    }
                }
                Document document = DocumentHelper.parseText(data);
                Element formExportEle = (Element) document.selectSingleNode("/formExport");
                for (String columnName : columnMap.keySet()) {
                    List<Element> valueExportList = formExportEle.selectNodes("./values/column[@name='" + columnName + "']");
                    if (valueExportList != null && valueExportList.size() > 0) {
                        for (Element ele : valueExportList) {
                            Element valueEle = ele.element("value");
                            String value = (String) valueEle.getData();
                            if (StringUtils.isNotEmpty(value)) {
                                String enumValue = getEnumValue(columnMap.get(columnName), value, parentFieldMap, enumFieldValueMap);
                                enumFieldValueMap.put(columnName, enumValue);
                                valueEle.setText(enumValue);
                            }
                        }
                    }
                    List<Element> subordinateFormExportList = formExportEle.selectNodes("./subForms/subForm");
                    if (subordinateFormExportList != null && subordinateFormExportList.size() > 0) {
                        for (Element subEle : subordinateFormExportList) {
                            List<Element> recordExportList = subEle.selectNodes("./values/row");
                            for (Element recordEle : recordExportList) {
                                Element ele = (Element) recordEle.selectSingleNode("column[@name='" + columnName + "']");
                                if (ele != null) {
                                    Element valueEle = ele.element("value");
                                    String value = (String) valueEle.getData();
                                    if (StringUtils.isNotEmpty(value)) {
                                        String enumValue = getEnumValue(columnMap.get(columnName), value, parentFieldMap, enumFieldValueMap);
                                        enumFieldValueMap.put(columnName, enumValue);
                                        valueEle.setText(enumValue);
                                    }
                                }

                            }
                        }
                    }
                }
                return document.asXML();
            }

        } catch (Exception e) {
            logger.error(e);
        }
        return data;
    }

    /**
     * 查询字段对应的枚举类型，取出ID
     *
     * @param fieldBean
     * @param value
     * @param parentFieldMap
     * @param enumValueMap
     * @return java.lang.String
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-3-30 15:39
     */
    private String getEnumValue(FormFieldBean fieldBean, String value, Map<String, FormFieldBean> parentFieldMap, Map<String, String> enumValueMap) {
        EnumManager enumManager = null;
        try {
            enumManager = CTPLocator.getInstance().lookup(EnumManager.class);
            List<CtpEnumItem> enumValues = enumManager.getEmumItemByEmumId(fieldBean.getEnumId());
            int level = fieldBean.getEnumLevel();
            for (CtpEnumItem item : enumValues) {
                if ((value.equals(item.getEnumvalue()) || value.equals(Long.toString(item.getId()))) && level == item.getLevelNum()) {
                    FormFieldBean parentFildBean = parentFieldMap.get(fieldBean.getDisplay());
                    if (parentFildBean != null) {

                        String strParentId = String.valueOf(item.getParentId());
                        if (strParentId.equals(enumValueMap.get(parentFildBean.getDisplay()))) {
                            return String.valueOf(item.getId());
                        }
                    } else {
                        return String.valueOf(item.getId());
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        logger.error("请检查oa系统的枚举值是否存在 value=" + value + "字段：" + fieldBean.getDisplay());
        return value;

    }

    /**
     * 将参数数据，转换为流程模板格式的数据
     *
     * @param param        参数数据
     * @param templateCode 模板编码
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-3-30 17:48
     */
    private Map<String, Object> jsonValueToXml(Map<String, Object> param, String templateCode) {
        if (templateManager == null) {
            templateManager = (TemplateManager) AppContext.getBean("templateManager");
        }
        String templateXml = "";

        try {
            // 获取模板的XML格式
            templateXml = getTemplateXml(templateCode);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

        Object data = param.get("data");

        CtpTemplate template = templateManager.getTempleteByTemplateNumber(templateCode);
        if (template == null || templateXml.length() == 0 || "".equals(templateXml)) {
            return null;
        }

        try {
            //将templateXml从表去除，然后根据sub信息重新插入
            List<Element> subDellist = new ArrayList<Element>();
            Document document = DocumentHelper.parseText(templateXml);
            Element priceTmp = document.getRootElement().element("subForms").element("subForm");
            subDellist = document.selectNodes("//subForms/subForm");
            //删除节点
            for (int i = 0; i < subDellist.size(); i++) {
                Element delSub = subDellist.get(i);
                delSub.getParent().remove(delSub);
            }
            templateXml = document.asXML();

            //将json的打他转换对象
            Map jsonObject;
            if (data instanceof Map || data instanceof LinkedHashMap) {
                jsonObject = (Map) data;
            } else {
                jsonObject = (Map) JSONUtil.parseJSONString(String.valueOf(data));
            }
            Object sub1 = jsonObject.get("sub");
            List sub = null;
            if (sub1 instanceof List) {
                sub = (List) sub1;
            } else {
                sub = (List) JSONUtil.parseJSONString(String.valueOf(sub1));
            }
            boolean isForm = template.getModuleType() == ModuleType.govdocSend.getKey() && Integer.valueOf(template.getBodyType()) == 20;
            if (isForm) {
                FormApi4Cap4 formApi4Cap4 = (FormApi4Cap4) AppContext.getBean("formApi4Cap4");
                com.seeyon.cap4.form.bean.FormBean fromBean4 = formApi4Cap4.getFormByFormCode(template);
                if (fromBean4 != null) {
                    // 根据模板编号、表示名称 找出对应的字段
                    List<com.seeyon.cap4.form.bean.FormFieldBean> allFields = fromBean4.getAllFieldBeans();
                    List<com.seeyon.cap4.form.bean.FormFieldBean> mainFields = new ArrayList<com.seeyon.cap4.form.bean.FormFieldBean>();
                    List<com.seeyon.cap4.form.bean.FormFieldBean> subFields = new ArrayList<com.seeyon.cap4.form.bean.FormFieldBean>();
                    for (com.seeyon.cap4.form.bean.FormFieldBean field : allFields) {
                        if (field.isMasterField()) {
                            mainFields.add(field);
                        } else {
                            subFields.add(field);
                        }
                    }
                    document = DocumentHelper.parseText(templateXml);
                    Element formExportEle = (Element) document.selectSingleNode("/formExport");
                    //循环主表字段
                    for (com.seeyon.cap4.form.bean.FormFieldBean field : mainFields) {
                        List<Element> valueExportList = formExportEle.selectNodes("./values/column[@name='" + field.getDisplay() + "']");
                        if (valueExportList != null && valueExportList.size() > 0) {
                            for (Element ele : valueExportList) {
                                Element valueEle = ele.element("value");
                                if (jsonObject.get(field.getDisplay()) != null) {
                                    valueEle.setText(jsonObject.get(field.getDisplay()).toString());
                                }
                            }
                        }
                    }
                    //根据sub来插入subForm
                    Element subForms = document.getRootElement().element("subForms");
                    List subFormlist = subForms.elements();
                    //循环从表字段
                    if (sub != null && sub.size() > 0) {
                        for (int i = 0; i < sub.size(); i++) {
                            //创建元素
                            Element subForm = new BaseElement("subForm");
                            Element values = subForm.addElement("values");
                            Element row = values.addElement("row");
                            for (com.seeyon.cap4.form.bean.FormFieldBean field : subFields) {
                                Map jsonSub;
                                if (sub.get(i) instanceof Map) {
                                    jsonSub = (Map) sub.get(i);
                                } else {
                                    jsonSub = (Map) JSONUtil.parseJSONString(sub.get(i).toString());
                                }
                                if (jsonSub.get(field.getDisplay()) != null) {
                                    Element column = row.addElement("column");
                                    column.addAttribute("name", field.getDisplay());
                                    Element value = column.addElement("value");
                                    value.setText(jsonSub.get(field.getDisplay()).toString());
                                }
                            }
                            subFormlist.add(i, subForm);
                        }
                    }
                } else {
                    FormBean formBean = getFormApi4Cap3().getFormByFormCode(template);
                    // 根据模板编号、表示名称 找出对应的字段
                    List<FormFieldBean> allFields = formBean.getAllFieldBeans();
                    List<FormFieldBean> mainFields = new ArrayList<FormFieldBean>();
                    List<FormFieldBean> subFields = new ArrayList<FormFieldBean>();
                    for (FormFieldBean field : allFields) {
                        if (field.isMasterField()) {
                            mainFields.add(field);
                        } else {
                            subFields.add(field);
                        }
                    }
                    document = DocumentHelper.parseText(templateXml);
                    Element formExportEle = (Element) document.selectSingleNode("/formExport");
                    //循环主表字段
                    for (FormFieldBean field : mainFields) {
                        List<Element> valueExportList = formExportEle.selectNodes("./values/column[@name='" + field.getDisplay() + "']");
                        if (valueExportList != null && valueExportList.size() > 0) {
                            for (Element ele : valueExportList) {
                                Element valueEle = ele.element("value");
                                if (jsonObject.get(field.getDisplay()) != null) {
                                    valueEle.setText(jsonObject.get(field.getDisplay()).toString());
                                }
                            }
                        }
                    }
                    //根据sub来插入subForm
                    Element subForms = document.getRootElement().element("subForms");
                    List subFormslist = subForms.elements();
                    //循环从表字段
                    if (sub != null && sub.size() > 0) {
                        for (int i = 0; i < sub.size(); i++) {
                            //创建元素
                            Element subForm = new BaseElement("subForm");
                            Element values = subForm.addElement("values");
                            Element row = values.addElement("row");
                            for (FormFieldBean field : subFields) {
                                Map jsonSub = null;
                                if (sub.get(i) instanceof Map) {
                                    jsonSub = (Map) sub.get(i);
                                } else {

                                    jsonSub = (Map) JSONUtil.parseJSONString(sub.get(i).toString());
                                }
                                if (jsonSub.get(field.getDisplay()) != null) {
                                    Element column = row.addElement("column");
                                    column.addAttribute("name", field.getDisplay());
                                    Element value = column.addElement("value");
                                    value.setText(jsonSub.get(field.getDisplay()).toString());
                                }
                            }
                            subFormslist.add(i, subForm);
                        }
                    }
                }

                param.put("data", document.asXML());
                return param;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据模板编码获取XML格式化数据
     *
     * @param templateCode
     * @return java.lang.String
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-3-30 17:48
     */
    private String getTemplateXml(String templateCode) throws ServiceException {
        CtpTemplate template = getTemplateManager().getTempleteByTemplateNumber(templateCode);
        String result = "";
        if (template == null || template.getWorkflowId() == null) {
            throw new ServiceException(ErrorServiceMessage.flowTempleExist.getErroCode(), ErrorServiceMessage.flowTempleExist.getValue() + ":" + templateCode);
        }
        String bodyType = template.getBodyType();
        if (bodyType.equals(String.valueOf(MainbodyType.FORM.getKey()))) {
            FormExport export = new FormExport();

            FormBean formBean;
            com.seeyon.cap4.form.bean.FormBean formBean4 = null;
            try {
                formBean4 = getFormApi4Cap4().getFormByFormCode(template);
            } catch (BusinessException e1) {
                logger.info("not in cap4", e1);
            }
            if (formBean4 != null) {
                List<DefinitionExport> define = new ArrayList<DefinitionExport>();
                List<SubordinateFormExport> subordinateFormExport = new ArrayList<SubordinateFormExport>();
                export.setSubordinateForms(subordinateFormExport);
                com.seeyon.cap4.form.bean.FormTableBean masterTableBean = formBean4.getMasterTableBean();

                export.setFormName(masterTableBean.getTableName());
                export.setDefinitions(define);

                List<com.seeyon.cap4.form.bean.FormFieldBean> fieldBean = masterTableBean.getFields();
                for (com.seeyon.cap4.form.bean.FormFieldBean field : fieldBean) {
                    FlowUtil.getDefinition(field, define);
                    logger.debug(field.getDisplay() + " " + field.getFieldType() + " ");
                }

                //获取所有子表
                List<com.seeyon.cap4.form.bean.FormTableBean> subtalbes = formBean4.getSubTableBean();
                for (com.seeyon.cap4.form.bean.FormTableBean formTableBean : subtalbes) {
                    //一个子表的定义
                    SubordinateFormExport sub = new SubordinateFormExport();
                    //子表总字段定义
                    List<DefinitionExport> subDefine = new ArrayList<DefinitionExport>();
                    //子表的一条数据记录
                    sub.setDefinitions(subDefine);
                    subordinateFormExport.add(sub);
                    List<com.seeyon.cap4.form.bean.FormFieldBean> subfieldBeans = formTableBean.getFields();
                    for (com.seeyon.cap4.form.bean.FormFieldBean subFieldValue : subfieldBeans) {
                        FlowUtil.getDefinition(subFieldValue, subDefine);
                        logger.debug(subFieldValue.getDisplay() + " " + subFieldValue.getFieldType() + " ");
                    }
                }
            } else {
                try {
                    formBean = getFormApi4Cap3().getFormByFormCode(template);
                } catch (BusinessException e) {
                    throw new ServiceException(-1, e.getLocalizedMessage());
                }
                List<DefinitionExport> define = new ArrayList<DefinitionExport>();
                List<SubordinateFormExport> subordinateFormExport = new ArrayList<SubordinateFormExport>();
                export.setSubordinateForms(subordinateFormExport);
                FormTableBean masterTableBean = formBean.getMasterTableBean();

                export.setFormName(masterTableBean.getTableName());
                export.setDefinitions(define);

                List<FormFieldBean> fieldBean = masterTableBean.getFields();
                for (FormFieldBean field : fieldBean) {
                    FlowUtil.getDefinition(field, define);
                    logger.debug(field.getDisplay() + " " + field.getFieldType() + " ");
                }

                //获取所有子表
                List<FormTableBean> subTableBeans = formBean.getSubTableBean();
                for (FormTableBean formTableBean : subTableBeans) {
                    //一个子表的定义
                    SubordinateFormExport sub = new SubordinateFormExport();
                    //子表总字段定义
                    List<DefinitionExport> subDefine = new ArrayList<DefinitionExport>();
                    //子表的一条数据记录
                    sub.setDefinitions(subDefine);
                    subordinateFormExport.add(sub);
                    List<FormFieldBean> subfieldBeans = formTableBean.getFields();
                    for (FormFieldBean subFieldValue : subfieldBeans) {
                        FlowUtil.getDefinition(subFieldValue, subDefine);
                        logger.debug(subFieldValue.getDisplay() + " " + subFieldValue.getFieldType() + " ");
                    }
                }
            }
            result = toXml(export);
        }
        return result;
    }

    private String toXml(FormExport export) {
        StringWriter writer = new StringWriter();
        try {
            SaveFormToXml.getInstance().saveXMLToStream(writer, export);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return writer.toString();

    }

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
    @Override
    public GbFileIdBo downloadFileByFtp(List<GbFileBo> files) throws BusinessException {
        if (Strings.isEmpty(files)) {
            return null;
        }
        //将FTP文件传至应用内
        FTPUtil ftp;
        try {
            ftp = FTPUtil.newInstance(GbFieldMapping.GB_FTP_URL, GbFieldMapping.GB_FTP_PORT, GbFieldMapping.GB_FTP_USER, GbFieldMapping.GB_FTP_PWD);
        } catch (IOException e) {
            throw new BusinessException("FTP连接失败：" + e.getMessage(), e);
        }
        FileUtil.makeDir(GbFieldMapping.gbTempPath);
        GbFileBo gbFile;
        String localFile;
        //下载文件
        for (int i = 0; i < files.size(); i++) {
            gbFile = files.get(i);
            //构建本地文件保存目录
            localFile = GbFieldMapping.gbTempPath + File.separator + System.currentTimeMillis() + gbFile.getFileSuffix();
            try {
                if (ftp.download(gbFile.getFtpFilePath(), localFile)) {
                    gbFile.setLocalPath(localFile);
                }
            } catch (IOException e) {
                throw new BusinessException("FTP下载文件失败：" + e.getMessage(), e);
            }
        }
        // 表单字段附件（报批附件）
        List<Long> formFieldFileIds = new ArrayList<>();
        // 流程附件
        List<Long> summaryFileIds = new ArrayList<>();

        Long contentFileId = null;
        //保存到系统中
        for (int i = 0; i < files.size(); i++) {
            gbFile = files.get(i);
            // 创建新的文件（应用类型放form,edoc类型显示不出来附件）
            File file = new File(gbFile.getLocalPath());
            ApplicationCategoryEnum applicationCategoryEnum;
            // 正文附件
            if (GbEnum.FILE_TYPE.CONTENT.getKey().equals(gbFile.getFileType())) {
                applicationCategoryEnum = ApplicationCategoryEnum.collaboration;
                V3XFile v3XFile = fileManager.save(file, applicationCategoryEnum, gbFile.getFileName(), new Date(), true);
                contentFileId = v3XFile.getId();
            } else {
                // 流程或表单附件
                applicationCategoryEnum = ApplicationCategoryEnum.form;
                V3XFile v3XFile = fileManager.save(file, applicationCategoryEnum, gbFile.getFileName(), new Date(), true);
                //判断附件类型
                if (GbEnum.FILE_TYPE.FIELD.getKey().equals(gbFile.getFileType())) {
                    formFieldFileIds.add(v3XFile.getId());
                } else {
                    summaryFileIds.add(v3XFile.getId());
                }
            }
        }

        //清理文件
        for (int i = 0; i < files.size(); i++) {
            gbFile = files.get(i);
            try {
                //删除本地文件
                FileUtil.deleteFile(new File(gbFile.getLocalPath()));
                //删除ftp文件
                //ftp.deleteFile(gbFile.getFileContents());
            } catch (Exception e) {
                //即使删除失败，此处也不影响正常业务逻辑，日志输出
                logger.error("文件删除失败：" + e.getMessage(), e);
            }
        }
        //关闭连接
        if (ftp != null) {
            ftp.disconnect();
            ftp = null;
        }
        GbFileIdBo gbFileIdBo = new GbFileIdBo();
        gbFileIdBo.setContentFileId(contentFileId);
        gbFileIdBo.setFormFieldFileIds(formFieldFileIds);
        gbFileIdBo.setSummaryFileIds(summaryFileIds);
        return gbFileIdBo;
    }

    /**
     * 给已有公文添加附件（ftp上下载）
     *
     * @param summaryId      公文id
     * @param subReferenceId 附件关联关系Id
     * @param gbFileIdBo     附件集合辅助类
     * @throws BusinessException
     */
    @Override
    public void addEDocAttachment(Long summaryId, Long subReferenceId, GbFileIdBo gbFileIdBo) throws BusinessException {
        //空判断
        if (null == summaryId || -1L == summaryId) {
            return;
        }
        if (null == gbFileIdBo) {
            return;
        }
        //判断summaryId是否存在
        if (null == govdocSummaryManager.getSummaryById(summaryId)) {
            return;
        }
        // 表单字段附件（报批附件）
        List<Long> formFieldFileIds = gbFileIdBo.getFormFieldFileIds();
        // 流程附件
        List<Long> summaryFileIds = gbFileIdBo.getSummaryFileIds();

        // 保存表表单字段附件
        if (CollectionUtils.isNotEmpty(formFieldFileIds)) {
            Long[] formFieldFileIdArr = new Long[formFieldFileIds.size()];
            formFieldFileIds.toArray(formFieldFileIdArr);
            // 与公文表单字段关联起来（应用类型放form,edoc类型显示不出来附件）
            attachmentManager.create(formFieldFileIdArr, ApplicationCategoryEnum.form, summaryId, subReferenceId);
        }
        // 保存流程附件
        if (CollectionUtils.isNotEmpty(summaryFileIds)) {
            Long[] summaryFileIdArr = new Long[summaryFileIds.size()];
            summaryFileIds.toArray(summaryFileIdArr);
            // 与公文流程关联起来（应用类型放form,edoc类型显示不出来附件）
            attachmentManager.create(summaryFileIdArr, ApplicationCategoryEnum.form, summaryId, summaryId);
        }
    }


    private EdocManager edocManager = (EdocManager) AppContext.getBean("edocManager");
    private MainbodyManager ctpMainbodyManager = (MainbodyManager) AppContext.getBean("ctpMainbodyManager");
    private CommentManager ctpCommentManager = (CommentManager) AppContext.getBean("ctpCommentManager");
    private V3xHtmDocumentSignatManager htmSignetManager = (V3xHtmDocumentSignatManager) AppContext.getBean("htmSignetManager");
    private static Pattern fieldSpanPatt = Pattern.compile("<span[\\s]+id=\"[\\S]{9}_span\"[\\s\\S]*?>[\\s\\S]*?</span></span>", 10);
    private AffairManager affairManager = (AffairManager) AppContext.getBean("affairManager");
    private GovdocFormManager govdocFormManager = (GovdocFormManager) AppContext.getBean("govdocFormManager");


    /**
     * 获取文单HTML
     *
     * @param affairId
     * @return
     * @throws BusinessException
     */
    @Override
    public String getFormHtmlByAffairId(Long affairId) throws BusinessException {
        // 根据summaryId 获取 summary 数据
        CtpAffair affair = affairManager.get(affairId);
        EdocSummary edocSummary = edocManager.getEdocSummaryById(affair.getObjectId(), false);
        Map<String, Object> vomMap = new HashMap();
        vomMap.put("formAppid", edocSummary.getFormAppid());
        vomMap.put("govdocType", edocSummary.getGovdocType());
        String rightId = govdocFormManager.getGovdocFormViewRight(vomMap, affair);
        //把 rightId 加入 session 防止权限验证
        //String[] rightIds = rightId.split("\\.");
        Set<Long> a = (HashSet) AppContext.getSessionContext("myRightSetName");
        if (a == null) {
            a = new HashSet<>();
        }
//        for (String r : rightIds) {
//            String[] rs = r.split("\\_");
//            for (String ts : rs){
//                a.add(Long.parseLong(ts));
//            }
//        }
        AppContext.putSessionContext("myRightSetName", a);

        List<CtpContentAllBean> contentList = ctpMainbodyManager.transContentViewResponse(ModuleType.edoc, affair.getObjectId(), 2, rightId, 0, -1L);
        String utf8Meta = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
        //TODO 需要指定视图，需要取哪个视图传递给第三方，此处默认显示第一个
        String htmlContent = utf8Meta + contentList.get(0).getContentHtml();
        // 获取公文文单格式
        FormOptionExtend formOptionExtend = govdocFormManager.getFormOptionExtend(edocSummary.getFormAppid());
        FormOpinionConfig displayConfig = govdocFormManager.getFormOpinionConfig(formOptionExtend);
        Map<String, EdocOpinionModel> maps = govdocFormManager.getGovdocOpinion(edocSummary.getFormAppid(), edocSummary, displayConfig);
        List<Comment> commentList = ctpCommentManager.getCommentList(ModuleType.edoc, edocSummary.getId());
        List<V3xHtmDocumentSignature> signatuers = this.htmSignetManager.findBySummaryIdAndType(edocSummary.getId(), V3xHtmSignatureEnum.HTML_SIGNATURE_DOCUMENT.getKey());
        Map<String, Object> strMap = EdocOptionDisplayUtil.convertOpinionToString(maps, displayConfig, affair, true, signatuers, commentList);
        //获取所有的key
        Set<String> keys = strMap.keySet();
        Matcher matcherSpan = fieldSpanPatt.matcher(htmlContent);
        while (matcherSpan.find()) {
            String spanNode = matcherSpan.group(0);
            for (String key : keys) {
                Integer isKey = spanNode.indexOf(key);
                if (isKey != -1) {
                    // 去除后面的 标签
                    String newSpanNode = spanNode.substring(0, spanNode.length() - "</span></span>".length());
                    String oldSpanStyle = "style=\"border-color:#cccccc;\"";
                    //  添加样式
                    String spanNodeStyle = "style=\"border: 0px; height: auto; min-height: 0px; margin-bottom: 5px;\" contenteditable=\"false\"";
                    // 替换样式
                    newSpanNode = newSpanNode.replace(oldSpanStyle, spanNodeStyle);
                    // 组装意见及签批
                    Object opinion = strMap.get(key);
                    opinion = this.convertSignToString(key, opinion.toString(), signatuers);
                    newSpanNode = newSpanNode + opinion + "</span></span>";
                    htmlContent = htmlContent.replace(spanNode, newSpanNode);
                }
            }
        }
        return htmlContent;
    }

    /**
     * 转换签章图片信息
     *
     * @param key
     * @param opinions
     * @param signatuers
     * @return
     * @throws BusinessException
     */
    public String convertSignToString(String key, String opinions, List<V3xHtmDocumentSignature> signatuers) throws BusinessException {
        StringBuilder newOpinions = new StringBuilder();
        //拆分开公文意见
        String[] opinionList = opinions.split("<div");
        for (int i = 0; i < opinionList.length; i++) {
            String opinion = opinionList[i];
            if (Strings.isBlank(opinion)) {
                continue;
            }
            //先拼装头
            newOpinions.append("<div ").append(opinion);
            for (V3xHtmDocumentSignature signature : signatuers) {
                String fieldName = signature.getFieldName();
                Long affairId = signature.getAffairId();
                if (opinion.indexOf(affairId.toString()) > -1 && fieldName.indexOf(key) > -1) {
                    byte[] pictureValue = null;
                    String fieldValue = signature.getFieldValue();
                    iMsgServer2000 msgObj = new iMsgServer2000();
                    if (Strings.isNotBlank(fieldValue)) {
                        pictureValue = msgObj.LoadRevisionAsImgByte(fieldValue);
                    }
                    BASE64Encoder encoder = new BASE64Encoder();
                    String pictureBase = encoder.encode(pictureValue);
                    //加入签批的 html 标签
                    String pictureHtml = "<img src=\"data:image/jpg;base64," + pictureBase + "\" </img>";
                    newOpinions.append(pictureHtml);
                }
            }
        }
        return newOpinions.toString();
    }

    private CommentManager commentManager = (CommentManager) AppContext.getBean("ctpCommentManager");
    private AuthenticationManager authenticationManager;

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private ThirdPartyManager thirdPartyManager;

    public void setThirdPartyManager(ThirdPartyManager thirdPartyManager) {
        this.thirdPartyManager = thirdPartyManager;
    }

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
    @Override
    public void onGbProcessFinished(WorkflowEventData data) {

        try {
            EdocSummary summary = (EdocSummary) data.getSummaryObj();
            // 通过summaryId查找对应的系统编码信息
            SummaryAuthenticationPo summaryAuthenticationPo = authenticationManager.getSummaryAuthenticationPoBySummaryId(summary.getId());
            if (null == summaryAuthenticationPo) {
                return;
            }
            // 通过系统ID获取注册系统信息，查看是否无效或未开启
            AuthenticationVo authenticationVo = authenticationManager.getAuthenticationVoById(summaryAuthenticationPo.getAuthenticationId().toString());
            if (null == authenticationVo
                    || null == authenticationVo.getIsEnable()
                    || GbEnum.EnableEnum.NO.getKey().equals(authenticationVo.getIsEnable())) {
                return;
            }
            // 获取待办集合
            List<CtpAffair> affairs = affairManager.getAffairs(summary.getId(), StateEnum.col_done);
            if (CollectionUtils.isEmpty(affairs)) {
                return;
            }
            // 正文数据
            Map<String, Object> businessData = data.getBusinessData();
            GbBpDataBo dataBo = new GbBpDataBo();
            dataBo.setSummaryId(summary.getId());
            dataBo.setTitle(summary.getSubject());
            //所有需要上传的附件
            List<GbFileBo> ftpFiles = new LinkedList<>();
            //附件要上传的的ftp文件夹
            String targetFtpDir = "/G6/" + authenticationVo.getAppCode() + "/" + DateUtil.getYear() + "/" + DateUtil.getMonth() + "/" + DateUtil.getDay() + "/";
            //将表单文单里的内容，赋值给对应的字段
            GbUtils.setDataBo(dataBo, businessData, GbFieldMapping.gb_bp_table_map);
            //将枚举ID转换
            GbUtils.dealEnumVal(dataBo);
            //将正文附件导出
            GbUtils.dealContent(dataBo, ftpFiles, targetFtpDir);
            // 获取HTML文单内容
            String edocHtmlContent = getFormHtmlByAffairId(affairs.get(0).getId());
            //将文单转PDF导出
            GbUtils.dealHtmlContentFile(dataBo, ftpFiles, summary.getSubject(), edocHtmlContent, targetFtpDir);
            //获取所有意见
            List<Comment> commentList = commentManager.getCommentAllByModuleId(ModuleType.edoc, summary.getId());
            //获取所有相关文档、附件
            List<Attachment> attachmentList = attachmentManager.getByReference(summary.getId());
            if (Strings.isNotEmpty(attachmentList)) {
                for (int j = 0; j < attachmentList.size(); j++) {
                    Attachment attachment = attachmentList.get(j);
                    //文件，就是附件
                    if (Constants.ATTACHMENT_TYPE.FILE.ordinal() == attachment.getType()) {
                        //默认为文单附件
                        String attachmentType = GbEnum.FILE_TYPE.SUMMARY.getKey();
                        //附件类型
                        if (!attachment.getReference().equals(attachment.getSubReference())) {
                            //报批附件
                            attachmentType = GbEnum.FILE_TYPE.FIELD.getKey();
                        }
                        //SubReference是意见表的（意见表的附件也导出）
                        if (null != findComment(commentList, attachment.getSubReference(), false)) {
                            attachmentType = GbEnum.FILE_TYPE.COMMENT.getKey();
                        }
                        //构建文件bean，添加至主数据
                        GbFileBo fileBO = GbUtils.buildGbFileBO(attachment.getFileUrl(), attachmentType, targetFtpDir);
                        dataBo.addDocFile(fileBO);
                        ftpFiles.add(fileBO);
                    }
                    //关联文档(必须为公文的)
                    else if (Constants.ATTACHMENT_TYPE.DOCUMENT.ordinal() == attachment.getType() && "edoc".equals(attachment.getMimeType())) {
                        dataBo.addDocFileId(attachment.getFileUrl());
                    }
                }
            }

            if (Strings.isNotEmpty(ftpFiles)) {
                //上传ftp
                FTPUtil ftp = null;
                try {
                    ftp = FTPUtil.newInstance(GbFieldMapping.GB_FTP_URL, GbFieldMapping.GB_FTP_PORT, GbFieldMapping.GB_FTP_USER, GbFieldMapping.GB_FTP_PWD);
                    if (!ftp.isDirectoryExists(targetFtpDir)) {
                        ftp.createDirectory(targetFtpDir);
                    }
                    GbFileBo gbFileBo;
                    for (int i = 0; i < ftpFiles.size(); i++) {
                        gbFileBo = ftpFiles.get(i);
                        //上传至ftp
                        ftp.upload(gbFileBo.getLocalPath(), gbFileBo.getFtpFilePath());
                        //清理本地临时文件
                        FileUtil.deleteFile(new File(gbFileBo.getLocalPath()));
                    }
                } catch (IOException e) {
                    throw new BusinessException(e);
                } finally {
                    //关闭连接
                    if (ftp != null) {
                        ftp.disconnect();
                        ftp = null;
                    }
                }
            }
            // 拼装调用第三方接口推送的数据格式
            GbBpResultDataBo gbBpResultDataBo = new GbBpResultDataBo();
            gbBpResultDataBo.setData(dataBo);
            gbBpResultDataBo.setSystemCode(authenticationVo.getAppCode());
            gbBpResultDataBo.setThirdBusinessCode(summaryAuthenticationPo.getThirdBusinessCode());
            Long memberId = AppContext.getCurrentUser().getId();
            // 异步调用第三方接口推送数据
            ThreadTaskUtils.run(() -> {
                thirdPartyManager.transferThirdInterface(gbBpResultDataBo, memberId);
            });
            //查询节点处理情况
//        bulidNodeBOs(affairManager.getAffairs(ApplicationCategoryEnum.edoc, summary.getId()), dataBO, commentList);
//        Long appid = col.getFormAppid();
//        FormDataMasterBean masterBean = FormService.findDataById(mainFromId,appid);
        } catch (BusinessException e) {
            logger.error("====国博报批件公文流程结束推送数据失败：" + e.getMessage());
        }
    }

    /**
     * 根据id或者affairId获取comment
     *
     * @param clist
     * @param id
     * @param isAffairId
     * @return
     */
    private Comment findComment(List<Comment> clist, Long id, boolean isAffairId) {
        if (Strings.isEmpty(clist)) {
            return null;
        }
        Comment comment = null;
        Long compareId = null;
        for (int i = 0; i < clist.size(); i++) {
            comment = clist.get(i);
            if (isAffairId) {
                compareId = comment.getAffairId();
            } else {
                compareId = comment.getId();
            }
            //找到对应的
            if (id.equals(compareId)) {
                return comment;
            }
        }
        return null;
    }

    public static void main(String[] args) {

        String targetFtpDir = "/G6/system_01/" + DateUtil.getYear() + "/" + DateUtil.getMonth() + "/" + DateUtil.getDay() + "/";
        System.out.println(targetFtpDir);
    }
}