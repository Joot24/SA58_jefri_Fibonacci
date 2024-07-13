package com.project.moneycounting.validator;

import com.project.moneycounting.model.MoneyCount;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MoneyCountValidator implements Validator {
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.00");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("10000.00");

    @Override
    public boolean supports(Class<?> clazz) {
        return MoneyCount.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MoneyCount moneyCount = (MoneyCount) target;

        // Validate targetAmount
        BigDecimal targetAmount = null;
        try{
            targetAmount = moneyCount.getTargetAmount();    
        } catch (NumberFormatException e) {
            errors.rejectValue("targetAmount", "targetAmount.notANumber",
                    "Target amount must be a valid number");
        }

        if (targetAmount == null) {
            errors.rejectValue("targetAmount", "targetAmount.null", "Please input a target amount");
        } else {
                if (targetAmount.scale() > 2) {
                    errors.rejectValue("targetAmount", "targetAmount.tooManyDecimals",
                            "Target amount cannot have more than 2 decimal places");
                }
                if (targetAmount.compareTo(MIN_AMOUNT) < 0) {
                    errors.rejectValue("targetAmount", "targetAmount.tooLow",
                            "Target amount must be at least $0.00");
                }
                if (targetAmount.compareTo(MAX_AMOUNT) > 0) {
                    errors.rejectValue("targetAmount", "targetAmount.tooHigh",
                            "Target amount cannot be more than $10,000.00");
                }
        }

        // Validate denominationSelected
        List<Boolean> denominationSelected = moneyCount.getDenominationSelected();
        if (denominationSelected == null || !denominationSelected.contains(true)) {
            errors.rejectValue("denominationSelected", "denominationSelected.noneSelected",
                    "Select at least 1 denomination");
        }

    }
}
