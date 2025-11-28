package com.manoel.springauthapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.password")
public class PasswordPolicyProperties {

private int minLength;
private String regex;
private String message;
}
