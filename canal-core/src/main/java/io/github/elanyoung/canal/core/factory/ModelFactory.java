package io.github.elanyoung.canal.core.factory;

import io.github.elanyoung.canal.core.handler.EntryHandler;

/**
 * 模型工厂接口
 * 
 * @param <T> 列数据类型
 * @author william@StarImmortal
 * @since 2025/03/05
 */
public interface ModelFactory<T> {

	/**
	 * 创建新实例
	 * @param <R> 返回实体类型
	 * @param entryHandler 实体处理器
	 * @param t 列数据
	 * @return 新实体实例
	 */
	<R> R newInstance(EntryHandler<R> entryHandler, T t);

}
