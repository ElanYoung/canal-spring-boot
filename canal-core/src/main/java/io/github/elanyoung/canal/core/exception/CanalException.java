package io.github.elanyoung.canal.core.exception;

/**
 * Canal异常类
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
public class CanalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CanalException(String message) {
		super(message);
	}

	public CanalException(String message, Throwable cause) {
		super(message, cause);
	}

	public CanalException(Throwable cause) {
		super(cause);
	}

}