package network.impl;

import network.api.Messages;
import network.api.ServiceListener;
import network.impl.messages.ContractMessage;

public class ContractListener implements ServiceListener {

	@Override
	public void notify(Messages messages) {
		System.out.println("message recu");
		for(String s: messages.getNames())
		{
			System.out.println(s);
			System.out.println(messages.getMessage(s));
		}
	}

}
