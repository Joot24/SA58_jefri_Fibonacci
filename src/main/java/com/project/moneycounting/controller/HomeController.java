package com.project.moneycounting.controller;

import com.project.moneycounting.model.MoneyCount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("api")
@CrossOrigin
@RestController
public class HomeController {

    @PostMapping("count")
    public ResponseEntity<List<Integer>> countResult(@RequestBody MoneyCount moneyCount) {
        List<Integer> denominationCountList = denominationCount(moneyCount);
        return new ResponseEntity<> (denominationCountList, HttpStatus.OK);
    }

    private List<Integer> denominationCount(MoneyCount moneyCount){
        BigDecimal targetAmount = moneyCount.getTargetAmount();
        // List of denomination selected in checkbox
        List<Boolean> denominationSelected = moneyCount.getDenominationSelected();
        // List of all denominations possible
        List<BigDecimal> denominationAmountList = moneyCount.getDenominationAmountList();

        // List to store counts of each denomination
        List<Integer> denominationCountList = new ArrayList<>();
        // Start from biggest denomination
        for (int i = denominationSelected.size() - 1; i >= 0; i--) {
            Integer denominationCount = 0;
            // Only consider denominations selected in checkbox
            if (denominationSelected.get(i) == true) {
                BigDecimal denominationAmount = denominationAmountList.get(i);
                while (targetAmount.compareTo(denominationAmount) >= 0) {
                    targetAmount = targetAmount.subtract(denominationAmount);
                    denominationCount++;
                }
                denominationCountList.add(0, denominationCount);
            }
        }
        return denominationCountList;
    }
}
