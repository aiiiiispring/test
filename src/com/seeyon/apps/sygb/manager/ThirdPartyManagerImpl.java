package com.seeyon.apps.sygb.manager;

import com.alibaba.fastjson.JSONObject;
import com.seeyon.apps.sygb.bo.GbBpDataBo;
import com.seeyon.apps.sygb.bo.GbBpResultDataBo;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.vo.AuthenticationVo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.log.CtpLogFactory;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.json.JSONUtil;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.manager </p>
 * <p> File: ThirdPartyManagerImpl.java </p>
 * <p> Module: 调用第三方接口 </p>
 * <p> Description: [调用第三方接口实现类 ] </p>
 * <p> Date: 2020-4-13 16:53</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 16:53
 * @since jdk1.8
 */
public class ThirdPartyManagerImpl implements ThirdPartyManager {

    private static Log LOG = CtpLogFactory.getLog(ThirdPartyManagerImpl.class);

    private AuthenticationManager authenticationManager;

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    private GbEdocLogManager gbEdocLogManager;

    public void setGbEdocLogManager(GbEdocLogManager gbEdocLogManager) {
        this.gbEdocLogManager = gbEdocLogManager;
    }

    /**
     * 调用第三方接口推送数据
     *
     * @param gbBpResultDataBo 组装好的公文数据
     * @param memberId 处理人
     * @return void
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-16 11:15
     */
    @Override
    public void transferThirdInterface(GbBpResultDataBo gbBpResultDataBo,Long memberId) {

        AuthenticationVo authenticationVo = null;
        String resultInfo = "";
        GbEnum.HANDLE_RESULT handleResult = GbEnum.HANDLE_RESULT.FAILD;
        try {
            if (null == gbBpResultDataBo) {
                throw new BusinessException("需要传递的公文数据为空，请联系系统管理员！");
            }
            authenticationVo = authenticationManager.getAuthenticationVoByCodeAndModuleType(gbBpResultDataBo.getSystemCode(), GbEnum.MODULE_TYPE.BP_EDOC);
            if (null == authenticationVo
                    || null == authenticationVo.getIsEnable()
                    || GbEnum.EnableEnum.NO.getKey().equals(authenticationVo.getIsEnable())) {
                throw new BusinessException("编号为（" + gbBpResultDataBo.getSystemCode() + "）的第三方系统接口未注册或未启用，请联系system系统管理员");
            }
            RestTemplate restTemplate = new RestTemplate();
            String url = authenticationVo.getUrl();
            if (Strings.isBlank(url)) {
                resultInfo = "第三方接口地址为空，请联系system系统管理员！";
            }else{
                ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, gbBpResultDataBo, JSONObject.class);
                if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                    JSONObject jsonObj = responseEntity.getBody();
                    if (null != jsonObj && null != jsonObj.get("code") && "0".equals(jsonObj.get("code").toString())) {
                        handleResult = GbEnum.HANDLE_RESULT.SUCCESS;
                    }else{
                        if (null != jsonObj.get("message")){
                            resultInfo = jsonObj.get("message").toString();
                        }
                    }
                }else{
                    resultInfo = "接口调用失败，状态码："+responseEntity.getStatusCode().value();
                }
            }
        } catch (BusinessException e) {
            resultInfo = "综合平台G6警告：" + e.getMessage();
        } catch (ResourceAccessException e) {
            resultInfo = "接口连接超时异常：" + e.getMessage();
        } catch (RestClientException e) {
            resultInfo = "Rest客户端异常，调用接口失败：" + e.getMessage();
        } catch (IllegalArgumentException e) {
            resultInfo = "接口地址异常：" + e.getMessage();
        } catch (IllegalStateException e) {
            resultInfo = "接口地址错误：" + e.getMessage();
        } catch (Exception e) {
            resultInfo = "接口调用失败：" + e.getMessage();
        }
        // 记录错误日志并保存
        if (authenticationVo != null){
            String jsonString = JSONUtil.toJSONString(gbBpResultDataBo);
            try {
                gbEdocLogManager.saveGbLog(authenticationVo.getRegisterId(),memberId,authenticationVo.getUrl(), GbEnum.MODULE_TYPE.BP_EDOC, GbEnum.ACTION_TYPE.OUT, handleResult,resultInfo,jsonString);
            } catch (BusinessException e) {
                LOG.error("===国博发起报批件记录日志失败："+e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:80/seeyon/rest/guobo/test/edoc";
            url += "?token=21a843cf-c4f8-4573-8fd2-3c24fa84271e";
            // 可自己定义实体，或者传输map
            GbBpDataBo gbBpDataBo = new GbBpDataBo();
            gbBpDataBo.setTitle("标题");
            gbBpDataBo.setDrafterMember("malei");
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, gbBpDataBo, JSONObject.class);
            if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                JSONObject jsonObj = responseEntity.getBody();
                if (null != jsonObj && null != jsonObj.get("code") && 0 == Integer.parseInt(jsonObj.get("code").toString())) {
                    System.out.println("成功！"+jsonObj.get("message"));
                }else{
                    System.out.println("失败！"+jsonObj.get("message"));
                }
            }else{
                System.out.println("接口调用失败，状态码："+responseEntity.getStatusCode().value());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("接口地址异常：" + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("接口地址错误：" + e.getMessage());
        } catch (org.springframework.web.client.ResourceAccessException e) {
            System.out.println("接口连接超时异常：" + e.getMessage());
        } catch (com.alibaba.fastjson.JSONException e) {
            System.out.println("返回值数据格式异常：" + e.getMessage());
        } catch (Exception e) {
            System.out.println("接口调用失败：" + e.getMessage());
        }
    }
}
