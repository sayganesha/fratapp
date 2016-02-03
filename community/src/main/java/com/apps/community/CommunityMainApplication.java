package com.apps.community;

import com.apps.community.health.TemplateHealthCheck;
import com.apps.community.resources.HelloWorldResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CommunityMainApplication extends Application<CommunityMainConfiguration> {

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
    }

}
