package com.platform.annotation;

import java.lang.annotation.*;

/**
 * 该注解表示非必填项,仅仅作为标识
 * @author phil
 */
@Documented
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface NotRequire {

	boolean value() default true;
	
}
