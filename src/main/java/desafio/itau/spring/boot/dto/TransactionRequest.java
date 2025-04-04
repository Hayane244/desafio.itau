package desafio.itau.spring.boot.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransactionRequest {

	//Não aceita valores nulos.
	@NotNull
	//Não aceita valores zero.
	@Min(0)
	private double valor;
	
	@NotNull
	private OffsetDateTime dataHora;
	
	public double getValor() {
		return valor;
	}
	public OffsetDateTime getDataHora() {
		return dataHora;
	}	
}
