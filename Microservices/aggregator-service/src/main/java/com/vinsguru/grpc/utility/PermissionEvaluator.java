package com.vinsguru.grpc.utility;

import com.vinsguru.grpc.dto.UserDto;
import com.vinsguru.grpc.helperModel.Permission;
import com.vinsguru.grpc.helperModel.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component("permissionEvaluator")
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    /**
     * @param authentication     represents the user in question. Should not be null.
     * @param targetDomainObject the domain object for which permissions should be
     *                           checked. May be null in which case implementations should return false, as the null
     *                           condition can be checked explicitly in the expression.
     * @param permission         a representation of the permission object as supplied by the
     *                           expression system. Not null.
     * @return true if the permission is granted, false otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication != null && permission instanceof String) {
            User loggedUser = (User) authentication.getPrincipal();
            String permissionToCheck = (String) permission;
            // in this part of the code you need to check if the loggedUser has the "permission" over the
            // targetDomainObject. In this implementation the "permission" is a string, for example "read", or "update"
            // The targetDomainObject is an actual object, for example a object of UserProfile class (a class that
            // has the profile information for a User)

            // You can implement the permission to check over the targetDomainObject in the way that suits you best
            // A naive approach:List<PostDto
            if (targetDomainObject.getClass().getSimpleName().compareTo("List<PostDto>") == 0) {
                List<Permission> p = loggedUser.getPerm();
                for(Permission perm : p){
                    if(perm.getAuthority().equals(permissionToCheck)){
                        return true;
                    }
                }
            }
            if (targetDomainObject.getClass().getSimpleName().compareTo("String") == 0) {
                    List<Permission> p = loggedUser.getPerm();
                    for(Permission perm : p){
                        if(perm.getAuthority().equals(permissionToCheck)){
                            return true;
                        }
                    }
            }
            if (targetDomainObject.getClass().getSimpleName().compareTo("UserDto") == 0) {
                List<Permission> p = loggedUser.getPerm();
                for(Permission perm : p){
                    if(perm.getAuthority().equals(permissionToCheck)){
                        return true;
                    }
                }
            }
        }
        //access denied
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

}
