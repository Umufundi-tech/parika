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
public class PaymentListDto {

    private Long id;
    private TransactionDto transaction;
    private ParkingTicketDto parkingTicket;

    public static PaymentListDto fromEntity(Payment payment) {
        if(payment == null) {
            return null;
        }

        return PaymentListDto.builder()
                .id(payment.getId())
                .transaction(TransactionDto.fromEntity(payment.getTransaction()))
                .parkingTicket(ParkingTicketDto.fromEntity(payment.getParkingTicket()))
                .build();
    }

    public static Payment toEntity(PaymentListDto paymentDto) {
        if(paymentDto == null) {
            return null;
        }

        Payment payment = new Payment();
        payment.setId(paymentDto.getId());
        payment.setTransaction(TransactionDto.toEntity(paymentDto.getTransaction()));
        payment.setParkingTicket(ParkingTicketDto.toEntity(paymentDto.getParkingTicket()));

        return payment;
    }
}
