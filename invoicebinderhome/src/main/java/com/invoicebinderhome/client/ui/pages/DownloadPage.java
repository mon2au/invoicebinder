/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client.ui.pages;

import com.invoicebinderhome.client.service.download.DownloadServiceClientImpl;
import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.shared.misc.Constants;
import com.invoicebinderhome.shared.model.DownloadInfo;
import com.invoicebinderhome.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class DownloadPage extends Composite {
    
    private static final DownloadPageUiBinder uiBinder = GWT.create(DownloadPageUiBinder.class);
    @UiField HTMLPanel downloadPanel;
    @UiField Label messageLabel;  
    @UiField Button btnDownload;
    @UiField Anchor releaseNotes;
    @UiField Anchor installInstructions;
    @UiField CheckBox checkConditions;    
    
    private final DownloadServiceClientImpl downloadService;
    private final Index index;
    
    public void updateValidationResult(ValidationResult result) {
        Element element;
        element = DOM.getElementById(result.getTagname());
        element.focus();
        this.messageLabel.setStyleName("message-visible");
        this.messageLabel.setText(result.getMessage());
    }

    public void setDownloadInfo(DownloadInfo info) {
        this.btnDownload.setText("Download " + Constants.TITLE + " " + info.getDownloadVersion());
        this.releaseNotes.setText("View release notes for version " + info.getDownloadVersion());
        this.releaseNotes.setHref("#releasenotes");
        this.installInstructions.setHref("#installinstructions");  
        this.btnDownload.addClickHandler(new DefaultClickHandler(this));
    }

    private void download() {
        downloadService.validateAndDownload(checkConditions.getValue());
    }

    interface DownloadPageUiBinder extends UiBinder<Widget, DownloadPage> {
    }
    
    public DownloadPage(Index index) {
        this.index = index;
        this.downloadService = new DownloadServiceClientImpl(GWT.getModuleBaseURL() + "services/download", this.index);        
        initWidget(uiBinder.createAndBindUi(this));
        btnDownload.setStyleName("greenbutton");
        checkConditions.getElement().setId("checkConditions");  
        downloadService.getDownloadInfo(this);
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final DownloadPage pageReference;
        
        public DefaultClickHandler(DownloadPage reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnDownload) {
                this.pageReference.download();
                event.getNativeEvent().preventDefault();
            }
        }
    }    
}
