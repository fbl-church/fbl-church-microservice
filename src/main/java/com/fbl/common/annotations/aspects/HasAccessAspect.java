/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.annotations.aspects;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.enums.WebRole;
import com.fbl.exception.types.InsufficientPermissionsException;
import com.fbl.jwt.utility.JwtHolder;

/**
 * Aspect to check if a user has access to the provided data.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Aspect
@Component
public class HasAccessAspect {

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * Determines if the caller has access to this data.
     * 
     * @param joinPoint The args called with the method.
     * @param access    What type of access the method requires.
     * @return The arguements originally passed into the method.
     * @throws Throwable Exception if they don't have access
     */
    @Around(value = "@annotation(anno)", argNames = "jp, anno")
    public Object access(ProceedingJoinPoint joinPoint, HasAccess access) throws Throwable {
        if (!WebRole.hasEqualPermission(jwtHolder.getWebRole(), List.of(access.value()))) {
            throw new InsufficientPermissionsException(jwtHolder.getWebRole());
        }
        return joinPoint.proceed();
    }
}
