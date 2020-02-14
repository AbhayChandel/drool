package com.hexlindia.drool.common.datamigration.test;

import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongobeeConfig {

    @Autowired
    MongoTemplate mongoTemplate;

    @Bean
    @Profile("test")
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee("mongodb://localhost:27017/testdrool");
        runner.setMongoTemplate(mongoTemplate);
        runner.setChangeLogsScanPackage(
                "com.hexlindia.drool.common.datamigration.test.changelog");
        return runner;
    }
}
