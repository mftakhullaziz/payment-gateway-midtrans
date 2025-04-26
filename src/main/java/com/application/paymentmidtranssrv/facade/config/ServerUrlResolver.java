package com.application.paymentmidtranssrv.facade.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ServerUrlResolver implements HandlerInterceptor {

    private static String detectedUrl;

    public static String getDetectedUrl() {
        return detectedUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (detectedUrl == null) {
            String scheme = request.getScheme(); // http or https
            String host = request.getHeader("host"); // e.g., d563-114-10-103-236.ngrok-free.app
            detectedUrl = scheme + "://" + host;
        }
        return true;
    }
}
