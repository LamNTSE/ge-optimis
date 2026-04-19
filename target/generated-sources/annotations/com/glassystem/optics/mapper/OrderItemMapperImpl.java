package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.request.OrderItemCreationRequest;
import com.glassystem.optics.dto.response.OrderItemResponse;
import com.glassystem.optics.entity.OrderItem;
import com.glassystem.optics.entity.ProductVariant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-19T20:55:22+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Override
    public OrderItem toOrderItem(OrderItemCreationRequest orderItemCreationRequest) {
        if ( orderItemCreationRequest == null ) {
            return null;
        }

        OrderItem.OrderItemBuilder orderItem = OrderItem.builder();

        orderItem.quantity( orderItemCreationRequest.getQuantity() );
        orderItem.lensId( orderItemCreationRequest.getLensId() );
        orderItem.prescription( prescriptionMapper.toPrescription( orderItemCreationRequest.getPrescription() ) );

        return orderItem.build();
    }

    @Override
    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemResponse orderItemResponse = new OrderItemResponse();

        orderItemResponse.setProductVariantId( orderItemProductVariantId( orderItem ) );
        orderItemResponse.setOrderItemId( orderItem.getId() );
        orderItemResponse.setOrderItemType( orderItem.getOrderItemType() );
        orderItemResponse.setQuantity( orderItem.getQuantity() );
        orderItemResponse.setUnitPrice( orderItem.getUnitPrice() );
        orderItemResponse.setLensId( orderItem.getLensId() );
        orderItemResponse.setLensName( orderItem.getLensName() );
        orderItemResponse.setLensPrice( orderItem.getLensPrice() );
        orderItemResponse.setTotalPrice( orderItem.getTotalPrice() );
        orderItemResponse.setStatus( orderItem.getStatus() );
        orderItemResponse.setPrescription( prescriptionMapper.toPrescriptionResponse( orderItem.getPrescription() ) );

        orderItemResponse.setProductId( getProductId(orderItem) );
        orderItemResponse.setProductName( getProductName(orderItem) );
        orderItemResponse.setProductImage( getProductImage(orderItem) );
        orderItemResponse.setVariantName( buildVariantName(orderItem) );
        orderItemResponse.setItemName( getProductName(orderItem) );
        orderItemResponse.setLensPriceTotal( calculateLensPriceTotal(orderItem) );

        return orderItemResponse;
    }

    @Override
    public List<OrderItem> toOrderItemList(List<OrderItemCreationRequest> orderItemCreationRequests) {
        if ( orderItemCreationRequests == null ) {
            return null;
        }

        List<OrderItem> list = new ArrayList<OrderItem>( orderItemCreationRequests.size() );
        for ( OrderItemCreationRequest orderItemCreationRequest : orderItemCreationRequests ) {
            list.add( toOrderItem( orderItemCreationRequest ) );
        }

        return list;
    }

    @Override
    public List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> orderItems) {
        if ( orderItems == null ) {
            return null;
        }

        List<OrderItemResponse> list = new ArrayList<OrderItemResponse>( orderItems.size() );
        for ( OrderItem orderItem : orderItems ) {
            list.add( toOrderItemResponse( orderItem ) );
        }

        return list;
    }

    private String orderItemProductVariantId(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        ProductVariant productVariant = orderItem.getProductVariant();
        if ( productVariant == null ) {
            return null;
        }
        String id = productVariant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
