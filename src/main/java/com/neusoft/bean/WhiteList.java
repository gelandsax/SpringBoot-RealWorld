package com.neusoft.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix="application.white-list")
public class WhiteList {
    private List<String> pass_url;
    public List<String> getPass_url() {
        return pass_url;
    }

    public void setPass_url(List<String> pass_url) {
        this.pass_url = pass_url;
    }
}
