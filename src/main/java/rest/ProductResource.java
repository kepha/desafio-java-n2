
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

import rest.dto.req.ProductReqDTO;
import service.ProductService;


@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Product", description = "Todos os métodos de podutos")
public class ProductResource {

    @Inject
    ProductService productService;

    @POST
    @Operation(description = "Salva um produto no banco de dados", summary = "Salva um produto")
    @APIResponse(responseCode = "201", description = "Produto cadastrado")
    public Response createProduct(ProductReqDTO productReqDTO) {

        productService.create(productReqDTO);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Operation(description = "Lista todos os produtos cadastrados", summary = "Lista de produtos")
    @APIResponse(responseCode = "200", description = "OK")    
    public Response listProduct() {
        return Response.ok(productService.searchAll()).build();
    }

    @GET
    @Operation(description = "Busca um produto pelo seu ID", summary = "Busca produto pelo ID")    
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso o produto não seja encontrado")
    @Path("{productId}")
    public Response productById(
            @PathParam("productId") Long productId) {
        return Response.ok(productService.findById(productId)).build();
    }

    @DELETE
    @Operation(description = "Deleta um produto no banco de dados", summary = "Deleta um produto")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Caso o produto não seja encontrado")    @Path("{productId}")
    public Response deleteProduct(
            @PathParam("productId") Long productId) {
        productService.delete(productId);

        return Response.noContent().build();
    }
}
