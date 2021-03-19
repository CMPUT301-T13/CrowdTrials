package com.example.crowdtrials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BinomialExpTest {

    private BinomialExp mockBinomialExp = new BinomialExp();

    @Test
    void testGenResult() {
        if (Math.random() > 1) {
            assertEquals(true, mockBinomialExp.genResult());
        } else {
            assertEquals(false, mockBinomialExp.genResult());
        }


    }

}
