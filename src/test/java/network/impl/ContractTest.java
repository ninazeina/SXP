package network.impl;

import org.junit.Test;

import network.api.ContractService;
import network.api.Peer;
import network.api.service.Service;
import network.factories.PeerFactory;
import network.impl.jxta.JxtaContractService;
import java.io.*;

public class ContractTest {

	private Peer peer1;
	private Peer peer2;
	private ContractService contractService1;
	private ContractService contractService2;
	
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
		//crée les pairs
		setPeer1(PeerFactory.createDefaultAndStartPeerForTest());
		setPeer2(PeerFactory.createDefaultAndStartPeerForTest());
		System.out.println("\n");
		
		for (Service s : getPeer1().getServices())
		{
			System.out.println(s.getName());
		}
		for (Service s : getPeer2().getServices())
		{
			System.out.println(s.getName());
		}
		setContractService1((ContractService)getPeer1().getService("contracts"));
		setContractService2((ContractService)getPeer2().getService("contracts"));
		getContractService1().addListener(new ContractListener(), getPeer2().getUri());
		getContractService2().addListener(new ContractListener(), getPeer1().getUri());
		
		
		getContractService1().sendContract("titi", getPeer2().getUri(), this.getPeer1().getUri());
		System.out.println("\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContractService2().sendContract("toto", getPeer1().getUri(), this.getPeer2().getUri());
		System.out.println("\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContractService1().sendContract("tata", getPeer2().getUri(), this.getPeer1().getUri());
		System.out.println("\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContractService2().sendContract("tutu", getPeer1().getUri(), this.getPeer2().getUri());
		System.out.println("\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Peer1 : "+getPeer1().getUri());
		System.out.println("Peer2 : "+getPeer2().getUri());
	}
	@Test
	public void test() 
	{
		init();
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
