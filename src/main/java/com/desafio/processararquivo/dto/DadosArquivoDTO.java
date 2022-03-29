package com.desafio.processararquivo.dto;

public class DadosArquivoDTO {
	
	private String agencia;
	private String conta;
	private double saldo;
	private String status;
	
	public DadosArquivoDTO(String agencia, String conta, double saldo, String status) {
		super();
		this.agencia = removeHifem(agencia);
		this.conta = removeHifem(conta);
		this.saldo = saldo;
		this.status = status;
	}
	
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	private String removeHifem(String texto) {
		if (texto == null) {
			return null;
		} else {
			if (!texto.contains("-")) {
				return texto;
			} else {
				String[] textoArray = texto.split("-");
				String result = "";
				for (String parteTexto : textoArray) {
					result += parteTexto;
				}
				return result;
			}
		}
	}

}

