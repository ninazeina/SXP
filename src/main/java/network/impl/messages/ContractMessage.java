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
	
	@MessageElement("itemVoulu")
	private String itemVoulu;
	
	@MessageElement("itemAEchanger")
	private String itemAEchanger;
	
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

	public String getItemVoulu() {
		return itemVoulu;
	}

	public void setItemVoulu(String itemVoulu) {
		this.itemVoulu = itemVoulu;
	}

	public String getItemAEchanger() {
		return itemAEchanger;
	}

	public void setItemAEchanger(String itemAEchanger) {
		this.itemAEchanger = itemAEchanger;
	}
}
