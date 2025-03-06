package io.github.elanyoung.canal.core.handler.impl;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import io.github.elanyoung.canal.core.handler.AbstractMessageHandler;
import io.github.elanyoung.canal.core.handler.EntryHandler;
import io.github.elanyoung.canal.core.handler.RowDataHandler;

import java.util.List;

/**
 * 同步消息处理器
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
public class SyncMessageHandlerImpl extends AbstractMessageHandler {

	public SyncMessageHandlerImpl(List<? extends EntryHandler<?>> entryHandlers,
			RowDataHandler<CanalEntry.RowData> rowDataHandler) {
		super(entryHandlers, rowDataHandler);
	}

	@Override
	public void handleMessage(Message message) {
		super.handleMessage(message);
	}

}
