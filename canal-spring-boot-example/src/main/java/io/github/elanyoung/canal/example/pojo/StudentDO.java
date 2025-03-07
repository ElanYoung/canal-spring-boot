package io.github.elanyoung.canal.example.pojo;

import lombok.Data;

/**
 * 学生实体
 *
 * @author william@StarImmortal
 * @since 2025/02/02
 */
@Data
public class StudentDO {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 真实姓名
	 */
	private String realName;

}
