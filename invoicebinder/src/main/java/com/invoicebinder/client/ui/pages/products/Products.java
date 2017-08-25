/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.products;

import com.invoicebinder.client.service.product.ProductServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ProductInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.invoicebinder.shared.misc.Utils;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author mon2
 */
public class Products extends Composite {
    
    private static final ProductsUiBinder uiBinder = GWT.create(ProductsUiBinder.class);
    private final ProductServiceClientImpl productService;
    private ProductInfo[] selection;
    private final Main main;
    private CellTable<ProductInfo> table;
    private String productFilterText;
    private Range productDataRange; 
    private final ArrayList<GridColSortInfo> gridColSortList;
    private final ProductsDataProvider productsDataProvider;
    private final VerticalPanel panel;
    private final SimplePager pager;   
    private final VerticalPanel gridDataPanel;       
    
    @UiField Button newProduct;
    @UiField Button editProduct;
    @UiField Button deleteProduct;
    @UiField HTMLPanel productsPanel;
    @UiField TextBox txtSearch;   
    
    private static final String PRODUCT_NAME_FILTER_TEXT = "filter by product name";    

    public void refresh() {
        table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
    }

    public void updateTableCount(Integer count) {
        productsDataProvider.updateRowCount(count, true);
    }
    
    interface ProductsUiBinder extends UiBinder<Widget, Products> {
    }

    public Products(Object main) {
        this.main = (Main)main;
        this.productService = new ProductServiceClientImpl(GWT.getModuleBaseURL() + "services/product", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        productsDataProvider = new ProductsDataProvider(this);
        gridColSortList = new ArrayList<GridColSortInfo>();             
        panel = new VerticalPanel();
        pager = new SimplePager();
        gridDataPanel = new VerticalPanel();
        
        panel.setWidth("100%");
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        txtSearch.setText(PRODUCT_NAME_FILTER_TEXT);
        table = createProductsTable();
        pager.setDisplay(table);
        gridDataPanel.add(table);
        gridDataPanel.setHeight(Constants.STANDARD_GRID_HEIGHT);
        panel.add(gridDataPanel);
        panel.add(pager);
        
        // Add the widgets to the root panel.
        productsPanel.add(panel);
        newProduct.addClickHandler(new Products.DefaultClickHandler());
        editProduct.addClickHandler(new Products.DefaultClickHandler());
        deleteProduct.addClickHandler(new Products.DefaultClickHandler());
        txtSearch.addFocusHandler(new SearchFocusHandler());
        txtSearch.addBlurHandler(new SearchLostFocusHandler());
        txtSearch.addKeyUpHandler(new SearchChangeHandler());        
        
        //set defaults
        newProduct.setStyleName("appbutton-default");
        editProduct.setStyleName("appbutton-default-disabled");
        deleteProduct.setStyleName("appbutton-default-disabled");
        editProduct.setEnabled(false);
        deleteProduct.setEnabled(false);
    }
    
    private CellTable createProductsTable(){
        table = new CellTable();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED);
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);
        
        
        // Add a text column to show the name.
        TextColumn<ProductInfo> name =
                new TextColumn<ProductInfo>() {
                    @Override
                    public String getValue(ProductInfo object) {
                        return object.getName();
                    }
                };
        table.setColumnWidth(name, 250, Style.Unit.PX);             
        table.addColumn(name, "Name");
        
        // Add a text column to show the desc.
        TextColumn<ProductInfo> description =
                new TextColumn<ProductInfo>() {
                    @Override
                    public String getValue(ProductInfo object) {
                        return object.getDescription();
                    }
                };
        table.setColumnWidth(description, 650, Style.Unit.PX);    
        table.addColumn(description, "Description");
        
        // Add a text column to show the price.
        TextColumn<ProductInfo> price
                = new TextColumn<ProductInfo>() {
                    @Override
                    public String getValue(ProductInfo object) {
                        return object.getUnitprice().toString();
                    }
                };
        table.addColumn(price, "Price");
        
        productsDataProvider.addDataDisplay(table);
        
        // Add a selection model to handle user selection.
        MultiSelectionModel<ProductInfo> selectionModel;
        selectionModel = new MultiSelectionModel();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new Products.GridSelectionEventHandler());
        table.setWidth(Constants.STANDARD_GRID_WIDTH, true);
        table.setPageSize(Constants.STANDARD_GRID_PAGESIZE);
        table.setEmptyTableWidget(new Label(Constants.EMPTY_DATATABLE_MESSAGE));        
        return table;
    }
    
    private void getAllProducts() {
        productFilterText = txtSearch.getText();
        if (productFilterText.equals(PRODUCT_NAME_FILTER_TEXT)) {
            productFilterText = "";
        }
        productService.getAllProducts(
                productDataRange.getStart(), 
                productDataRange.getLength(), 
                gridColSortList,
                productFilterText,
                productsDataProvider);        
        productService.getProductsCount(productFilterText);
    }
    
    private class ProductsDataProvider extends AsyncDataProvider<ProductInfo> {
        private final Products pageReference;
        
        public ProductsDataProvider(Products reference) {
            this.pageReference = reference;
        }

        @Override
        protected void onRangeChanged(HasData<ProductInfo> display) {
            this.pageReference.productDataRange = display.getVisibleRange();
            this.pageReference.getAllProducts();
        }                   
    }    
        
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">        
    private class DefaultClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == newProduct) {
                History.newItem(Views.newproduct.toString());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == editProduct) {
                if (Utils.isDemoApplication()) return;
                History.newItem(Views.editproduct.toString() + "/productId=" + selection[0].getId());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == deleteProduct) {
                if (Utils.isDemoApplication()) return;
                deleteProducts();
            }
        }
        
        private void deleteProducts() {
            long[] ids = new long[selection.length];
            
            for (int i=0;i<selection.length;i++) {
               ids[i] = selection[i].getId();
            }
            
            productService.deleteProducts(ids);
        } 
    }
    private class GridSelectionEventHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            Set<ProductInfo> selected = ((MultiSelectionModel)event.getSource()).getSelectedSet();
            selection = new ProductInfo[selected.size()];
            int i = 0;
            
            if (selected.isEmpty()) {
                editProduct.setStyleName("appbutton-default-disabled");
                deleteProduct.setStyleName("appbutton-default-disabled");
                deleteProduct.setEnabled(false); 
                editProduct.setEnabled(false);
            }
            else {
                if (selected.size() == 1) {
                    editProduct.setStyleName("appbutton-default");
                    editProduct.setEnabled(true);
                    deleteProduct.setStyleName("appbutton-default");
                    deleteProduct.setEnabled(true);  
                }
                else {
                    editProduct.setStyleName("appbutton-default-disabled");
                    editProduct.setEnabled(false);
                    deleteProduct.setStyleName("appbutton-default");
                    deleteProduct.setEnabled(true);                    
                }
                
                for (ProductInfo info : selected) {
                    selection[i] = info;
                    i++;
                }               
            }
        }
    }
    private class SearchFocusHandler implements FocusHandler {

        @Override
        public void onFocus(FocusEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().equals(PRODUCT_NAME_FILTER_TEXT)) {
                ((TextBox)sender).setText("");
            }
        }
    }
    private class SearchLostFocusHandler implements BlurHandler {
        @Override
        public void onBlur(BlurEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().isEmpty()) {
                ((TextBox)sender).setText(PRODUCT_NAME_FILTER_TEXT);
            }
        }
    }    
    private class SearchChangeHandler implements KeyUpHandler {
        @Override
        public void onKeyUp(KeyUpEvent event) {
            getAllProducts();
        }
    }    
    // </editor-fold>
}