package com.fr.plugin.sendcloud.config;

import com.fr.plugin.context.PluginContext;
import com.fr.plugin.observer.inner.AbstractPluginLifecycleMonitor;

/**
 * @author richie
 * @version 10.0
 * Created by richie on 2018-12-04
 */
public class InitializeMonitor extends AbstractPluginLifecycleMonitor {
    @Override
    public void afterRun(PluginContext pluginContext) {
        PlatformConfig.getInstance();
    }

    @Override
    public void beforeStop(PluginContext pluginContext) { }
}
