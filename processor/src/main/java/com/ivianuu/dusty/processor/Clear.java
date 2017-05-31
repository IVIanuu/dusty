package com.ivianuu.dusty.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Manuel Wrage (IVIanuu)
 */

@Retention(RetentionPolicy.CLASS)@Target(ElementType.FIELD)
public @interface Clear {}