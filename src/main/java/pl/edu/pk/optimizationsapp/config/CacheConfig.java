package pl.edu.pk.optimizationsapp.config;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pk.optimizationsapp.utils.CacheKeys;

import javax.cache.Caching;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class CacheConfig implements CachingConfigurer {

    @Value("${optimization-app.cache.offersCountersRefreshMinutes}")
    Integer offersEventsCountersRefreshMinutes;


    @Bean
    @Override
    public org.springframework.cache.CacheManager cacheManager() {
        return new JCacheCacheManager(createEhCacheManager());
    }

    private javax.cache.CacheManager createEhCacheManager() {
        EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider();
        DefaultConfiguration configuration = new DefaultConfiguration(getCacheConfigurations(), this.getClass().getClassLoader());
        return getCacheManager(provider, configuration);
    }

    private javax.cache.CacheManager getCacheManager(EhcacheCachingProvider provider, DefaultConfiguration configuration) {
        return provider.getCacheManager(provider.getDefaultURI(), configuration);
    }

    private Map<String, CacheConfiguration<?, ?>> getCacheConfigurations() {
        Map<String, org.ehcache.config.CacheConfiguration<?, ?>> caches = new HashMap<>();

        CacheConfiguration<Object, Object> offersEventsCountersCache = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, Object.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(5, MemoryUnit.MB).build())
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(offersEventsCountersRefreshMinutes))).build();
        caches.put(CacheKeys.OFFERS_COUNTERS, offersEventsCountersCache);

        return caches;
    }
}
