package com.wallet.controller.wallet.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wallet.controller.AbstractControllerTest;
import com.wallet.dto.TransactionDto;
import com.wallet.dto.WalletDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest extends AbstractControllerTest {

    @Test
    void transactions() throws Exception {
        //register user
        String token = getTokenViaRegistry("username_transaction");

        //create wallet
        BigDecimal initialBalance = BigDecimal.ZERO;
        WalletDto wallet = createWallet(
                token,
                new WalletDto(null, "Wallet name", "Wallet description", initialBalance)
        );

        assertEquals(initialBalance, wallet.balance());

        //topup wallet
        String walletId = wallet.id();
        BigDecimal amount = BigDecimal.TEN.setScale(2, RoundingMode.UP);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/wallets/{walletId}/topup/{amount}", walletId, amount)
                                .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andExpect(status().isOk());

        List<TransactionDto> transactions = mapper.readValue(
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/v1/wallets/{walletId}/transactions", walletId)
                                .header(HttpHeaders.AUTHORIZATION, token)
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {
                });

        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        TransactionDto transaction = transactions.get(0);
        assertNotNull(transaction.id());
        assertEquals("topup", transaction.description());
        assertEquals(amount, transaction.amount());
        assertNotNull(transaction.transactionDate());
    }
}
