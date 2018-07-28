package com.platform.annotation;

import java.lang.annotation.*;

/**
 * 数据过滤
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017年11月16日 下午10:43:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {

    /**
     * sql中数据创建用户（通常传入CREATE_USER_ID）的别名
     */
    String userAlias() default "";

    /**
     * sql中数据deptId的别名
     */
    String deptAlias() default "";

    /**
     * true：没有部门数据权限，也能查询本人数据
     */
    boolean self() default true;
}
