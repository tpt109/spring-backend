package com.figpop.backend.infrastructure.mybatisgen;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class MybatisgenApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MybatisgenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // clean file gen before perform
        Properties properties = new Properties();
        properties.load(new FileInputStream(new ClassPathResource("application.properties").getFile()));
        String pathJava = properties.getProperty("mbg.target.java");
        String pathXML = properties.getProperty("mbg.target.xml");
        deleteFileWithPath(pathJava);
        deleteFileWithPath(pathXML);

        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = new ClassPathResource("mybatisGeneratorConfig.xml").getFile();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

        myBatisGenerator.generate(null);
        System.exit(0);
    }

    private void deleteFileWithPath(String folderPath) {

        File folder = new File(folderPath);

        // Check if the folder exists
        if (folder.exists()) {
            deleteFolderAndContents(folder);
        } else {
            System.err.println("The specified folder does not exist.");
        }
    }

    private static void deleteFolderAndContents(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFolderAndContents(file); // Recursively delete subfolder and its contents
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }

}
