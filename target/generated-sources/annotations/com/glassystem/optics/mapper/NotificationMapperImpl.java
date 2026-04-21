package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.response.NotificationResponse;
import com.glassystem.optics.entity.Notification;
import com.glassystem.optics.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:37+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationResponse toResponse(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationResponse.NotificationResponseBuilder notificationResponse = NotificationResponse.builder();

        notificationResponse.recipientId( notificationRecipientId( notification ) );
        notificationResponse.isRead( notification.isRead() );
        notificationResponse.id( notification.getId() );
        notificationResponse.title( notification.getTitle() );
        notificationResponse.content( notification.getContent() );
        notificationResponse.senderId( notification.getSenderId() );
        notificationResponse.createdAt( notification.getCreatedAt() );
        notificationResponse.readAt( notification.getReadAt() );

        notificationResponse.recipientName( resolveRecipientName(notification) );

        return notificationResponse.build();
    }

    private String notificationRecipientId(Notification notification) {
        if ( notification == null ) {
            return null;
        }
        User recipient = notification.getRecipient();
        if ( recipient == null ) {
            return null;
        }
        String id = recipient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
