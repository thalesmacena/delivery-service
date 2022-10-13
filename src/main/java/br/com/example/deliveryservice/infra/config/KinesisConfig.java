package br.com.example.deliveryservice.infra.config;

import org.apache.camel.component.aws2.kinesis.Kinesis2Configuration;
import org.apache.camel.component.aws2.kinesis.client.KinesisInternalClient;
import org.apache.camel.component.aws2.kinesis.client.impl.KinesisClientStandardImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.core.Protocol;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisClient;

@Configuration
public class KinesisConfig {
    @Value("${amazon.region}")
    private Region region;

    @Value("${amazon.accessKey}")
    private String accessKey;

    @Value("${amazon.secretKey}")
    private String secretKey;

    @Value("${amazon.kinesis.proxy-host}")
    private String host;

    @Value("${amazon.kinesis.proxy-port}")
    private int port;

    @Value("${amazon.kinesis.proxy-protocol}")
    private Protocol protocol;

    @Value("${amazon.use-local-uri}")
    private boolean mustUseLocalUri;

    @Value("${amazon.local-endpoint}")
    private String localUri;

    @Bean("kinesisClient")
    public KinesisClient amazonKinesis() {
        Kinesis2Configuration clientConfiguration = new Kinesis2Configuration();

        clientConfiguration.setProxyHost(host);
        clientConfiguration.setProxyPort(port);
        clientConfiguration.setProxyProtocol(protocol);
        clientConfiguration.setAccessKey(accessKey);
        clientConfiguration.setSecretKey(secretKey);
        clientConfiguration.setRegion(region.toString());

        clientConfiguration.setOverrideEndpoint(mustUseLocalUri);
        clientConfiguration.setUriEndpointOverride(localUri);

        KinesisInternalClient internalClientConfiguration = new KinesisClientStandardImpl(clientConfiguration);
        return internalClientConfiguration.getKinesisClient();
    }
}
