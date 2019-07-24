package com.cormacLaptops.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cormacLaptops.data.LaptopDao;
import com.cormacLaptops.model.Laptop;

@Path("/laptops")
@Stateless
@LocalBean
public class LaptopWs {
	@EJB
	private LaptopDao laptopDao;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAll(){
		final List<Laptop> laptops = laptopDao.getLaptops();
		return Response.status(200).entity(laptops).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Laptop getLaptop(@PathParam("id") final int laptopId) {
		return laptopDao.getLaptop(laptopId);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/search")
	public Response getLaptopByMake(@QueryParam("make") final String make) {
		final List<Laptop> laptops = laptopDao.getLaptopByMake(make);
		return Response.status(200).entity(laptops).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("ram/{ram}")
	public Response getLaptopBytRam(@PathParam("ram") final int ram) {
		final List<Laptop> laptops = laptopDao.getLaptopsByRam(ram, ram);
		return Response.status(200).entity(laptops).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("ram/{minRam}/{maxRam}")
	public Response getLaptopByRam(@PathParam("minRam") final int ramMin, @PathParam("maxRam") final int maxRam) {
		final List<Laptop> laptops = laptopDao.getLaptopsByRam(ramMin, maxRam);
		return Response.status(200).entity(laptops).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveLaptop(final Laptop laptop) {
		laptopDao.addLaptop(laptop);
		return Response.status(201).entity(laptop).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateLaptop(final Laptop laptop) {
		laptopDao.update(laptop);
		return Response.status(200).entity(laptop).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeLaptop(@PathParam("id") final int laptopId){
		laptopDao.removeLaptop(laptopId);
		return Response.status(204).build();
	}

}
