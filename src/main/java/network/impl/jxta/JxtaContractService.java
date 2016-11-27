/**
 * 
 */
package network.impl.jxta;

import java.util.Scanner;

import net.jxta.pipe.PipeMsgEvent;
import network.api.ContractService;
import network.api.Messages;
import network.impl.MessagesGeneric;
import network.impl.messages.ContractMessage;
import network.impl.messages.WishMessage;
import protocol.api.Wish;

/**
 * @author soriano
 *
 */
public class JxtaContractService extends JxtaService implements ContractService 
{
	public static final String NAME = "contracts";
	
	public JxtaContractService ()
	{
		this.name = NAME;
	}
	
	/*private Messages getResponseMessage(Messages msg) {
		MessagesGeneric m = new MessagesGeneric();
		m.addField("type", "response");
		m.setWho(msg.getWho());
		return m;
	}*/
	
	@Override
	public ContractMessage sendContract(String title, String who, String itemVoulu, String itemAEchanger,  String... peerURIs) 
	{
		ContractMessage m = new ContractMessage();
		m.setTitle(title);
		m.setWho(who);
		m.setSource(this.peerUri);
		m.setItemAEchanger(itemAEchanger);
		m.setItemVoulu(itemVoulu);
		this.sendMessages(m, peerURIs);
		return m;
	}
	
	@Override
	public void sendWish(Wish w,String who, String... peerUris)
	{
		WishMessage m = new WishMessage();
		m.SetWish(w.toString());
		m.setWho(who);
		this.sendMessages(m, peerUris);
	}
	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		Messages message = toMessages(event.getMessage());
		if(message.getMessage("type").equals("wish")) {
			System.out.println("\nVoeux reçu");
			
			super.pipeMsgEvent(event);
			return;
		}
		if(message.getMessage("type").equals("contracts")) {
			System.out.println("Mr.   "+message.getMessage("WHO"));
			System.out.println("vous avez reçu un contrat :\n");
			
			super.pipeMsgEvent(event);
			System.out.println("Acceptez vous les clauses ?\n");
			return;
		}
		super.pipeMsgEvent(event);
		//this.sendMessages(getResponseMessage(message), message.getMessage("source"));
		
	}

}
