package rest;


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
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import rest.dto.req.OrderReqDTO;
import service.OrderService;


@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Order", description = "Todos métodos de order")
public class OrderResource {
    @Inject
    OrderService orderService;

    @POST
    @Operation(description = "Salva uma order no banco de dados", summary = "Salva uma order")
    @APIResponse(responseCode = "201", description = "Order cadastrado")
    public Response createOrder(OrderReqDTO orderReqDTO) {

        orderService.create(orderReqDTO);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Operation(description = "Lista todos as orders cadastradas", summary = "Lista todos as orders")
    @APIResponse(responseCode = "200", description = "OK")
    public Response listOrder() {
        return Response.ok(orderService.seachAll()).build();
    }

    @GET
    @Path("{orderId}")
    @Operation(description = "Busca uma order pelo seu ID", summary = "Busca order pelo ID")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso a order não seja encontrada")
    public Response orderById(
            @PathParam("orderId") Long orderId) {
        return Response.ok(orderService.findById(orderId)).build();
    }

    @GET
    @Path("/customers/{customerId}")
    @Operation(description = "Busca uma order pelo o ID do cliente", summary = "Busca order pelo cliente")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso a order não seja encontrada")
    public Response orderByCustomer(
            @PathParam("customerId") Long customerId) {
        return Response.ok(orderService.findByCartCustomer(customerId)).build();
    }

    @DELETE
    @Path("{orderId}")
    @Operation(description = "Deleta uma order pelo seu ID", summary = "Deleta uma order")
    @APIResponse(responseCode = "404", description = "Caso o order não seja encontrada")
    public Response deleteOrder(
            @PathParam("orderId") Long orderId) {
        orderService.delete(orderId);

        return Response.noContent().build();
    }    
}
