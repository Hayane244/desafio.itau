package desafio.itau.spring.boot.controller;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import desafio.itau.spring.boot.dto.TransactionRequest;
import desafio.itau.spring.boot.model.Transaction;
import desafio.itau.spring.boot.service.TransactionService;
import jakarta.validation.Valid;



@RestController
//Endpoint da transação:
@RequestMapping("/transacao")
public class TransactionController {

	private final TransactionService transactionService;
	
	//Construtor que vai passar o Service:
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	/**
	 * Retorna a entidade Response sem corpo. 
	 * Passando com RequesBody o DTO.
	 * Usando o Valid para fazer as validações desse DTO:
	 */
	public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequest request) {
		//Se a transação for no futuro retorne 422 Unprocessable Entity:
		if (request.getDataHora().isAfter(OffsetDateTime.now()) || request.getValor() <= 0) {
			return ResponseEntity.unprocessableEntity().build();
		}
		//Se passou pela validação, é adicionada uma transação com o seu valor e DataHora:
		transactionService.addTransaction(new Transaction(request.getValor(), request.getDataHora()));
		//Retorna 201
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	//Endpoint para excluir todos os dado de transações
	@DeleteMapping
	public ResponseEntity<Void> clearTransaction() {
		transactionService.clearTransactions();
		return ResponseEntity.ok().build();
	}
}
