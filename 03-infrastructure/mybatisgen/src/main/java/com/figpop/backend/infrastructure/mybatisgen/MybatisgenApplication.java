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
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MybatisgenApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MybatisgenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
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

}
