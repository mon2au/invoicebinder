/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.widget.globaladdress;

import com.invoicebinder.client.service.commondb.CommonDBServiceClientImpl;
import com.invoicebinder.client.service.suggestion.CitySuggestOracle;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.CountryInfo;
import com.invoicebinder.shared.model.CountryItem;
import com.invoicebinder.shared.model.StateInfo;
import com.invoicebinder.shared.suggestion.CitySuggestion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

/**
 *
 * @author mon2
 */
public class GlobalAddressPanel extends Composite {
    
    private static final GlobalAddressPanelUiBinder uiBinder = GWT.create(GlobalAddressPanelUiBinder.class);
    @UiField HTMLPanel globalAddressPanel;
    private TextBox txtAddress1;
    private TextBox txtAddress2;
    private ListBox lstCountry;
    private ListBox lstState;
    private TextBox txtState;
    private CitySuggestOracle oracle;
    private SuggestBox citySuggest;
    private TextBox txtCity;
    private CitySuggestion suggestion;
    private TextBox txtPostCode;
    private final FlexTable tblAddress;
    private final CommonDBServiceClientImpl commondbService;
    
    private String countryCode;
    private String state;
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    public String getCountryCode() {
        return lstCountry.getValue(lstCountry.getSelectedIndex());
    }
    public String getPostCode() {
        return txtPostCode.getText();
    }
    public String getCity() {
        if (txtCity.isVisible()) {
            return txtCity.getText();
        }
        else {
            return citySuggest.getValue();
        }
    }
    public String getAddress1() {
        return txtAddress1.getText();
    }
    public String getAddress2() {
        return txtAddress2.getText();
    }
    public String getState() {
        if (txtState.isVisible()) {
            return txtState.getText();
        }
        else {
            return lstState.getValue(lstState.getSelectedIndex());
        }
    }
    // </editor-fold>
    
    interface GlobalAddressPanelUiBinder extends UiBinder<Widget, GlobalAddressPanel> {
    }
    
    @SuppressWarnings("LeakingThisInConstructor")
    public GlobalAddressPanel(boolean loadDefaultData) {
        this.commondbService = new CommonDBServiceClientImpl(GWT.getModuleBaseURL() + "services/commondb");
        initWidget(uiBinder.createAndBindUi(this));
        this.tblAddress = getAddressTable();
        this.globalAddressPanel.add(this.tblAddress);
        //handlers
        lstCountry.addChangeHandler(new DefaultChangeHandler(this));
        lstState.addChangeHandler(new DefaultChangeHandler(this));
        //services
        if (loadDefaultData) {
            commondbService.loadCountryData(this);
        }
    }
    
    public GlobalAddressPanel() {
        this(true);
    }
    
    // <editor-fold defaultstate="collapsed" desc="UI Controls">
    private FlexTable getAddressTable() {
        FlexTable table = new FlexTable();
        VerticalPanel cityPanel = new VerticalPanel();
        VerticalPanel statePanel = new VerticalPanel();
        oracle = new CitySuggestOracle();
        citySuggest = new SuggestBox(oracle);
        citySuggest.setLimit(40);
        citySuggest.addSelectionHandler(new CitySelectionHandler());
        citySuggest.addKeyDownHandler(new DefaultKeyDownHandler(this));
        suggestion = null;
        
        txtAddress1 = new TextBox();
        txtAddress2 = new TextBox();
        lstCountry = new ListBox();
        lstState = new ListBox();
        txtState = new TextBox();
        txtPostCode = new TextBox();
        txtCity = new TextBox();
        
        //set panel visibility
        txtState.setVisible(false);
        txtCity.setVisible(false);
        statePanel.add(lstState);
        statePanel.add(txtState);
        cityPanel.add(citySuggest);
        cityPanel.add(txtCity);
        
        int rowIndex = 1;
        
        table.getFlexCellFormatter().setWidth(rowIndex, 0, "160px");
        
        table.setHTML(rowIndex, 0, "Country:");
        table.setWidget(rowIndex++, 1, lstCountry);
        table.setHTML(rowIndex, 0, "State/Territory:");
        table.setWidget(rowIndex++, 1, statePanel);
        table.setHTML(rowIndex, 0, "Address 1:");
        table.setWidget(rowIndex++, 1, txtAddress1);
        table.setHTML(rowIndex, 0, "Address 2:");
        table.setWidget(rowIndex++, 1, txtAddress2);
        table.setHTML(rowIndex, 0, "City/Suburb:");
        table.setWidget(rowIndex++, 1, cityPanel);
        table.setHTML(rowIndex, 0, "Post Code:");
        table.setWidget(rowIndex++, 1, txtPostCode);
        lstCountry.setStyleName("list-box");
        lstState.setStyleName("list-box");
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        return table;
    }
    // </editor-fold>
    
