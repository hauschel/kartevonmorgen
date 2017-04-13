package org.kartevonmorgen.backend;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.kartevonmorgen.backend.db.AddressRepository;
import org.kartevonmorgen.backend.db.AddressRepositoryImpl;
import org.kartevonmorgen.backend.domain.Address;

@Path("addresses")
public class AddressResource {

	private AddressRepository addressRepository = new AddressRepositoryImpl();
	
//	public AddressResource(AddressRepository addressRepositoryParam)
//	{
//		this.addressRepository = addressRepositoryParam;	
//	}
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Address createAddress(Address address) 
    {    	
    	Address result = null;
    	try {
			result = new Address( addressRepository.create(address) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }

    @GET @Path("/{id}")
    @Produces({"application/json"})
    public Address getAddress(@PathParam("id") long id)
    {
    	return addressRepository.read(id);
    }
    
    @GET @Path("/list")
    @Produces({"application/json"})
    public List<Address> getAddressList() 
    {
    	return addressRepository.list();
    }
    
    @DELETE @Path("/{id}")
    @Produces({"application/json"})
    public void deleteAddress(@PathParam("id") long id) 
    {
    	addressRepository.delete(id);
    }
    
}
