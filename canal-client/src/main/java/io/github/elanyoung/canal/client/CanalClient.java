package io.github.elanyoung.canal.client;

/**
 * Canal Client
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
public interface CanalClient {

	/**
	 * 启动
	 */
	void start();

	/**
	 * 停止
	 */
	void stop();

	/**
	 * 处理数据
	 */
	void process();

}
