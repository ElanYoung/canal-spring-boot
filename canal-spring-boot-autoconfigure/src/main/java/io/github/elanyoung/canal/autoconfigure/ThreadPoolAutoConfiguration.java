package io.github.elanyoung.canal.autoconfigure;

import com.alibaba.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author william@StarImmortal
 * @since 2025/02/25
 */
@Configuration
@ConditionalOnProperty(value = CanalProperties.CANAL_ASYNC, havingValue = "true", matchIfMissing = true)
public class ThreadPoolAutoConfiguration {

	private static final String THREAD_NAME_FORMAT = "canal-execute-thread-%d";

	@Bean(destroyMethod = "shutdown")
	public ExecutorService executorService() {
		return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 200, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1024), new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_FORMAT).build(),
				new ThreadPoolExecutor.AbortPolicy());
	}

}
