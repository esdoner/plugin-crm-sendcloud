package com.fr.plugin.sendcloud.config;

import com.fr.decision.fun.impl.AbstractEmbedRequestFilterProvider;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InitializeFilterBridge extends AbstractEmbedRequestFilterProvider {

    @Override
    public void init(FilterConfig filterConfig) {
        PlatformConfig.getInstance();
    }

    @Override
    public void filter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

    }
}
