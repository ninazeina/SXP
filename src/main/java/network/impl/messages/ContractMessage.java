package network.impl.messages;

import network.api.annotation.MessageElement;
import network.impl.MessagesImpl;

public class ContractMessage extends MessagesImpl 
{
	@MessageElement("source")
	private String sourceUri;
	
	@MessageElement("title")
	private String title;
	
	@MessageElement("type")
	private String type = "contracts";
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setSource(String source) {
		this.sourceUri = source;
	}
	
	public String getSource() {
		return sourceUri;
	}
}
