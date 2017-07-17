package com.xuaxi.framework.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

/**
 * @author xiazhijian
 * @date 2016年10月10日 上午10:49:14
 * @Description Entity抽象
 * @FileName AbstractEntity.java
 * @param <PK>
 */
public abstract class AbstractEntity<PK> implements Serializable {

	private static final long serialVersionUID = -2340789090623239757L;

	/**
	 * 主键
	 */
	@TableConfig(colName="t.id",sort=true,operators=QueryOperatorEnum.eq)
	protected PK id;

	/**
	 * 删除标记true为已删除false为正常对应数据库数据为1和0
	 */
	@TableConfig(colName="t.deleteTag")
	protected Boolean deleteTag;

	/**
	 * 创建人
	 */
	@TableConfig(colName="t.createBy")
	protected PK createBy;

	/**
	 * 创建人姓名
	 */
	@TableConfig(colName="t.createByName")
	protected String createByName;
	

	/**
	 * 创建时间
	 */
	@TableConfig(colName="t.createTime",sort=true)
	protected Timestamp createTime;

	/**
	 * 最后修改人
	 */
	@TableConfig(colName="t.modifyBy")
	protected PK modifyBy;

	/**
	 * 最后修改人姓名
	 */
	@TableConfig(colName="t.modifyByName")
	protected String modifyByName;
	
	/**
	 * 最后修改时间
	 */
	@TableConfig(colName="t.modifyTime",sort=true)
	protected Timestamp modifyTime;
	
	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}
	
	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getModifyByName() {
		return modifyByName;
	}

	public void setModifyByName(String modifyByName) {
		this.modifyByName = modifyByName;
	}

	public Boolean getDeleteTag() {
		return deleteTag;
	}

	public void setDeleteTag(Boolean deleteTag) {
		this.deleteTag = deleteTag;
	}

	public PK getCreateBy() {
		return createBy;
	}

	public void setCreateBy(PK createBy) {
		this.createBy = createBy;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public PK getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(PK modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
}
