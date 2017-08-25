/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.clients;

import com.invoicebinder.client.service.client.ClientServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.client.ui.widget.globaladdress.GlobalAddressPanel;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.misc.Utils;
import com.invoicebinder.shared.model.ClientInfo;
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

/**
 *
 * @author mon2
 */
public class NewClient extends Composite {
    
    private static final NewClientUiBinder uiBinder = GWT.create(NewClientUiBinder.class);
    @UiField HTMLPanel newClientPanel;
    private final GlobalAddressPanel pnlClientAddress;
    
    private TextBox txtFirstName;
    private TextBox txtLastName;
    private TextBox txtHomePhone;
    private TextBox txtWorkPhone;
    private TextBox txtMobile;
    private TextBox txtFax;
    private TextBox txtEmail;
    private final HorizontalPanel buttonPanel;
    private final ClientServiceClientImpl clientService;
    private final Main main;
    private final VerticalPanel mainPanel;
    private final Label lblHeader;
    private final Label lblClientDetailsHeader;
    private final Label lblAddressDetailsHeader;
    private final Label lblContactDetailsHeader;
    private final Button btnSave;
    private final Button btnCancel;
    
    private int clientId = 0;
    private static final String LABELCOLWIDTH = "120px";
    
    interface NewClientUiBinder extends UiBinder<Widget, NewClient> {
    }
    
    public NewClient(Main main) {
        //initialize
        this.main = main;
        this.clientService = new ClientServiceClientImpl(GWT.getModuleBaseURL() + "services/client", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        this.mainPanel = new VerticalPanel();
        this.lblHeader = new Label();
        this.lblClientDetailsHeader = new Label();
        this.lblAddressDetailsHeader = new Label();
        this.lblContactDetailsHeader = new Label();
        this.buttonPanel = new HorizontalPanel();
        this.btnSave = new Button();
        this.btnCancel = new Button();
        this.pnlClientAddress = new GlobalAddressPanel(false); 
        if (Utils.getParamFromHref("clientId") != null) this.clientId = Integer.parseInt(Utils.getParamFromHref("clientId"));
        //assemble all components
        lblHeader.getElement().setInnerHTML("<h5><span>New Client</span></h5>");
        lblClientDetailsHeader.getElement().setInnerHTML("<h6><span>Client Details</span></h6>");
        lblAddressDetailsHeader.getElement().setInnerHTML("<h6><span>Address Details</span></h6>");
        lblContactDetailsHeader.getElement().setInnerHTML("<h6><span>Contact Details</span></h6>");
        mainPanel.add(lblHeader);
        mainPanel.add(lblClientDetailsHeader);
        mainPanel.add(getClientDetailsTable());
        mainPanel.add(lblAddressDetailsHeader);
        mainPanel.add(getAddressDetailsTable());
        mainPanel.add(lblContactDetailsHeader);
        mainPanel.add(getContactDetailsTable());
        
        //buttons
        btnSave.setStyleName("appbutton-default");
        btnCancel.setStyleName("appbutton-default");
        btnSave.setText("Save");
        btnCancel.setText("Cancel");
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        mainPanel.add(buttonPanel);
        mainPanel.setStyleName("container");
        newClientPanel.add(mainPanel);
        
        btnSave.addClickHandler(new DefaultClickHandler(this));
        btnCancel.addClickHandler(new DefaultClickHandler(this));
        
        if (clientId != 0) {
            loadClient(clientId);
        }
        else {
            this.pnlClientAddress.loadAddressData();
        }
    }
    
    public void setValidationResult(ValidationResult validation) {
        Element element;
        
        element = DOM.getElementById(validation.getTagname());
        element.focus();
        ValidationPopup.Show(validation.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }
    
    // <editor-fold defaultstate="collapsed" desc="UI Component Tables">
    private FlexTable getContactDetailsTable() {
        FlexTable table = new FlexTable();
        txtHomePhone = new TextBox();
        txtWorkPhone = new TextBox();
        txtMobile = new TextBox();
        txtFax = new TextBox();
        txtEmail = new TextBox();
        
        table.getColumnFormatter().setWidth(0, LABELCOLWIDTH);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setHTML(1, 0, "");
        //address information
        table.setHTML(2, 0, "Home Phone: ");
        table.setWidget(2, 1, txtHomePhone);
        table.setHTML(3, 0, "Work Phone: ");
        table.setWidget(3, 1, txtWorkPhone);
        table.setHTML(4, 0, "Mobile: ");
        table.setWidget(4, 1, txtMobile);
        table.setHTML(5, 0, "Fax: ");
        table.setWidget(5, 1, txtFax);
        table.setHTML(6, 0, "Email: ");
        table.setWidget(6, 1, txtEmail);
        return table;
    }
    
    private FlexTable getAddressDetailsTable() {
        FlexTable table = new FlexTable();
        table.setWidget(0, 0, pnlClientAddress);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        return table;
    }
    
    private FlexTable getClientDetailsTable() {
        FlexTable table = new FlexTable();
        txtFirstName = new TextBox();
        txtLastName = new TextBox();
        txtFirstName.getElement().setId("txtFirstName");
        table.getColumnFormatter().setWidth(0, LABELCOLWIDTH);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setHTML(0, 0, "");
        //client information
        table.setHTML(1, 0, "First Name:");
        table.setWidget(1, 1, txtFirstName);
        table.setHTML(2, 0, "Last Name:");
        table.setWidget(2, 1, txtLastName);
        
        return table;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class DefaultClickHandler implements ClickHandler {
        private final NewClient pageReference;
        
        public DefaultClickHandler(NewClient reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                this.pageReference.saveClient();
            }
            else if (sender == btnCancel) {
                History.newItem(Views.clients.toString());
                event.getNativeEvent().preventDefault();
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Callback Handlers">
    public void populateClientInfo(ClientInfo info) {
        this.txtFirstName.setText(info.getFirstName());
        this.txtLastName.setText(info.getLastName());
        this.txtHomePhone.setText(info.getHomePhone());
        this.txtWorkPhone.setText(info.getWorkPhone());
        this.txtMobile.setText(info.getMobilePhone());
        this.txtFax.setText(info.getFaxNumber());
        this.txtEmail.setText(info.getEmailAddress());
        
        this.pnlClientAddress.setAddressDetails(info.getCountryCode(), info.getState(), info.getAddress1(), info.getAddress2(), info.getCity(), info.getPostCode());
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Save / Load">
    private void saveClient() {
        ClientInfo cInfo = new ClientInfo();
        if (this.clientId != 0) {
            cInfo.setId((long)this.clientId);
        }
        cInfo.setFirstName(txtFirstName.getText());
        cInfo.setLastName(txtLastName.getText());
        cInfo.setHomePhone(txtHomePhone.getText());
        cInfo.setWorkPhone(txtWorkPhone.getText());
        cInfo.setMobilePhone(txtMobile.getText());
        cInfo.setFaxNumber(txtFax.getText());
        cInfo.setEmailAddress(txtEmail.getText());
        
        cInfo.setAddress1(pnlClientAddress.getAddress1());
        cInfo.setAddress2(pnlClientAddress.getAddress2());
        cInfo.setCity(pnlClientAddress.getCity());
        cInfo.setState(pnlClientAddress.getState());
        cInfo.setCountryCode(pnlClientAddress.getCountryCode());
        cInfo.setPostCode(pnlClientAddress.getPostCode());
        
        clientService.saveClient(cInfo);
    }
    
    private void loadClient(int clientId) {
        clientService.loadClient(clientId);
    }
    // </editor-fold>
}