package com.apps.community.test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Rule;
import org.junit.Test;

import com.apps.community.CommunityMainApplication;
import com.apps.community.CommunityMainConfiguration;
import com.apps.community.api.Saying;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class HelloWorldIntegrationTest {
    @Rule
    public final DropwizardAppRule<CommunityMainConfiguration> RULE =
        new DropwizardAppRule<CommunityMainConfiguration>(CommunityMainApplication.class,
            ResourceHelpers.resourceFilePath("my-app-config.yaml"));

    @Test
    public void runServerTest() {
        Client client = new JerseyClientBuilder().build();
        Saying result = client.target(
            String.format("http://localhost:%d/hello-world", RULE.getLocalPort())
        ).queryParam("name", "Hello Community").request().get(Saying.class);
        assertThat(result.getContent()).contains("dropwizard!");
    }
}
