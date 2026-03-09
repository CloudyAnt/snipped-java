package cn.itscloudy.snippedjava.feature.j21;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VirtualThreadFeatTest {

    @Test
    void shouldGetSameCarrierThreadNames() throws InterruptedException {
        VirtualThreadFeat virtualThreadFeat = new VirtualThreadFeat();
        List<String> carrierThreadNames = virtualThreadFeat.runAndGetCarrierThreadNames();
        assertEquals(2, carrierThreadNames.size());
        assertEquals(carrierThreadNames.get(0), carrierThreadNames.get(1));
    }
}