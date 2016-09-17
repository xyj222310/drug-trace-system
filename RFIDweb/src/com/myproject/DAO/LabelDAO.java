package com.myproject.DAO;

import org.hibernate.Query;


public interface LabelDAO {
	public Query queryfromdb(String table,Double tagid);
	public Query queryAll(String table);
}
