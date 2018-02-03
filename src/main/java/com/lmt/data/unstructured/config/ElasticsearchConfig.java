package com.lmt.data.unstructured.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.lmt.data.unstructured.util.UdConstant;

/**
 * @author MT-Lin
 * @date 2018/1/16 11:33
 */
@Configuration
public class ElasticsearchConfig {

	private Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;

	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNodes;

	@Bean
	@SuppressWarnings("resource")
	public TransportClient transportClient() {
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		try {
			Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true)
					.build();
			PreBuiltTransportClient preBuiltTransportClient = new PreBuiltTransportClient(settings);
			if (!StringUtils.isEmpty(this.clusterNodes)) {
				for (String nodes : clusterNodes.split(UdConstant.CLUSTER_NODES_SPLIT)) {
					String[] iNetSocket = nodes.split(UdConstant.ADDRESS_PORT_SPLIT);
					String address = iNetSocket[0];
					Integer port = Integer.valueOf(iNetSocket[1]);
					preBuiltTransportClient
							.addTransportAddress(new TransportAddress(InetAddress.getByName(address), port));
				}
				client = preBuiltTransportClient;
			}
		} catch (UnknownHostException e) {
			logger.error("Initial elasticsearch client error: ", e);
		}
		return client;
	}
}
