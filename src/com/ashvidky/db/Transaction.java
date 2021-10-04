package com.ashvidky.db;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Transaction<K, V> {

	private HashMap<K, V> db = new HashMap<>();
	
	public Set<Entry<K, V>> getEntries(){
		
		return db.entrySet();
	}
	
	public V get(K key) {
		return db.get(key);
	}
	
	public void set(K key, V value) {
		db.put(key, value);
	}
}
