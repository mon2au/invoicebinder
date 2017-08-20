/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.clients;

import com.invoicebinder.client.service.client.ClientServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.client.ClientStatus;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
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
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
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
 * @author mon2
 */
public class Clients extends Composite {
    private static final ClientsUiBinder uiBinder = GWT.create(ClientsUiBinder.class);
    private final ClientServiceClientImpl clientService;
    private ClientInfo[] selection;
    private final Main main;
    private CellTable<ClientInfo> table;
    private final VerticalPanel panel;
    private final VerticalPanel gridDataPanel;    
    private final SimplePager pager;
    private final ClientsDataProvider clientsDataProvider;
    private Range clientDataRange;
    private final ArrayList<GridColSortInfo> gridColSortList;
    private final ColumnSortList sortList;
    private String clientFilterText;
    private ColumnSortInfo item;
    private GridColSortInfo sortItem;
    private ClientStatus clientStatusFilter;
    
    @UiField Button newClient;
    @UiField Button editClient;
    @UiField Button deleteClient;
    @UiField Button archiveClient;
    @UiField ToggleButton toggleAll;
    @UiField ToggleButton toggleActive;
    @UiField ToggleButton toggleArchived;
    @UiField HTMLPanel clientsPanel;
    @UiField TextBox txtSearch;
    
    private static final String CLIENT_FIRSTNAME_FILTER_TEXT = "filter by firstname";
    
    public void refresh() {
        table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
    }
    
    interface ClientsUiBinder extends UiBinder<Widget, Clients> {
    }
    
