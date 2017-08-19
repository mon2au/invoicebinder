/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.server.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.server.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.item.Product;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ProductInfo;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mon2
 */
@Repository
public class ProductDAO extends BaseDAO<Product,Long>{
    
    public ProductDAO() {
        super(Product.class);
    }
    @SuppressWarnings("unchecked")
    public ArrayList<Product> getAllProductsInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String productNameFilter) {
        ArrayList<Product> productList = null;
        String sql;
        
        try {
            sql = " from Product p where p.id >= :start ";
            
            if (!productNameFilter.isEmpty()) {
                sql += "and p.name LIKE '%" + productNameFilter + "%' ";
            }
            sql += "order by p.name ";
            
            productList = (ArrayList<Product>) sf.getCurrentSession()
                    .createQuery(sql)
                    .setParameter("start", (long) start)
                    .setMaxResults(length).list();
            
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ProductDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));
            throw new RuntimeException(e.getMessage());
        }
        
        return productList;
    }
    
    @SuppressWarnings("unchecked")
    public int deleteProducts(long[] ids) {
        int result = 0;
        Product product;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            for (int i=0;i<ids.length;i++) {
                
                product = (Product)session.get(Product.class, ids[i]);
                session.delete(product);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ProductDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                  
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public void saveProduct(ProductInfo info) {
        Product product = new Product();
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            //fill receipt
            if (info.getId() != 0) {
                product.setId(info.getId());
            }
            product.setDescription(info.getDescription());
            product.setName(info.getName());
            product.setUnitprice(info.getUnitprice());
            
            session.saveOrUpdate(product);
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ProductDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                  
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public ProductInfo loadProduct(long productId) {
        Product product;
        ProductInfo info = new ProductInfo();
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            product = (Product)session.get(Product.class, productId);
            
            info.setDescription(product.getDescription());
            info.setName(product.getName());
            info.setUnitprice(product.getUnitprice());
            
            return info;
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ProductDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                  
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public List<Product> getAllMatchingProducts(String match) {
        List<Product> prodList = null;
        
        try {
            prodList = (List) sf.getCurrentSession()
                    .createQuery(" from Product where name LIKE '%" + match + "%'").list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ProductDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                  
            throw new RuntimeException(e.getMessage());          
        }
        return prodList;
    }
    
    public int getProductsCount(String productNameFilter) {
        Long value;
        int count = 0;
        String sql;
        
        try {
            sql = "SELECT COUNT(p) FROM Product p ";
            
            if (!productNameFilter.isEmpty()) {
                sql += "where p.name LIKE '%" + productNameFilter + "%' ";
            }
            
            value = (Long) sf.getCurrentSession()
                    .createQuery(sql).uniqueResult();
            count = value.intValue();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ProductDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                    
            throw new RuntimeException(e.getMessage());
        }
        
        return count;
    }
}
