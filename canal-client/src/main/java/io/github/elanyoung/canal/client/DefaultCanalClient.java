package io.github.elanyoung.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import io.github.elanyoung.canal.core.handler.MessageHandler;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

/**
 * 默认 Canal 客户端
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
@Getter
@Setter
public class DefaultCanalClient extends AbstractCanalClient {

	/**
	 * Canal服务器IP
	 */
	private String ip;

	/**
	 * Canal服务器端口
	 */
	private Integer port;

	/**
	 * Canal服务器用户名
	 */
	private String username;

	/**
	 * Canal服务器密码
	 */
	private String password;

	/**
	 * 目标
	 */
	private String destination;

	public DefaultCanalClient(ExecutorService executorService, MessageHandler<Message> messageHandler) {
		super(executorService, messageHandler);
	}

	@Override
	protected CanalConnector canalConnector() {
		return CanalConnectors.newSingleConnector(new InetSocketAddress(ip, port), destination, username, password);
	}

}
