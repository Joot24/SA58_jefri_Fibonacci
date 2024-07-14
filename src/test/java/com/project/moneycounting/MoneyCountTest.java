package com.project.moneycounting;

import com.project.moneycounting.model.MoneyCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyCountTest {
    private MoneyCount moneyCount;

    @BeforeEach
    public void setUp(){
        BigDecimal targetAmount = new BigDecimal("100.00");
        List<Boolean> denominationSelected = Arrays.asList(true, false, true, false, true, false, true, false, true, false, true, false);
        moneyCount = new MoneyCount(targetAmount, denominationSelected);
    }

    @Test
    public void testGetTargetAmount(){
        BigDecimal expectedAmount = new BigDecimal("100.00");
        assertEquals(expectedAmount, moneyCount.getTargetAmount());
    }

    @Test
    public void testGetDenominationSelected(){
        List<Boolean> denominationSelected = Arrays.asList(true, false, true, false, true, false, true, false, true, false, true, false);
        assertEquals(denominationSelected, moneyCount.getDenominationSelected());
    }

    @Test
    public void testDenominationAmounts(){
        List<BigDecimal> expectedDenominationAmounts = Arrays.asList(
                        "0.01", "0.05", "0.10", "0.20", "0.50", "1.00",
                        "2.00", "5.00", "10.00", "50.00", "100.00", "1000.00"
                ).stream()
                .map(BigDecimal::new)
                .collect(Collectors.toList());
        assertEquals(expectedDenominationAmounts, MoneyCount.DENOMINATION_AMOUNTS);
    }
}
