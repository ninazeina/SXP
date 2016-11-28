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

public class BlockChainEstablisher {

	private Wish w;
	private Status s;
	private ContractMessage contract;
	private ContractService serviceDest;
	private ContractService serviceSrc;
	//private EstablisherListener l;
	private String src;
	private String dest;
	
	/*
	 * initialise le contrat 
	 * Ajoute Les peers de la source et de la destinnation
	 * Ajoute les service gerant le contrat des peer de source et destination
	 * Permet d'envoyer et de voir(depuis la source) les réponses du destinataire
	 * Met le voeux a neutre et le status a nul part
	 */
	public void initialize(String source, String dest,ContractService srcService, ContractService destService) {
		this.setServiceSrc(srcService);
		this.setServiceDest(destService);
		setSrc(source);
		setDest(dest);
		w = Wish.NEUTRAL;
		s = Status.NOWHERE;
	}

	
	 // Ajoute le contrat a l'establisher  et met le status a FINALIZED
	public void start(String nomContrat,String itemVoulu, String itemAEchanger) {

		this.contract = getServiceSrc().sendContract(nomContrat,getSrc(),itemVoulu,itemAEchanger,getDest());
		setStatus(Status.FINALIZED);
	}
	
	/*
	 * Envois le voeux de la source a la destinnation et change le status en conséquence
	 * si la source est différent de la destination de l'establisher c'est qu'il y a une erreur
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
	
	public void setContract(ContractMessage c)
	{
		this.contract = c;
	}

	public ContractMessage getContract() {
		return contract;
	}

	public void setWish(Wish w) {
		this.w = w;
	}

	public Wish getWish() {
		return w;
	}

	public void setStatus(Status status)
	{
		this.s = status;
	}
	
	public Status getStatus() {
		return s;
	}

	/*public void addListener(EstablisherListener l) {
		this.l = l;

	}

	public void notifyListeners(Status s) 
	{
		this.l.establisherEvent(s);
	}*/

	public ContractService getServiceDest() {
		return serviceDest;
	}


	public void setServiceDest(ContractService serviceDest) {
		this.serviceDest = serviceDest;
	}


	public ContractService getServiceSrc() {
		return serviceSrc;
	}


	public void setServiceSrc(ContractService serviceSrc) {
		this.serviceSrc = serviceSrc;
	}


	public String getSrc() {
		return src;
	}


	public void setSrc(String source) {
		this.src = source;
	}


	public String getDest() {
		return dest;
	}


	public void setDest(String dest2) {
		this.dest = dest2;
	}

}
