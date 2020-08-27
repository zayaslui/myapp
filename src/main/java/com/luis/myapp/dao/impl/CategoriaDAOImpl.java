/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luis.myapp.dao.impl;

import com.luis.myapp.dao.CategoriaDAO;
import com.luis.myapp.dao.SQLClosable;
import com.luis.myapp.model.Categoria;
import com.luis.myapp.utilities.BEAN_CRUD;
import com.luis.myapp.utilities.BEAN_PAGINATION;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author luis
 */
public class CategoriaDAOImpl implements CategoriaDAO {

    private static final Logger LOG = Logger.getLogger(CategoriaDAOImpl.class.getName());

    private final DataSource pool;

    public CategoriaDAOImpl(DataSource pool) {
        this.pool = pool;
    }

    @Override
    public BEAN_PAGINATION getPagination(HashMap<String, Object> parameters, Connection conn) throws SQLException {
        BEAN_PAGINATION bean_pagination = new BEAN_PAGINATION();
        List<Categoria> list = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;

        try {
            pst = conn.prepareStatement("select count(idcategoria) from categoria where"
                    + " nombre like concat('%',?,'%')");
            pst.setString(0, String.valueOf(parameters.get("FILTER")));
            
            LOG.info(pst.toString());
            
            rs = pst.executeQuery();
            while (rs.next()) {
                bean_pagination.setCOUNT_FILTER(rs.getInt("COUNT"));
                if (rs.getInt("COUNT") > 0) {
                    pst = conn.prepareStatement("SELECT *FROM CATEGORIA WHERE NOMBRE LIKE CONCAT('%',?,'%') "
                            + " ORDER BY " + String.valueOf(parameters.get("SQL_ORDER_BY")) + " " + String.valueOf(parameters.get("SQL_LIMIT")));
                    pst.setString(1, String.valueOf(parameters.get("FILTER")));
                    
                    LOG.info(pst.toString());
                    
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        Categoria categoria = new Categoria();
                        categoria.setIdcategoria(rs.getInt("IDCATEGORIA"));
                        categoria.setNombre(rs.getString("NOMBRE"));
                        list.add(categoria);
                    }
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {

        }
        return bean_pagination;
    }

    @Override
    public BEAN_PAGINATION getPagination(HashMap<String, Object> parameters) throws SQLException {
        BEAN_PAGINATION bean_pagination = null;

        try (Connection conn = this.pool.getConnection()) {
            bean_pagination = getPagination(parameters, conn);
        } catch (SQLException e) {
            throw e;
        }
        return bean_pagination;
    }

    @Override
    public BEAN_CRUD add(Categoria obj, HashMap<String, Object> parameters) throws SQLException {
        BEAN_CRUD bean_crud = new BEAN_CRUD();

        PreparedStatement pst;
        ResultSet rs;
        try (Connection conn = this.pool.getConnection();
                SQLClosable finish = conn::rollback; //evitar mandar si se va la luz
                ) {
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("SELECT COUNT(CATEGORIAID) AS COUNT FROM CATEGORIA WHERE NOMBRE = ?");
            pst.setString(1, obj.getNombre());
            LOG.info(pst.toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt("COUNT") == 0) {
                    //realizamos la transaccion
                    pst = conn.prepareStatement("INSERT INTO CATEGORIA(NOMBRE) VALUES(?)");
                    pst.setString(1, obj.getNombre());
                    LOG.info(pst.toString());
                    pst.executeUpdate();
                    conn.commit();
                    bean_crud.setMESSAGE_SERVER("ok");
                    bean_crud.setBEAN_PAGINATION(getPagination(parameters, conn));
                } else {
                    //rechazamos el registro
                    bean_crud.setMESSAGE_SERVER("No se registro, ya existe!");
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            throw e;
        }
        return bean_crud;
    }

    @Override
    public BEAN_CRUD update(Categoria obj, HashMap<String, Object> parameters) throws SQLException {
        BEAN_CRUD bean_crud = new BEAN_CRUD();

        PreparedStatement pst;
        ResultSet rs;
        try (Connection conn = this.pool.getConnection();
                SQLClosable finish = conn::rollback; //evitar mandar si se va la luz
                ) {
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("SELECT COUNT(CATEGORIAID) AS COUNT FROM CATEGORIA WHERE NOMBRE = ? and IDCATEGORIA != ?");
            pst.setString(1, obj.getNombre());
            pst.setInt(2, obj.getIdcategoria());
            LOG.info(pst.toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt("COUNT") == 0) {
                    //realizamos la transaccion
                    pst = conn.prepareStatement("UPDATE CATEGORIA SET NOMBRE = ? WHERE IDCATEGORIA = ?");
                    pst.setString(1, obj.getNombre());
                    pst.setInt(2, obj.getIdcategoria());
                    LOG.info(pst.toString());
                    pst.executeUpdate();
                    conn.commit();
                    bean_crud.setMESSAGE_SERVER("ok");
                    bean_crud.setBEAN_PAGINATION(getPagination(parameters, conn));
                } else {
                    //rechazamos el registro
                    bean_crud.setMESSAGE_SERVER("No se modificó!");
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            throw e;
        }
        return bean_crud;
    }

    @Override
    public BEAN_CRUD delete(Integer id, HashMap<String, Object> parameters) throws SQLException {
        BEAN_CRUD bean_crud = new BEAN_CRUD();
        try (Connection conn = this.pool.getConnection();
                SQLClosable finish = conn::rollback;) {
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement("DELETE FROM CATEGORIA WHERE IDCATEGORIA = ?");
            pst.executeUpdate();
            conn.commit();
            bean_crud.setMESSAGE_SERVER("ok");
            bean_crud.setBEAN_PAGINATION(getPagination(parameters, conn));
        } catch (SQLException e) {
            throw e;
        }
        return bean_crud;

    }

}
