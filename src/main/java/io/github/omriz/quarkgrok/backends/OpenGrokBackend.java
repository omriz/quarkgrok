package io.github.omriz.quarkgrok.backends;

import java.net.URL;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;

public class OpenGrokbackend  implements BackendInterface{
    @Inject
    @RestClient
    OpenGrokClientInterface openGrokClient;

}