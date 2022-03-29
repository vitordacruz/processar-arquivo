/*
Cenário de Negócio:
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda da Instituição Financeira recebe e organiza as informações de 
contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje a Instituição Financeira 
já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, 
antes as 10:00 da manhã na abertura das agências.

Requisito:
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

Funcionalidade:
0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma 
nova coluna.


Formato CSV:
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I
3202;54001-2;0,00;P
3202;00321-2;34500,00;B
...

*/
package com.desafio.processararquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.desafio.processararquivo.dto.DadosArquivoDTO;
import com.desafio.processararquivo.exceptions.QuantidadeColunasInvalidaException;

@SpringBootApplication
public class SincronizacaoReceita {
	
	private static int quantidadeColunas = 4;

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoReceita.class, args);
		
		
		
	}
	
	public static void validaQuantidadeColunas(String linha) {
		
		String[] colunas = linha.split(";");
		
		if (colunas.length != quantidadeColunas) {
			throw new QuantidadeColunasInvalidaException();
		}
		
	}
	
	public static DadosArquivoDTO criaDTO(String linha) {
		
		String[] colunas = linha.trim().split(";");
		
		String valor = colunas[2].replace(".", "").replace(",", ".");
		Double saldo = Double.parseDouble(valor);
		
		return new DadosArquivoDTO(colunas[0], colunas[1], saldo, colunas[3]);
		
	}
	
	public static synchronized void escreveLinhaNoArquivo(String linha, Path path) throws IOException {
		
		Files.writeString(
				path,
				linha + System.lineSeparator(),
				StandardOpenOption.CREATE, StandardOpenOption.APPEND
		    );
		
	}

}
