package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-10 08:23:52
 */
@Data
@TableName("test")
public class TestEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * name1
	 */
	private String name;
	/**
	 * name2
	 */
	private String type;
	/**
	 * name3
	 */
	private Long relationUserId;
	/**
	 * name4
	 */
	private String uuid;
	/**
	 * name5
	 */
	private String url;
	/**
	 * name6
	 */
	private String city;
	/**
	 * c
	 */
	private Date createTime;
	/**
	 * u
	 */
	private Date updateTime;

}
