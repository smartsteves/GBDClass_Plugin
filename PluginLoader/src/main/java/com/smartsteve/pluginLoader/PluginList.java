package com.smartsteve.pluginLoader;

import java.util.HashMap;

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
    } //플러그인 리스트에 플러그인 추가하기.

    public void runInit() { //버킷에서 onEnable과 비슷한걸 실행해줌.
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
    } //플러그인이 있는지 찾기
    public Plugin getPlugin(String name){
        return pluginList.get(name);
    } //플러그인 객체 얻어오기
}
