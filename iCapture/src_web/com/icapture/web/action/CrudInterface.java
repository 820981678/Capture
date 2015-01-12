package com.icapture.web.action;

import com.connection.db.DBException;


public interface CrudInterface {
	
	boolean add(CrudEntity crud) throws DBException;
	
	boolean update(CrudEntity crud) throws DBException;
	
	boolean delete(CrudEntity crud) throws DBException;
	
}
