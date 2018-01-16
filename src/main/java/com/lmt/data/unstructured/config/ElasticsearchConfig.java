package com.lmt.data.unstructured.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author MT-Lin
 * @date 2018/1/16 11:33
 */
@Configuration
public class ElasticsearchConfig {

    @Bean
    public TransportClient transportClient() throws UnknownHostException {
        TransportAddress address = new TransportAddress(InetAddress.getByName("localhost"), 9300);
        Settings settings = Settings.builder()
                .put("cluster.name", "MT-Lin")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(address);
        return client;
    }
}
