package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
@Slf4j
public class MockApplication {

    static final String CONFIG_FILE = "text.json";
    public static void main(String[] args) {
        SpringApplication.run(MockApplication.class, args);
        try {
            zkImport(args.length > 1 ? args[0] : CONFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * json 文件格式：
     * 1. 分隔符为 ";"
     * 2. "#" 开始的行被认为是注释，忽略执行，并打印在控制台
     *
     * @throws Exception
     */
    static void zkImport(String configFile) throws Exception {
        InputStream in = null;
        BufferedReader br = null;
        try {
            in = getResourceAsStream(configFile);
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                log.info(line);
                String[] p = line.split(";");
                if (p.length > 1) {
                    String command = p[0].trim();
                    String path = p[1].trim();
                    String value = p.length > 2 ? p[2].trim() : null;
                    if (value != null && value.toUpperCase().startsWith("#FILE#")) {
                        if (value.toLowerCase().endsWith(".json")) {
                            value = value.substring(7).trim();
                        } else {
                            value = readFile(value.substring(7).trim());
                        }
                    }
                }
            }
        } finally {
            if (br != null) br.close();
            if (in != null) in.close();
        }
    }


    static String readFile(String path) throws Exception {
        InputStream in = null;
        BufferedReader br = null;
        StringBuffer content = new StringBuffer();
        try {
            in = getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } finally {
            if (br != null) br.close();
            if (in != null) in.close();
        }
        return content.toString();
    }


    static InputStream getResourceAsStream(String path) {
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            throw  new RuntimeException(e);
        }
    }
}
