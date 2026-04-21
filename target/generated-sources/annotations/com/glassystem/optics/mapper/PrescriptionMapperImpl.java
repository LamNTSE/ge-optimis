package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.request.PrescriptionRequest;
import com.glassystem.optics.dto.response.PrescriptionResponse;
import com.glassystem.optics.entity.Prescription;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PrescriptionMapperImpl implements PrescriptionMapper {

    @Override
    public Prescription toPrescription(PrescriptionRequest prescriptionRequest) {
        if ( prescriptionRequest == null ) {
            return null;
        }

        Prescription.PrescriptionBuilder prescription = Prescription.builder();

        prescription.imageUrl( prescriptionRequest.getImageUrl() );
        prescription.odSphere( prescriptionRequest.getOdSphere() );
        prescription.odCylinder( prescriptionRequest.getOdCylinder() );
        prescription.odAxis( prescriptionRequest.getOdAxis() );
        prescription.odAdd( prescriptionRequest.getOdAdd() );
        prescription.odPd( prescriptionRequest.getOdPd() );
        prescription.osSphere( prescriptionRequest.getOsSphere() );
        prescription.osCylinder( prescriptionRequest.getOsCylinder() );
        prescription.osAxis( prescriptionRequest.getOsAxis() );
        prescription.osAdd( prescriptionRequest.getOsAdd() );
        prescription.osPd( prescriptionRequest.getOsPd() );
        prescription.note( prescriptionRequest.getNote() );

        return prescription.build();
    }

    @Override
    public PrescriptionResponse toPrescriptionResponse(Prescription prescription) {
        if ( prescription == null ) {
            return null;
        }

        PrescriptionResponse.PrescriptionResponseBuilder prescriptionResponse = PrescriptionResponse.builder();

        prescriptionResponse.id( prescription.getId() );
        prescriptionResponse.imageUrl( prescription.getImageUrl() );
        prescriptionResponse.odSphere( prescription.getOdSphere() );
        prescriptionResponse.odCylinder( prescription.getOdCylinder() );
        prescriptionResponse.odAxis( prescription.getOdAxis() );
        prescriptionResponse.odAdd( prescription.getOdAdd() );
        prescriptionResponse.odPd( prescription.getOdPd() );
        prescriptionResponse.osSphere( prescription.getOsSphere() );
        prescriptionResponse.osCylinder( prescription.getOsCylinder() );
        prescriptionResponse.osAxis( prescription.getOsAxis() );
        prescriptionResponse.osAdd( prescription.getOsAdd() );
        prescriptionResponse.osPd( prescription.getOsPd() );
        prescriptionResponse.note( prescription.getNote() );

        return prescriptionResponse.build();
    }

    @Override
    public void updatePrescription(Prescription prescription, PrescriptionRequest prescriptionRequest) {
        if ( prescriptionRequest == null ) {
            return;
        }

        prescription.setImageUrl( prescriptionRequest.getImageUrl() );
        prescription.setOdSphere( prescriptionRequest.getOdSphere() );
        prescription.setOdCylinder( prescriptionRequest.getOdCylinder() );
        prescription.setOdAxis( prescriptionRequest.getOdAxis() );
        prescription.setOdAdd( prescriptionRequest.getOdAdd() );
        prescription.setOdPd( prescriptionRequest.getOdPd() );
        prescription.setOsSphere( prescriptionRequest.getOsSphere() );
        prescription.setOsCylinder( prescriptionRequest.getOsCylinder() );
        prescription.setOsAxis( prescriptionRequest.getOsAxis() );
        prescription.setOsAdd( prescriptionRequest.getOsAdd() );
        prescription.setOsPd( prescriptionRequest.getOsPd() );
        prescription.setNote( prescriptionRequest.getNote() );
    }
}
