/**
 * 
 */
package network.impl.jxta;

import net.jxta.pipe.PipeMsgEvent;
import network.api.ContractService;
import network.api.Messages;
import network.impl.MessagesGeneric;
import network.impl.messages.ContractMessage;

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
	public void sendContract(String title, String who, String... peerURIs) 
	{
		ContractMessage m = new ContractMessage();
		m.setTitle(title);
		m.setWho(who);
		m.setSource(this.peerUri);
		this.sendMessages(m, peerURIs);
	}
	
	//@Override
	/*public void pipeMsgEvent(PipeMsgEvent event) {
		//Messages message = toMessages(event.getMessage());
		//if(message.getMessage("type").equals("response")) {
			super.pipeMsgEvent(event);
			//return;
		//}
		//this.sendMessages(getResponseMessage(message), message.getMessage("source"));
		
	}*/

}
