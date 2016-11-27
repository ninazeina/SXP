package network.impl.messages;

import network.api.annotation.MessageElement;
import network.impl.MessagesImpl;

public class WishMessage extends MessagesImpl {
	
	@MessageElement("wish")
	private String wish;
	
	@MessageElement("type")
	private String type = "wish";
	
	
	public String getWish()
	{
		return wish;
	}
	
	public void SetWish(String w)
	{
		this.wish = w;
	}

}
