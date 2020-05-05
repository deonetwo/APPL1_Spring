package com.jtk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	@Autowired
	private Environment env;
	
	@Override
	protected String getDatabaseName() {
		return "userNotificationDB";
	}
	
	public Mongo mongo() throws Exception {
		MongoCredential credential =
			MongoCredential.createMongoCRCredential(
				env.getProperty("mongo.username"),
				getDatabaseName(),
				env.getProperty("mongo.password").toCharArray());
		
		return new MongoClient(
			new ServerAddress("localhost", 27017), Arrays.asList(credential));
	}

	@Override
	public MongoClient mongoClient() {
		// TODO Auto-generated method stub
		return null;
	}
 }
