package com.goorm.okim.filter;

import com.goorm.okim.util.ContainerIdUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@Component
public class GlobalRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UUID uniqueId = UUID.randomUUID();
        String containerId = ContainerIdUtil.getContainerId();
        MDC.put("tx-id", uniqueId.toString());
        MDC.put("container-id", containerId);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // 모든 응답 헤더에 tx-id 넣음(요청 실패 유무 관계 상관없이)
        responseWrapper.setHeader("tx-id", MDC.get("tx-id"));
        responseWrapper.setHeader("container-id", MDC.get("container-id"));
        filterChain.doFilter(request, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }
}
