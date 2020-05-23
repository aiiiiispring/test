package com.seeyon.apps.sygb.constants;

import java.util.List;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb </p>
 * <p> File: GbEnum.java </p>
 * <p> Module: 枚举类 </p>
 * <p> Description: [国博枚举类] </p>
 * <p> Date: 2020-4-1 11:47</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-1 11:47
 * @since jdk1.8
 */
public class GbEnum {
    /**
     * 附件类别
     */
    public enum FILE_TYPE {

        /**
         * 表单字段
         */
        FIELD("0", "表单字段"),
        /**
         * 流程主表
         */
        SUMMARY("1", "流程主表"),
        /**
         * 正文
         */
        CONTENT("2", "正文附件"),
        /**
         * 文单
         */
        DOCUMENT("3", "文单"),
        /**
         * 意见附件
         */
        COMMENT("4", "意见附件");

        private String key;
        private String desc;

        FILE_TYPE(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 是否启用枚举
     */
    public enum EnableEnum {
        /**
         * 启用
         */
        YES(1, "启用"),
        /**
         * 停用
         */
        NO(0, "停用");

        private Integer key;
        private String text;

        EnableEnum(Integer key, String text) {
            this.setKey(key);
            this.setText(text);
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static EnableEnum getValue(Integer key) {
            EnableEnum[] enums = EnableEnum.values();
            if (enums != null) {
                for (EnableEnum enum1 : enums) {
                    if (enum1.getKey().equals(key)) {
                        return enum1;
                    }
                }
            }
            return null;
        }

        public static EnableEnum getKey(String text) {
            EnableEnum[] enums = EnableEnum.values();
            if (enums != null) {
                for (EnableEnum enum1 : enums) {
                    if (enum1.getText().equals(text)) {
                        return enum1;
                    }
                }
            }
            return null;
        }

    }

    /**
     * 传递方式类型
     */
    public enum TRANSFER_METHOD_ENUM {
        /**
         * JSON
         */
        JSON(1, "JSON"),
        /**
         * XML
         */
        XML(0, "XML");

        private Integer key;
        private String text;

        TRANSFER_METHOD_ENUM(Integer key, String text) {
            this.setKey(key);
            this.setText(text);
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static TRANSFER_METHOD_ENUM getValue(Integer key) {
            TRANSFER_METHOD_ENUM[] enums = TRANSFER_METHOD_ENUM.values();
            if (enums != null) {
                for (TRANSFER_METHOD_ENUM enum1 : enums) {
                    if (enum1.getKey().equals(key)) {
                        return enum1;
                    }
                }
            }
            return null;
        }

        public static TRANSFER_METHOD_ENUM getKey(String text) {
            TRANSFER_METHOD_ENUM[] enums = TRANSFER_METHOD_ENUM.values();
            if (enums != null) {
                for (TRANSFER_METHOD_ENUM enum1 : enums) {
                    if (enum1.getText().equals(text)) {
                        return enum1;
                    }
                }
            }
            return null;
        }
    }


    /**
     * 模块类型
     */
    public enum MODULE_TYPE {
        /**
         * 报批件公文
         */
        BP_EDOC(1, "报批件公文");

        private Integer key;
        private String text;

        MODULE_TYPE(Integer key, String text) {
            this.setKey(key);
            this.setText(text);
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static MODULE_TYPE getValue(Integer key) {
            MODULE_TYPE[] enums = MODULE_TYPE.values();
            if (enums != null) {
                for (MODULE_TYPE enum1 : enums) {
                    if (enum1.getKey().equals(key)) {
                        return enum1;
                    }
                }
            }
            return null;
        }
    }
    /**
     * 动作方向
     */
    public enum ACTION_TYPE {
        /**
         * 外部推入
         */
        IN(0, "外部推入"),

        /**
         * 内部推送
         */
        OUT(1, "内部推送");
        private Integer key;
        private String text;

        ACTION_TYPE(Integer key, String text) {
            this.setKey(key);
            this.setText(text);
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static ACTION_TYPE getValue(Integer key) {
            ACTION_TYPE[] enums = ACTION_TYPE.values();
            if (enums != null) {
                for (ACTION_TYPE enum1 : enums) {
                    if (enum1.getKey().equals(key)) {
                        return enum1;
                    }
                }
            }
            return null;
        }
    }

    /**
     * 处理结果
     */
    public enum HANDLE_RESULT {
        /**
         * 成功
         */
        SUCCESS(1, "成功"),

        /**
         * 失败
         */
        FAILD(0, "失败");
        private Integer key;
        private String text;

        HANDLE_RESULT(Integer key, String text) {
            this.setKey(key);
            this.setText(text);
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static HANDLE_RESULT getValue(Integer key) {
            HANDLE_RESULT[] enums = HANDLE_RESULT.values();
            if (enums != null) {
                for (HANDLE_RESULT enum1 : enums) {
                    if (enum1.getKey().equals(key)) {
                        return enum1;
                    }
                }
            }
            return null;
        }
    }
}
