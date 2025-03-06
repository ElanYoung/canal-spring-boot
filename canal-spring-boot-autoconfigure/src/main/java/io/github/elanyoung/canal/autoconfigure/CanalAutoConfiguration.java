package io.github.elanyoung.canal.autoconfigure;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import io.github.elanyoung.canal.client.DefaultCanalClient;
import io.github.elanyoung.canal.core.factory.EntryColumnModelFactory;
import io.github.elanyoung.canal.core.handler.EntryHandler;
import io.github.elanyoung.canal.core.handler.MessageHandler;
import io.github.elanyoung.canal.core.handler.RowDataHandler;
import io.github.elanyoung.canal.core.handler.impl.AsyncMessageHandlerImpl;
import io.github.elanyoung.canal.core.handler.impl.RowDataHandlerImpl;
import io.github.elanyoung.canal.core.handler.impl.SyncMessageHandlerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Canal自动配置类
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@Import({ ThreadPoolAutoConfiguration.class })
@EnableConfigurationProperties(CanalProperties.class)
public class CanalAutoConfiguration {

	private final CanalProperties canalProperties;

	private final ExecutorService executorService;

	@Bean
	@Primary
	@ConditionalOnProperty(value = CanalProperties.CANAL_ASYNC, havingValue = "true", matchIfMissing = true)
	public MessageHandler<Message> asyncMessageHandler(List<EntryHandler<?>> entryHandlers,
			RowDataHandler<CanalEntry.RowData> rowDataHandler) {
		return new AsyncMessageHandlerImpl(entryHandlers, rowDataHandler, executorService);
	}

	@Bean
	@ConditionalOnProperty(value = CanalProperties.CANAL_ASYNC, havingValue = "false")
	public MessageHandler<Message> syncMessageHandler(List<EntryHandler<?>> entryHandlers,
			RowDataHandler<CanalEntry.RowData> rowDataHandler) {
		return new SyncMessageHandlerImpl(entryHandlers, rowDataHandler);
	}

	@Bean
	public RowDataHandler<CanalEntry.RowData> rowDataHandler() {
		return new RowDataHandlerImpl(new EntryColumnModelFactory());
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public DefaultCanalClient defaultCanalClient(MessageHandler<Message> messageHandler) {
		DefaultCanalClient defaultCanalClient = new DefaultCanalClient(executorService, messageHandler);
		defaultCanalClient.setIp(canalProperties.getServer().getIp());
		defaultCanalClient.setPort(canalProperties.getServer().getPort());
		defaultCanalClient.setUsername(canalProperties.getServer().getUsername());
		defaultCanalClient.setPassword(canalProperties.getServer().getPassword());
		defaultCanalClient.setDestination(canalProperties.getDestination());
		defaultCanalClient.setFilter(canalProperties.getFilter());
		defaultCanalClient.setBatchSize(canalProperties.getBatchSize());
		defaultCanalClient.setTimeout(canalProperties.getTimeout());
		defaultCanalClient.setUnit(canalProperties.getUnit());
		return defaultCanalClient;
	}

}