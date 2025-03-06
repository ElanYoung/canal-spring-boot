package io.github.elanyoung.canal.core.handler.impl;

import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.elanyoung.canal.core.factory.ModelFactory;
import io.github.elanyoung.canal.core.handler.EntryHandler;
import io.github.elanyoung.canal.core.handler.RowDataHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * 行数据处理器实现类
 *
 * @author william@StarImmortal
 * @since 2025/03/05
 */
@Slf4j
public class RowDataHandlerImpl implements RowDataHandler<CanalEntry.RowData> {

	private final ModelFactory<List<CanalEntry.Column>> modelFactory;

	public RowDataHandlerImpl(ModelFactory<List<CanalEntry.Column>> modelFactory) {
		this.modelFactory = modelFactory;
	}

	@Override
	public <R> void handleRowData(CanalEntry.RowData rowData, EntryHandler<R> entryHandler,
			CanalEntry.EventType eventType) {
		if (Objects.nonNull(entryHandler)) {
			switch (eventType) {
				case INSERT:
					R object = modelFactory.newInstance(entryHandler, rowData.getAfterColumnsList());
					entryHandler.insert(object);
					break;
				case UPDATE:
					R before = modelFactory.newInstance(entryHandler, rowData.getBeforeColumnsList());
					R after = modelFactory.newInstance(entryHandler, rowData.getAfterColumnsList());
					entryHandler.update(before, after);
					break;
				case DELETE:
					R r = modelFactory.newInstance(entryHandler, rowData.getBeforeColumnsList());
					entryHandler.delete(r);
					break;
				default:
					break;
			}
		}
	}

}
