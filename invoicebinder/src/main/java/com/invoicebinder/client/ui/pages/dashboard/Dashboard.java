/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.dashboard;

import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.service.dashboard.DashboardServiceClientImpl;
import com.invoicebinder.client.service.invoice.InvoiceServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.config.ApplicationSettingsItems;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.DashStatsInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.ReportDateInfo;
import com.invoicebinder.shared.model.ReportInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
/**
 *
 * @author mon2
 */
public class Dashboard extends Composite {
    
    private static final DashboardUiBinder uiBinder = GWT.create(DashboardUiBinder.class);
    private final FlowPanel chartPanel;
    @UiField HTMLPanel dashboard;
    @UiField HTMLPanel statsPanel;
    @UiField Label lblExpenseLastMonth;
    @UiField Label lblIncomeLastMonth;
    @UiField Label lblExpenseThisMonth;
    @UiField Label lblIncomeThisMonth;
    @UiField Label lblExpenseYTD;
    @UiField Label lblIncomeYTD;
    @UiField Label lblApplicationTitle;
    @UiField HTMLPanel gridPanel;
    private final Main main;
    private CellTable<InvoiceInfo> table;
    private final SimplePager pager;
    private final VerticalPanel gridDataPanel;
    private final InvoicesDataProvider invoicesDataProvider;
    private Range invoiceDataRange;
    private InvoiceInfo[] selection;
    private final InvoiceServiceClientImpl invoiceService;
    private final DashboardServiceClientImpl dashService;
    private final ConfigServiceClientImpl configService;
    private final ArrayList<GridColSortInfo> gridColSortList;
    
    public void setConfigData(ConfigurationSection configurationSection, ArrayList<ConfigData> arrayList) {
        HashMap<String, String> configData = new HashMap();
        if (!arrayList.isEmpty()) {
            for (ConfigData data : arrayList) {
                configData.put(data.getKey(), data.getValue());
            }
            
            if ( configurationSection == ConfigurationSection.ApplicationSettings) {
                lblApplicationTitle.setText(configData.get(ApplicationSettingsItems.APPTITLE.toString()));
            }
        }
    }
    
    interface DashboardUiBinder extends UiBinder<Widget, Dashboard> {
    }
    
    public Dashboard(Main main) {
        this.main = main;
        this.invoiceService = new InvoiceServiceClientImpl(GWT.getModuleBaseURL() + "services/invoice", this.main);
        this.dashService = new DashboardServiceClientImpl(GWT.getModuleBaseURL() + "services/dash", this.main);
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        gridColSortList = new ArrayList<GridColSortInfo>();
        invoicesDataProvider = new InvoicesDataProvider(this);
        chartPanel = new FlowPanel();
        pager = new SimplePager();
        gridDataPanel = new VerticalPanel();
        table = getOverdueInvoicesTable();
        pager.setDisplay(table);
        gridDataPanel.add(table);
        gridDataPanel.setHeight("300px");
        gridPanel.add(gridDataPanel);
        gridPanel.add(pager);
        statsPanel.add(chartPanel);
        dashService.getIncomeAndExpense(getReportDateInfo().getMonthVal(), getReportDateInfo().getYearVal(), Constants.REPORT_DATERANGE_SHOWMONTHS);
        dashService.getDashboardStats();
        configService.loadAppSettingConfigDataForDashboardPage();
    }
    
    private CellTable getOverdueInvoicesTable(){
        table = new CellTable();
        table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);
        
