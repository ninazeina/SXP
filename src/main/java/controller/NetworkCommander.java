package controller;

import javax.ws.rs.GET;  //REST-related dependencies
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import model.entity.LoginToken;
import network.api.ContractService;
import network.api.Peer;
import network.impl.ContractListener;
import network.impl.jxta.JxtaContractService;
import rest.api.Authentifier;
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
    @Path("afficheToken")
    public String afficheToken()
    {
    	/*
    	 * Juste une fonction test
    	 */
    	Authentifier auth = Application.getInstance().getAuth();
    	LoginToken token = new LoginToken();
		token.setToken(auth.getToken("aaa", "aaa"));
        String login = Application.getInstance().getAuth().getLogin(token.getToken());
		String password = Application.getInstance().getAuth().getPassword(token.getToken());
    	return token.getToken()+"\n Password"+password+"\n Login"+login;
    	
    	//return (token == null ? "test":token);
    }
    
    @GET
    @Path("ajouteListener/{input}")
    public String ajouteListener(@PathParam("input") String peer)
    {
    	/*
    	 * Début d'implementation de Listener sur le Token
    	 */
    	/*Authentifier auth = Application.getInstance().getAuth();
    	LoginToken token = new LoginToken();
		token.setToken(auth.getToken("aaa", "aaa"));
    	Application.getInstance().getPeer().getService(JxtaContractService.NAME).addListener(new ContractListener(), token.getToken() == null ? "test":token.getToken());
    	return "Listener ajoutée sur le token : "+token.getToken();*/
    	Application.getInstance().getPeer().getService(JxtaContractService.NAME).addListener(new ContractListener(), peer);
    	return "Listener ajoutée sur le peer : "+peer;
    }
    
    @GET
    @Path("envoyerMessage/{input1}/{input2}/{input3}/{input4}")
    public String envoyerMessage(@PathParam("input1") String nomContrat, @PathParam("input2") String destinataire, 
    		@PathParam("input3") String itemVoulu, @PathParam("input4") String itemAEchanger)
    {
    	Peer p = Application.getInstance().getPeer();
    	ContractService service = (ContractService) p.getService(JxtaContractService.NAME);
    	service.sendContract(nomContrat, p.getUri() , itemVoulu, itemAEchanger, destinataire);
    	return "Message envoye de "+p.getUri()+" vers "+destinataire;
    }
    
}
