package com.seeyon.ctp.rest.resources;

import com.seeyon.apps.sygb.manager.GbTaskManager;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>Title: 待办已办任务模块</p>
 * <p>Description: 查询待办已办任务</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: seeyon.com</p>
 */
@Path("guobo/task")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class GbTaskResource extends BaseResource {

    private GbTaskManager gbTaskManager = (GbTaskManager) AppContext.getBean("gbTaskManager");
    /**
     * 组织架构基础接口
     */
    private OrgManager orgManager = (OrgManager) AppContext.getBean("orgManager");
    /**
     * 已办任务查询
     *
     * @param map {pageNum : 当前页,pageSize : 分页每页条数, receiverAccount ：用户登录名}
     * @return Response
     */
    @GET
    @Path("/getPageDone")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHaveTask(Map<String, Object> map) throws BusinessException {
        //判断用户民是否为空
        if (null == map.get("receiverAccount")) {
            return fail("接收人账号参数不能为空，请检查");
        }
        //根据登录名查询Memberid
        String receiverAccount = map.get("receiverAccount").toString();
        V3xOrgMember member = orgManager.getMemberByLoginName(receiverAccount);
        if (null == member) {
            return fail("查询不到此接收人账号用户");
        }
        // 获取分页已办数据
        HashMap<String, Object> taskInformation1 = gbTaskManager.getTaskInformation(map, member.getId(), "1", "4");
        return success(taskInformation1);
    }
    /**
     * 待办任务查询
     *
     * @param map {pageNum : 当前页,pageSize : 分页每页条数, receiverAccount ：用户登录名}
     * @return Response
     */
    @GET
    @Path("/getPageTodo")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoTask(Map<String, Object> map) throws BusinessException {
        //判断用户民是否为空
        if (null == map.get("receiverAccount")) {
            return fail("接收人账号参数为空，请检查");
        }
        //根据登录名查询Memberid
        String receiverAccount = map.get("receiverAccount").toString();
        V3xOrgMember member = orgManager.getMemberByLoginName(receiverAccount);
        if (null == member) {
            return fail("查询不到此接收人账号用户");
        }
        //调用Manager层查询数据
        System.out.println(member.getId());
        HashMap<String, Object> taskInformation1 = gbTaskManager.getTaskInformation(map, member.getId(), "0", "3");
        return success(taskInformation1);
    }

}


