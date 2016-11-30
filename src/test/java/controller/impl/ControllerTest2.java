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
		//crée les pairs
		setPeer1(PeerFactory.createDefaultAndStartPeerForTest());
		setPeer2(PeerFactory.createDefaultAndStartPeerForTest());
		System.out.println("\n");
		
		//ajoute les listener sur les services qui gère les contrats
		/*setContractService1((ContractService)getPeer1().getService("contracts"));
		setContractService2((ContractService)getPeer2().getService("contracts"));*/
		//getContractService1().addListener(new ContractListener(), getPeer2().getUri());
		//getContractService2().addListener(new ContractListener(), getPeer1().getUri());
		
		
		Service service1 = new BlockChainEstablisher();
		service1.initAndStart(getPeer1());
		BlockChainEstablisher establisher1 = (BlockChainEstablisher) service1;
		
		
		Service service2 = new BlockChainEstablisher();
		service2.initAndStart(getPeer2());
		BlockChainEstablisher establisher2 = (BlockChainEstablisher) service2;
		
		establisher1.addListener(new ContractListener(), getPeer2().getUri());
		establisher2.addListener(new ContractListener(), getPeer1().getUri());
		
		//créer les items
		Item itemVoulu = new Item();
		Item itemAEchanger = new Item();
		
		itemVoulu.setTitle("Patates");
		itemAEchanger.setTitle("Carottes");
		
		pause(5000);
		
		establisher1.initialize(true,"utilisateur1");
		establisher2.initialize(false, "utilisateur2");
		
		System.out.println();
		
		pause(5000);
		
		establisher1.sendContract("contract n°1", "user1", itemVoulu.getTitle(), itemAEchanger.getTitle(), getPeer2().getUri());
		
		pause(5000);
		
		establisher1.displayContract();
		
		pause(5000);
		
		establisher2.displayContract();
		
		pause(5000);
		
		establisher2.sendWish(Wish.ACCEPT, "user2", getPeer1().getUri());
		
		pause(5000);
		
		establisher1.displayContract();
		
		pause(5000);
		
		establisher2.displayContract();
		
		pause(5000);
		
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
