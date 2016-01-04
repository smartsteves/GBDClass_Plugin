package com.heartPattern.plugin1;

import com.smartsteve.pluginLoader.PluginBase;

/**
 * Created by JUN on 2016-01-04.
 */
public class Plugin1 implements PluginBase {
    public void init(){
        System.out.println("Plugin1 Successfully Initialization");
    }
    public void run(){
        System.out.println("Plugin1 Run");
    }
}
