package network.impl;

import org.junit.Test;

import network.api.Peer;
import network.factories.PeerFactory;
import network.impl.jxta.JxtaContractService;
import java.io.*;

public class ContractTest {

	private Peer peer1;
	private Peer peer2;
	private JxtaContractService contractService1;
	private JxtaContractService contractService2;
	
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
	
	
	public void init()
	{
		setPeer1(PeerFactory.createDefaultAndStartPeerForTest());
		setPeer2(PeerFactory.createDefaultAndStartPeerForTest());
		setContractService1(new JxtaContractService());
		setContractService2(new JxtaContractService());
		contractService1.initAndStart(getPeer1());
		contractService2.initAndStart(getPeer2());
		System.out.println("\n");
		
		/*ContractListener contractListener = new ContractListener();
		contractService2.addListener(contractListener, getPeer1().getUri());
		contractService1.addListener(contractListener, getPeer2().getUri());*/
		contractService2.addListener(new ContractListener(), getPeer1().getUri());
		contractService1.addListener(new ContractListener(), getPeer2().getUri());
		
		contractService1.sendContract("titi", getPeer2().getUri(), this.getPeer1().getUri());
		System.out.println("\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contractService2.sendContract("toto", getPeer1().getUri(), this.getPeer2().getUri());
		System.out.println("\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contractService1.sendContract("tata", getPeer2().getUri(), this.getPeer1().getUri());
		System.out.println("\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contractService2.sendContract("tutu", getPeer1().getUri(), this.getPeer2().getUri());
		System.out.println("\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getPeer1().getServices().toString());
		System.out.println(getPeer2().getServices().toString());
		System.out.println("Peer1 : "+getPeer1().getUri());
		System.out.println("Peer2 : "+getPeer2().getUri());
	}
	@Test
	public void test() 
	{
		init();
	}
	public JxtaContractService getContractService1() {
		return contractService1;
	}
	public void setContractService1(JxtaContractService contractService1) {
		this.contractService1 = contractService1;
	}
	public JxtaContractService getContractService2() {
		return contractService2;
	}
	public void setContractService2(JxtaContractService contractService2) {
		this.contractService2 = contractService2;
	}
	
}
