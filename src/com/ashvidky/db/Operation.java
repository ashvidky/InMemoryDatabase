package com.ashvidky.db;

public class Operation <K,V>{

	private Op op;
	private K key;
	private V value;
	
	public Operation(Op op, K key, V value) {
		this.op = op;
		this.key = key;
		this.value = value;
	}
	
	public Op getOp() {
		return op;
	}
	
	public K getKey() {
		return key;
		
	}
	
	public V getValue() {
		return value;
	}
}
