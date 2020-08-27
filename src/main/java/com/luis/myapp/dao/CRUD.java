/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luis.myapp.dao;

import com.luis.myapp.utilities.BEAN_CRUD;
import com.luis.myapp.utilities.BEAN_PAGINATION;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author luis
 */
public interface CRUD<T> {

    BEAN_PAGINATION getPagination( HashMap<String, Object> parameters, Connection conn) throws SQLException;

    BEAN_PAGINATION getPagination( HashMap<String, Object> parameters) throws SQLException;
    
    BEAN_CRUD add(T obj, HashMap<String, Object> parameters) throws SQLException;
    
    BEAN_CRUD update(T obj, HashMap<String, Object> parameters) throws SQLException;

    BEAN_CRUD delete(Integer id, HashMap<String, Object> parameters) throws SQLException;    
}
