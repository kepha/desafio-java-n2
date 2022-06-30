package rest.openapi;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;


@OpenAPIDefinition(info = @Info(description = "Conjunto de endpoints da  applicação Kepha store", 
title = "Kepha Store", version = "0.0.1", contact = @Contact(name = "John", email = "progjohn@outlook.com")))
public class DocAPI extends Application{
    
}
