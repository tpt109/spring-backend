package com.figpop.backend.fgcore.fgutils.spring;

public class GenericTypeUtils {
    private static IGenericTypeResolver GENERIC_TYPE_RESOLVER;

    /**
     * Get the generic tool helper
     */
    public static Class<?>[] resolveTypeArguments(final Class<?> clazz, final Class<?> genericIfc) {
        if (null == GENERIC_TYPE_RESOLVER) {
            // Use spring static methods directly to reduce object creation
            return SpringReflectionHelper.resolveTypeArguments(clazz, genericIfc);
        }
        return GENERIC_TYPE_RESOLVER.resolveTypeArguments(clazz, genericIfc);
    }

    /**
     * Set up a generic tool helper. If you don’t want to use Spring encapsulation, you can replace it before use.
     */
    public static void setGenericTypeResolver(IGenericTypeResolver genericTypeResolver) {
        GENERIC_TYPE_RESOLVER = genericTypeResolver;
    }
}
