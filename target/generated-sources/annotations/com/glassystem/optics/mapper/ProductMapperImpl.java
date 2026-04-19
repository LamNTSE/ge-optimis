package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.request.ProductCreateRequest;
import com.glassystem.optics.dto.request.ProductUpsertRequest;
import com.glassystem.optics.dto.response.ProductImageResponse;
import com.glassystem.optics.dto.response.ProductResponse;
import com.glassystem.optics.entity.Product;
import com.glassystem.optics.entity.ProductImage;
import com.glassystem.optics.enums.ProductCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-19T20:55:24+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.brand( request.getBrand() );
        product.category( request.getCategory() );
        product.frameType( request.getFrameType() );
        product.gender( request.getGender() );
        product.shape( request.getShape() );
        product.frameMaterial( request.getFrameMaterial() );
        product.hingeType( request.getHingeType() );
        product.nosePadType( request.getNosePadType() );
        product.weightGram( request.getWeightGram() );
        product.status( request.getStatus() );

        return product.build();
    }

    @Override
    public Product toProduct(ProductUpsertRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.brand( request.getBrand() );
        if ( request.getCategory() != null ) {
            product.category( Enum.valueOf( ProductCategory.class, request.getCategory() ) );
        }
        product.frameType( request.getFrameType() );
        product.gender( request.getGender() );
        product.shape( request.getShape() );
        product.frameMaterial( request.getFrameMaterial() );
        product.hingeType( request.getHingeType() );
        product.nosePadType( request.getNosePadType() );
        product.weightGram( request.getWeightGram() );
        product.status( request.getStatus() );

        return product.build();
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.brand( product.getBrand() );
        if ( product.getCategory() != null ) {
            productResponse.category( product.getCategory().name() );
        }
        productResponse.frameType( product.getFrameType() );
        productResponse.gender( product.getGender() );
        productResponse.shape( product.getShape() );
        productResponse.frameMaterial( product.getFrameMaterial() );
        productResponse.hingeType( product.getHingeType() );
        productResponse.nosePadType( product.getNosePadType() );
        productResponse.weightGram( product.getWeightGram() );
        productResponse.status( product.getStatus() );
        productResponse.modelUrl( product.getModelUrl() );
        productResponse.imageUrl( productImageListToProductImageResponseList( product.getImageUrl() ) );

        productResponse.minPrice( calculateMinPrice(product) );
        productResponse.maxPrice( calculateMaxPrice(product) );

        return productResponse.build();
    }

    @Override
    public void updateProduct(Product product, ProductUpsertRequest request) {
        if ( request == null ) {
            return;
        }

        product.setName( request.getName() );
        product.setBrand( request.getBrand() );
        if ( request.getCategory() != null ) {
            product.setCategory( Enum.valueOf( ProductCategory.class, request.getCategory() ) );
        }
        else {
            product.setCategory( null );
        }
        product.setFrameType( request.getFrameType() );
        product.setGender( request.getGender() );
        product.setShape( request.getShape() );
        product.setFrameMaterial( request.getFrameMaterial() );
        product.setHingeType( request.getHingeType() );
        product.setNosePadType( request.getNosePadType() );
        product.setWeightGram( request.getWeightGram() );
        product.setStatus( request.getStatus() );
    }

    protected ProductImageResponse productImageToProductImageResponse(ProductImage productImage) {
        if ( productImage == null ) {
            return null;
        }

        ProductImageResponse.ProductImageResponseBuilder productImageResponse = ProductImageResponse.builder();

        productImageResponse.id( productImage.getId() );
        productImageResponse.imageUrl( productImage.getImageUrl() );

        return productImageResponse.build();
    }

    protected List<ProductImageResponse> productImageListToProductImageResponseList(List<ProductImage> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductImageResponse> list1 = new ArrayList<ProductImageResponse>( list.size() );
        for ( ProductImage productImage : list ) {
            list1.add( productImageToProductImageResponse( productImage ) );
        }

        return list1;
    }
}
