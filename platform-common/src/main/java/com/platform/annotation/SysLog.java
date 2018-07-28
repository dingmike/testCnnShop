package com.platform.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017年11月16日 下午10:43:16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "操作日志";
}
