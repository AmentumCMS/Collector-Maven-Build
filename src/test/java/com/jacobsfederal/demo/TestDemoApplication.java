package com.amentumcms.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionDetails;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.PulsarContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestDemoApplication {

	@Bean
	GenericContainer<?> activeMQContainer() {
		return new GenericContainer<>(DockerImageName.parse("symptoma/activemq:5.16.0"))
				.withExposedPorts(61616);
	}

	@Bean
	ActiveMQConnectionDetails activeMQConnectionDetails(GenericContainer<?> activeMQContainer) {
		return new ActiveMQConnectionDetails() {
			@Override
			public String getBrokerUrl() {
				return "tcp://" + activeMQContainer.getHost() + ":" + activeMQContainer.getMappedPort(61616);
			}

			@Override
			public String getUser() {
				return "admin";
			}

			@Override
			public String getPassword() {
				return "admin";
			}
		};
	}

	@Bean
	@ServiceConnection
	KafkaContainer kafkaContainer() {
		return new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"));
	}

	@Bean
	@ServiceConnection
	MariaDBContainer<?> mariaDbContainer() {
		return new MariaDBContainer<>(DockerImageName.parse("mariadb:11.4"));
	}

	@Bean
	@ServiceConnection
	MongoDBContainer mongoDbContainer() {
		return new MongoDBContainer(DockerImageName.parse("mongo:7.0"));
	}

	@Bean
	@ServiceConnection
	MySQLContainer<?> mysqlContainer() {
		return new MySQLContainer<>(DockerImageName.parse("mysql:8.4"));
	}

	@Bean
	@ServiceConnection
	OracleContainer oracleFreeContainer() {
		return new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:23-slim"));
	}

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));
	}

	@Bean
	@ServiceConnection
	PulsarContainer pulsarContainer() {
		return new PulsarContainer(DockerImageName.parse("apachepulsar/pulsar:3.3.0"));
	}

	@Bean
	@ServiceConnection
	RabbitMQContainer rabbitContainer() {
		return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.13"));
	}

	@Bean
	@ServiceConnection(name = "redis")
	GenericContainer<?> redisContainer() {
		return new GenericContainer<>(DockerImageName.parse("redis:7.4"))
				.withExposedPorts(6379);
	}

	public static void main(String[] args) {
		SpringApplication.from(DemoApplication::main).with(TestDemoApplication.class).run(args);
	}
}
