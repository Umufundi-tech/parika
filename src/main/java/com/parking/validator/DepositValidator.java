package com.parking.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.parking.dto.DepositSaveDto;

public class DepositValidator {

    public static List<String> validate(DepositSaveDto depositDto){
        List<String> errors = new ArrayList<>();

        if(depositDto == null) {
            errors.add("Veuillez selectionner un compte");
            errors.add("Veuiller renseigner le montant de transaction");
            errors.add("Le montant de transaction doit etre superieur à zero");
            return errors;
        }
        if(depositDto.getAccountNumber() == null) {
            errors.add("Veuillez selectionner un compte");
        }

        if(depositDto.getDepositAmount()== null) {
            errors.add("Veuiller renseigner le montant de depot");
        }
        if(depositDto.getDepositAmount() == null || depositDto.getDepositAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
            errors.add("Le montant de depot doit etre superieur à zero");
        }

        return errors;
    }
}
