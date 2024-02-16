package br.com.rentalsystem.locacoes.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClienteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cliente getClienteSample1() {
        return new Cliente()
            .id(1L)
            .nome("nome1")
            .telefone1("telefone11")
            .telefone2("telefone21")
            .email("email1")
            .cpf("cpf1")
            .rg("rg1")
            .nHabilidacao("nHabilidacao1");
    }

    public static Cliente getClienteSample2() {
        return new Cliente()
            .id(2L)
            .nome("nome2")
            .telefone1("telefone12")
            .telefone2("telefone22")
            .email("email2")
            .cpf("cpf2")
            .rg("rg2")
            .nHabilidacao("nHabilidacao2");
    }

    public static Cliente getClienteRandomSampleGenerator() {
        return new Cliente()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .telefone1(UUID.randomUUID().toString())
            .telefone2(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .cpf(UUID.randomUUID().toString())
            .rg(UUID.randomUUID().toString())
            .nHabilidacao(UUID.randomUUID().toString());
    }
}
