package com.investnow.initializer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * An ugly way of initializing startup data on a Spring Boot Application(Or may be not) considering the data.sql load on startup
 * feature (which requires Hibernate ddl-auto property to be initialized to create/create-drop) also, data.sql and schema.sql
 * option which requires the ddl-auto property to be initialized to none(In this case Hibernate doesn't create schema on startup)
 * Also, Spring Boot 2 onwards, for non embedded databases like MySql, Oracle etc, initialize spring.datasource.initialization-mode to always
 */
@Component
public class DataInitializer implements ApplicationRunner
{
    final static Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private static String FILE_NAME = "data.sql";

    @Value("${spring.datasource.url}")
    protected String dataSourceUrl;

    @Value("${spring.datasource.username}")
    protected String username;

    @Value("${spring.datasource.password}")
    protected String password;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        try
        {
            Thread.sleep(2000);
            Connection con = DriverManager.getConnection(dataSourceUrl, username, password);
            ScriptRunner runner = new ScriptRunner(con);
            ClassPathResource resource = new ClassPathResource(FILE_NAME);
            InputStream inputStream = resource.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(streamReader);
            runner.runScript(reader);
            logger.info("Successfully loaded the seed data from the file data.sql on startup");
        }
        catch (Exception e)
        {
            logger.error("Exception while initializing the data on startup :: ", e);
        }
    }
}
