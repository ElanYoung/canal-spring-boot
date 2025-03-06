package io.github.elanyoung.canal.core.util;

import cn.hutool.core.util.ModifierUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import io.github.elanyoung.canal.core.handler.EntryHandler;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Canal工具类
 *
 * @author william@StarImmortal
 * @since 2025/03/06
 */
public class CanalUtil {

	private static final Map<Class<? extends EntryHandler<?>>, Class<?>> ENTITY_CLASS_CACHE_MAP = new ConcurrentHashMap<>(
			16);

	@SuppressWarnings({ "unchecked" })
	public static <T> Class<T> getTableClass(EntryHandler<?> entryHandler) {
		Class<? extends EntryHandler<?>> handlerClass = (Class<? extends EntryHandler<?>>) entryHandler.getClass();
		// 从缓存中获取实体类
		Class<?> tableClass = ENTITY_CLASS_CACHE_MAP.get(handlerClass);
		if (tableClass == null) {
			// 缓存未命中，获取实体类并缓存
			Type typeArgument = TypeUtil.getTypeArgument(handlerClass);
			tableClass = TypeUtil.getClass(typeArgument);
			ENTITY_CLASS_CACHE_MAP.putIfAbsent(handlerClass, tableClass);
		}
		return (Class<T>) tableClass;
	}

	/**
	 * 获取实体类字段与数据库表字段映射关系
	 * <p>
	 * 该方法会获取实体类所有字段（包括父类字段），并过滤掉以下字段：
	 * <ul>
	 * <li>被 @Transient 注解标记字段</li>
	 * <li>被 @TableField(exist = false) 注解标记字段</li>
	 * <li>静态字段</li>
	 * </ul>
	 * </p>
	 * @param clazz 实体类 Class 对象
	 * @return 实体类字段与数据库表字段映射关系
	 * <ul>
	 * <li>key：数据库表字段名（由 @TableField、@Column 注解指定，或默认将驼峰命名转换为下划线格式）</li>
	 * <li>value：Java 类字段名</li>
	 * </ul>
	 */
	public static Map<String, String> getFieldName(Class<?> clazz) {
		// 获取类的所有字段（包括父类）
		Field[] fields = ReflectUtil.getFields(clazz);
		// 过滤并生成字段映射
		return Arrays.stream(fields)
			.filter(field -> !CanalUtil.isTransient(field))
			.filter(field -> !ModifierUtil.isStatic(field))
			.collect(Collectors.toMap(CanalUtil::getColumnName, Field::getName));
	}

	/**
	 * 获取数据库表字段名
	 * @param field Java类字段
	 * @return 数据库表字段名
	 */
	private static String getColumnName(Field field) {
		// MyBatis-Plus：@TableField 注解指定字段名
		TableField tableField = field.getAnnotation(TableField.class);
		if (Objects.nonNull(tableField) && StringUtils.hasText(tableField.value())) {
			return tableField.value();
		}
		// JPA：@Column 注解指定字段名
		Column column = field.getAnnotation(Column.class);
		if (Objects.nonNull(column) && StringUtils.hasText(column.name())) {
			return column.name();
		}
		// 默认：将Java字段名转换为下划线格式作为表字段名
		return StrUtil.toUnderlineCase(field.getName());
	}

	/**
	 * 判断字段是否为瞬时字段（非持久化字段）
	 * @param field 待检查字段
	 * @return true：字段为瞬时字段（非持久化）；false：字段为持久化字段
	 */
	private static boolean isTransient(Field field) {
		// MyBatis Plus：检查 @TableField 注解
		TableField tableField = field.getAnnotation(TableField.class);
		if (tableField != null) {
			return !tableField.exist();
		}
		// JPA：检查 @Transient 注解，有该注解表示是瞬时字段
		return Objects.nonNull(field.getAnnotation(Transient.class));
	}

}
