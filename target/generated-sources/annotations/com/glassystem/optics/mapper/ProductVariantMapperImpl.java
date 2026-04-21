package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.request.ProductVariantRequest;
import com.glassystem.optics.dto.response.ProductVariantResponse;
import com.glassystem.optics.entity.Product;
import com.glassystem.optics.entity.ProductVariant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProductVariantMapperImpl implements ProductVariantMapper {

    @Override
    public ProductVariant toProductVariant(ProductVariantRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductVariant.ProductVariantBuilder productVariant = ProductVariant.builder();

        productVariant.colorName( request.getColorName() );
        productVariant.frameFinish( request.getFrameFinish() );
        productVariant.lensWidthMm( request.getLensWidthMm() );
        productVariant.bridgeWidthMm( request.getBridgeWidthMm() );
        productVariant.templeLengthMm( request.getTempleLengthMm() );
        productVariant.sizeLabel( request.getSizeLabel() );
        productVariant.price( request.getPrice() );
        productVariant.quantity( request.getQuantity() );
        productVariant.status( request.getStatus() );
        productVariant.orderItemType( request.getOrderItemType() );

        return productVariant.build();
    }

    @Override
    public ProductVariant toEntity(ProductVariantRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductVariant.ProductVariantBuilder productVariant = ProductVariant.builder();

        productVariant.colorName( request.getColorName() );
        productVariant.frameFinish( request.getFrameFinish() );
        productVariant.lensWidthMm( request.getLensWidthMm() );
        productVariant.bridgeWidthMm( request.getBridgeWidthMm() );
        productVariant.templeLengthMm( request.getTempleLengthMm() );
        productVariant.sizeLabel( request.getSizeLabel() );
        productVariant.price( request.getPrice() );
        productVariant.quantity( request.getQuantity() );
        productVariant.status( request.getStatus() );
        productVariant.orderItemType( request.getOrderItemType() );

        return productVariant.build();
    }

    @Override
    public ProductVariantResponse toResponse(ProductVariant variant) {
        if ( variant == null ) {
            return null;
        }

        ProductVariantResponse.ProductVariantResponseBuilder productVariantResponse = ProductVariantResponse.builder();

        productVariantResponse.productId( variantProductId( variant ) );
        productVariantResponse.id( variant.getId() );
        productVariantResponse.colorName( variant.getColorName() );
        productVariantResponse.frameFinish( variant.getFrameFinish() );
        productVariantResponse.lensWidthMm( variant.getLensWidthMm() );
        productVariantResponse.bridgeWidthMm( variant.getBridgeWidthMm() );
        productVariantResponse.templeLengthMm( variant.getTempleLengthMm() );
        productVariantResponse.sizeLabel( variant.getSizeLabel() );
        productVariantResponse.price( variant.getPrice() );
        productVariantResponse.quantity( variant.getQuantity() );
        productVariantResponse.status( variant.getStatus() );
        productVariantResponse.orderItemType( variant.getOrderItemType() );

        return productVariantResponse.build();
    }

    @Override
    public void updateEntity(ProductVariant variant, ProductVariantRequest request) {
        if ( request == null ) {
            return;
        }

        variant.setColorName( request.getColorName() );
        variant.setFrameFinish( request.getFrameFinish() );
        variant.setLensWidthMm( request.getLensWidthMm() );
        variant.setBridgeWidthMm( request.getBridgeWidthMm() );
        variant.setTempleLengthMm( request.getTempleLengthMm() );
        variant.setSizeLabel( request.getSizeLabel() );
        variant.setPrice( request.getPrice() );
        variant.setQuantity( request.getQuantity() );
        variant.setStatus( request.getStatus() );
        variant.setOrderItemType( request.getOrderItemType() );
    }

    private String variantProductId(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }
        Product product = productVariant.getProduct();
        if ( product == null ) {
            return null;
        }
        String id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
