<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" pageEncoding="UTF-8" %>
<%@ page language="java" import="com.seeyon.ctp.common.authenticate.sso.SSOTicketManager.TicketInfo" %>
<%@ page import="com.seeyon.apps.sygb.util.TaskBusinessUtil" %>
<%
    String ticket = request.getParameter("ticket");
    System.out.println("ticket的值 ==== " + ticket);
    TicketInfo ticketInfo = com.seeyon.ctp.portal.sso.SSOTicketBean.getTicketInfo(ticket);
    String decodeTicket = TaskBusinessUtil.decode(ticket);
    System.out.println("解码后的ticket====" + decodeTicket);
    String[] spliteTicket = TaskBusinessUtil.spliteTicket(decodeTicket);
    if (ticketInfo != null) {
        String url = "";
        //待办路径
        if ("1".equals(spliteTicket[2])) {
            url = "sygb/ssoController.do?method=window&url=collaboration%2fcollaboration.do%3fmethod%3dsummary%26openFrom%3dlistPending%26affairId%3d" + spliteTicket[1];
        }
        //公文路径
        if ("4".equals(spliteTicket[2])) {
            url = "sygb/ssoController.do?method=window&url=govdoc%2fgovdoc.do%3fmethod%3dsummary%26openFrom%3dlistPending%26affairId%3d" + spliteTicket[1] + "%26contentAnchor%3d%26_isModalDialog%3dtrue";
        }
        System.out.println("重定向url的值 ==== " + url);
        response.sendRedirect(com.seeyon.ctp.portal.sso.SSOTicketBean.makeURLOfSSOTicket(ticket, url));
    }
%>