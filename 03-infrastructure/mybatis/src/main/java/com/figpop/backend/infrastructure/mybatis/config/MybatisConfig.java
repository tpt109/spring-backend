package com.figpop.backend.infrastructure.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value={"com.figpop.backend.infrastructure.mybatis.**.mapper*"})
public class MybatisConfig {

}
