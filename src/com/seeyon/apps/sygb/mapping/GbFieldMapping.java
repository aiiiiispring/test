package com.seeyon.apps.sygb.mapping;

import com.seeyon.ctp.common.SystemEnvironment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/***
 * 项目：国家博物馆客开项目
 * <p> Package: com.seeyon.apps.sygb.bo </p>
 * <p> File: FieldMapping.java </p>
 * <p> Title: [用例名称]_[说明]</p>
 * <p> Module: 公文发文接口 </p>
 * <p> Description: [公文发文接口，属性配置映射类] </p>
 * <p> Date: 2020-3-30 11:10</p>
 * @author ML
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-3-30 11:10
 * @since jdk1.8
 * @version 1.0
 */
public class GbFieldMapping {
    /**
     * 国博报批单模板代码
     */
    public static final String GB_BP_TEMPLATE_CODE = "GB_EDOC_002";
    /**
     * 国博报批单-bean与字段的映射
     */
    public static final Map<String, String> gb_bp_map = new HashMap<>();
    /**
     * 国博报批单-bean与动态表字段的映射
     */
    public static final Map<String, String> gb_bp_table_map = new HashMap<>();

    // 报批件实体对应关系
    static {
        //报批单 bean与单子的域对应关系-----------start
//        gb_bp_map.put("deptEDocNumber", "部门文号");
        gb_bp_map.put("urgency", "紧急程度");
        gb_bp_map.put("drafterMember", "起草人");
        gb_bp_map.put("drafterDept", "发起部门");
        gb_bp_map.put("title", "标题");
        gb_bp_map.put("contactNumber", "联系电话");
        gb_bp_map.put("payType", "支付类型");
        gb_bp_map.put("payMoney", "付款金额");
        gb_bp_map.put("approvalFileId", "报批附件");
        gb_bp_map.put("signContract", "签署合同");
        //报批单 bean与单子的域对应关系-------------end

        //报批单 bean与动态表字段对应关系-----------start
//        gb_bp_table_map.put("deptEDocNumber", "field0001");
        gb_bp_table_map.put("urgency", "field0023");
        gb_bp_table_map.put("drafterMember", "field0008");
        gb_bp_table_map.put("drafterDept", "field0017");
        gb_bp_table_map.put("title", "field0002");
        gb_bp_table_map.put("contactNumber", "field0009");
        gb_bp_table_map.put("payType", "field0011");
        gb_bp_table_map.put("payMoney", "field0012");
//        gb_bp_table_map.put("approvalFileId", "field0013");
        gb_bp_table_map.put("signContract", "field0014");

//        gb_bp_table_map.put("curatorInstruction", "field0003");
//        gb_bp_table_map.put("leaderInstruction", "field0004");
//        gb_bp_table_map.put("noteOpinion", "field0005");
//        gb_bp_table_map.put("countersignDeptOpinion", "field0006");
//        gb_bp_table_map.put("deptLeaderLoginName", "field0007");
        gb_bp_table_map.put("remarks", "field0010");
        gb_bp_table_map.put("documentStatus", "field0015");
//        gb_bp_table_map.put("toMeeting", "field0016");
        gb_bp_table_map.put("serialNumber", "field0018");
        gb_bp_table_map.put("approvalDate", "field0019");
//        gb_bp_table_map.put("documentType", "field0020");
        gb_bp_table_map.put("reportedBy", "field0021");
//        gb_bp_table_map.put("importance", "field0022");
        //报批单 bean与动态表字段对应关系-------------end
    }

    /**
     * 临时目录（用于ftp上传，下载文件，临时存在服务器本地）
     */
    public static final String gbTempPath = SystemEnvironment.getSystemTempFolder() + File.separator + "gbFtpFile";

    /**
     * FTP服务器相关
     */
    public static final String GB_FTP_URL = "127.0.0.1";
    public static final int GB_FTP_PORT = 21;
    public static final String GB_FTP_USER = "MALEI";
    public static final String GB_FTP_PWD = "lei123456";

}