    public void setAddressDetails(String countryCode, String state, String address1, String address2, String city, String postCode) {
        this.countryCode = countryCode;
        this.state = state;
        txtAddress1.setText(address1);
        txtAddress2.setText(address2);
        txtPostCode.setText(postCode);
        citySuggest.setText(city);
        
        this.loadAddressData();
    }
    
    private void setCountryAndStateInfoForCitySuggestion() {
        oracle.setCountryCode(lstCountry.getValue(lstCountry.getSelectedIndex()));
        if (lstState.getItemCount() > 0) {
            oracle.setStateCode(lstState.getValue(lstState.getSelectedIndex()));
        }
    }
    public void updateCountryList(CountryInfo countryInfo) {
        this.lstCountry.clear();
        
        for(CountryItem cty : countryInfo.getCountryList()) {
            this.lstCountry.addItem(cty.getCountryName(), cty.getCountryCode());
        }
        if (this.countryCode != null) {
            selectListBoxValue(lstCountry, this.countryCode);
        }
        else {
            this.lstCountry.setSelectedIndex(countryInfo.getDefaultItemIndex());
        }
        loadStateList();
    }
    public void updateStateList(List<StateInfo> stateList) {
        if (stateList.size() > 0) {
            lstState.setVisible(true);
            citySuggest.setVisible(true);
            txtState.setVisible(false);
            txtCity.setVisible(false);
            this.lstState.clear();
            for(StateInfo info : stateList) {
                this.lstState.addItem(info.getStateName(), info.getStateCode());
            }
            selectListBoxValue(lstState, this.state);
        }
        else {
            citySuggest.setVisible(false);
            lstState.setVisible(false);
            txtState.setVisible(true);
            txtCity.setVisible(true);
            txtState.setText(this.state);
        }
    }
    private void loadStateList() {
        commondbService.loadStateData(this, lstCountry.getValue(lstCountry.getSelectedIndex()));
    }
    public void loadAddressData() {
        commondbService.loadCountryData(this);
    }
    private void clearControlsOnCountryChange() {
        this.txtPostCode.setText(Constants.EMPTY_STRING);
        this.txtState.setText(Constants.EMPTY_STRING);
        this.citySuggest.setText(Constants.EMPTY_STRING);
        this.txtCity.setText(Constants.EMPTY_STRING);
    }
    private void clearControlsOnStateChange() {
        this.txtPostCode.setText(Constants.EMPTY_STRING);
        this.citySuggest.setText(Constants.EMPTY_STRING);
        this.txtCity.setText(Constants.EMPTY_STRING);
    }
    
    private void selectListBoxValue(ListBox list, String value) {
        if (value != null) {
            for (int i=0; i<list.getItemCount(); i++) {
                if (list.getValue(i).equals(value)) {
                    list.setItemSelected(i, true);
                    break;
                }
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class CitySelectionHandler implements SelectionHandler {
        
        @Override
        public void onSelection(SelectionEvent event) {
            suggestion = ((CitySuggestion) event.getSelectedItem());
            txtPostCode.setText(suggestion.getPostCode());
            txtPostCode.setReadOnly(true);
        }
    }
    private class DefaultChangeHandler implements ChangeHandler {
        private final GlobalAddressPanel pageReference;
        
        public DefaultChangeHandler(GlobalAddressPanel reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onChange(ChangeEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == lstCountry) {
                this.pageReference.loadStateList();
                this.pageReference.clearControlsOnCountryChange();
            } else if (sender == lstState) {
                this.pageReference.clearControlsOnStateChange();
            }
            event.getNativeEvent().preventDefault();
        }
    }
    private class DefaultKeyDownHandler implements KeyDownHandler {
        private final GlobalAddressPanel pageReference;
        
        public DefaultKeyDownHandler(GlobalAddressPanel reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onKeyDown(KeyDownEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == citySuggest) {
                pageReference.setCountryAndStateInfoForCitySuggestion();
            }
            
        }
        
    }
    // </editor-fold>
}
