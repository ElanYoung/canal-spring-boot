package io.github.elanyoung.canal.core.annotation;

import java.lang.annotation.*;

/**
 * Canal表监听注解
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CanalTable {

	/**
	 * 表名
	 */
	String value() default "";

}