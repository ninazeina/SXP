package protocol.impl.BlockChain;

import protocol.api.EstablisherListener;
import protocol.api.Status;

public class BCEstablisherListener implements EstablisherListener {

	@Override
	public void establisherEvent(Status s) {
		System.out.println(s);
		System.out.println("PATATIPATITATA");
	}
	

}
