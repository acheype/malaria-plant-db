package nc.ird.malariaplantdb.service;

import nc.ird.malariaplantdb.entities.Publication;
import nc.ird.malariaplantdb.repositories.PubTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

@Component
@Path("/tests")
@Produces("application/json")
public class TestService {

    @Autowired
    PubTestRepository pubRepo;

    @GET
    @Path("/get/{id}")
    public Publication get(@PathParam("id") Long id) {
        try {
            return pubRepo.findOne(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @POST
    public Long postRequest(String title) {
        Publication pub = new Publication();
        pub.setTitle(title);
        return pubRepo.save(pub).getId();
    }
}
