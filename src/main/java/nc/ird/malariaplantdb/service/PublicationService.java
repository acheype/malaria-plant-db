package nc.ird.malariaplantdb.service;

import com.fasterxml.jackson.annotation.JsonView;
import jersey.repackaged.com.google.common.collect.Lists;
import nc.ird.malariaplantdb.entities.Publication;
import nc.ird.malariaplantdb.repositories.PublicationRepo;
import nc.ird.malariaplantdb.service.json.View;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Service class for the publication entities.
 * <p>
 * Some operations are directly exposes as a Rest Web Services via the JAX-RS specification.
 *
 * @author acheype
 */
@Service
@Path("/publications")
public class PublicationService {

    static final private String DEFAULT_PAGE_SIZE = "20";

    @Inject
    private PublicationRepo publiRepo;

    @Transactional(readOnly = true)
    @JsonView(View.Summary.class)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<Publication> getAll(@DefaultValue("0") @QueryParam("page") Integer page,
                                    @DefaultValue(DEFAULT_PAGE_SIZE) @QueryParam("size") Integer size) {
        PageRequest request = new PageRequest(page, size);

        return Lists.newArrayList(publiRepo.findAll(request));
    }

    @Transactional(readOnly = true)
    @JsonView(View.Detailed.class)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Publication pub = publiRepo.findOne(id);

        if (pub == null)
            return Response
                    .status(404)
                    .entity("The publication with the id " + id + " does not exist")
                    .build();
        else
            return Response.status(200).entity(pub).build();
    }

    @Transactional(readOnly = true)
    @Produces(MediaType.TEXT_PLAIN)
    @HEAD
    @Path("/{id}")
    public Response exists(@PathParam("id") Long id) {
        if (!publiRepo.exists(id))
            return Response.status(404).build();
        else
            return Response.status(200).build();
    }

    @Transactional(readOnly = true)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/count")
    public long count() {
        return publiRepo.count();
    }

}

