package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        String method = request.getMethod();
        String email = request.getParameter("email");
        String course = request.getParameter("course");

        if ("/login".equals(uri) && "POST".equalsIgnoreCase(method)) {
            if ("user@example.com".equals(email) &&
                "password".equals(request.getParameter("password"))) {
                logger.info("[" + LocalDateTime.now() + "] LOGIN SUCCESS for: " + email);
            } else {
                logger.warning("[" + LocalDateTime.now() + "] LOGIN FAILURE for: " + email);
            }
        }

        if ("/register".equals(uri) && "POST".equalsIgnoreCase(method)) {
            logger.info("[" + LocalDateTime.now() + "] COURSE REGISTERED: " + course);
        }

        return true;
    }
}
