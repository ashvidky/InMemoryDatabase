package com.ashvidky.db;
import java.util.ArrayList;

public class NewTransaction<K, V> {

	ArrayList<Operation<K, V>> transactionLog = new ArrayList<>();
	
	Database<K, V> db;
	
	public NewTransaction(Database<K, V> db) {
		this.db = db;
	}
	
	public void add(Operation<K, V> o) {
		
		transactionLog.add(o);
	}
	
	public void rollback() {
		transactionLog.clear();
	}
	
	public void commit() {
		
		transactionLog.forEach(o -> {
			
			switch (o.getOp()) {
			
			case SET: 
				break;
				
			case DELETE: 
				break;
			}
			
		});
	}
}
