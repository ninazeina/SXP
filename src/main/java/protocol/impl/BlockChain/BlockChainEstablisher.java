package protocol.impl.BlockChain;

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
	
	public void initialize(ContractService src, ContractService dest) {
		this.setServiceSrc(src);
		this.setServiceDest(dest);
		w = Wish.NEUTRAL;
		s = Status.NOWHERE;
	}

	
	public void start(String nomContrat,String source, String itemVoulu, String itemAEchanger,String dest) {
		setSrc(source);
		setDest(dest);
		
		this.contract = getServiceSrc().sendContract(nomContrat,source,itemVoulu,itemAEchanger,dest);
	}
	
	public void sendWish(Wish w, String src, String dest)
	{
		if(src.equals(getDest()))
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
			}
			setWish(w);
			getServiceDest().sendWish(getWish(),src, dest);
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
