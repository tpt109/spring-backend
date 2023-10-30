package com.figpop.backend.fgcore.fgutils.spring;

public interface IGenericTypeResolver {
    Class<?>[] resolveTypeArguments(final Class<?> clazz, final Class<?> genericIfc);
}
