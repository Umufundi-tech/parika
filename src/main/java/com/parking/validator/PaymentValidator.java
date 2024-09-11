package com.parking.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.parking.dto.PaymentListDto;
import com.parking.dto.PaymentSaveDto;

public class PaymentValidator {

    public static List<String> validate(PaymentSaveDto paymentDto){
        List<String> errors = new ArrayList<>();

        if(paymentDto == null) {
            errors.add("Veuiller selectionner un ticket");
            errors.add("Veuillez selectionner un compte");
            errors.add("Veuiller renseigner le montant de transaction");
            errors.add("Le montant de transaction doit etre superieur à zero");
            return errors;
        }

        if(paymentDto.getParkingTicket() == null || paymentDto.getParkingTicket().getId() == null || paymentDto.getParkingTicket().getId().compareTo(0L) == 0) {
            errors.add("Veuiller selectionner un ticket");
        }
        if(paymentDto.getAccount() == null || paymentDto.getAccount().getId() == null || paymentDto.getAccount().getId().compareTo(0L) == 0) {
            errors.add("Veuillez selectionner un compte");
        }
        
        assert paymentDto.getParkingTicket()!=null;
        
        if(paymentDto.getParkingTicket().getFareAmount() == null) {
            errors.add("Veuiller renseigner le montant de transaction");
        }
        if(paymentDto.getParkingTicket().getFareAmount() == null || paymentDto.getParkingTicket().getFareAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
            errors.add("Le montant de transaction doit etre superieur à zero");
        }

        return errors;
    }
}