        TextColumn<InvoiceInfo> clientName =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getClientName();
                    }
                };
        table.addColumn(clientName, "Client Name");
        
        TextColumn<InvoiceInfo> invoiceNo =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getInvoiceNumber();
                    }
                };
        table.addColumn(invoiceNo, "Invoice No");
        
        // Add a text column to show the amount.
        TextColumn<InvoiceInfo> amount =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        if (object.getAmount() != null) {
                            return object.getAmount().toString();
                        }
                        else {
                            return "";
                        }
                    }
                };
        table.addColumn(amount, "Amount");
        
        // Add a text column to show the date.
        TextColumn<InvoiceInfo> invoiceDate =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getInvoiceDate().toString();
                    }
                };
        table.addColumn(invoiceDate, "Invoice Date");
        
        // Add a text column to show the date.
        TextColumn<InvoiceInfo> dueDate =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getDueDate().toString();
                    }
                };
        table.addColumn(dueDate, "Due Date");
        
        invoicesDataProvider.addDataDisplay(table);
        
        // Add a selection model to handle user selection.
        MultiSelectionModel<InvoiceInfo> selectionModel;
        selectionModel = new MultiSelectionModel();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new GridSelectionEventHandler());
        table.setWidth(Constants.STANDARD_GRID_WIDTH, true);
        table.setPageSize(Constants.DASH_GRID_PAGESIZE);
        table.setEmptyTableWidget(new Label(Constants.EMPTY_DATATABLE_MESSAGE));
        return table;
    }
    
    private ReportDateInfo getReportDateInfo() {
        ReportDateInfo info = new ReportDateInfo();
        DateTimeFormat dateFormat;
        Date date = new Date();
        
        dateFormat= DateTimeFormat.getFormat("MM");
        CalendarUtil.addMonthsToDate(date, -(Constants.REPORT_DATERANGE_SHOWMONTHS - 1));
        info.setMonthVal(Integer.parseInt(dateFormat.format(date)));
        dateFormat= DateTimeFormat.getFormat("yyyy");
        info.setYearVal(Integer.parseInt(dateFormat.format(date)));
        return info;
    }
    
    private Options getOptions() {
        Options options = Options.create();
        options.setWidth(515);
        options.setHeight(220);
        options.setTitle("Income & Expense (last 6 months)");
        return options;
    }
    
    public void setReportData(final List<ReportInfo> list) {
        Runnable onLoadCallback = new Runnable() {
            @Override
            public void run() {
                ColumnChart area = new ColumnChart(getDataTable(list), getOptions());
                chartPanel.clear();
                chartPanel.add(area);
                chartPanel.addStyleName("report-chart-dashboard");
            }
        };
        VisualizationUtils.loadVisualizationApi(onLoadCallback, ColumnChart.PACKAGE);
    }
    
    public void updateStats(DashStatsInfo dashStatsInfo) {
        lblExpenseLastMonth.setText("$" + String.valueOf(dashStatsInfo.getExpenseLastMonth()));
        lblIncomeLastMonth.setText("$" + String.valueOf(dashStatsInfo.getIncomeLastMonth()));
        lblExpenseThisMonth.setText("$" + String.valueOf(dashStatsInfo.getExpenseThisMonth()));
        lblIncomeThisMonth.setText("$" + String.valueOf(dashStatsInfo.getIncomeThisMonth()));
        lblExpenseYTD.setText("$" + String.valueOf(dashStatsInfo.getExpenseYTD()));
        lblIncomeYTD.setText("$" + String.valueOf(dashStatsInfo.getIncomeYTD()));
    }
    
    private AbstractDataTable getDataTable(List<ReportInfo> reportData) {
        DataTable data = DataTable.create();
        String[] items = new String[] { "Income", "Expenses" };
        int counter = 0;
        
        data.addColumn(AbstractDataTable.ColumnType.STRING, "Year");
        for (String item : items) {
            data.addColumn(AbstractDataTable.ColumnType.NUMBER, item);
        }
        
        data.addRows(reportData.size());
        for (ReportInfo info:reportData) {
            data.setValue(counter, 0, info.getMonth() + "-" + info.getYear());
            data.setValue(counter, 1, Float.parseFloat(info.getField1()));
            data.setValue(counter, 2, Float.parseFloat(info.getField2()));
            counter++;
        }
        return data;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Data Provider">
    private class InvoicesDataProvider extends AsyncDataProvider<InvoiceInfo> {
        private final Dashboard pageReference;
        
        public InvoicesDataProvider(Dashboard reference) {
            this.pageReference = reference;
        }
        
        @Override
        protected void onRangeChanged(HasData<InvoiceInfo> display) {
            this.pageReference.invoiceDataRange = display.getVisibleRange();
            this.pageReference.getAllOverdueInvoices();
        }
    }
    // </editor-fold>
    
    private class GridSelectionEventHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            Set<InvoiceInfo> selected = ((MultiSelectionModel)event.getSource()).getSelectedSet();
            selection = new InvoiceInfo[selected.size()];
            int i = 0;
            
            for (InvoiceInfo info : selected) {
                selection[i] = info;
                i++;
            }
        }
    }
    
    private void getAllOverdueInvoices() {
        invoiceService.getAllOverdueInvoices(
                invoiceDataRange.getStart(),
                invoiceDataRange.getLength(),
                gridColSortList,
                invoicesDataProvider);
        invoiceService.getOverdueInvoicesCount();
    }
    
    public void updateTableCount(Integer count) {
        invoicesDataProvider.updateRowCount(count, true);
    }
}