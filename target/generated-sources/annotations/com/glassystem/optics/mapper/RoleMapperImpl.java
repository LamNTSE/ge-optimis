package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.request.PermissionRequest;
import com.glassystem.optics.dto.request.RoleRequest;
import com.glassystem.optics.dto.response.RoleResponse;
import com.glassystem.optics.entity.Permission;
import com.glassystem.optics.entity.Role;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T06:23:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toRole(RoleRequest roleRequest) {
        if ( roleRequest == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.name( roleRequest.getName() );
        role.description( roleRequest.getDescription() );

        return role.build();
    }

    @Override
    public RoleResponse toRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.name( role.getName() );
        roleResponse.description( role.getDescription() );
        roleResponse.permissions( permissionSetToPermissionRequestSet( role.getPermissions() ) );

        return roleResponse.build();
    }

    protected PermissionRequest permissionToPermissionRequest(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionRequest.PermissionRequestBuilder permissionRequest = PermissionRequest.builder();

        permissionRequest.name( permission.getName() );
        permissionRequest.description( permission.getDescription() );

        return permissionRequest.build();
    }

    protected Set<PermissionRequest> permissionSetToPermissionRequestSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionRequest> set1 = new LinkedHashSet<PermissionRequest>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionToPermissionRequest( permission ) );
        }

        return set1;
    }
}
