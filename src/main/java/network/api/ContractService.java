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
	 * 
	 * @param title
	 * 		titre du contrat
	 * @param who
	 * 		destinataire
	 * @param itemVoulu
	 * 		item voulu
	 * @param itemAEchanger
	 * 		item a echanger
	 * @param uris
	 * 		source
	 * @return
	 * 		contrat envoyé
	 */
	public ContractMessage sendContract(String title, String who, String itemVoulu, String itemAEchanger, String ...uris);
	
	/**
	 * 
	 * @param w
	 * 		voeux
	 * @param who
	 * 		destinataire
	 * @param uris
	 * 		source
	 */
	public void sendWish(Wish w,String who, String ...uris);
	
	public static final String NAME = "contracts";

}
