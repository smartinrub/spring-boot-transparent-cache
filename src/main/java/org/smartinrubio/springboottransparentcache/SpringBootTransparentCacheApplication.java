package org.smartinrubio.springboottransparentcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringBootTransparentCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTransparentCacheApplication.class, args);
    }

}
