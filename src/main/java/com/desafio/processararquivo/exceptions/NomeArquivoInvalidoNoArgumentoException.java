package com.desafio.processararquivo.exceptions;

public class NomeArquivoInvalidoNoArgumentoException extends RuntimeException {
	
	private static final long serialVersionUID = 5661066522865746070L;

	public NomeArquivoInvalidoNoArgumentoException() {
		super("Nome Arquivo Inválido");		
	}

}
