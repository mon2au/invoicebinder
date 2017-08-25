/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.products;

import com.invoicebinder.client.service.product.ProductServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.misc.Utils;
import com.invoicebinder.shared.model.ProductInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.math.BigDecimal;

/**
 *
 * @author mon2
 */
public class NewProduct extends Composite {
    
    private static final NewProductUiBinder uiBinder = GWT.create(NewProductUiBinder.class);
    @UiField HTMLPanel newProductPanel;
    private final Button btnSave;
    private final Button btnCancel;
    private TextBox txtName;
    private TextBox txtDescription;
    private TextBox txtPrice;
    private final VerticalPanel mainPanel;
    private final Label lblHeader;
    private final Label lblProductDetailsHeader;  
    private final HorizontalPanel buttonPanel;
    private final ProductServiceClientImpl productService;
    private final Main main;
    private int productId = 0;

    public void populateProductInfo(ProductInfo info) {
        this.txtName.setText(info.getName());
        this.txtDescription.setText(info.getDescription());
        this.txtPrice.setText(info.getUnitprice().toString());
    }

    public void setValidationResult(ValidationResult validation) {
        Element element;
        
        element = DOM.getElementById(validation.getTagname());
        element.focus();
        ValidationPopup.Show(validation.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    } 
   
    interface NewProductUiBinder extends UiBinder<Widget, NewProduct> {
    }
    
    public NewProduct(Object main) {
        this.main = (Main)main;
        this.productService = new ProductServiceClientImpl(GWT.getModuleBaseURL() + "services/product", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        this.mainPanel = new VerticalPanel();
        this.lblHeader = new Label();
        this.lblProductDetailsHeader = new Label();
        this.buttonPanel = new HorizontalPanel();  
        this.btnSave = new Button();
        this.btnCancel = new Button();
        if (Utils.getParamFromHref("productId") != null) this.productId = Integer.parseInt(Utils.getParamFromHref("productId"));
        //product info
        lblHeader.getElement().setInnerHTML("<h5><span>New Product</span></h5>");
        lblProductDetailsHeader.getElement().setInnerHTML("<h6><span>Product Details</span></h6>");
        mainPanel.add(lblHeader);
        mainPanel.add(lblProductDetailsHeader);
        mainPanel.add(getProductTable());
        //buttons
        btnSave.setStyleName("appbutton-default");
        btnCancel.setStyleName("appbutton-default");
        btnSave.setText("Save");
        btnCancel.setText("Cancel");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        mainPanel.add(buttonPanel);
        mainPanel.setStyleName("container");
        newProductPanel.add(mainPanel);
        
        btnSave.addClickHandler(new DefaultClickHandler(this));
        btnCancel.addClickHandler(new DefaultClickHandler(this));
        
        if (productId != 0) {
            loadProduct(productId);
        }
    }
    
    private void saveProduct() {
   	ProductInfo pInfo = new ProductInfo();
        if (this.productId != 0) {
            pInfo.setId((long)this.productId);
        }
        pInfo.setName(txtName.getText());
        pInfo.setDescription(txtDescription.getText());
        if (!txtPrice.getText().isEmpty()) pInfo.setUnitprice(new BigDecimal(txtPrice.getText()));
        
        productService.saveProduct(pInfo);
    }
        
    private void loadProduct(int clientId) {
        productService.loadProduct(clientId);
    }
    
    private FlexTable getProductTable() {
        FlexTable table = new FlexTable();
        txtName = new TextBox();
        txtName.getElement().setId("txtProductName");
        txtDescription = new TextBox();
        txtDescription.getElement().setId("txtDescription");
        txtPrice = new TextBox();
        txtPrice.getElement().setId("txtPrice");
        
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setHTML(0, 0, "");
        //client information
        table.setHTML(1, 0, "Name:");
        table.setWidget(1, 1, txtName);
        table.setHTML(2, 0, "Description:");
        table.setWidget(2, 1, txtDescription);
        table.setHTML(3, 0, "Price:");
        table.setWidget(3, 1, txtPrice);
        
        return table;
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final NewProduct pageReference;
        
        public DefaultClickHandler(NewProduct reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                this.pageReference.saveProduct();
            }
            else if (sender == btnCancel) {
                History.newItem(Views.products.toString());
                event.getNativeEvent().preventDefault();
            }
        }
    }
    
}