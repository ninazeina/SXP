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
	
	private ContractService contractService1;
	private ContractService contractService2;
	
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
		
		pause(10000);
		
		establisher1.initialize(true,"utilisateur1");
		establisher2.initialize(false, "utilisateur2");
		
		System.out.println();
		
		pause(10000);
		
		establisher1.sendContract("contract n°1", "user1", itemVoulu.getTitle(), itemAEchanger.getTitle(), getPeer2().getUri());
		
		pause(10000);
		
		establisher1.displayContract();
		
		pause(10000);
		
		establisher2.displayContract();
		
		pause(10000);
		
		establisher2.sendWish(Wish.ACCEPT, "user2", getPeer1().getUri());
		
		pause(10000);
		
		establisher1.displayContract();
		
		pause(10000);
		
		establisher2.displayContract();
		
		pause(10000);
		
		//establisher1.sendWish(Wish.ACCEPT, getPeer2().getUri(), getPeer1().getUri());
		
		/*
		 * Peer1 veux envoyer une demande d'echange a Peer2
		 * On crée l'establisher qui récupère les services des deux peers
		 * Establisher envoie avec start un contrat de Peer1 vers Peer2 et le garde dans Establisher
		 */
		/*BlockChainEstablisher establisher = new BlockChainEstablisher();
		establisher.initialize(getPeer2().getUri(),getPeer1().getUri(),getContractService1(),getContractService2());
		establisher.start("contrat n°1",itemVoulu.getTitle(),itemAEchanger.getTitle());*/
		
		/*
		 * on met des pauses sinon les messages s'affiche dans le mauvais ordres
		 * et le dernier ne s'affiche pas dans le test gradle
		 */
		
		// On affiche le status qui devrait etre en attente de réponse
		//voirStatus(establisher);
		
		// Peer2 envoi une réponse favorable
		//establisher.sendWish(Wish.ACCEPT, this.getPeer1().getUri(), this.getPeer2().getUri());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public ContractService getContractService1() {
		return contractService1;
	}

	public void setContractService1(ContractService contractService1) {
		this.contractService1 = contractService1;
	}

	public ContractService getContractService2() {
		return contractService2;
	}

	public void setContractService2(ContractService contractService2) {
		this.contractService2 = contractService2;
	}

}
