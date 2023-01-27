/*
package com.example.productdelivery.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "service.properties")
public class ServiceProperties {
  private String port;
  private String ip;
  private String url;
  private String protocol;

  public String getServiceUrl() {
    return this.protocol + "://" + this.ip + ":" + this.port;
  }
}
*/
