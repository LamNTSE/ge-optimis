package com.glassystem.optics.mapper;

import com.glassystem.optics.dto.request.PermissionRequest;
import com.glassystem.optics.dto.request.UserCreationRequest;
import com.glassystem.optics.dto.request.UserUpdateRequest;
import com.glassystem.optics.dto.response.RoleResponse;
import com.glassystem.optics.dto.response.UserResponse;
import com.glassystem.optics.entity.Permission;
import com.glassystem.optics.entity.Role;
import com.glassystem.optics.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-19T20:55:24+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( request.getUsername() );
        user.password( request.getPassword() );
        user.firstName( request.getFirstName() );
        user.lastName( request.getLastName() );
        user.dob( request.getDob() );
        user.imageUrl( request.getImageUrl() );
        user.email( request.getEmail() );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.username( user.getUsername() );
        userResponse.firstName( user.getFirstName() );
        userResponse.lastName( user.getLastName() );
        userResponse.dob( user.getDob() );
        userResponse.imageUrl( user.getImageUrl() );
        userResponse.email( user.getEmail() );
        userResponse.phone( user.getPhone() );
        userResponse.roles( roleSetToRoleResponseSet( user.getRoles() ) );

        return userResponse.build();
    }

    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setPassword( request.getPassword() );
        user.setFirstName( request.getFirstName() );
        user.setLastName( request.getLastName() );
        user.setDob( request.getDob() );
        user.setImageUrl( request.getImageUrl() );
        user.setEmail( request.getEmail() );
        user.setPhone( request.getPhone() );
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

    protected RoleResponse roleToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.name( role.getName() );
        roleResponse.description( role.getDescription() );
        roleResponse.permissions( permissionSetToPermissionRequestSet( role.getPermissions() ) );

        return roleResponse.build();
    }

    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleResponse( role ) );
        }

        return set1;
    }
}
