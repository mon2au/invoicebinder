package com.invoicebinder.client.service.product;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ProductInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mon2
 */
public class ProductServiceClientImpl implements  ProductServiceClientInt {
    private final ProductServiceAsync service;
    private final Main mainui;
    
    public ProductServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(ProductService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }
    
    @Override
    public void getAllProducts(int start, int length, ArrayList<GridColSortInfo> sortList, String productNameFilter, AsyncDataProvider<ProductInfo> provider) {
        this.service.getAllProductsInfo(start, length, sortList, productNameFilter, new GetProductListCallback(provider, start));
    }  

    @Override
    public void deleteProducts(long[] ids) {
         this.service.deleteProducts(ids, new DeleteProductCallback());
    }  

    @Override
    public void saveProduct(ProductInfo info) {
        ValidationResult result = new ValidationResult();
        
        if (info.getName().isEmpty()) {
            result.setMessage("Product name must be supplied.");
            result.setTagname("txtProductName");
            mainui.getContainer().doValidation(Views.newproduct, result);
            return;
        }        
        
        if (info.getDescription().isEmpty()) {
            result.setMessage("Product description must be supplied.");
            result.setTagname("txtDescription");
            mainui.getContainer().doValidation(Views.newproduct, result);
            return;
        }   
        
        if (info.getUnitprice() == null) {
            result.setMessage("Product price must be supplied.");
            result.setTagname("txtPrice");
            mainui.getContainer().doValidation(Views.newproduct, result);
            return;
        }        
        
        this.service.saveProduct(info, new SaveProductCallback());
    }

    @Override
    public void loadProduct(long clientId) {
        this.service.loadProduct(clientId, new LoadProductCallback());
    }

    @Override
    public void getProductsCount(String productNameFilter) {
        this.service.getProductsCount(productNameFilter, new GetProductsCountCallback());
    }

    @Override
    public void isProductsTableEmpty() {
        this.service.getProductsCount("", new IsProductsTableEmptyCallback());
    }
    
    private class LoadProductCallback implements AsyncCallback<ProductInfo> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(ProductInfo result) {
            mainui.getContainer().loadProduct(result);
        }
    }
    
    private class DeleteProductCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            if ((Integer)result == 0) {
                mainui.getContainer().refreshProducts();
            } else {
            Alert.show("unable to delete.", AlertLevel.ERROR);                
            }
        }
    }

    private class GetProductListCallback implements AsyncCallback {
        private final AsyncDataProvider<ProductInfo> dataProvider;
        private final int start;
        
        public GetProductListCallback(AsyncDataProvider<ProductInfo> provider, int start){
            this.dataProvider = provider;
            this.start = start;
        }
                
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            this.dataProvider.updateRowData(start,(List<ProductInfo>)result);
        }
    }

    private class IsProductsTableEmptyCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            if ((Integer)result == 0) {
                mainui.updateNewInvoiceProductTableEmptyMessage();
            }
        }
    }
    
    private class SaveProductCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
             History.newItem(Views.products.toString());
        }
    }

    private class GetProductsCountCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
             mainui.updateProductsCount((Integer)result);
        }
    }     
}
