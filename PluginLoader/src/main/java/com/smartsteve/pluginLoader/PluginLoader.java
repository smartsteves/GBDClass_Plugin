package com.smartsteve.pluginLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

/**
 * Created by JUN on 2015-12-31.
 */
public class PluginLoader {
    PluginList plugins = new PluginList();
    public static void main(String... args){
        new PluginLoader();
    }
    public PluginLoader(){
        plugins = new PluginList();
        File pluginFolder = new File("D:/plugins/"); //편의를 위해 그냥 D:/plugins/ 를 플러그인 폴더로 잡음.
        File[] files = pluginFolder.listFiles((ff,ss)->ss.endsWith(".jar")); //파일네임 필터를 이용해 .jar파일만 불러옴.
        for(File file:files){
            try {
                plugins.addPlugin(new Plugin(file)); //플러그인 저장소에 플러그인 넣기.
            }
            catch(Exception e){ //Plugin 만들때 뱉어내는 오류들을 처리.
                System.err.print(e.getMessage());
                e.printStackTrace();
            }
        }
        plugins.runInit();
        listen();
    }
    public void listen(){
        class ConsolListner extends Thread{
            @Override
            public void run(){
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                String[] arguments = line.split(" ");
                switch(arguments[0])
                {
                    case "getVersion":
                        System.out.println(plugins.getPlugin(arguments[1]).getVersion());
                        break;
                    case "getAuthor":
                        System.out.println(plugins.getPlugin(arguments[1]).getAuthor());
                    case "exit":
                        return;
                }
            }
        }
        ConsolListner cl = new ConsolListner();
        cl.start();
    }
}
