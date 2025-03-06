package io.github.elanyoung.canal.core.factory;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.elanyoung.canal.core.handler.EntryHandler;
import io.github.elanyoung.canal.core.util.CanalUtil;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Canal数据列模型工厂实现类
 *
 * @author william@StarImmortal
 * @since 2025/03/05
 */
public class EntryColumnModelFactory extends AbstractModelFactory<List<CanalEntry.Column>> {

	@Override
	<R> R newInstance(Class<R> c, List<CanalEntry.Column> columns) {
		// 创建对象实例
		R object = ReflectUtil.newInstance(c);
		// 获取字段名称映射
		Map<String, String> fieldNameMap = CanalUtil.getFieldName(c);
		// 遍历列集合，设置属性值
		columns.forEach(column -> {
			String fieldName = fieldNameMap.get(column.getName());
			if (StringUtils.hasText(fieldName)) {
				ReflectUtil.setFieldValue(object, fieldName, column.getValue());
			}
		});
		return object;
	}

	@Override
	public <R> R newInstance(EntryHandler<R> entryHandler, List<CanalEntry.Column> columns) {
		Class<R> tableClass = CanalUtil.getTableClass(entryHandler);
		return newInstance(tableClass, columns);
	}

}
