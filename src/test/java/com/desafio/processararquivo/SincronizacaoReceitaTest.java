package com.desafio.processararquivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.desafio.processararquivo.dto.DadosArquivoDTO;
import com.desafio.processararquivo.exceptions.QuantidadeColunasInvalidaException;




public class SincronizacaoReceitaTest {
	
	private static final String QUANTIDADE_DE_COLUNAS_INVÁLIDA = "Quantidade de colunas inválida";

	@Test
	public void verificaSeValidaQuantidadeColunasSucesso() {
		
		// Give
		
		String linha1 = "agencia;conta;saldo;status\n";
				
		String linha2 = "0101;12225-6;100,00;A\n";
		
		// Then
		
		SincronizacaoReceita.validaQuantidadeColunas(linha1);
		
		SincronizacaoReceita.validaQuantidadeColunas(linha2);
		
	}
	
	@Test
	public void verificaSeValidaQuantidadeColunasLancaExcecao() {
		
		// Give
		
		String linha1 = "agencia;conta;saldo\n";
		
		String linha2 = "0101;12225-6;100,00;A;true\n";
		
		String linha3 = "";
		
		// Then
		
		QuantidadeColunasInvalidaException thrown1 = assertThrows(
				QuantidadeColunasInvalidaException.class,
		           () -> SincronizacaoReceita.validaQuantidadeColunas(linha1)
		    );
		
		QuantidadeColunasInvalidaException thrown2 = assertThrows(
				QuantidadeColunasInvalidaException.class,
		           () -> SincronizacaoReceita.validaQuantidadeColunas(linha2)
		    );
		
		QuantidadeColunasInvalidaException thrown3 = assertThrows(
				QuantidadeColunasInvalidaException.class,
		           () -> SincronizacaoReceita.validaQuantidadeColunas(linha3)
		    );
		
		assertEquals(thrown1.getMessage(), QUANTIDADE_DE_COLUNAS_INVÁLIDA);
		
		assertEquals(thrown2.getMessage(), QUANTIDADE_DE_COLUNAS_INVÁLIDA);
		
		assertEquals(thrown3.getMessage(), QUANTIDADE_DE_COLUNAS_INVÁLIDA);

		
	}
	
	@Test
	public void verificaSeCriaDTOCorretamente() {
		
		// Give
		
		String agencia = "0101";
		
		String conta = "12225-6";
		
		String contaDTO = "122256";
		
		String saldo = "100,00";
		
		Double saldoDouble = 100D;
		
		String status = "A";
		
		String linha1 = agencia +";" + conta + ";" + saldo + ";" + status+ "\n";
		
		DadosArquivoDTO dto = SincronizacaoReceita.criaDTO(linha1);
		
		// Then
		
		assertEquals(dto.getAgencia(), agencia);
		assertEquals(dto.getConta(), contaDTO);
		assertEquals(dto.getSaldo(), saldoDouble);
		assertEquals(dto.getStatus(), status);
		
		
	}

}