    public Clients(Main main) {
        //init
        this.main = main;
        this.clientService = new ClientServiceClientImpl(GWT.getModuleBaseURL() + "services/client", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        this.setClientStatusToggle(ClientStatus.ACTIVE);
        clientsDataProvider = new ClientsDataProvider(this);
        gridColSortList = new ArrayList<GridColSortInfo>();
        panel = new VerticalPanel();
        gridDataPanel = new VerticalPanel();        
        pager = new SimplePager();
        table = createClientsTable();
        sortList = table.getColumnSortList();
        
        panel.setWidth("100%");
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        txtSearch.setText(CLIENT_FIRSTNAME_FILTER_TEXT);
        pager.setDisplay(table);
        gridDataPanel.add(table);
        gridDataPanel.setHeight(Constants.STANDARD_GRID_HEIGHT);
        panel.add(gridDataPanel);
        panel.add(pager);
        
        // Add the widgets to the root panel.
        clientsPanel.add(panel);
        newClient.addClickHandler(new DefaultClickHandler(this));
        editClient.addClickHandler(new DefaultClickHandler(this));
        deleteClient.addClickHandler(new DefaultClickHandler(this));
        archiveClient.addClickHandler(new DefaultClickHandler(this));
        
        toggleAll.addClickHandler(new DefaultClickHandler(this));
        toggleActive.addClickHandler(new DefaultClickHandler(this));
        toggleArchived.addClickHandler(new DefaultClickHandler(this));
        
        txtSearch.addFocusHandler(new SearchFocusHandler());
        txtSearch.addBlurHandler(new SearchLostFocusHandler());
        txtSearch.addKeyUpHandler(new SearchChangeHandler());
        
        //set defaults
        newClient.setStyleName("appbutton-default");
        editClient.setStyleName("appbutton-default-disabled");
        deleteClient.setStyleName("appbutton-default-disabled");
        archiveClient.setStyleName("appbutton-default-disabled");
        editClient.setEnabled(false);
        deleteClient.setEnabled(false);
        archiveClient.setEnabled(false);
        
        //create sort list.
        for(int i=0;i<sortList.size();i++) {
            item = sortList.get(i);
            sortItem = new GridColSortInfo(item.getColumn().getDataStoreName(), item.isAscending());
            gridColSortList.add(sortItem);
        }
        //call remote service to fetch the data.
        getAllClients();
    }
    
    public void updateTableCount(int count) {
        clientsDataProvider.updateRowCount(count, true);
    }
    
    private void setClientStatusToggle(ClientStatus status) {
        if (status == ClientStatus.ALL) {
            this.clientStatusFilter = ClientStatus.ALL;
            this.toggleAll.setDown(true);
            this.toggleActive.setDown(false);
            this.toggleArchived.setDown(false);
            this.toggleAll.setStyleName("appbutton-default-toggle");
            this.toggleActive.setStyleName("appbutton-default");
            this.toggleArchived.setStyleName("appbutton-default");
        }
        if (status == ClientStatus.ACTIVE) {
            this.clientStatusFilter = ClientStatus.ACTIVE;
            this.toggleAll.setDown(false);
            this.toggleActive.setDown(true);
            this.toggleArchived.setDown(false);
            this.toggleAll.setStyleName("appbutton-default");
            this.toggleActive.setStyleName("appbutton-default-toggle");
            this.toggleArchived.setStyleName("appbutton-default");            
        }
        if (status == ClientStatus.ARCHIVED) {
            this.clientStatusFilter = ClientStatus.ARCHIVED;
            this.toggleAll.setDown(false);
            this.toggleActive.setDown(false);
            this.toggleArchived.setDown(true);
            this.toggleAll.setStyleName("appbutton-default");
            this.toggleActive.setStyleName("appbutton-default");
            this.toggleArchived.setStyleName("appbutton-default-toggle");            
        }
    }
    
    private CellTable createClientsTable(){
        table = new CellTable();
        table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);
        
        
        // Add a text column to show the firstname.
        TextColumn<ClientInfo> firstName =
                new TextColumn<ClientInfo>() {
                    @Override
                    public String getValue(ClientInfo object) {
                        return object.getFirstName();
                    }
                };
        //firstName.setSortable(true);
        table.setColumnWidth(firstName, 100, Style.Unit.PX);
        table.addColumn(firstName, "Firstname");
        
        // Add a text column to show the lastname.
        TextColumn<ClientInfo> lastName =
                new TextColumn<ClientInfo>() {
                    @Override
                    public String getValue(ClientInfo object) {
                        return object.getLastName();
                    }
                };
        //lastName.setSortable(true);
        table.setColumnWidth(lastName, 100, Style.Unit.PX);
        table.addColumn(lastName, "Lastname");
        
        //mobile phone
        TextColumn<ClientInfo> mobilePhone =
                new TextColumn<ClientInfo>() {
                    @Override
                    public String getValue(ClientInfo object) {
                        return object.getMobilePhone();
                    }
                };
        //mobilePhone.setSortable(true);
        table.setColumnWidth(mobilePhone, 140, Style.Unit.PX);
        table.addColumn(mobilePhone, "Mobile Phone");
        
        //email address
        TextColumn<ClientInfo> emailAddr =
                new TextColumn<ClientInfo>() {
                    @Override
                    public String getValue(ClientInfo object) {
                        return object.getEmailAddress();
                    }
                };
        //mobilePhone.setSortable(true);
        table.addColumn(emailAddr, "Email Address");
        
        // Add a text column to show the address.
        TextColumn<ClientInfo> addressColumn
                = new TextColumn<ClientInfo>() {
                    @Override
                    public String getValue(ClientInfo object) {
                        return object.getAddressString() + " " + object.getCityStatePostcodeString();
                    }
                };
        table.addColumn(addressColumn, "Address");
        table.setColumnWidth(addressColumn, 550, Style.Unit.PX);
        clientsDataProvider.addDataDisplay(table);
        
        //column sort handler
        AsyncHandler columnSortHandler = new AsyncHandler(table);
        table.addColumnSortHandler(columnSortHandler);
        
        // We know that the data is sorted alphabetically by default.
        //table.getColumnSortList().push(firstName);
        
        // Add a selection model to handle user selection.
        MultiSelectionModel<ClientInfo> selectionModel;
        selectionModel = new MultiSelectionModel();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new GridSelectionEventHandler());
        table.setWidth(Constants.STANDARD_GRID_WIDTH, true);
        table.setPageSize(Constants.STANDARD_GRID_PAGESIZE);
        table.setEmptyTableWidget(new Label(Constants.EMPTY_DATATABLE_MESSAGE));        
        return table;
    }
    
    private void deleteClients() {
        long[] ids = new long[selection.length];
        
        for (int i=0;i<selection.length;i++) {
            ids[i] = selection[i].getId();
        }
        
        clientService.deleteClients(ids);
    }
    
    private void archiveClients() {
        long[] ids = new long[selection.length];
        
        for (int i=0;i<selection.length;i++) {
            ids[i] = selection[i].getId();
        }
        
        clientService.changeClientStatus(ids, ClientStatus.ARCHIVED);
    }
    
    private void getAllClients() {
        clientFilterText = txtSearch.getText();
        if (clientFilterText.equals(CLIENT_FIRSTNAME_FILTER_TEXT)) {
            clientFilterText = "";
        }
        clientService.getAllClients(
                clientDataRange.getStart(),
                clientDataRange.getLength(),
                gridColSortList,
                clientFilterText,
                clientStatusFilter,
                clientsDataProvider);
        clientService.getClientsCount(clientFilterText, clientStatusFilter);
    }
    
    private class ClientsDataProvider extends AsyncDataProvider<ClientInfo> {
        private final Clients pageReference;
        
        public ClientsDataProvider(Clients reference) {
            this.pageReference = reference;
        }
        
        @Override
        protected void onRangeChanged(HasData<ClientInfo> display) {
            this.pageReference.clientDataRange = display.getVisibleRange();
            this.pageReference.getAllClients();
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    //grid multiselection model.
    private class GridSelectionEventHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            Set<ClientInfo> selected = ((MultiSelectionModel)event.getSource()).getSelectedSet();
            selection = new ClientInfo[selected.size()];
            int i = 0;
            
            if (selected.isEmpty()) {
                editClient.setStyleName("appbutton-default-disabled");
                deleteClient.setStyleName("appbutton-default-disabled");
                archiveClient.setStyleName("appbutton-default-disabled");
                archiveClient.setEnabled(false);
                deleteClient.setEnabled(false);
                editClient.setEnabled(false);
            }
            else {
                if (selected.size() == 1) {
                    editClient.setStyleName("appbutton-default");
                    editClient.setEnabled(true);
                    deleteClient.setStyleName("appbutton-default");
                    deleteClient.setEnabled(true);
                    archiveClient.setStyleName("appbutton-default");
                    archiveClient.setEnabled(true);
                }
                else {
                    editClient.setStyleName("appbutton-default-disabled");
                    editClient.setEnabled(false);
                    deleteClient.setStyleName("appbutton-default");
                    deleteClient.setEnabled(true);
                    archiveClient.setStyleName("appbutton-default");
                    archiveClient.setEnabled(true);
                }
                
                for (ClientInfo info : selected) {
                    selection[i] = info;
                    i++;
                }
            }
        }
    }
    private class DefaultClickHandler implements ClickHandler {
        private final Clients pageReference;
        
        public DefaultClickHandler(Clients reference) {
            this.pageReference = reference;
        }
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == newClient) {
                History.newItem(Views.newclient.toString());
                event.getNativeEvent().preventDefault();
            }
            if (sender == editClient) {
                if (isDemoApplication()) return;                    
                History.newItem(Views.editclient.toString() + "/clientId=" + selection[0].getId());
                event.getNativeEvent().preventDefault();
            }
            if (sender == deleteClient) {
                if (isDemoApplication()) return;                    
                this.pageReference.deleteClients();
            }
            if (sender == archiveClient) {
                if (isDemoApplication()) return;                 
                this.pageReference.archiveClients();
            }
            if (sender == toggleAll) {
                this.pageReference.setClientStatusToggle(ClientStatus.ALL);
                this.pageReference.getAllClients();
                
            }
            if (sender == toggleActive) {
                this.pageReference.setClientStatusToggle(ClientStatus.ACTIVE);
                this.pageReference.getAllClients();
            }
            if (sender == toggleArchived) {
                this.pageReference.setClientStatusToggle(ClientStatus.ARCHIVED);
                this.pageReference.getAllClients();
            }
        }
    }
    private class SearchFocusHandler implements FocusHandler {
        
        @Override
        public void onFocus(FocusEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().equals(CLIENT_FIRSTNAME_FILTER_TEXT)) {
                ((TextBox)sender).setText("");
            }
        }
    }
    private class SearchLostFocusHandler implements BlurHandler {
        @Override
        public void onBlur(BlurEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().isEmpty()) {
                ((TextBox)sender).setText(CLIENT_FIRSTNAME_FILTER_TEXT);
            }
        }
    }
    private class SearchChangeHandler implements KeyUpHandler {
        @Override
        public void onKeyUp(KeyUpEvent event) {
            getAllClients();
        }
    }
    // </editor-fold>
}

