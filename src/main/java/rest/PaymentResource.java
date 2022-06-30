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

import rest.dto.req.PaymentReqDTO;
import service.PaymentService;

@Path("payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Payment", description = "Todos os métodos de pagamento")
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    @POST
    @Operation(description = "Salva um pagamento no banco de dados", summary = "Salva um pagamento")
    @APIResponse(responseCode = "201", description = "Cliente cadastrado")    
    public Response createPayment(PaymentReqDTO paymentReqDTO) {
        paymentService.create(paymentReqDTO);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Operation(description = "Lista todos os pagamentos cadastrados", summary = "Lista todos os pagamentos")
    @APIResponse(responseCode = "200", description = "OK")
    public Response listPayment() {
        return Response.ok(paymentService.searchAll()).build();
    }

    @GET
    @Path("{paymentId}")
    @Operation(description = "Busca um pagamento pelo seu ID", summary = "Busca pagamento pelo ID")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso o pagamento não seja encontrado")
    public Response paymentById(
            @PathParam("paymentId") Long paymentId) {
        return Response.ok(paymentService.findById(paymentId)).build();
    }

    @GET
    @Path("/orders/{paymentId}")
    @Operation(description = "Busca um uma order pelo seu pagamento", summary = "Busca pagamento pelo ID")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso o pagamento não seja encontrado")
    public Response orderByPaymentd(
            @PathParam("paymentId") Long paymentId) {
        return Response.ok(paymentService.findOrderByPayment(paymentId)).build();
    }

    @DELETE
    @Path("{paymentId}")
    @Operation(description = "Cancela um pagamento pelo seu ID", summary = "Cancela um pagamento")
    @APIResponse(responseCode = "404", description = "Caso o pagamento não seja encontrado")
    public Response deletePayment(
            @PathParam("paymentId") Long paymentId) {
        paymentService.delete(paymentId);

        return Response.noContent().build();
    }
}
