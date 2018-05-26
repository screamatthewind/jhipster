package com.screamatthewind.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.screamatthewind.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.screamatthewind.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Agent.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Template.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Stb.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Channel.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Channel.class.getName() + ".servicePackages", jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Genre.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.Tariff.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.PeriodType.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.ServicePackage.class.getName(), jcacheConfiguration);
            cm.createCache(com.screamatthewind.domain.ServicePackage.class.getName() + ".tariffs", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
