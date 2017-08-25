/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.reports;

import com.invoicebinder.client.service.report.ReportServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.ReportDateInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;

import java.util.Date;
import java.util.List;

/**
 *
 * @author mon2
 */
public class PopularProducts extends Composite {
    
    private static final PopularProductsUiBinder uiBinder = GWT.create(PopularProductsUiBinder.class);
    @UiField HorizontalPanel reportDateRangePanel;
    @UiField HTMLPanel reportGraphPanel;
    private final FlowPanel chartPanel;
    private Label lblReportHeader;
    private Button btnRefresh;
    private ListBox lstMonthYear;
    
    private final ReportServiceClientImpl service;
    private final Main main;    

    PopularProducts(Main main) {
        this.main = main;
        this.service = new ReportServiceClientImpl(GWT.getModuleBaseURL() + "services/report", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        chartPanel = new FlowPanel();
        reportDateRangePanel.setWidth("100%");
        reportDateRangePanel.add(createReportsDateRangeTable());
        reportGraphPanel.setWidth("100%");
        reportGraphPanel.add(chartPanel);
        //service.getIncomeAndExpense(getReportDateInfo().getMonthVal(), getReportDateInfo().getYearVal(), Constants.REPORT_DATERANGE_SHOWMONTHS);
    }
    
    private FlexTable createReportsDateRangeTable() {
        FlexTable table = new FlexTable();
        lblReportHeader = new Label();
        HorizontalPanel panel = new HorizontalPanel();
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM-yyyy");
        Date date = new Date();
        btnRefresh = new Button();
        lstMonthYear = new ListBox();
        lstMonthYear.setStyleName("list-box");
        lstMonthYear.addChangeHandler(new DateChangeHandler());
        
        CalendarUtil.addMonthsToDate(date, -(Constants.REPORT_DATERANGE_SHOWMONTHS - 1));
        for (int i=0;i<Constants.REPORT_DATERANGE_SHOWMONTHS;i++) {
            lstMonthYear.addItem(dateFormat.format(date));
            CalendarUtil.addMonthsToDate(date, 1);
        }
        
        lblReportHeader.getElement().setInnerHTML("<h6><span>Reports > Income/Expenses</span></h6>");
        //create buttons panel.
        btnRefresh.setText("Refresh");
        btnRefresh.setStyleName("appbutton-default");
        panel.add(lstMonthYear);
        panel.add(btnRefresh);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setWidget(0, 0, lblReportHeader);
        table.setWidget(0, 1, panel);
        table.getFlexCellFormatter().setWidth(0,0,"50%");
        return table;
    }    

    void setReportData(List list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    interface PopularProductsUiBinder extends UiBinder<Widget, PopularProducts> {
    }
    
    private ReportDateInfo getReportDateInfo() {
        ReportDateInfo info = new ReportDateInfo();
        String selectedDate;
        selectedDate = lstMonthYear.getValue(lstMonthYear.getSelectedIndex());
        info.setMonthVal(Integer.parseInt(selectedDate.split("-")[0]));
        info.setYearVal(Integer.parseInt(selectedDate.split("-")[1]));
        return info;
    }
    
    private class DateChangeHandler implements ChangeHandler {
        @Override
        public void onChange(ChangeEvent event) {
            //service.getIncomeAndExpense(getReportDateInfo().getMonthVal(), getReportDateInfo().getYearVal(), Constants.REPORT_DATERANGE_SHOWMONTHS);
        }
    }    
}
