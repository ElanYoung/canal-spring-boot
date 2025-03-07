package io.github.elanyoung.canal.example.handler;

import io.github.elanyoung.canal.core.annotation.CanalTable;
import io.github.elanyoung.canal.core.handler.EntryHandler;
import io.github.elanyoung.canal.example.pojo.StudentDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 学生处理器
 *
 * @author william@StarImmortal
 * @since 2025/03/05
 */
@Slf4j
@Component
@CanalTable(value = "student")
public class StudentHandler implements EntryHandler<StudentDO> {

	@Override
	public void insert(StudentDO student) {
		log.info("insert message {}", student);
	}

	@Override
	public void update(StudentDO before, StudentDO after) {
		log.info("update before {} ", before);
		log.info("update after {}", after);
	}

	@Override
	public void delete(StudentDO student) {
		log.info("delete {}", student);
	}

}
