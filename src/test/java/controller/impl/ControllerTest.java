package controller.impl;

import java.util.Properties;

import org.junit.Test;
import static org.junit.Assert.*; //JUnit

import model.entity.Item;
import model.syncManager.UserSyncManagerImpl;
import network.api.ContractService;
import network.api.Peer;
import network.factories.PeerFactory;
import network.impl.ContractListener;
import network.impl.messages.ContractMessage;
import protocol.api.Contract;
import protocol.api.Establisher;
import protocol.api.Wish;
import protocol.base.BaseContract;
import protocol.impl.BlockChain.BCEstablisherListener;
import protocol.impl.BlockChain.BlockChainEstablisher;
import protocol.impl.contract.ElGamalContract;
import rest.api.Authentifier;
import rest.factories.AuthentifierFactory;
import rest.factories.RestServerFactory;

public class ControllerTest 
{
	private Peer peer1;
	private Peer peer2;
	
	private ContractService contractService1;
	private ContractService contractService2;
	
	@Test
	public void test() 
	{
		//crée les pairs
		setPeer1(PeerFactory.createDefaultAndStartPeerForTest());
		setPeer2(PeerFactory.createDefaultAndStartPeerForTest());
		System.out.println("\n");
		
		//ajoute les listener sur les services qui gère les contrats
		setContractService1((ContractService)getPeer1().getService("contracts"));
		setContractService2((ContractService)getPeer2().getService("contracts"));
		getContractService1().addListener(new ContractListener(), getPeer2().getUri());
		getContractService2().addListener(new ContractListener(), getPeer1().getUri());
		
		//créer les items
		Item itemVoulu = new Item();
		Item itemAEchanger = new Item();
		
		itemVoulu.setTitle("Patates");
		itemAEchanger.setTitle("Carottes");
		
		/*
		 * Peer1 veux envoyer une demande d'echange a Peer2
		 * On crée l'establisher qui récupère les services des deux peers
		 * Establisher envoie avec start un contrat de Peer1 vers Peer2 et le garde dans Establisher
		 */
		BlockChainEstablisher establisher = new BlockChainEstablisher();
		establisher.initialize(getContractService1(),getContractService2());
		establisher.start("contrat n°1", getPeer2().getUri(),itemVoulu.getTitle(),itemAEchanger.getTitle(), this.getPeer1().getUri());
		//establisher.addListener(new BCEstablisherListener());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		establisher.sendWish(Wish.ACCEPT, this.getPeer1().getUri(), this.getPeer2().getUri());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
