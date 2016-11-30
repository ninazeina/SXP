package network.impl.messages;

import network.api.annotation.MessageElement;
import network.impl.MessagesImpl;

public class SignatureMessage extends MessagesImpl {
	
	@MessageElement("signature")
	private String signe;
	
	@MessageElement("type")
	private String type = "signature";
	
	public String getSigne()
	{
		return signe;
	}
	
	public void setSigne(String s)
	{
		this.signe = s;
	}
}
