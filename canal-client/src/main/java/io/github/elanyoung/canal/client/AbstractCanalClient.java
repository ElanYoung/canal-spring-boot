package io.github.elanyoung.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import io.github.elanyoung.canal.core.handler.MessageHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author william@StarImmortal
 * @since 2025/02/25
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractCanalClient implements CanalClient {

	/**
	 * 运行标志
	 */
	protected volatile boolean flag;

	/**
	 * 获取连接器
	 * @return {@link CanalConnector}
	 */
	protected abstract CanalConnector canalConnector();

	/**
	 * 线程池
	 */
	private final ExecutorService executorService;

	/**
	 * 消息处理器
	 */
	private final MessageHandler<Message> messageHandler;

	/**
	 * 过滤规则
	 */
	protected String filter = StringUtils.EMPTY;

	/**
	 * 批量获取数据大小
	 */
	protected Integer batchSize = 1;

	/**
	 * 超时时间
	 */
	protected Long timeout = 1L;

	/**
	 * 超时时间单位
	 */
	protected TimeUnit unit = TimeUnit.SECONDS;

	protected AbstractCanalClient(ExecutorService executorService, MessageHandler<Message> messageHandler) {
		this.executorService = executorService;
		this.messageHandler = messageHandler;
	}

	@Override
	public void start() {
		log.info("Starting Canal Client...");
		flag = true;
		executorService.submit(this::process);
	}

	@Override
	public void stop() {
		log.info("Stopping Canal Client...");
		flag = false;
		if (executorService != null) {
			executorService.shutdown();
		}
	}

	@Override
	public void process() {
		CanalConnector canalConnector = canalConnector();
		while (flag) {
			try {
				canalConnector.connect();
				canalConnector.subscribe(filter);
				while (flag) {
					Message message = canalConnector.getWithoutAck(batchSize, timeout, unit);
					long batchId = message.getId();
					if (message.getId() != -1 && !message.getEntries().isEmpty()) {
						messageHandler.handleMessage(message);
					}
					canalConnector.ack(batchId);
				}
			}
			catch (Exception e) {
				log.error("Canal Client running abnormally", e);
			}
			finally {
				canalConnector.disconnect();
			}
		}
	}

}
