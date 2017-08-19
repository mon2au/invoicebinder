/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.users;

import com.invoicebinder.client.service.user.UserServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.UserInfo;
import com.google.gwt.core.client.GWT;
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
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author msushil
 */
public class Users extends Composite {
    
    private static final UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);
    private final UserServiceClientImpl userService;   
    private UserInfo[] selection;    
    private final Main main;
    private CellTable<UserInfo> table;
    private Range userDataRange;
    private String userFilterText;   
    private final UsersDataProvider usersDataProvider;   
    private final ArrayList<GridColSortInfo> gridColSortList;       
    private final VerticalPanel gridDataPanel;        
    
    @UiField Button btnNewUser;
    @UiField Button btnEditUser;
    @UiField Button btnDeleteUser;
    @UiField HTMLPanel usersPanel;
    @UiField TextBox txtSearch;  
    
    
    private static final String USERNAME_FILTER_TEXT = "filter by user name";       

    public void updateTableCount(Integer count) {
        usersDataProvider.updateRowCount(count, true);
    }
   
    interface UsersUiBinder extends UiBinder<Widget, Users> {
    }
    
    public Users(Main main) {
        this.main = main;
        this.userService = new UserServiceClientImpl(GWT.getModuleBaseURL() + "services/user", this.main);        
        initWidget(uiBinder.createAndBindUi(this));
        
        usersDataProvider = new UsersDataProvider(this);
        gridColSortList = new ArrayList<GridColSortInfo>();         
        VerticalPanel panel = new VerticalPanel();
        gridDataPanel = new VerticalPanel();          
        SimplePager pager = new SimplePager();
        panel.setWidth("100%");
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        table = createUserTable();
        pager.setDisplay(table);
        panel.add(pager);
        gridDataPanel.add(table);
        gridDataPanel.setHeight(Constants.STANDARD_GRID_HEIGHT);
        panel.add(gridDataPanel);
        
        // Add the widgets to the root panel.
        usersPanel.add(panel);
        btnNewUser.addClickHandler(new DefaultClickHandler(this));
        btnEditUser.addClickHandler(new DefaultClickHandler(this));
        btnDeleteUser.addClickHandler(new DefaultClickHandler(this));
        txtSearch.addFocusHandler(new SearchFocusHandler());
        txtSearch.addBlurHandler(new SearchLostFocusHandler());
        txtSearch.addKeyUpHandler(new SearchChangeHandler());        
        
        //set defaults
        txtSearch.setText(USERNAME_FILTER_TEXT);        
        btnNewUser.setStyleName("appbutton-default");
        btnEditUser.setStyleName("appbutton-default-disabled");
        btnDeleteUser.setStyleName("appbutton-default-disabled");
        btnEditUser.setEnabled(false);
        btnDeleteUser.setEnabled(false);
   
    }
    
    // <editor-fold defaultstate="collapsed" desc="Data Provider">        
    private class UsersDataProvider extends AsyncDataProvider<UserInfo> {
        private final Users pageReference;
        
        public UsersDataProvider(Users reference) {
            this.pageReference = reference;
        }

        @Override
        protected void onRangeChanged(HasData<UserInfo> display) {
            this.pageReference.userDataRange = display.getVisibleRange();
            this.pageReference.getAllUsers();
        }                   
    }    
    // </editor-fold> 
    
    private CellTable createUserTable(){
        table = new CellTable();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED);
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);
        
        //Checkbox
        //Column<InvoiceInfo, Boolean> checkColumn = new Column<InvoiceInfo, Boolean>(
        //        new CheckboxCell(true, false)) {
        //            @Override
        //            public Boolean getValue(InvoiceInfo object) {
        //                return true;                        
        //            }
        //        };
        //table.addColumn(checkColumn, "");
        //table.setColumnWidth(checkColumn, 40, Unit.PX);
        
        //Username
        TextColumn<UserInfo> userName =
                new TextColumn<UserInfo>() {
                    @Override
                    public String getValue(UserInfo object) {
                        return object.getUserName();
                    }
                };
        table.addColumn(userName, "Username");   
        
        //First Name
        TextColumn<UserInfo> userFirstName =
                new TextColumn<UserInfo>() {
                    @Override
                    public String getValue(UserInfo object) {
                        return object.getFirstName();
                    }
                };
        table.addColumn(userFirstName, "First Name");        
        
        //Last Name
        TextColumn<UserInfo> userLastName =
                new TextColumn<UserInfo>() {
                    @Override
                    public String getValue(UserInfo object) {
                        return object.getLastName();
                    }
                };
        table.addColumn(userLastName, "Last Name");   
        
        //Email
        TextColumn<UserInfo> userEmail =
                new TextColumn<UserInfo>() {
                    @Override
                    public String getValue(UserInfo object) {
                        return object.getPrimaryEmailAddr();
                    }
                };
        table.addColumn(userEmail, "Email");  
        
        //Status
        TextColumn<UserInfo> status =
                new TextColumn<UserInfo>() {
                    @Override
                    public String getValue(UserInfo object) {
                        return object.getUserStatus();
                    }
                };
        table.addColumn(status, "Status");

        usersDataProvider.addDataDisplay(table);
        
        // Add a selection model to handle user selection.
        MultiSelectionModel<UserInfo> selectionModel;
        selectionModel = new MultiSelectionModel();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new GridSelectionEventHandler());
        table.setWidth(Constants.STANDARD_GRID_WIDTH, true);
        table.setPageSize(Constants.STANDARD_GRID_PAGESIZE);
        table.setEmptyTableWidget(new Label(Constants.EMPTY_DATATABLE_MESSAGE));            
        return table;
    }
    
    public void refresh() {
        table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">        
    private class DefaultClickHandler implements ClickHandler {
        Users pageReference;
        
        public DefaultClickHandler(Users reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnNewUser) {
                if (isDemoApplication()) return; 
                History.newItem(Views.newuser.toString());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == btnEditUser) {
                if (isDemoApplication()) return; 
                History.newItem(Views.edituser.toString() + "/userId=" + selection[0].getId());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == btnDeleteUser) {
                if (isDemoApplication()) return; 
                pageReference.deleteUsers();
            }
        }       
    }
    private class GridSelectionEventHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            Set<UserInfo> selected = ((MultiSelectionModel)event.getSource()).getSelectedSet();
            selection = new UserInfo[selected.size()];
            int i = 0;
            
            if (selected.isEmpty()) {
                btnEditUser.setStyleName("appbutton-default-disabled");
                btnDeleteUser.setStyleName("appbutton-default-disabled");
                btnDeleteUser.setEnabled(false);
                btnEditUser.setEnabled(false);
            }
            else {
                if (selected.size() == 1) {
                    btnEditUser.setStyleName("appbutton-default");
                    btnEditUser.setEnabled(true);
                    btnDeleteUser.setStyleName("appbutton-default");
                    btnDeleteUser.setEnabled(true);
                }
                else {
                    btnEditUser.setStyleName("appbutton-default-disabled");
                    btnEditUser.setEnabled(false);
                    btnDeleteUser.setStyleName("appbutton-default");
                    btnDeleteUser.setEnabled(true);               
                }
                
                for (UserInfo info : selected) {
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
            if (((TextBox)sender).getText().equals(USERNAME_FILTER_TEXT)) {
                ((TextBox)sender).setText("");
            }
        }
    }
    private class SearchLostFocusHandler implements BlurHandler {
        @Override
        public void onBlur(BlurEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().isEmpty()) {
                ((TextBox)sender).setText(USERNAME_FILTER_TEXT);
            }
        }
    }    
    private class SearchChangeHandler implements KeyUpHandler {
        @Override
        public void onKeyUp(KeyUpEvent event) {
            getAllUsers();
        }
    }    
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Save / Load">    
    private void getAllUsers() {
        userFilterText = txtSearch.getText();
        if (userFilterText.equals(USERNAME_FILTER_TEXT)) {
            userFilterText = "";
        }
        userService.getAllUsers(
                userDataRange.getStart(), 
                userDataRange.getLength(), 
                gridColSortList,
                userFilterText,
                usersDataProvider);        
        userService.getUsersCount(userFilterText);
    }
    private void deleteUsers() {
        long[] ids = new long[selection.length];

        for (int i=0;i<selection.length;i++) {
            ids[i] = selection[i].getId();
        }

        userService.deleteUsers(ids);
    }
    // </editor-fold>      
       
}
