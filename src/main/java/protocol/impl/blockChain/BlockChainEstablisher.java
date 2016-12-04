package protocol.impl.blockChain;

import net.jxta.pipe.PipeMsgEvent;
import network.api.ContractService;
import network.api.Messages;
import network.impl.jxta.JxtaService;
import network.impl.messages.ContractMessage;
import network.impl.messages.SignatureMessage;
import network.impl.messages.WishMessage;
import protocol.api.Status;
import protocol.api.Wish;

/**
 * 
 * @author soriano
 *
 */
public class BlockChainEstablisher extends JxtaService implements ContractService {

	private Wish w;
	private Status s;
	private ContractMessage contract = null;
	private boolean contractOwner;
	private String establisherOwner;
	public static final String NAME = "establisher";
	private String signatureAutrePartie = "false";
	
	public BlockChainEstablisher ()
	{
		this.name = NAME;
	}
	
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
	public void initialize(boolean contractOwner, String name) {
		setContractOwner(contractOwner);
		setEstablisherOwner(name);
		w = Wish.NEUTRAL;
		s = Status.NOWHERE;
		setSignatureAutrePartie("false");
		setContract(null);
	}

	public void displayContract()
	{
		System.out.println("Contract de :"+getEstablisherOwner());
		System.out.println(contract.getMessage("title"));
		System.out.println(contract.getMessage("itemVoulu"));
		System.out.println(contract.getMessage("itemAEchanger"));
		System.out.println("Voeux : "+getWish());
		System.out.println("Status : "+getStatus());
		System.out.println("signature : "+getSignatureAutrePartie()+"\n");
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

	@Override
	public ContractMessage sendContract(String title, String who, String itemVoulu, String itemAEchanger,
			String... uris) {
		ContractMessage m = new ContractMessage();
		m.setTitle(title);
		m.setWho(who);
		m.setDest(this.peerUri);
		m.setItemAEchanger(itemAEchanger);
		m.setItemVoulu(itemVoulu);
		this.sendMessages(m, uris);
		this.contract = m;
		setWish(Wish.ACCEPT);
		return m;
	}

	public void sendSignature(String signe, String who, String... uris)
	{
		SignatureMessage m = new SignatureMessage();
		m.setSigne(signe);
		m.setWho(who);
		this.sendMessages(m, uris);
	}
	@Override
	public void sendWish(Wish w, String who, String... uris) {
		if(w.equals(Wish.ACCEPT))
		{
			setStatus(Status.SIGNING);
			
		}
		else if(w.equals(Wish.REFUSE))
		{
			setStatus(Status.CANCELLED);
		}
		WishMessage m = new WishMessage();
		m.SetWish(w.toString());
		m.setWho(who);
		this.sendMessages(m, uris);
		setWish(w);
	}
	
	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		Messages message = toMessages(event.getMessage());
		if(message.getMessage("type").equals("wish")) 
		{
			System.out.println("Mr.   "+getEstablisherOwner());
			System.out.println("Voeux reçu");
			System.out.println(message.getMessage("wish")+"\n");
			if(message.getMessage("wish").equals("ACCEPT"))
			{
				if(getWish().equals(Wish.ACCEPT))
				{
					System.out.println("Contrat accepté par les deux partie !");
					/*
					 * Deploiement du contrat dans la blockchain et appel du constructeur
					 */
					setStatus(Status.SIGNING);
				}
			}
			else if(message.getMessage("wish").equals("REFUSE"))
			{
				setStatus(Status.CANCELLED);
				System.out.println("status refusé contrat abandonné");
			}
			super.pipeMsgEvent(event);
			return;
		}
		
		if(message.getMessage("type").equals("contracts")) 
		{
			this.initialize(false, "utilisateur2");
			System.out.println("Mr.   "+getEstablisherOwner());
			System.out.println("vous avez reçu un contrat :");
			
			super.pipeMsgEvent(event);
			System.out.println("Acceptez vous les clauses ?\n");
			ContractMessage contractMessage = new ContractMessage();
			contractMessage.setItemAEchanger(message.getMessage("itemAEchanger"));
			contractMessage.setTitle(message.getMessage("title"));
			contractMessage.setItemVoulu(message.getMessage("itemVoulu"));
			contractMessage.setWho(message.getMessage("WHO"));
			contractMessage.setDest(message.getMessage("source"));
			this.contract = contractMessage;
			return;
		}
		
		if(message.getMessage("type").equals("signature"))
		{
			setSignatureAutrePartie(message.getMessage("signature"));
			super.pipeMsgEvent(event);
			return;
		}
		
		super.pipeMsgEvent(event);
		//this.sendMessages(getResponseMessage(message), message.getMessage("source"));
		
	}
	public String getEstablisherOwner() {
		return establisherOwner;
	}

	public void setEstablisherOwner(String establisherOwner) {
		this.establisherOwner = establisherOwner;
	}

	public boolean isContractOwner() {
		return contractOwner;
	}

	public void setContractOwner(boolean contractOwner) {
		this.contractOwner = contractOwner;
	}

	public String getSignatureAutrePartie() {
		return signatureAutrePartie;
	}

	public void setSignatureAutrePartie(String signatureAutrePartie) {
		this.signatureAutrePartie = signatureAutrePartie;
	}

}
