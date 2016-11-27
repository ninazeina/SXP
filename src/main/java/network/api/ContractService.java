/**
 * 
 */
package network.api;

import network.api.service.Service;
import network.impl.messages.ContractMessage;
import protocol.api.Wish;

/**
 * @author soriano
 *
 */
public interface ContractService extends Service 
{
	/**
	 * Send contract
	 * @param title contract title
	 * @param who sender
	 * @param uris target peers
	 */
	public ContractMessage sendContract(String title, String who, String itemVoulu, String itemAEchanger, String ...uris);
	
	public void sendWish(Wish w,String who, String ...uris);
	
	public static final String NAME = "contracts";

}
