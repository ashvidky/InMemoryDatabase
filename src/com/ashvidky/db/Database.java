package com.ashvidky.db;
import java.util.HashMap;
import java.util.LinkedList;


/*
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class Database <K, V>{

	
	private HashMap<K, V> db = new HashMap<>();
	private HashMap<V, Integer> valueCounter = new HashMap<>();
	
	private LinkedList<Transaction<K, V>> transactionLog = new LinkedList<>(); 
	
	public void set(K key, V value) {
		
		if (!transactionLog.isEmpty()) {
			
			Transaction<K, V> lastTr = transactionLog.getLast();
			lastTr.set(key, value);
			
			
//			V v = null;
//			for (int i = transactionLog.size() - 1; i >=0; i-- ) {
//				Transaction<K, V> tr = transactionLog.get(i);
//				v = tr.get(key);
//				if (v != null)
//					break;
//			}
			
		}
		else {
			
			V prev = db.put(key, value);
			
			if (prev != null) {
				if (valueCounter.containsKey(prev)) {
					
					Integer counter = valueCounter.get(prev);
					if (counter == 1)
						valueCounter.remove(prev);
					else
						valueCounter.put(prev, counter - 1);
				}
			}
				
				
			valueCounter.put(value, valueCounter.getOrDefault(value, 0) + 1);
		}
				
	}
	
	public V get(K key) {
		
		V v = null;
		if (!transactionLog.isEmpty()) {
			
			Transaction<K, V> lastTr = transactionLog.getLast();
			v = lastTr.get(key);
		}
		
		if (v != null)
			return v;
		else
			return db.get(key);
	}
	
	public void delete(K key) {
		
		if (!transactionLog.isEmpty()) {
			
			Transaction<K, V> lastTr = transactionLog.getLast();
			//lastTr.delete(key);
		}
		else {
			
			V value = db.remove(key);
			
			if (valueCounter.containsKey(value)) {
				
				Integer counter = valueCounter.get(value);
				if (counter == 1)
					valueCounter.remove(value);
				else
					valueCounter.put(value, counter - 1);
			}
		}
		
	}
	
	public int count(V value) {
		
		return valueCounter.getOrDefault(value, 0);
		
	}
	
	public void begin() {
		
		transactionLog.add(new Transaction<>());
	}
	
	public void rollback() {
		
		transactionLog.removeLast();
	}
	
	public void commit() {
		
		transactionLog.stream().forEach(t -> {
			
			t.getEntries().stream().forEach(e ->{
				db.put(e.getKey(), e.getValue());
			});
		});
		
		transactionLog.clear();
	}
	
	
	public static void main(String[] args) {
		
		Database<String, Integer> db = new Database<String, Integer>();
		
		db.set("a", 10);
		db.set("a", 20);
		db.set("b", 30);
		
		System.out.println("db.get(a):" + db.get("a"));
		
		System.out.println("db.count(0):" + db.count(0));
		System.out.println("db.count(10):" + db.count(10));
		System.out.println("db.count(20):" + db.count(20));
		System.out.println("db.count(30):" + db.count(30));
		
		db.delete("a");
		
		System.out.println("db.count(0):" + db.count(0));
		System.out.println("db.count(10):" + db.count(10));
		System.out.println("db.count(20):" + db.count(20));
		System.out.println("db.count(30):" + db.count(30));
		
		db.begin();
		db.set("b", 40);
		db.set("c", 50);
		System.out.println("db.get(b):" + db.get("b"));
		System.out.println("db.get(c):" + db.get("c"));
		
		db.commit();
		System.out.println("db.get(b):" + db.get("b"));
		System.out.println("db.get(c):" + db.get("c"));
	}
}
