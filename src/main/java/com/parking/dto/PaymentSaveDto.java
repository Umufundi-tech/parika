package com.parking.dto;

import com.parking.model.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSaveDto {

    private Long id;
    private String qrCodeString;
    private ParkingTicketListDto parkingTicket;

    public static PaymentSaveDto fromEntity(Payment payment) {
        if(payment == null) {
            return null;
        }

        return PaymentSaveDto.builder()
                .id(payment.getId())
                .qrCodeString(payment.getTransaction().getAccount().getQrCodeString())
                .parkingTicket(ParkingTicketListDto.fromEntity(payment.getParkingTicket()))
                .build();
    }

    public static Payment toEntity(PaymentSaveDto paymentSaveDto, TransactionDto transactionDto, ParkingTicketListDto parkingTicketListDto) {
        if(paymentSaveDto == null || transactionDto == null ||  parkingTicketListDto == null ) {
            return null;
        }

        Payment payment = new Payment();
        payment.setId(paymentSaveDto.getId());
        payment.setTransaction(TransactionDto.toEntity(transactionDto));
        payment.setParkingTicket(ParkingTicketListDto.toEntity(parkingTicketListDto));
        return payment;
    }

}
