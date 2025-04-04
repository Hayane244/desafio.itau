package desafio.itau.spring.boot.service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Service;

import desafio.itau.spring.boot.model.Transaction;

@Service
public class TransactionService {

	//Tipagem da fila Queue de transação com a classe ConcurrentLinkedQueue:
	private final Queue<Transaction> transactions = new ConcurrentLinkedQueue<>();

	//Adicionar uma transação a fila, passando Transaction por parâmetro:
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	//Metódo para limpar as transações:
	public void clearTransactions() {
		transactions.clear();
	}
	
	//Metódo para o retorno das estatísticas:
	public DoubleSummaryStatistics getStatistics() {
	//Usar o OffsetDateTime para pegar a data atual:
	OffsetDateTime now = OffsetDateTime.now();
	//Retornar as estatísticas buscando da fila, e criando um stream usando a expressão lambda:
	return transactions.stream()
	//Filtro para obter a DataHora das transações, e verquificar se esta em um intervalo de 60 segundos
			.filter(t -> t.getDataHora().isAfter(now.minusSeconds(60)))
			.mapToDouble(Transaction::getValor)
			.summaryStatistics();
	}
}
