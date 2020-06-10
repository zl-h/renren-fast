package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.TestEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-10 08:23:52
 */
@Mapper
public interface TestDao extends BaseMapper<TestEntity> {
	
}
