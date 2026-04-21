package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.request.OrderCreationRequest;
import com.glassystem.optics.dto.response.BankInfoResponse;
import com.glassystem.optics.dto.response.OrderResponse;
import com.glassystem.optics.entity.Combo;
import com.glassystem.optics.entity.Orders;
import com.glassystem.optics.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Orders toOrder(OrderCreationRequest orderCreationRequest) {
        if ( orderCreationRequest == null ) {
            return null;
        }

        Orders.OrdersBuilder orders = Orders.builder();

        orders.deliveryAddress( orderCreationRequest.getDeliveryAddress() );
        orders.recipientName( orderCreationRequest.getRecipientName() );
        orders.phoneNumber( orderCreationRequest.getPhoneNumber() );
        orders.paymentMethod( orderCreationRequest.getPaymentMethod() );
        orders.items( orderItemMapper.toOrderItemList( orderCreationRequest.getItems() ) );

        return orders.build();
    }

    @Override
    public OrderResponse toOrderResponse(Orders order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setBankInfo( ordersToBankInfoResponse( order ) );
        orderResponse.setCustomerId( orderCustomerId( order ) );
        orderResponse.setOrderId( order.getId() );
        orderResponse.setOrderStatus( order.getStatus() );
        orderResponse.setComboId( orderComboId( order ) );
        orderResponse.setComboName( orderComboName( order ) );
        orderResponse.setComboDiscountAmount( order.getComboDiscountAmount() );
        orderResponse.setComboSnapshot( order.getComboSnapshot() );
        orderResponse.setDeliveryAddress( order.getDeliveryAddress() );
        orderResponse.setRecipientName( order.getRecipientName() );
        orderResponse.setPhoneNumber( order.getPhoneNumber() );
        orderResponse.setTotalAmount( order.getTotalAmount() );
        orderResponse.setDepositAmount( order.getDepositAmount() );
        orderResponse.setRemainingAmount( order.getRemainingAmount() );
        orderResponse.setItems( orderItemMapper.toOrderItemResponseList( order.getItems() ) );
        orderResponse.setTrackingNumber( order.getTrackingNumber() );

        return orderResponse;
    }

    protected BankInfoResponse ordersToBankInfoResponse(Orders orders) {
        if ( orders == null ) {
            return null;
        }

        BankInfoResponse bankInfoResponse = new BankInfoResponse();

        bankInfoResponse.setBankName( orders.getBankName() );
        bankInfoResponse.setBankAccountNumber( orders.getBankAccountNumber() );
        bankInfoResponse.setAccountHolderName( orders.getAccountHolderName() );

        return bankInfoResponse;
    }

    private String orderCustomerId(Orders orders) {
        if ( orders == null ) {
            return null;
        }
        User customer = orders.getCustomer();
        if ( customer == null ) {
            return null;
        }
        String id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String orderComboId(Orders orders) {
        if ( orders == null ) {
            return null;
        }
        Combo combo = orders.getCombo();
        if ( combo == null ) {
            return null;
        }
        String id = combo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String orderComboName(Orders orders) {
        if ( orders == null ) {
            return null;
        }
        Combo combo = orders.getCombo();
        if ( combo == null ) {
            return null;
        }
        String name = combo.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
