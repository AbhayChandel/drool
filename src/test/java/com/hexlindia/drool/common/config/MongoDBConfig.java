package com.hexlindia.drool.common.config;


import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@TestConfiguration
public class MongoDBConfig {
    @Bean
    public IMongodConfig embeddedMongoConfiguration() throws IOException {
        return new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .build();
    }
}
