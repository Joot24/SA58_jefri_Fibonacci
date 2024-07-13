package com.project.moneycounting.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoneyCount {

    private BigDecimal targetAmount;
    private List<Boolean> denominationSelected;
    private List<BigDecimal> denominationAmountList;

    public MoneyCount() {
        denominationAmountList = Arrays.asList(
                        "0.01", "0.05", "0.10", "0.20", "0.50", "1.00",
                        "2.00", "5.00", "10.00", "50.00", "100.00", "1000.00"
                ).stream()
                .map(BigDecimal::new)
                .collect(Collectors.toList());
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public List<BigDecimal> getDenominationAmountList() {
        return denominationAmountList;
    }

    public void setDenominationAmountList(List<BigDecimal> denominationAmountList) {
        this.denominationAmountList = denominationAmountList;
    }

    public List<Boolean> getDenominationSelected() {
        return denominationSelected;
    }

    public void setDenominationSelected(List<Boolean> denominationSelected) {
        this.denominationSelected = denominationSelected;
    }
}
