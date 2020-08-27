
package com.luis.myapp.dao;

import java.sql.SQLException;


public interface SQLClosable extends AutoCloseable{

    @Override
    public void close() throws SQLException;
    
}
