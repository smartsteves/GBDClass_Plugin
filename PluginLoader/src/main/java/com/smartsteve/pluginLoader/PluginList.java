package com.smartsteve.pluginLoader;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by JUN on 2016-01-02.
 */
public class PluginList {
    private HashMap<String,Plugin> pluginList;
    public PluginList(){
        pluginList = new HashMap<String,Plugin>();
    }
    public void addPlugin(Plugin plugin){
        pluginList.put(plugin.getName(),plugin);
    }
    public void runInit(){
        for(Plugin plugin:pluginList.values()){
            try {
                plugin.getPlugin().init();
            }
            catch(Exception e){
                System.err.println("Error occured while initializing " + plugin.getName() );
            }
        }
    }
    public boolean pluginExist(String name){
        return pluginList.containsKey(name);
    }
    public Plugin getPlugin(String name){
        return pluginList.get(name);
    }
}
