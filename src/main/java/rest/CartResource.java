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

import service.CartService;


@Path("carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cart", description = "Todos os métodos de carrinho")
public class CartResource {
    @Inject
    CartService cartService;

    @POST
    @Path("{customerId}")
    @Operation(description = "Salva um carrinho no banco de dados passando o ID do cli", summary = "Salva um carrinho")
    @APIResponse(responseCode = "201", description = "Carrinho cadastrado")
    @APIResponse(responseCode = "400", description = "Caso já esteja um carro ativo para o mesmo cliente")
    public Response createCart(@PathParam("customerId") Long customerId) {
        cartService.create(customerId);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Operation(description = "Lista todos os carrinhos cadastrados", summary = "Lista todos os carrinhos")
    @APIResponse(responseCode = "200", description = "OK")
    public Response listCart() {
        return Response.ok(cartService.searchAll()).build();
    }

    @GET
    @Path("/actives")
    @Operation(description = "Lista todos os carrinhos cadastrado que ativos", summary = "Lista todos os carrinhos ativos")
    @APIResponse(responseCode = "200", description = "OK")
    public Response activesCart() {
        return Response.ok(cartService.activesCart()).build();
    }

    @GET
    @Path("{cartId}")
    @Operation(description = "Busca um carrinho pelo seu ID", summary = "Busca carrinho pelo ID")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso o carrinho não seja encontrado")
    public Response cartById(
            @PathParam("cartId") Long cartId) {
        return Response.ok(cartService.findById(cartId)).build();
    }

    @DELETE
    @Path("{cartId}")
    @Operation(description = "Cancela um carrinho seu ID", summary = "Cancelar um carrinho")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso o carrinho não seja encontrado")
    public Response deleteCart(
            @PathParam("cartId") Long cartId) {
        cartService.delete(cartId);
        return Response.noContent().build();
    }    
}
