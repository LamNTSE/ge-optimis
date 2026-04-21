package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.response.RefundBankAccountResponse;
import com.glassystem.optics.dto.response.RefundResponse;
import com.glassystem.optics.entity.Refund;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class RefundMapperImpl implements RefundMapper {

    @Override
    public RefundResponse toRefundResponse(Refund refund) {
        if ( refund == null ) {
            return null;
        }

        RefundResponse.RefundResponseBuilder refundResponse = RefundResponse.builder();

        refundResponse.refundId( refund.getId() );
        refundResponse.refundStatus( refund.getStatus() );
        refundResponse.refundAmount( refund.getRefundAmount() );
        refundResponse.refundPercentage( refund.getRefundPercentage() );
        refundResponse.deductionAmount( refund.getDeductionAmount() );

        return refundResponse.build();
    }

    @Override
    public RefundBankAccountResponse toRefundBankAccountResponse(Refund refund) {
        if ( refund == null ) {
            return null;
        }

        RefundBankAccountResponse.RefundBankAccountResponseBuilder refundBankAccountResponse = RefundBankAccountResponse.builder();

        refundBankAccountResponse.bankAccount( refund.getBankAccountNumber() );
        refundBankAccountResponse.bankName( refund.getBankName() );

        refundBankAccountResponse.customerName( resolveDisplayCustomerName(refund) );

        return refundBankAccountResponse.build();
    }
}
