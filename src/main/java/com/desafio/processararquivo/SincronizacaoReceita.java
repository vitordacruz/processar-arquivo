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
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.desafio.processararquivo.dto.DadosArquivoDTO;
import com.desafio.processararquivo.exceptions.QuantidadeColunasInvalidaException;

@SpringBootApplication
public class SincronizacaoReceita {
	
	private static int quantidadeColunas = 4;

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoReceita.class, args);
		
		if (args == null || args.length != 1 
				|| args[0] == null || args[0].isBlank()) {
			System.out.println("Você deve especificar o arquivo");
			return;
		}
		
		String enderecoArquivo = args[0];
			
		processaArquivo(enderecoArquivo);
		
	}
	
	private static void processaArquivo(String enderecoArquivo) {
		
		System.out.println("Processando arquivo");
		
		Path path = Paths.get(enderecoArquivo);
		final Path pathArquivoNovo= Paths.get(enderecoArquivo + ".processado.csv");
		
		if (!path.toFile().exists()) {
			System.out.println("Arquivo não existe");
			System.out.println("Terminou");
			return;
		}
		
		if (pathArquivoNovo.toFile().exists()) {
			pathArquivoNovo.toFile().delete();
		}
		
		try (Stream<String> stream = Files.lines(path)) {
				
			ReceitaService receitaService = new ReceitaService();
			
			Integer[] numeroLinha = { 0 };

	        stream.forEach((String linha) -> {
	        	
	        	numeroLinha[0] = numeroLinha[0] + 1;
	        		        	
	        	System.out.println("Processando a linha " + numeroLinha[0]);
	        	
	        	// primeira linha deve ser os nomes das colunas
	        	if (numeroLinha[0] > 1) {
	        	
	        		DadosArquivoDTO dto = null;
	        		
	        		try {
			        	//processa a linha do arquivo
			        	processaLinha(pathArquivoNovo, receitaService, linha);
			        	
	        		} catch (Exception e) {
	        			
	        			System.out.println(e.getMessage());
	        			
	        			try {
							escreveLinhaNoArquivo(linha + ";E", pathArquivoNovo);
						} catch (IOException e1) {
							System.out.println("Ocorreu um erro");
							System.out.println(e1.getMessage());
						}
					}
	        		
	        		
	        	} else {
	        		try {
			        	//processa a linha do arquivo
			        	validaQuantidadeColunas(linha);
			        	escreveLinhaNoArquivo(linha + ";result", pathArquivoNovo);
			        	
	        		} catch (Exception e) {
	        			
	        			System.out.println(e.getMessage());
	        			
					}
	        		
	        	}
	        	
	        });
	        System.out.println("Terminou");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void processaLinha(Path path, ReceitaService receitaService, String linha)
			throws InterruptedException, IOException {
		boolean foiAtualizado;
		DadosArquivoDTO dto;
		validaQuantidadeColunas(linha);
		dto = criaDTO(linha);
		
		foiAtualizado = receitaService.atualizarConta(dto.getAgencia(), dto.getConta(), dto.getSaldo(), dto.getStatus());
		
		if (foiAtualizado) {
			escreveLinhaNoArquivo(linha + ";A", path);
		} else {
			escreveLinhaNoArquivo(linha + ";N", path);
		}
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
