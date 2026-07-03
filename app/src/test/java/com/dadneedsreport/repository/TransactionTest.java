package com.dadneedsreport.repository;

import com.dadneedsreport.models.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dadneedsreport.config.SecurityConfig;
import com.dadneedsreport.dto.TransactionRequest;
import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.repositories.TransactionRepository;
import com.dadneedsreport.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertFalse; // Boa prática: Asserts

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addValuesAndVerify() {
        // Improvement: Tratamento caso o usuário 2 não exista no banco real
        User user = userRepository.findById(2L)
                .orElseThrow(() -> new AssertionError("Usuário de ID 2L não encontrado no banco de dados para o teste."));

        for (int i = 0; i < 20; ++i) {
            // CORREÇÃO 1: Instanciar uma NOVA transação a cada repetição do loop
            Transaction t = new Transaction(); 
            
            TransactionRequest d = new TransactionRequest(
                    TransactionType.INCOME, 
                    "dud " + i, // Diferenciando a descrição para o print
                    new BigDecimal("120000"), // Boa prática: Passar String no BigDecimal evita problemas de precisão
                    "Pedro",
                    LocalDate.now()
            );
            
            t.prepareToPersist(d, user);
            transactionRepository.save(t);
        }

        // CORREÇÃO 2: Força o JPA a sincronizar as inserções na transação atual do banco 
        // antes de rodar a query de busca no método seguinte.
        transactionRepository.flush(); 

        // Executa a validação
        testFindAllByUserType(user.getId());
    }

    // Improvement: Passando o ID dinamicamente como parâmetro
    private void testFindAllByUserType(Long userId) {
        List<Transaction> transactions = transactionRepository
                .findAllByTypeAndUserId(TransactionType.EXPENSE, userId, SecurityConfig.getFindAllLimit())
                .getContent();

        // Improvement: Teste automatizado de verdade precisa validar uma condição (Assert)
        assertFalse(transactions.isEmpty(), "A lista de transações não deveria estar vazia!");

        // Print elegante usando Method Reference ou Lambda simplificado
        transactions.forEach(value -> System.out.printf("Desc: %s | Title: %s | Type: %s%n", 
                value.getDescription(), value.getTitle(), value.getType()));
    } 
}