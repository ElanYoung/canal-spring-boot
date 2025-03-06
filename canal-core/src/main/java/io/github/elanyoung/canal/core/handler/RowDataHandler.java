package io.github.elanyoung.canal.core.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 行数据处理器
 *
 * @author william@StarImmortal
 * @since 2025/03/05
 */
public interface RowDataHandler<T> {

	/**
	 * 处理行数据
	 * @param t 行数据
	 * @param entryHandler 行数据处理器
	 * @param eventType 事件类型
	 */
	<R> void handleRowData(T t, EntryHandler<R> entryHandler, CanalEntry.EventType eventType);

}
