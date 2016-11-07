/**
 * 
 */
package network.api;

import network.api.service.Service;

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
	public void sendContract(String title, String who, String itemVoulu, String itemAEchanger, String ...uris);
	
	public static final String NAME = "contracts";

}
