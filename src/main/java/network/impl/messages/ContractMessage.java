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
	@MessageElement("dest")
	private String destUri;
	
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
	 * @param dest
	 */
	public void setDest(String dest) {
		this.destUri = dest;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDest() {
		return destUri;
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
