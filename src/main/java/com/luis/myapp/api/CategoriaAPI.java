/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luis.myapp.api;

import com.google.gson.Gson;
import com.luis.myapp.dao.CategoriaDAO;
import com.luis.myapp.dao.impl.CategoriaDAOImpl;
import com.luis.myapp.model.Categoria;
import com.luis.myapp.utilities.BEAN_CRUD;
import com.luis.myapp.utilities.BEAN_PAGINATION;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author luis
 */
@WebServlet(name = "CategoriaAPI", urlPatterns = {"/categorias"})
public class CategoriaAPI extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CategoriaAPI.class.getName());

    @Resource(name = "jdbc/dbmyapp")
    private static DataSource pool;

    private Gson jsonParse;
    private HashMap<String, Object> parameters;
    private CategoriaDAO categoriaDAO;
    private String jsonREsponse;
    private String action;

    @Override
    public void init() throws ServletException {
        this.jsonParse = new Gson();
        this.parameters = new HashMap<>();
        this.categoriaDAO = new CategoriaDAOImpl(this.pool);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
            try {
                if ((this.pool.getConnection() != null)) {
                    //LOG.info("Conexion Exitosa");
                } else {
                    //LOG.info("Conexion Fallida");
                }            
            } catch (SQLException e) {
                Logger.getLogger(CategoriaAPI.class.getName()).log(Level.SEVERE, null, e);
            }

        try {
            this.action = request.getParameter("action") == null ? "" : request.getParameter("action");
            //LOG.info("action=" + this.action);
            switch(this.action){
                case "paginarCategoria":
                    BEAN_PAGINATION beanPagination = this.categoriaDAO.getPagination(getParameters(request));
                    BEAN_CRUD beanCrud = new BEAN_CRUD(beanPagination);
                    processCategoria(beanCrud, response);
                    //processCategoria(new BEAN_CRUD(this.categoriaDAO.getPagination(getParameters(request))), response);
                    break;
                case "addCategoria":
                    BEAN_CRUD bean_crud1 = this.categoriaDAO.add(getCategoria(request), getParameters(request));
                    processCategoria(bean_crud1, response);
                    //processCategoria(this.categoriaDAO.add(getCategoria(request), getParameters(request)), response);
                    break;
                case "updateCategoria":
                    BEAN_CRUD bean_crud2 = this.categoriaDAO.update(getCategoria(request), getParameters(request));
                    processCategoria(bean_crud2, response);                    
                    break;
                case "deleteCategoria":
                    BEAN_CRUD bean_crud3 = this.categoriaDAO.delete(Integer.parseInt(request.getParameter("txtIdCategoriaER")), getParameters(request));
                    processCategoria(bean_crud3, response);                    
                    break;
                default:
                    //LOG.info("ningun caso :|");
                    request.getRequestDispatcher("/jsp_app/mantenimiento/categoria.jsp").forward(request, response);
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //private void processCategoria(BEAN_CRUD beanCrud,HttpServletRequest request, HttpServletResponse response){
    private void processCategoria(BEAN_CRUD beanCrud, HttpServletResponse response){
        try {
            //LOG.info(beanCrud.toString());
            this.jsonREsponse = this.jsonParse.toJson(beanCrud);
            //LOG.info(this.jsonREsponse);
            response.setContentType("application/json");
            response.getWriter().write(this.jsonREsponse);
        } catch (IOException ex) {
            Logger.getLogger(CategoriaAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private HashMap< String, Object> getParameters(HttpServletRequest request){
        this.parameters.clear();
        this.parameters.put("FILTER", request.getParameter("txtNombreCategoria"));
        this.parameters.put("SQL_ORDER_BY","NOMBRE ASC");
        this.parameters.put("SQL_LIMIT"," LIMIT " 
                                +request.getParameter("sizePageCategoria") 
                                + " OFFSET "  
                                +  (Integer.parseInt(request.getParameter("numberPageCategoria")) - 1) 
                                * 
                                Integer.parseInt(request.getParameter("sizePageCategoria"))
                            );
        
        
        return parameters;
    }
    
    private Categoria getCategoria(HttpServletRequest request){
        Categoria categoria = new Categoria();
        if(request.getParameter("action").equals("updateCategoria")){
            categoria.setIdcategoria(Integer.parseInt("txtIdCategoriaER"));
        }
        categoria.setNombre(request.getParameter("txtNombreCategoriaER"));
        return categoria;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
