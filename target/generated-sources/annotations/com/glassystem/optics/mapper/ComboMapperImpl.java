package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.response.ComboItemResponse;
import com.glassystem.optics.dto.response.ComboResponse;
import com.glassystem.optics.entity.Combo;
import com.glassystem.optics.entity.ComboItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-19T20:55:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ComboMapperImpl implements ComboMapper {

    @Override
    public ComboResponse toComboResponse(Combo combo) {
        if ( combo == null ) {
            return null;
        }

        ComboResponse.ComboResponseBuilder comboResponse = ComboResponse.builder();

        comboResponse.comboItems( comboItemListToComboItemResponseList( combo.getComboItems() ) );
        comboResponse.id( combo.getId() );
        comboResponse.name( combo.getName() );
        comboResponse.description( combo.getDescription() );
        comboResponse.discountType( combo.getDiscountType() );
        comboResponse.discountValue( combo.getDiscountValue() );
        comboResponse.startTime( combo.getStartTime() );
        comboResponse.endTime( combo.getEndTime() );
        comboResponse.status( combo.getStatus() );
        comboResponse.isManuallyDisabled( combo.getIsManuallyDisabled() );
        comboResponse.createdAt( combo.getCreatedAt() );
        comboResponse.updatedAt( combo.getUpdatedAt() );

        return comboResponse.build();
    }

    @Override
    public ComboItemResponse toComboItemResponse(ComboItem item) {
        if ( item == null ) {
            return null;
        }

        ComboItemResponse.ComboItemResponseBuilder comboItemResponse = ComboItemResponse.builder();

        comboItemResponse.id( item.getId() );
        comboItemResponse.requiredQuantity( item.getRequiredQuantity() );

        comboItemResponse.productId( item.getProduct() != null ? item.getProduct().getId() : null );
        comboItemResponse.productName( item.getProduct() != null ? item.getProduct().getName() : null );
        comboItemResponse.skuId( item.getProductVariant() != null ? item.getProductVariant().getId() : null );
        comboItemResponse.skuLabel( buildSkuLabel(item) );

        return comboItemResponse.build();
    }

    protected List<ComboItemResponse> comboItemListToComboItemResponseList(List<ComboItem> list) {
        if ( list == null ) {
            return null;
        }

        List<ComboItemResponse> list1 = new ArrayList<ComboItemResponse>( list.size() );
        for ( ComboItem comboItem : list ) {
            list1.add( toComboItemResponse( comboItem ) );
        }

        return list1;
    }
}
