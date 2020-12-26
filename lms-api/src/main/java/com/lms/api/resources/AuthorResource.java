package com.lms.api.resources;

import com.lms.api.ApiConstants;
import com.lms.common.models.Author;
import com.lms.common.models.AuthorList;
import com.lms.service.AuthorService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path(ApiConstants.LMS_AUTHOR_PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@OpenAPIDefinition(
        info = @Info(title = "LMS", description = "Library Management", version = "1.0.0"))
public class AuthorResource {

    private final AuthorService authorService;

    @Autowired
    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    // http://localhost:8080/v1/authors
    // http://localhost:8080/v1/authors?firstName=XYZ
    // http://localhost:8080/v1/authors?firstName=XYZ&lastName=ABC

    @GET
    @Operation(
            description = "Gets all get available authors",
            method = ApiConstants.GET,
            security = {
                    @SecurityRequirement(
                            name = "Authorization",
                            scopes = {"Authorization"})
            },
            operationId = "getAuthors")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = ApiConstants.OK,
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            schema = @Schema(implementation = AuthorList.class))
                            }),
                    @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST),
                    @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN),
                    @ApiResponse(responseCode = "422", description = ApiConstants.UNPROCESSABLE),
                    @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND)
            })
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public AuthorList getAuthors(
            @Parameter(description = "First Name") @QueryParam("firstName") String firstName,
            @Parameter(description = "Last Name") @QueryParam("lastName") String lastName) {


        return authorService.getAuthors(firstName, lastName);
    }


    // http://localhost:8080/v1/authors/123

    @GET
    @Path("/{id}")
    @Operation(
            description = "Gets author details for a given id",
            method = ApiConstants.GET,
            security = {
                    @SecurityRequirement(
                            name = "Authorization",
                            scopes = {"Authorization"})
            },
            operationId = "getAuthor")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = ApiConstants.OK,
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            schema = @Schema(implementation = Author.class))
                            }),
                    @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST),
                    @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN),
                    @ApiResponse(responseCode = "422", description = ApiConstants.UNPROCESSABLE),
                    @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND)
            })
    public Author getAuthor(
            @Parameter(description = "Id of a server", required = true) @PathParam("id") @NotNull
                    Integer id) {
        return this.authorService.getAuthor(id);
    }

    @POST
    @Operation(
            description = "Register an author in Library",
            method = ApiConstants.POST,
            security = {
                    @SecurityRequirement(
                            name = "Authorization",
                            scopes = {"Authorization"})
            },
            operationId = "register")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = ApiConstants.OK,
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            schema = @Schema(implementation = Author.class))
                            }),
                    @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST)
            })
    public Author register(
            @Parameter(description = "Author registration request") Author author) {

        return this.authorService.register(author);
    }
}
