package protocol.impl.blockChain;

import network.api.ContractService;
import network.api.Peer;
import network.impl.messages.ContractMessage;
import protocol.api.Contract;
import protocol.api.Establisher;
import protocol.api.EstablisherListener;
import protocol.api.Status;
import protocol.api.Wish;
import protocol.base.BaseContract;

/**
 * 
 * @author soriano
 *
 */
public class BlockChainEstablisher {

	private Wish w;
	private Status s;
	private ContractMessage contract;
	private ContractService serviceDest;
	private ContractService serviceSrc;
	private String src;
	private String dest;
	
	/**
	 * initialise le contrat 
	 * Ajoute Les peers de la source et de la destinnation
	 * Ajoute les service gerant le contrat des peer de source et destination
	 * Permet d'envoyer et de voir(depuis la source) les réponses du destinataire
	 * Met le voeux a neutre et le status a nul part
	 * @param source
	 *		PeerId du peer source du contrat
	 * @param dest
	 * 		PeerId du peer de destination
	 * @param srcService 
	 * 		service du peer source
	 * @param destService
	 * 		serice du peer destination
	 */
	public void initialize(String source, String dest,ContractService srcService, ContractService destService) {
		this.setServiceSrc(srcService);
		this.setServiceDest(destService);
		setSrc(source);
		setDest(dest);
		w = Wish.NEUTRAL;
		s = Status.NOWHERE;
	}

	
	 /**
	  *  Ajoute le contrat a l'establisher  et met le status a FINALIZED
	  * @param nomContrat
	  * @param itemVoulu
	  * @param itemAEchanger
	  */
	public void start(String nomContrat,String itemVoulu, String itemAEchanger) {

		this.contract = getServiceSrc().sendContract(nomContrat,getSrc(),itemVoulu,itemAEchanger,getDest());
		setStatus(Status.FINALIZED);
	}
	
	/**
	 * Envois le voeux de la source a la destinnation et change le status en conséquence si la source est différent de la destination de l'establisher c'est qu'il y a une erreur
	 * @param w
	 * @param src
	 * @param dest
	 */
	public void sendWish(Wish w, String src, String dest)
	{
		if(src.equals(getDest()))
		{
			if(getStatus().equals(Status.CANCELLED))
				System.out.println("imposible d'envoyer le voeux le contrat et déja finis");
			else
			{
				if(getStatus().equals(Status.NOWHERE))
					System.out.println("impossible d'envoyer le voeux le contrat n'est pas encore commencé");
				else
				{
					if(w.toString().equals("ACCEPT"))
					{
						/*
						 * Envoyer le contrat sur la blockchain !
						 * Pour l'instant le contrat a comme arguments :
						 * WHO : la personne qui envoie le contrat
						 * type : son type donc ici "contracts"
						 * title : titre du contrat
						 * sourceUri : destinataires du contract
						 * itemVoulu : itemVoulu
						 * itemAEchanger : itemAEchanger
						 * 
						 * voir la classe ContractMessage
						 * 
						 * Pour récupérer les String de ces arguments faire : getContract().getMessage("arguments");
						 * avec arguments = WHO, type, itemVoulu, etc ..
						 * 
						 * bien entendu on peut changer les clauses du contrat en changeant/ rajoutants des arguments
						 */
						setStatus(Status.SIGNING);
					}
					else if(w.toString().equals("REFUSE"))
					{
						setStatus(Status.CANCELLED);
					}
					setWish(w);
					getServiceDest().sendWish(getWish(),src, dest);
				}
			}
		}
		else
			System.out.println("erreur la personnes essayant d'accepter le contrat n'est pas la bonne");
	}
	
	/**
	 * 
	 * @param c
	 */
	public void setContract(ContractMessage c)
	{
		this.contract = c;
	}
	
	/**
	 * 
	 * @return
	 */
	public ContractMessage getContract() {
		return contract;
	}
	
	/**
	 * 
	 * @param w
	 */
	public void setWish(Wish w) {
		this.w = w;
	}
	
	/**
	 * 
	 * @return
	 */
	public Wish getWish() {
		return w;
	}
	
	/**
	 * 
	 * @param status
	 */
	public void setStatus(Status status)
	{
		this.s = status;
	}
	
	/**
	 * 
	 * @return
	 */
	public Status getStatus() {
		return s;
	}

	/**
	 * 
	 * @return
	 */
	public ContractService getServiceDest() {
		return serviceDest;
	}

	/**
	 * 
	 * @param serviceDest
	 */
	public void setServiceDest(ContractService serviceDest) {
		this.serviceDest = serviceDest;
	}

	/**
	 * 
	 * @return
	 */
	public ContractService getServiceSrc() {
		return serviceSrc;
	}

	/**
	 * 
	 * @param serviceSrc
	 */
	public void setServiceSrc(ContractService serviceSrc) {
		this.serviceSrc = serviceSrc;
	}

	/**
	 * 
	 * @return
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * 
	 * @param source
	 */
	public void setSrc(String source) {
		this.src = source;
	}

	/**
	 * 
	 * @return
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * 
	 * @param dest2
	 */
	public void setDest(String dest2) {
		this.dest = dest2;
	}

}
