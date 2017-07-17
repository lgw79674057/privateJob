package com.xuaxi.framework.core.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author xiazhijian
 * @date 2016年10月10日 上午10:48:52
 * @Description domain抽象
 * @FileName AbstractDomain.java
 * @param <PK>
 */
public abstract class AbstractDomain<PK> implements Serializable{
	
	private static final long serialVersionUID = 6746059345781288650L;
	/**
	 * 主键
	 */
	protected PK id;
	/**
	 * 删除标记true为已删除false为正常对应数据库数据为1和0
	 */
	protected Boolean deleteTag;
	/**
	 * 创建人
	 */
	protected PK createBy;
	
	/**
	 * 创建人姓名
	 */
	protected String createByName;
	
	/**
	 * 创建时间
	 */
	protected Timestamp createTime;
	/**
	 * 最后修改人
	 */
	protected PK modifyBy;
	
	/**
	 * 最后修改人姓名
	 */
	protected String modifyByName;
	
	/**
	 * 最后修改时间
	 */
	protected Timestamp modifyTime;
	public PK getId() {
		return id;
	}
	public void setId(PK id) {
		this.id = id;
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
}
