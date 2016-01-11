package com.myproject.DAOimpl;

import com.myproject.Model.Transport;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.myproject.DAO.LabelDAO;
import com.myproject.Model.Factory;
import com.myproject.Model.Store;
import com.myproject.Model.Tags;
import com.myproject.hibernate.HibernateSessionFactory;

public  class LabelDAOImpl implements LabelDAO {

	public Query  queryfromdb(String table,Double id) {
		// TODO Auto-generated method stub
		SessionFactory sf = HibernateSessionFactory.getSessionFactory();
    	Session s =sf.openSession();
		s.beginTransaction();		
		Query query = null;
		switch (table) {
		case "factory":
			query  =  s.createSQLQuery("select * from factory where tagid = ?").addEntity(Factory.class);
				break;
		case "store":
			 query  =  s.createSQLQuery("select * from store where tagid = ?").addEntity(Store.class);
			break;
		case "tag":
			 query  =  s.createSQLQuery("select * from tags where tagsid = ?").addEntity(Tags.class);
			break;
		case "transport":
			 query  =  s.createSQLQuery("select * from transport where tagid = ?").addEntity(Transport.class);
			break;
		default:
			break;
		}
		query.setParameter(0,id);
//		Query query  =  s.createSQLQuery("select * from "+table+"where tagid = "+"tagid").addEntity(table.getClass());
		s.getTransaction().commit();
		return query;
	}
	public Query queryAll(String table){
		SessionFactory sf = HibernateSessionFactory.getSessionFactory();
    	Session s =sf.openSession();
		s.beginTransaction();		
		Query query = null;
		switch (table) {
		case "factory":
			query  =  s.createSQLQuery("select * from factory ").addEntity(Factory.class);
				break;
		case "store":
			 query  =  s.createSQLQuery("select * from store ").addEntity(Store.class);
			break;
		case "tag":
			 query  =  s.createSQLQuery("select * from tags").addEntity(Tags.class);
			break;
		case "transport":
			 query  =  s.createSQLQuery("select * from transport ").addEntity(Transport.class);
			break;
		default:
			break;
		}
		s.getTransaction().commit();
		return query;
	}
}