package io.github.elanyoung.canal.autoconfigure;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * Canal配置属性
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
@Data
@ConfigurationProperties(prefix = CanalProperties.CANAL_PREFIX)
public class CanalProperties {

	/**
	 * 属性前缀
	 */
	public static final String CANAL_PREFIX = "canal";

	/**
	 * 是否异步处理消息
	 */
	public static final String CANAL_ASYNC = CANAL_PREFIX + "async";

	/**
	 * 服务器配置
	 */
	private Server server;

	/**
	 * 目标
	 */
	private String destination;

	/**
	 * 过滤规则
	 */
	private String filter = StringUtils.EMPTY;

	/**
	 * 批量获取数据大小
	 */
	private Integer batchSize = 1;

	/**
	 * 超时时间
	 */
	private Long timeout = 1L;

	/**
	 * 超时时间单位
	 */
	private TimeUnit unit = TimeUnit.SECONDS;

	/**
	 * 是否异步处理消息
	 */
	private Boolean async = true;

	@Data
	public static class Server {

		/**
		 * Canal服务器IP
		 */
		private String ip = "127.0.0.1";

		/**
		 * Canal服务器端口
		 */
		private Integer port = 11111;

		/**
		 * Canal服务器用户名
		 */
		private String username;

		/**
		 * Canal服务器密码
		 */
		private String password;

	}

}