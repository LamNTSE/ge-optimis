package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.response.PolicyResponse;
import com.glassystem.optics.entity.Policy;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PolicyMapperImpl implements PolicyMapper {

    @Override
    public PolicyResponse toPolicyResponse(Policy policy) {
        if ( policy == null ) {
            return null;
        }

        PolicyResponse.PolicyResponseBuilder policyResponse = PolicyResponse.builder();

        policyResponse.id( policy.getId() );
        policyResponse.code( policy.getCode() );
        policyResponse.title( policy.getTitle() );
        policyResponse.description( policy.getDescription() );
        policyResponse.effectiveFrom( policy.getEffectiveFrom() );
        policyResponse.effectiveTo( policy.getEffectiveTo() );
        policyResponse.createdAt( policy.getCreatedAt() );

        policyResponse.managerUserId( policy.getManagerUser() != null ? policy.getManagerUser().getId() : null );
        policyResponse.managerUsername( policy.getManagerUser() != null ? policy.getManagerUser().getUsername() : null );

        return policyResponse.build();
    }
}
