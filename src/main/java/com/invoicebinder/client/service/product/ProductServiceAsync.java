package com.invoicebinder.client.service.product;

import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ProductInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mon2
 */
public interface ProductServiceAsync {

    public void getAllProductsInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String productNameFilter, AsyncCallback<List<ProductInfo>> asyncCallback);
    public void deleteProducts(long[] id, AsyncCallback<Integer> asyncCallback);
    public void saveProduct(ProductInfo info, AsyncCallback<Void> asyncCallback);
    public void loadProduct(long clientId, AsyncCallback<ProductInfo> asyncCallback);
    public void getProductsCount(String productNameFilter, AsyncCallback<Integer>  productsCountCallback);    
}
