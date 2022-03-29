package com.desafio.processararquivo.exceptions;


public class QuantidadeColunasInvalidaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1032771793026831811L;
	
	
	public QuantidadeColunasInvalidaException() {
		super("Quantidade de colunas inválida");		
	}
	
}
