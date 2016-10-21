package network.impl;

import network.api.Messages;
import network.api.ServiceListener;
import network.impl.messages.ContractMessage;

public class ContractListener implements ServiceListener {

	@Override
	public void notify(Messages messages) {
		System.out.println("message recu");
		//String who = messages.getWho();
		for(String s: messages.getNames())
		{
			System.out.println(s);
			System.out.println(messages.getMessage(s));
		}
		//System.out.println(((ContractMessage) messages).getTitle());
		//String title = ((ContractMessage) messages).getTitle();
		//String source = ((ContractMessage) messages).getSource();
		//if(messages.getWho().compareTo(getPeer1().getUri()) == 0)
		//	who = "Peer1";
		//if(messages.getWho().compareTo(getPeer2().getUri()) == 0)
			//who = "Peer2";
		//System.out.println(title);
		//System.out.println(who+"a recu le message : "+title+" de : "+source);
	}

}
