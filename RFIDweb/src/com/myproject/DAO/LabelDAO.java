package com.myproject.DAO;

import org.hibernate.Query;


public interface LabelDAO {
	public Query queryfromdb(String table,int id);
	public Query queryAll(String table);
}
