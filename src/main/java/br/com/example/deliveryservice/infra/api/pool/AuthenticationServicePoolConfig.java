package br.com.example.deliveryservice.infra.api.pool;

import feign.Client;
import feign.Request;
import feign.httpclient.ApacheHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.apache.http.impl.client.HttpClientBuilder;


import java.util.concurrent.TimeUnit;

public class AuthenticationServicePoolConfig {
    @Value("${application.authentication-service.pool.maxConn}")
    private Integer connPerRoute;

    @Value("${application.authentication-service.pool.maxRoutes}")
    private Integer maxConnTotal;

    @Value("${application.authentication-service.pool.connTimeout}")
    private Integer connTimeout;

    @Value("${application.authentication-service.pool.readTimeout}")
    private Integer readTimeout;

    @Bean()
    public Request.Options options() {
        return new Request.Options(connTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, true);
    }

    @Bean
    public Client poolConfig() {
        return new ApacheHttpClient(
                HttpClientBuilder.create().setMaxConnPerRoute(connPerRoute).setMaxConnTotal(maxConnTotal).build());
    }
}
