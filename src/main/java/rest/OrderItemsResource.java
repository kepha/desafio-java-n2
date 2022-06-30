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

import rest.dto.req.OrderItemsReqDTO;
import service.OrderItemsService;



@Path("orderItems")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "OrderItems", description = "Todos os métodos de order items")
public class OrderItemsResource {
    @Inject
    OrderItemsService orderItemService;

    @POST
    @Operation(description = "Salva uma order de items no banco de dados", summary = "Salva uma order de items")
    @APIResponse(responseCode = "201", description = "Order items cadastrada")
    @APIResponse(responseCode = "400", description = "Caso o cliente já exista")
    public Response createOrderItem(OrderItemsReqDTO OrderItemsReqDTO) {
        orderItemService.create(OrderItemsReqDTO);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Operation(description = "Lista todas as orders items cadastrados", summary = "Lista as orders items")
    @APIResponse(responseCode = "200", description = "OK")
    public Response listOrderItem() {
        return Response.ok(orderItemService.seachAll()).build();
    }

    @GET
    @Path("{orderItemId}")
    @Operation(description = "Busca uma order item pelo seu ID", summary = "Busca order item pelo ID")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso order items não seja encontrada")
    public Response orderItemById(
            @PathParam("orderItemId") Long orderItemId) {
        return Response.ok(orderItemService.findById(orderItemId)).build();
    }

    @GET
    @Path("orders/{orderId}")
    @Operation(description = "Lista order items de uma order", summary = "Busca order items pela order")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso order items não seja encontrada")
    public Response orderItemByOrderId(
            @PathParam("orderId") Long orderId) {
        return Response.ok(orderItemService.findByOrderId(orderId)).build();
    }

    @DELETE
    @Path("{orderItemId}")
    @Operation(description = "Deleta uma order de items pelo seu ID", summary = "Deleta uma order de items")
    @APIResponse(responseCode = "404", description = "Caso order items não seja encontrado")
    public Response deleteOrderItem(
            @PathParam("orderItemId") Long orderItemId) {
        orderItemService.delete(orderItemId);

        return Response.noContent().build();
    }
}
