
package com.theodo.albeniz.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LibraryInterceptor implements HandlerInterceptor {

    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime =  System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        long endTime = System.currentTimeMillis();
        Long startTime = (Long) request.getAttribute("startTime");
        System.out.println("the url : "+ request.getRequestURI() + " took: " + (endTime-startTime)+ " ms");
    }

}

