package br.com.rentalsystem.locacoes.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VeiculoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Veiculo getVeiculoSample1() {
        return new Veiculo().id(1L).placa("placa1").marca("marca1").modelo("modelo1");
    }

    public static Veiculo getVeiculoSample2() {
        return new Veiculo().id(2L).placa("placa2").marca("marca2").modelo("modelo2");
    }

    public static Veiculo getVeiculoRandomSampleGenerator() {
        return new Veiculo()
            .id(longCount.incrementAndGet())
            .placa(UUID.randomUUID().toString())
            .marca(UUID.randomUUID().toString())
            .modelo(UUID.randomUUID().toString());
    }
}
