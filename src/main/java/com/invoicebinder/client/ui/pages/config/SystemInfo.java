/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.config;

import com.invoicebinder.client.service.system.SystemServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.misc.Constants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author msushil
 */
public class SystemInfo extends Composite {
    
    private static final SystemInfoUiBinder uiBinder = GWT.create(SystemInfoUiBinder.class);
    private final Main main;    
    private final SystemServiceClientImpl systemService;
    private final FlexTable tblSystemInfo;    
    private final HorizontalPanel mainPanel;      
    private Label lblBuildVersion;
    private Label lblBuildDate;  
    private Frame frmReleaseNotes;
    @UiField HTMLPanel systemInfoPanel;    

    void setSystemInfo(com.invoicebinder.shared.model.SystemInfo systemInfo) {
        this.lblBuildDate.setText(systemInfo.getBuildDate());
        this.lblBuildVersion.setText(systemInfo.getBuildVersion());
    }
    
    interface SystemInfoUiBinder extends UiBinder<Widget, SystemInfo> {
    }
    
    public SystemInfo(Main main) {
       this.main = main;
        this.systemService = new SystemServiceClientImpl(GWT.getModuleBaseURL() + "services/system", this.main);        
        initWidget(uiBinder.createAndBindUi(this));
        mainPanel = new HorizontalPanel();      
        
        this.tblSystemInfo = getSysInfoTable(); 
        mainPanel.setWidth("100%");
        mainPanel.add(this.tblSystemInfo);
        systemInfoPanel.add(mainPanel);
        
        systemService.getSystemInfo();        
    }
    
    private FlexTable getSysInfoTable() {
        FlexTable table = new FlexTable();
        Label lblSystemInfoHeader = new Label();
        
        lblBuildVersion = new Label();
        lblBuildDate = new Label();
        frmReleaseNotes = new Frame();
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);

        lblSystemInfoHeader.getElement().setInnerHTML("<h6><span>System Information</span></h6>");
        frmReleaseNotes.setStyleName("release-notes-frame");
        frmReleaseNotes.setSize("100%", "100%");
        frmReleaseNotes.setHeight("700px");
        frmReleaseNotes.setUrl("release/releasenotes.html");
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);
        table.setWidget(0, 0, lblSystemInfoHeader);
        table.setHTML(1, 0, "Build Version: ");
        table.setWidget(1, 1, lblBuildVersion);
        table.setHTML(2, 0, "Build Date:");
        table.setWidget(2, 1, lblBuildDate);
        table.getFlexCellFormatter().setColSpan(4, 0, 2);
        table.getFlexCellFormatter().setColSpan(4, 1, 2);
        table.setHTML(4, 0, "Release Notes: ");
        table.getFlexCellFormatter().setColSpan(5, 0, 2);
        table.getFlexCellFormatter().setColSpan(5, 1, 2);
        table.setWidget(5, 0, frmReleaseNotes);
        return table;
    }    
}
