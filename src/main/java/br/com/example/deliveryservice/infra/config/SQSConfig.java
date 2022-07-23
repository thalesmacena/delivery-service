package br.com.example.deliveryservice.infra.config;

import org.apache.camel.component.aws2.sqs.Sqs2Configuration;
import org.apache.camel.component.aws2.sqs.client.Sqs2InternalClient;
import org.apache.camel.component.aws2.sqs.client.impl.Sqs2ClientStandardImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.core.Protocol;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SQSConfig {

    @Value("${amazon.region}")
    private Region region;

    @Value("${amazon.accessKey}")
    private String accessKey;

    @Value("${amazon.secretKey}")
    private String secretKey;

    @Value("${amazon.sqs.proxy-host}")
    private String host;

    @Value("${amazon.sqs.proxy-port}")
    private int port;

    @Value("${amazon.sqs.proxy-protocol}")
    private Protocol protocol;

    @Value("${amazon.use-local-uri}")
    private boolean mustUseLocalUri;

    @Value("${amazon.local-endpoint}")
    private String localUri;

    @Bean("sqsClient")
    public SqsClient amazonSQS() {
        Sqs2Configuration clientConfiguration = new Sqs2Configuration();

        clientConfiguration.setProxyHost(host);
        clientConfiguration.setProxyPort(port);
        clientConfiguration.setProxyProtocol(protocol);
        clientConfiguration.setAccessKey(accessKey);
        clientConfiguration.setSecretKey(secretKey);
        clientConfiguration.setRegion(region.toString());

        clientConfiguration.setOverrideEndpoint(mustUseLocalUri);
        clientConfiguration.setUriEndpointOverride(localUri);

        Sqs2InternalClient internalClientConfiguration = new Sqs2ClientStandardImpl(clientConfiguration);
        return internalClientConfiguration.getSQSClient();
    }
}
