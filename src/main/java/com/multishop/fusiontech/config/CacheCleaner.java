package com.multishop.fusiontech.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheCleaner {

    @Autowired
    private SessionFactory sessionFactory;

    @Bean
    CommandLineRunner clearCache() {
        return args -> {
            sessionFactory.getCache().evictAllRegions(); // Keşi təmizləyir
        };
    }
}
