package com.seeyon.apps.sygb.util;

import com.seeyon.apps.sygb.bo.GbBpDataBo;
import com.seeyon.apps.sygb.bo.GbFileBo;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.mapping.GbFieldMapping;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.ModuleType;
import com.seeyon.ctp.common.content.dao.ContentDao;
import com.seeyon.ctp.common.content.mainbody.MainbodyType;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.filemanager.manager.FileManager;
import com.seeyon.ctp.common.htmltopdf.manager.HtmlToPdfManager;
import com.seeyon.ctp.common.po.content.CtpContentAll;
import com.seeyon.ctp.common.po.filemanager.V3XFile;
import com.seeyon.ctp.organization.bo.V3xOrgDepartment;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.util.DateUtil;
import com.seeyon.ctp.util.FileUtil;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.UUIDLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.util </p>
 * <p> File: GbUtils.java </p>
 * <p> Module: 公文发起工具类 </p>
 * <p> Description: [] </p>
 * <p> Date: 2020-3-30 14:42</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-3-30 14:42
 * @since jdk1.8
 */
public class GbUtils {
    private final static Log logger = LogFactory.getLog(GbUtils.class);
    private final static EnumManager enumManager = (EnumManager) AppContext.getBean("enumManagerNew");
    private final static ContentDao contentDao = (ContentDao) AppContext.getBean("contentDao");
    private final static FileManager fileManager = (FileManager) AppContext.getBean("fileManager");
    private final static OrgManager orgManager = (OrgManager) AppContext.getBean("orgManager");

    /**
     * 将javaBean中的数据反射为创建表单所需样式
     *
     * @param javaBean bean
     * @param mapping  映射关系
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author ML
     * @version 1.0
     * @Time 2020-3-30 14:51
     */
    public static Map<String, Object> fillBillData(Object javaBean, Map<String, String> mapping) {
        Map<String, Object> map = new HashMap<>();
        //反射获取私有属性
        Class<?> clz = javaBean.getClass();
        Field[] fields = clz.getDeclaredFields();
        Method m = null;
        for (Field field : fields) {
            //映射里包含这个字段
            if (mapping.containsKey(field.getName())) {
                //字段类型判断
                try {
                    m = clz.getMethod(getGetterMethodName(field.getName()));
                    //添加数据
                    map.put(mapping.get(field.getName()), m.invoke(javaBean));
                } catch (Exception e) {
                    logger.error("反射字段数据出错：" + e.getMessage(), e);
                }
            }
        }
        return map;
    }


