package org.idou.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器：
 *  1：可以处理web应用中请求的一些通用性问题
 *  2：共性问题在拦截器中处理，可以减少重复代码，便于维护
 *
 * 拦截器和过滤器
 *  ①拦截器是基于java的反射机制的，而过滤器是基于函数回调。
 *  ②拦截器不依赖与servlet容器，过滤器依赖与servlet容器。
 *  ③拦截器只能对action请求起作用，而过滤器则可以对几乎所有的请求起作用。
 *  ④拦截器可以访问action上下文、值栈里的对象，而过滤器不能访问。
 *  ⑤在action的生命周期中，拦截器可以多次被调用，而过滤器只能在容器初始化时被调用一次。
 *  ⑥拦截器可以获取IOC容器中的各个bean，而过滤器就不行，这点很重要，在拦截器里注入一个service，可以调用业务逻辑
 */
public class LoginInterceptor implements HandlerInterceptor{
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        request.setCharacterEncoding("utf-8");
        //对用户是否登录进行判断
        if(request.getSession().getAttribute("user")==null){
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("msg","这里是被拦截器修改之后的消息！");
        modelAndView.setViewName("/demo.jsp");
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
