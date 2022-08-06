package br.com.example.deliveryservice.infra.config;

import org.apache.camel.component.aws2.s3.AWS2S3Configuration;
import org.apache.camel.component.aws2.s3.client.AWS2CamelS3InternalClient;
import org.apache.camel.component.aws2.s3.client.impl.AWS2S3ClientStandardImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.core.Protocol;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${amazon.region}")
    private Region region;

    @Value("${amazon.accessKey}")
    private String accessKey;

    @Value("${amazon.secretKey}")
    private String secretKey;

    @Value("${amazon.s3.proxy-host}")
    private String host;

    @Value("${amazon.s3.proxy-port}")
    private int port;

    @Value("${amazon.s3.proxy-protocol}")
    private Protocol protocol;

    @Value("${amazon.use-local-uri}")
    private boolean mustUseLocalUri;

    @Value("${amazon.local-endpoint}")
    private String localUri;

    @Bean("s3Client")
    public S3Client amazonS3() {
        AWS2S3Configuration clientConfiguration = new AWS2S3Configuration();

        clientConfiguration.setProxyHost(host);
        clientConfiguration.setProxyPort(port);
        clientConfiguration.setProxyProtocol(protocol);
        clientConfiguration.setAccessKey(accessKey);
        clientConfiguration.setSecretKey(secretKey);
        clientConfiguration.setRegion(region.toString());

        clientConfiguration.setOverrideEndpoint(mustUseLocalUri);
        clientConfiguration.setUriEndpointOverride(localUri);

        AWS2CamelS3InternalClient internalClientConfiguration = new AWS2S3ClientStandardImpl(clientConfiguration);
        return internalClientConfiguration.getS3Client();
    }
}
