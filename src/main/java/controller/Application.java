package controller;

import java.util.Properties;

import javax.servlet.jsp.el.ImplicitObjectELResolver;

import org.ethereum.cli.CLIInterface;

import model.syncManager.UserSyncManagerImpl;
import network.api.Peer;
import network.factories.PeerFactory;
import protocol.impl.blockChain.EthereumImpl;
import rest.api.Authentifier;
import rest.factories.AuthentifierFactory;
import rest.factories.RestServerFactory;

/**
 * Main class
 * {@link Application} is a singleton
 * @author Julien Prudhomme
 *
 */
public class Application {
	private static Application instance = null;
	private Peer peer;
	private Authentifier auth;
	
	// variable For test
	private int serveur;
	
	public Application() {
		if(instance != null) {
			throw new RuntimeException("Application can be instanciate only once !");
		}
		instance = this;
	}

	public static Application getInstance()	{
		return instance;
	}

	public void run() {
		setPeer(PeerFactory.createDefaultAndStartPeer());
		setAuth(AuthentifierFactory.createDefaultAuthentifier());
		RestServerFactory.createAndStartDefaultRestServer(8080); //start the rest api
	}

	public void runForTests(int restPort) {
		setServeur(restPort);
		Properties p = System.getProperties();
		p.put("derby.system.home", "./.db-" + restPort + "/");
		new UserSyncManagerImpl(); //just init the db
		setPeer(PeerFactory.createDefaultAndStartPeerForTest());
		setAuth(AuthentifierFactory.createDefaultAuthentifier());
		RestServerFactory.createAndStartDefaultRestServer(restPort);
	}
	
	public static void main(String[] args) {
		new Application();
		Application.getInstance().runForTests(8082);
		CLIInterface.call(args);
		new Thread(new EthereumImpl().new deployContract()).start();
	}

	//Function for test
	
	public void affichePeer()
	{
		System.out.println("Peer : "+getPeer().getUri());
	}
	
	//Function for test
	
	public void afficheServeur()
	{
		System.out.println("serveur : "+getServeur());
	}
	public Peer getPeer() {
		return peer;
	}

	public void setPeer(Peer peer) {
		this.peer = peer;
	}

	public Authentifier getAuth() {
		return auth;
	}

	public void setAuth(Authentifier auth) {
		this.auth = auth;
	}

	public int getServeur() {
		return serveur;
	}

	public void setServeur(int serveur) {
		this.serveur = serveur;
	}
}
