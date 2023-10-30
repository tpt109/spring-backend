package com.figpop.backend.fgcore.fgutils.spring;

import org.springframework.core.GenericTypeResolver;

public class SpringReflectionHelper {

    public static Class<?>[] resolveTypeArguments(Class<?> clazz, Class<?> genericIfc) {
        return GenericTypeResolver.resolveTypeArguments(clazz, genericIfc);
    }
}
