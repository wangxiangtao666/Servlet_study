package com.tao.listener;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//统计网站在线人数：统计session
public class OnLineCountListener implements HttpSessionListener {
    @Override
    //监听到session创建：一旦创建成功就会触发这个事件
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");
        if(onlineCount==null){
            onlineCount = new Integer(1);
        }else{
            int count = onlineCount.intValue();
            onlineCount = new Integer(count+1);
        }
        servletContext.setAttribute("OnlineCount",onlineCount);

    }

    @Override
    //监听到session销毁
    //应该在session销毁之前吧，不然怎么获取这个session
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");
        if(onlineCount==null){
            onlineCount = new Integer(0);
        }else{
            int count = onlineCount.intValue();
            onlineCount = new Integer(count-1);
        }
        servletContext.setAttribute("OnlineCount",onlineCount);
    }
}


//销毁session的两种方法：
//1、自动销毁，没有设置销毁时间，关闭浏览器自动销毁
//2、在web.xml中配置销毁时间
//<session-config>
//<session-timeout>1</session-timeout>
//</session-config>