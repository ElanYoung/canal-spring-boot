package io.github.elanyoung.canal.core.handler.impl;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import io.github.elanyoung.canal.core.handler.AbstractMessageHandler;
import io.github.elanyoung.canal.core.handler.EntryHandler;
import io.github.elanyoung.canal.core.handler.RowDataHandler;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * 异步消息处理器
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
public class AsyncMessageHandlerImpl extends AbstractMessageHandler {

	private final ExecutorService executorService;

	public AsyncMessageHandlerImpl(List<? extends EntryHandler<?>> entryHandlers,
			RowDataHandler<CanalEntry.RowData> rowDataHandler, ExecutorService executorService) {
		super(entryHandlers, rowDataHandler);
		this.executorService = executorService;
	}

	@Override
	public void handleMessage(Message message) {
		CompletableFuture.runAsync(() -> super.handleMessage(message), executorService);
	}

}
