/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.service;

import com.invoicebinder.client.service.product.ProductService;
import com.invoicebinder.server.dataaccess.ProductDAO;
import com.invoicebinder.shared.entity.item.Product;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ProductInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("product")
public class ProductServiceImpl extends RemoteServiceServlet implements
        ProductService {
    
    @Autowired
    private ProductDAO productDAO;

    @Override
    public List<ProductInfo> getAllProductsInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String productNameFilter) {
        List<ProductInfo> allProductsInfo = new ArrayList<ProductInfo>();
        List<Product> productList;
        ProductInfo info;
        
        productList = productDAO.getAllProductsInfo(start, length, sortList, productNameFilter);
        
        for(Product product : productList) {
            info = new ProductInfo();
            info.setId(product.getId());
            info.setName(product.getName());
            info.setDescription(product.getDescription());
            info.setUnitprice(product.getUnitprice());
            allProductsInfo.add(info);
        }
        return allProductsInfo;
    }

    @Override
    public int deleteProducts(long[] ids) {
        return productDAO.deleteProducts(ids);
    }

    @Override
    public void saveProduct(ProductInfo info) {
        productDAO.saveProduct(info);
    }

    @Override
    public ProductInfo loadProduct(long clientId) {
        return productDAO.loadProduct(clientId);
    }    

    @Override
    public int getProductsCount(String productNameFilter) {
        return productDAO.getProductsCount(productNameFilter);
    }
}
