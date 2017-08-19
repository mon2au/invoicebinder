package com.invoicebinder.client.service.product;

import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ProductInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/product")
public interface ProductService extends RemoteService {
    public List<ProductInfo> getAllProductsInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String productNameFilter);      
    public int deleteProducts(long[] id); 
    public void saveProduct(ProductInfo info);
    public ProductInfo loadProduct(long clientId);
    public int getProductsCount(String productNameFilter);    
}
