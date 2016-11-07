package controller;

import javax.ws.rs.GET;  //REST-related dependencies
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import crypt.api.hashs.Hasher; //module to test dependencies
import crypt.factories.HasherFactory;
import network.api.Peer;
import rest.api.ServletPath;

@ServletPath("/command/network/*")  //url path. PREFIX WITH COMMAND/ !!!
@Path("/")
public class NetworkCommander {   
    @GET
    @Path("affichePeer") //a way to name the pieces of the query
    public String affichePeer() {
    	Peer p = Application.getInstance().getPeer();
    	
    	return p.getUri();
    }
    
    @GET
    @Path("afficheServeur")
    public String afficheServeur()
    {
    	int serveur = Application.getInstance().getServeur();
    	return String.valueOf(serveur);
    }
    
    @GET
    @Path("envoyerMessage/{input1}/{input2}")
    public String envoyerMessage(@PathParam("input1") String peer1, @PathParam("input2") String peer2)
    {
    	return "Message envoye de "+peer1+" vers "+peer2;
    }
}
