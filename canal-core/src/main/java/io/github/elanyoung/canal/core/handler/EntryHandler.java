package io.github.elanyoung.canal.core.handler;

/**
 * 实体对象处理器
 *
 * @author william@StarImmortal
 * @since 2025/02/25
 */
public interface EntryHandler<T> {

	/**
	 * 新增
	 * @param t 实体对象
	 */
	void insert(T t);

	/**
	 * 修改
	 * @param before 修改前实体对象
	 * @param after 修改后实体对象
	 */
	void update(T before, T after);

	/**
	 * 删除
	 * @param t 实体对象
	 */
	void delete(T t);

}
