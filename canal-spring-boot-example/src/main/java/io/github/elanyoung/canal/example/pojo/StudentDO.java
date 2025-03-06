package io.github.elanyoung.canal.example.pojo;

import lombok.Data;

/**
 * @author william@StarImmortal
 * @since 2025/2/2
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
