package com.project.moneycounting.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoneyCount {

    private BigDecimal targetAmount;
    private List<Boolean> denominationSelected;
    public static final List<BigDecimal> DENOMINATION_AMOUNTS = Arrays.asList(
            "0.01", "0.05", "0.10", "0.20", "0.50", "1.00",
            "2.00", "5.00", "10.00", "50.00", "100.00", "1000.00"
            ).stream()
                .map(BigDecimal::new)
                .collect(Collectors.toList());

    public MoneyCount(){}

    public MoneyCount(BigDecimal targetAmount, List<Boolean> denominationSelected) {
        this.targetAmount = targetAmount;
        this.denominationSelected = denominationSelected;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public List<Boolean> getDenominationSelected() {
        return denominationSelected;
    }
}
