package com.xuaxi.framework.core.enums;

public enum RedisDataBaseEnum {

	SESSION(0),RBAC(1);
	
	private int databaseId;

	public int getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(int databaseId) {
		this.databaseId = databaseId;
	}

	RedisDataBaseEnum(int databaseId) {
		this.databaseId = databaseId;
	}
}
