package io.github.elanyoung.canal.core.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.protobuf.InvalidProtocolBufferException;
import io.github.elanyoung.canal.core.annotation.CanalTable;
import io.github.elanyoung.canal.core.exception.CanalException;
import io.github.elanyoung.canal.core.util.CanalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author william@StarImmortal
 * @since 2025/02/25
 */
@Slf4j
public abstract class AbstractMessageHandler implements MessageHandler<Message> {

	private final Map<String, EntryHandler<?>> tableHandlerMap;

	private final RowDataHandler<CanalEntry.RowData> rowDataHandler;

	public AbstractMessageHandler(List<? extends EntryHandler<?>> entryHandlers,
			RowDataHandler<CanalEntry.RowData> rowDataHandler) {
		this.tableHandlerMap = entryHandlers.stream()
			.collect(Collectors.toMap(this::getTableName, Function.identity()));
		this.rowDataHandler = rowDataHandler;
	}

	@Override
	public void handleMessage(Message message) {
		message.getEntries()
			.stream()
			.filter(entry -> entry.getEntryType().equals(CanalEntry.EntryType.ROWDATA))
			.forEach(entry -> {
				try {
					String tableName = entry.getHeader().getTableName();
					EntryHandler<?> entryHandler = tableHandlerMap.get(tableName);
					CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
					List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
					CanalEntry.EventType eventType = rowChange.getEventType();
					rowDataList.forEach(rowData -> this.rowDataHandler.handleRowData(rowData, entryHandler, eventType));
				}
				catch (InvalidProtocolBufferException e) {
					throw new CanalException("解析 Canal 消息失败", e);
				}
			});
	}

	/**
	 * 获取实体处理器对应表名
	 * @param entryHandler 实体对象处理器
	 * @return 表名
	 * @throws IllegalArgumentException 当实体处理器未配置任何表名注解时抛出
	 */
	@SuppressWarnings("unchecked")
	private String getTableName(EntryHandler<?> entryHandler) {
		Class<? extends EntryHandler<?>> handlerClass = (Class<? extends EntryHandler<?>>) entryHandler.getClass();
		// 检查 @CanalTable 注解
		CanalTable canalTable = handlerClass.getAnnotation(CanalTable.class);
		if (Objects.nonNull(canalTable) && StringUtils.hasText(canalTable.value())) {
			return canalTable.value();
		}

		// 从缓存中获取实体类
		Class<?> tableClass = CanalUtil.getTableClass(entryHandler);

		// JPA：检查 @Table 注解
		Table table = tableClass.getAnnotation(Table.class);
		if (Objects.nonNull(table) && StringUtils.hasText(table.name())) {
			return table.name();
		}

		// MyBatis Plus：检查 @TableName 注解
		TableName tableName = tableClass.getAnnotation(TableName.class);
		if (Objects.nonNull(tableName) && StringUtils.hasText(tableName.value())) {
			return tableName.value();
		}

		throw new CanalException(
				"Please use @CanalTable on the entity handler, @Table or @TableName annotation on the entity class");
	}

}
