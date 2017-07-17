package com.xuaxi.framework.core.entity;

import java.io.Serializable;

public class KeyValue<K,V> implements Serializable{
	
	private static final long serialVersionUID = -5349656959958018639L;

	private K key;
	
	private V value;
	
	public KeyValue(){
		
	}
	
	public KeyValue(K key,V value){
		this.key=key;
		this.value=value;
	}
	
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