    /**
     * 获取属性的getter方法名
     *
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static String getGetterMethodName(String fieldName) throws Exception {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return "get" + new String(items);
    }

    /**
     * 获取属性的setter方法名
     *
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static String getSetterMethodName(String fieldName) throws Exception {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return "set" + new String(items);
    }

    /**
     * 设置主体数据的bean
     *
     * @param dataBo  主数据bean
     * @param dataMap 动态表的map
     * @param mapping 映射关系
     */
    public static void setDataBo(GbBpDataBo dataBo, Map<String, Object> dataMap, Map<String, String> mapping) {
        Class<?> clz = dataBo.getClass();
        Field[] fields = clz.getDeclaredFields();
        Method m = null;
        Object data = null;
        String val = "";
        for (Field field : fields) {
            //映射里包含这个字段
            if (mapping.containsKey(field.getName())) {
                //字段类型判断
                try {
                    m = clz.getMethod(GbUtils.getSetterMethodName(field.getName()), String.class);
                    //添加数据
                    data = dataMap.get(mapping.get(field.getName()));
                    if (data == null) {
                        val = "";
                    } else {
                        if (data instanceof Date) {
                            val = DateUtil.format((Date) data);
                        } else {
                            val = data + "";
                        }
                    }
                    m.invoke(dataBo, val);
                } catch (Exception e) {
                    logger.error("反射设置字段数据出错：" + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 处理枚举类型：id-》枚举值
     *
     * @param dataBo
     */
    public static void dealEnumVal(GbBpDataBo dataBo) throws BusinessException {
        if (null == dataBo) {
            return;
        }
        //处理紧急程度
        if (Strings.isNotEmpty(dataBo.getUrgency())) {
            dataBo.setUrgency(enumManager.getEnumItem(Long.parseLong(dataBo.getUrgency())).getValue());
        }
        //处理支付类型
        if (Strings.isNotEmpty(dataBo.getPayType())) {
            dataBo.setPayType(enumManager.getEnumItem(Long.parseLong(dataBo.getPayType())).getValue());
        }
        // 处理签署合同
        if (Strings.isNotEmpty(dataBo.getSignContract())) {
            dataBo.setSignContract(enumManager.getEnumItem(Long.parseLong(dataBo.getSignContract())).getValue());
        }
        // 是否上会
//        if (Strings.isNotEmpty(dataBo.getToMeeting())) {
//            dataBo.setToMeeting(enumManager.getEnumItem(Long.parseLong(dataBo.getToMeeting())).getValue());
//        }
        // 单据类型
//        if (Strings.isNotEmpty(dataBo.getDocumentType())) {
//            dataBo.setDocumentType(enumManager.getEnumItem(Long.parseLong(dataBo.getDocumentType())).getValue());
//        }
        // 印发文废文
        if (Strings.isNotEmpty(dataBo.getDocumentStatus())) {
            dataBo.setDocumentStatus(enumManager.getEnumItem(Long.parseLong(dataBo.getDocumentStatus())).getValue());
        }
        // 处理人员
        if (Strings.isNotEmpty(dataBo.getDrafterMember())) {
            dataBo.setDrafterMember(orgManager.getMemberById(Long.parseLong(dataBo.getDrafterMember())).getLoginName());
        }
        // 处理部门
        if (Strings.isNotEmpty(dataBo.getDrafterDept())) {
            V3xOrgDepartment department = orgManager.getDepartmentById(Long.parseLong(dataBo.getDrafterDept()));
            if (null != department) {
                dataBo.setDrafterDept(department.getCode());
                dataBo.setDrafterDeptName(department.getName());
            }
        }
        // 部门领导人
//        if (Strings.isNotEmpty(dataBo.getDeptLeaderLoginName())) {
//            V3xOrgMember member = orgManager.getMemberById(Long.parseLong(dataBo.getDeptLeaderLoginName()));
//            if (member != null){
//                dataBo.setDeptLeaderLoginName(member.getLoginName());
//            }
//        }
        // 汇报人
        if (Strings.isNotEmpty(dataBo.getReportedBy())) {
            V3xOrgMember member = orgManager.getMemberById(Long.parseLong(dataBo.getReportedBy()));
            if (member != null) {
                dataBo.setReportedBy(member.getLoginName());
            }
        }
    }

    /**
     * 处理正文：正文类型为word，则上传文件，设置内容字段为ftp地址
     *
     * @param dataBo       主体对象
     * @param ftpFiles     要被上传的ftp文件集合
     * @param targetFtpDir ftp目标文件夹
     * @throws BusinessException
     * @throws NumberFormatException
     */
    public static void dealContent(GbBpDataBo dataBo, List<GbFileBo> ftpFiles, String targetFtpDir) throws BusinessException {
        if (null == dataBo) {
            return;
        }
        //正文为空，只处理报批单
        if (Strings.isEmpty(dataBo.getContent())) {
            List<CtpContentAll> docList = new ArrayList<>();
            List<CtpContentAll> govdocSendList = contentDao.findByModuleId(ModuleType.govdocSend, dataBo.getSummaryId());
            if (Strings.isNotEmpty(govdocSendList)) {
                docList.addAll(govdocSendList);
            }

            List<CtpContentAll> list = contentDao.findByModuleId(ModuleType.edoc, dataBo.getSummaryId());
            if (Strings.isNotEmpty(list)) {
                docList.addAll(list);
            }
            if (Strings.isEmpty(docList)) {
                return;
            }
            CtpContentAll content;
            String contentStr;
            for (int i = 0; i < docList.size(); i++) {
                content = docList.get(i);
                int contentType = content.getContentType().intValue();
                //文单排除
                if (contentType == MainbodyType.FORM.getKey()) {
                    continue;
                }
                //正文开始
                contentStr = content.getContent();
                //标准正文
                if (contentType == MainbodyType.HTML.getKey()) {
                    dataBo.setContent(contentStr);
                } else {
                    //其他类型正文（均为文件形式）
                    Long fileId = null;
                    try {
                        fileId = Long.parseLong(contentStr);
                    } catch (Exception e) {
                        logger.error("文件正文id转换失败", e);
                    }
                    if (null != fileId) {
                        GbFileBo file = buildGbFileBO(fileId, GbEnum.FILE_TYPE.CONTENT.getKey(), targetFtpDir);
                        //上传
                        ftpFiles.add(file);
                        dataBo.addDocFile(file);
                        //赋值
//                        dataBo.setContent(file.getFtpFilePath());
                    }
                }
                //返回
                return;
            }
        }
    }


    /**
     * 构建附件bean
     *
     * @param fileId       附件id
     * @param type         类型：文单附件，意见附件
     * @param targetFtpDir 上传的目标文件夹
     * @return
     * @throws BusinessException
     */
    public static GbFileBo buildGbFileBO(Long fileId, String type, String targetFtpDir) throws BusinessException {
        GbFileBo fileBO = new GbFileBo();
        if (null == fileId || fileId == -1L) {
            return fileBO;
        }
        V3XFile v3xFile = fileManager.getV3XFile(fileId);
        File file = fileManager.getSpicFile(v3xFile.getId(), true);
        String oldName = v3xFile.getFilename();
        //后缀名处理
        if (oldName.indexOf(".") < 0) {
            oldName += getSuffixByMimeType(v3xFile.getMimeType());
        }
        String suffix = "";
        if (oldName.indexOf(".") > 0) {
            suffix = oldName.substring(oldName.lastIndexOf("."));
        }

        String realPath = GbFieldMapping.gbTempPath + File.separator + file.getName();
        FileUtil.copyFile(file, new File(realPath));
        fileBO.setCreateTime(DateUtil.format(v3xFile.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        fileBO.setFtpFilePath(targetFtpDir + file.getName());
        fileBO.setLocalPath(realPath);
        fileBO.setFileType(type);
        fileBO.setFileName(oldName);
        fileBO.setFileSuffix(suffix);
        fileBO.setFileMimeType(v3xFile.getMimeType());
        return fileBO;
    }

    /**
     * 根据mimeType获取后缀名
     *
     * @param mimeType
     * @return
     */
    public static String getSuffixByMimeType(String mimeType) {
        switch (mimeType) {
            case "application/msword":
                return ".doc";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return ".docx";
            case "application/pdf":
                return ".pdf";
            case "application/vnd.ms-excel":
                return ".xls";
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                return ".xlsx";
            case "application/wps":
            case "application/kswps":
                return ".wps";
            case "application/ofd":
                return ".ofd";

            default:
                return "";
        }
    }

    private final static HtmlToPdfManager htmltopdfManager = (HtmlToPdfManager) AppContext.getBean("htmltopdfManager");

    /**
     * 将html内容转PDF文件写到本地临时目录，并加载到推送数据的对象中
     *
     * @param dataBo          数据实例
     * @param ftpFiles        附件集合
     * @param subject         公文流程名称（用作文单PDF的文件名）
     * @param edocHtmlContent 公文文单HTML内容
     * @param targetFtpDir    ftp目标地址
     * @return void
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-9 15:17
     */
    public static void dealHtmlContentFile(GbBpDataBo dataBo, List<GbFileBo> ftpFiles, String subject, String edocHtmlContent, String targetFtpDir) {
        //将html首先写入到本地临时目录中
        String htmlName = UUIDLong.longUUID() + ".html";
        String gbTempPath = GbFieldMapping.gbTempPath;
        String htmlPath = gbTempPath + File.separator + htmlName;
        FileUtil.makeDir(gbTempPath);
        File file = new File(htmlPath);
        try {
            file.createNewFile();
        } catch (Exception e) {
            logger.error("创建文件失败" + file.getName());
        }
        FileUtil.addContent(file, edocHtmlContent);
        String destPath = gbTempPath + File.separator;
        //将html文件转换为PDF并输出到指定目录，返回新生成的PDF的实际文件名
        String pdfPath = htmltopdfManager.singleHtmlToPdf(htmlPath, destPath, null);
        String localPdfPath = destPath + pdfPath;
        String suffix = "";
        if (pdfPath.indexOf(".") > 0) {
            suffix = pdfPath.substring(pdfPath.lastIndexOf("."));
            pdfPath = pdfPath.replace(".pdf", "");
        }
        // 创建文单类型的文件类型，填写到对象中，推送给第三方系统
        GbFileBo fileBo = new GbFileBo();
        fileBo.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        fileBo.setFtpFilePath(targetFtpDir + pdfPath);
        fileBo.setLocalPath(localPdfPath);
        fileBo.setFileType(GbEnum.FILE_TYPE.DOCUMENT.getKey());
        fileBo.setFileName(subject + suffix);
        fileBo.setFileSuffix(suffix);
        fileBo.setFileMimeType("");
        ftpFiles.add(fileBo);
        dataBo.addDocFile(fileBo);
    }
}
