package com.mrrikimaru.softwaredesign.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableTransactionManagement
@EntityScan("com.mrrikimaru.softwaredesign.model")
@EnableJpaRepositories("com.mrrikimaru.softwaredesign.repository")
public class HibernateConfig {
}
