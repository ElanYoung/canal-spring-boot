package io.github.elanyoung.canal.core.handler;

/**
 * 消息处理器
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
public interface MessageHandler<T> {

	/**
	 * 处理消息
	 * @param message 消息
	 */
	void handleMessage(T message);

}
