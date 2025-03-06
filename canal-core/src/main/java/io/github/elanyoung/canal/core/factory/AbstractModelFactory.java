package io.github.elanyoung.canal.core.factory;

import io.github.elanyoung.canal.core.handler.EntryHandler;
import io.github.elanyoung.canal.core.util.CanalUtil;

/**
 * 抽象模型工厂
 *
 * @param <T> 列数据类型
 * @author william@StarImmortal
 * @since 2025/03/05
 */
public abstract class AbstractModelFactory<T> implements ModelFactory<T> {

	/**
	 * 创建指定类型新实例
	 * @param <R> 返回实体类型
	 * @param c 目标类型 Class 对象
	 * @param t 列数据
	 * @return 返回创建实体实例
	 */
	abstract <R> R newInstance(Class<R> c, T t);

	@Override
	public <R> R newInstance(EntryHandler<R> entryHandler, T t) {
		Class<R> tableClass = CanalUtil.getTableClass(entryHandler);
		return newInstance(tableClass, t);
	}

}
