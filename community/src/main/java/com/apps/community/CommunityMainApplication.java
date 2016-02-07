package com.apps.community;

import com.apps.community.health.TemplateHealthCheck;
import com.apps.community.resources.HelloWorldResource;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CommunityMainApplication extends Application<CommunityMainConfiguration> {

	private String DB_ADDR = "192.168.56.101";

	public static void main(final String[] args) throws Exception {
		new CommunityMainApplication().run(args);
	}

	@Override
	public String getName() {
		return "CommunityMain";
	}

	@Override
	public void initialize(final Bootstrap<CommunityMainConfiguration> bootstrap) {
		// TODO: application initialization
	}

	@Override
	public void run(final CommunityMainConfiguration configuration,
			final Environment environment) {
		final TemplateHealthCheck healthCheck =
				new TemplateHealthCheck("template");
		environment.healthChecks().register("template", healthCheck); 
		final HelloWorldResource resource = new HelloWorldResource(
				"dropwizard!",
				"none"
				);
		environment.jersey().register(resource);


		// Totally test code to check connection to cassandra
		Cluster cluster;
		Session session;

		// Connect to the cluster and keyspace "communit"
		cluster = Cluster.builder().addContactPoint(DB_ADDR).build();
		session = cluster.connect("community");

		// Use select to get the user we just entered
		ResultSet results = session.execute("SELECT * FROM users");
		for (Row row : results) {
			System.out.format("%s %s\n", row.getString("fname"), row.getString("email"));
		}

	}

}
