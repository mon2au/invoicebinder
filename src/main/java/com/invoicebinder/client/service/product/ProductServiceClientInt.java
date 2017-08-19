package com.invoicebinder.client.service.product;

import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ProductInfo;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;

/**
 *
 * @author msushil
 */
public interface ProductServiceClientInt {
    void getAllProducts(int start, int length, ArrayList<GridColSortInfo> sortList, String filter, AsyncDataProvider<ProductInfo> provider);
    void deleteProducts(long[] ids);
    void saveProduct(ProductInfo info);
    void loadProduct(long productId);
    void getProductsCount(String productNameFilter);   
    void isProductsTableEmpty();    
}
