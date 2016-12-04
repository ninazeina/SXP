package org.ethereum;

import java.io.IOException;

import org.ethereum.core.TransactionReceipt;
import org.ethereum.crypto.ECKey;
import org.ethereum.solidity.compiler.SolidityCompiler;
import org.ethereum.solidity.compiler.CompilationResult.ContractMetadata;

public interface EthereumSXP {

	public SolidityCompiler.Result compileContrat(String contrat) throws IOException ;
	
	public ContractMetadata createData(SolidityCompiler.Result contratCompiler) throws IOException ;
	
	public TransactionReceipt sendTxAndWait(ECKey senderAddress, byte[] receiveAddress, byte[] data) throws Exception ;
	
	public TransactionReceipt waitForTx(byte[] txHash) throws Exception ;
	
}

