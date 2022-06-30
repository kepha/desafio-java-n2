
package rest;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import rest.dto.req.CustomerReqDTO;
import rest.dto.resp.CustomerRespDTO;
import service.CustomerService;


@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@Tag(name = "Customer", description = "Todos os métodos de cliente")
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @POST
    @Operation(description = "Salva um cliente no banco de dados", summary = "Salva um cliente")
    @APIResponse(responseCode = "201", description = "Cliente cadastrado")
    @APIResponse(responseCode = "400", description = "Caso o cliente já exista")
    public Response createCustomer(CustomerReqDTO customerDTO) {
        CustomerRespDTO customerRespDTO = customerService.create(customerDTO);
        return Response.status(Status.CREATED).entity(customerRespDTO).build();
    }

    @GET
    @Operation(description = "Lista todos os clientes cadastrados", summary = "Lista todos os clientes")
    @APIResponse(responseCode = "200", description = "OK")
    public Response listCustomer() {
        return Response.ok(customerService.seachAll()).build();
    }

    @GET
    @Path("/actives")
    @Operation(description = "Lista todos os clientes cadastrado ativos", summary = "Lista todos os clientes ativos")
    @APIResponse(responseCode = "200", description = "OK")
    public Response Actives() {
        return Response.ok(customerService.allActives()).build();
    }

    @GET
    @Path("/inactives")
    @Operation(description = "Lista todos os clientes cadastrado inativos", summary = "Lista todos os clientes inativos")
    @APIResponse(responseCode = "200", description = "OK")
    public Response Inactives() {
        return Response.ok(customerService.allInactives()).build();
    }

    @GET
    @Path("{customerId}")
    @Operation(description = "Busca um cliente pelo seu ID", summary = "Busca cliente pelo ID")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso o cliente não seja encontrado")
    public Response customerById(
            @PathParam("customerId") Long customerId) {
        return Response.ok(customerService.findById(customerId)).build();
    }

    @DELETE
    @Path("{customerId}")
    @Operation(description = "Inativa um cliente pelo seu ID", summary = "Inativa um cliente")
    @APIResponse(responseCode = "404", description = "Caso o cliente não seja encontrado")
    public Response deleteCustomer(
            @PathParam("customerId") Long customerId) {
        customerService.delete(customerId);

        return Response.noContent().build();
    }
}