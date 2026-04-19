package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.request.LensCreateRequest;
import com.glassystem.optics.dto.response.LensResponse;
import com.glassystem.optics.entity.Lens;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-19T20:55:24+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class LensMapperImpl implements LensMapper {

    @Override
    public Lens toLens(LensCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Lens.LensBuilder lens = Lens.builder();

        lens.name( request.getName() );
        lens.material( request.getMaterial() );
        lens.price( request.getPrice() );
        lens.description( request.getDescription() );

        return lens.build();
    }

    @Override
    public LensResponse toLensResponse(Lens lens) {
        if ( lens == null ) {
            return null;
        }

        LensResponse.LensResponseBuilder lensResponse = LensResponse.builder();

        lensResponse.id( lens.getId() );
        lensResponse.name( lens.getName() );
        lensResponse.material( lens.getMaterial() );
        lensResponse.price( lens.getPrice() );
        lensResponse.description( lens.getDescription() );

        return lensResponse.build();
    }
}
