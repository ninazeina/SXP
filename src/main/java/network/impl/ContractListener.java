package network.impl;

import network.api.Messages;
import network.api.ServiceListener;
import network.impl.messages.ContractMessage;

public class ContractListener implements ServiceListener {

	@Override
	public void notify(Messages messages) {
		if(messages.getMessage("type").equals("contracts"))
		{
				System.out.println("Item a echanger : "+messages.getMessage("itemAEchanger"));
				System.out.println("Item Voulu : "+messages.getMessage("itemVoulu")+"\n");
		}
		
		else if(messages.getMessage("type").equals("wish"))
			System.out.println(messages.getMessage("wish"));
		
	}

}
