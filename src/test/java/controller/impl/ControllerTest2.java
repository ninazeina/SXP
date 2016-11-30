package controller.impl;

import org.junit.Test;
import model.entity.Item;
import network.api.ContractService;
import network.api.Peer;
import network.api.service.Service;
import network.factories.PeerFactory;
import network.impl.ContractListener;
import protocol.api.Wish;
import protocol.impl.blockChain.BlockChainEstablisher;
import protocol.api.Status;

public class ControllerTest2
{
	private Peer peer1;
	private Peer peer2;
	
	/*private ContractService contractService1;
	private ContractService contractService2;*/
	
	public void voirStatus(BlockChainEstablisher establisher)
	{
		System.out.println();
		if(establisher.getStatus().equals(Status.SIGNING))
			System.out.println("Bravo contrat signé !!!");
		else if(establisher.getStatus().equals(Status.CANCELLED))
			System.out.println("Réponse négative, fermeture du contrat");
		else 
			System.out.println("Le contrat est en cours de finalisation ( attente d'une réponse positive ou négative )");
	}
	
	public void signer(BlockChainEstablisher establisher,boolean signe,String... uris)
	{
		/*
		 * Signer sur la blockchain
		 */
		String sign;
		if(signe)
			sign = "true";
		else
			sign = "false";
		establisher.sendSignature(sign, establisher.getEstablisherOwner(), uris);
	}
	
	public void pause(int time)
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() 
	{
		// crée les pairs
		setPeer1(PeerFactory.createDefaultAndStartPeerForTest());
		setPeer2(PeerFactory.createDefaultAndStartPeerForTest());
		System.out.println("\n");
		
		// récupere leurs establisher
		BlockChainEstablisher establisher1 = (BlockChainEstablisher) getPeer1().getService("establisher");
		BlockChainEstablisher establisher2 = (BlockChainEstablisher) getPeer2().getService("establisher");
		
		// ajoute les listener 
		establisher1.addListener(new ContractListener(), getPeer2().getUri());
		establisher2.addListener(new ContractListener(), getPeer1().getUri());
		
		// créer les items
		Item itemVoulu = new Item();
		Item itemAEchanger = new Item();
		
		itemVoulu.setTitle("Patates");
		itemAEchanger.setTitle("Carottes");
		
		
		/*
		 * On met a chaque fois des pauses sinon la communication ne fonctionne pas
		 */
		
		pause(5000);
		
		/*
		 * on initialise l'establisher de l'owner et on envoie un contrat a l'utilisateur 2
		 */
		establisher1.initialize(true,"utilisateur1");
		System.out.println();
		pause(5000);
		establisher1.sendContract("contract n°1", getPeer1().getUri(), itemVoulu.getTitle(), itemAEchanger.getTitle(), getPeer2().getUri());
		
		/*
		 * On affiche les contrats
		 */
		pause(5000);
		
		establisher1.displayContract();
		
		pause(5000);
		
		establisher2.displayContract();
		
		pause(5000);
		
		/*
		 * L'utilisateur 2 envoi son voeux
		 */
		establisher2.sendWish(Wish.ACCEPT, getPeer2().getUri(), getPeer1().getUri());
		
		pause(5000);
		
		establisher1.displayContract();
		
		pause(5000);
		
		establisher2.displayContract();
		
		pause(5000);
		
		/*
		 * Signature de l'utilisateur 1
		 */
		signer(establisher1,true,getPeer2().getUri());
		
		pause(5000);
		
		establisher1.displayContract();
		
		pause(5000);
		
		establisher2.displayContract();
		
		pause(5000);
		// On affiche le status qui devrait etre signé !
		//voirStatus(establisher);
		
		
	}
	
	public Peer getPeer1() {
		return peer1;
	}
	public void setPeer1(Peer peer1) {
		this.peer1 = peer1;
	}
	public Peer getPeer2() {
		return peer2;
	}
	public void setPeer2(Peer peer2) {
		this.peer2 = peer2;
	}
}
