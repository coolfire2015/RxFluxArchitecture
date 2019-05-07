package com.huyingbao.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to retrieve LibraryGlideModule and GlideExtension classes in our annotation processor from
 * libraries and applications.
 *
 * <p>Part of the internals of Glide's annotation processor and not for public use.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Index {
    String[] modules() default {};
}
