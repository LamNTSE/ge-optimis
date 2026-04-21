package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.response.FeedbackResponse;
import com.glassystem.optics.entity.Feedback;
import com.glassystem.optics.entity.Orders;
import com.glassystem.optics.entity.Product;
import com.glassystem.optics.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class FeedbackMapperImpl implements FeedbackMapper {

    @Override
    public FeedbackResponse toFeedbackResponse(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }

        FeedbackResponse.FeedbackResponseBuilder feedbackResponse = FeedbackResponse.builder();

        feedbackResponse.feedbackId( feedback.getId() );
        feedbackResponse.orderId( feedbackOrderId( feedback ) );
        feedbackResponse.productId( feedbackProductId( feedback ) );
        feedbackResponse.productName( feedbackProductName( feedback ) );
        feedbackResponse.customerId( feedbackCustomerId( feedback ) );
        feedbackResponse.rating( feedback.getRating() );
        feedbackResponse.comment( feedback.getComment() );
        List<String> list = feedback.getImageUrls();
        if ( list != null ) {
            feedbackResponse.imageUrls( new ArrayList<String>( list ) );
        }
        feedbackResponse.createdAt( feedback.getCreatedAt() );
        feedbackResponse.updatedAt( feedback.getUpdatedAt() );

        feedbackResponse.customerName( buildCustomerName(feedback) );

        return feedbackResponse.build();
    }

    @Override
    public List<FeedbackResponse> toFeedbackResponseList(List<Feedback> feedbacks) {
        if ( feedbacks == null ) {
            return null;
        }

        List<FeedbackResponse> list = new ArrayList<FeedbackResponse>( feedbacks.size() );
        for ( Feedback feedback : feedbacks ) {
            list.add( toFeedbackResponse( feedback ) );
        }

        return list;
    }

    private String feedbackOrderId(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }
        Orders order = feedback.getOrder();
        if ( order == null ) {
            return null;
        }
        String id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String feedbackProductId(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }
        Product product = feedback.getProduct();
        if ( product == null ) {
            return null;
        }
        String id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String feedbackProductName(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }
        Product product = feedback.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String feedbackCustomerId(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }
        User customer = feedback.getCustomer();
        if ( customer == null ) {
            return null;
        }
        String id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
