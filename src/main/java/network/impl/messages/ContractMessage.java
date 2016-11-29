package network.impl.messages;

import network.api.annotation.MessageElement;
import network.impl.MessagesImpl;

/**
 * 
 * @author soriano
 *
 */
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
	
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @param source
	 */
	public void setSource(String source) {
		this.sourceUri = source;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSource() {
		return sourceUri;
	}

	/**
	 * 
	 * @return
	 */
	public String getItemVoulu() {
		return itemVoulu;
	}

	/**
	 * 
	 * @param itemVoulu
	 */
	public void setItemVoulu(String itemVoulu) {
		this.itemVoulu = itemVoulu;
	}

	/**
	 * 
	 * @return
	 */
	public String getItemAEchanger() {
		return itemAEchanger;
	}

	/**
	 * 
	 * @param itemAEchanger
	 */
	public void setItemAEchanger(String itemAEchanger) {
		this.itemAEchanger = itemAEchanger;
	}
}
