package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.response.PaymentResponse;
import com.glassystem.optics.entity.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toPaymentResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponse.PaymentResponseBuilder paymentResponse = PaymentResponse.builder();

        paymentResponse.id( payment.getId() );
        paymentResponse.paymentMethod( payment.getPaymentMethod() );
        paymentResponse.paymentPurpose( payment.getPaymentPurpose() );
        paymentResponse.amount( payment.getAmount() );
        paymentResponse.percentage( payment.getPercentage() );
        paymentResponse.status( payment.getStatus() );
        paymentResponse.paymentDate( payment.getPaymentDate() );
        paymentResponse.description( payment.getDescription() );

        return paymentResponse.build();
    }
}
