package com.smartsteve.pluginLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.gson.*;
import com.smartsteve.pluginLoader.PluginBase;

/**
 * Created by JUN on 2015-12-31.
 */
public class Plugin {
    public class PluginLoadException extends Exception{
        public PluginLoadException(String message){
            super(message);
        }
    }
    private PluginBase plugin; //플러그인 메인 클래스
    private String name; //플러그인 이름
    private String version; //플러그인 버전
    private HashSet<String> author; //플러그인 제작자 리스트
    public Plugin(PluginBase plugin, String name, String version, HashSet<String> author){
        this.plugin = plugin;
        this.name = name;
        this.version = version;
        this.author = author;
    }
    public Plugin(File file) throws PluginLoadException, IOException, ClassNotFoundException, JsonSyntaxException, IllegalAccessException, InstantiationException{
        JarFile jar = new JarFile(file);
        author = new HashSet<String>();
        String main ="";
        JarEntry description;
        JsonObject object;

        //plugin.json파일 찾기
        description = jar.getJarEntry("plugin.json");
        if(description==null){
            throw new PluginLoadException("No Plugin.json");
        }

        //plugin.json파일에서 정보 읽기
        object = (new JsonParser()).parse(new InputStreamReader(jar.getInputStream(description))).getAsJsonObject();
        if(object.has("main")&&object.has("version")&&object.has("name")){
            main = object.get("main").getAsString();
            version = object.get("version").getAsString();
            name = object.get("name").getAsString();
        }
        else{
            throw new PluginLoadException("No Essential Data Found");
        }
        if(object.has("author")){
            for(JsonElement element:object.get("author").getAsJsonArray()){
                author.add(element.getAsString());
            }
        }

        //plugin.json에 적힌 main의 정보로 플러그인 메인 클래스 로드와 init()실행
        URLClassLoader loader = new URLClassLoader(new URL[]{file.toURL()});
        Class<?> clazz = loader.loadClass(main);
        Object pluginMainClass = clazz.newInstance();
        if(!(pluginMainClass instanceof PluginBase)){
            throw new PluginLoadException("Plugin Main Class Does't Extends PluginBase");
        }
        plugin = (PluginBase)pluginMainClass;
    }
    public PluginBase getPlugin() {
        return plugin;
    }
    public String getName() {
        return name;
    }
    public String getVersion() {
        return version;
    }
    public Set<String> getAuthor() { return author;}
}
