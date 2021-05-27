package com.project.blog.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@ConfigurationProperties(prefix = "application.jwt")
@Component
public class JwtConfigProperties {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;
}
