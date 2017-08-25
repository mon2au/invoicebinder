/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client.ui.container;

import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.client.ui.pages.ContactUs;
import com.invoicebinderhome.client.ui.pages.Demo;
import com.invoicebinderhome.client.ui.pages.DownloadPage;
import com.invoicebinderhome.client.ui.pages.HomePage;
import com.invoicebinderhome.client.ui.pages.InstallationSteps;
import com.invoicebinderhome.client.ui.pages.ReleaseNotes;
import com.invoicebinderhome.client.ui.pages.Terms;
import com.invoicebinderhome.client.ui.pages.Tour;
import com.invoicebinderhome.shared.model.DemoConfigInfo;
import com.invoicebinderhome.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class Container extends Composite {
    
    private static final ContainerUiBinder uiBinder = GWT.create(ContainerUiBinder.class);
    private final Index indexPage;
    private final HomePage home;
    private Tour tour;
    private DownloadPage download;
    private ContactUs contactus;
    private Terms terms;
    private Demo demo;
    private ReleaseNotes relNotes;
    private InstallationSteps installInstructions;
        
    @UiField HTMLPanel containerPanel;    
    
    public HTMLPanel getContainerPanel() {
        return containerPanel;
    }    

    public void showHome() {
        this.containerPanel.clear();
        this.containerPanel.add(home);
    }

    public void showTour() {
        this.tour = new Tour();
        this.containerPanel.clear();
        this.containerPanel.add(tour);
    }
    
    public void showDownload() {
        this.download = new DownloadPage(indexPage);
        this.containerPanel.clear();
        this.containerPanel.add(download);        
    }    

    public void showContactUs() {
        this.contactus = new ContactUs(indexPage);
        this.containerPanel.clear();
        this.containerPanel.add(contactus);
    }

    public void showTermsPage() {
        this.terms = new Terms();
        this.containerPanel.clear();
        this.containerPanel.add(terms);
    }

    public void showDemoPage() {
        this.demo = new Demo(indexPage);
        this.containerPanel.clear();
        this.containerPanel.add(demo);
    }    

    public void updateContactUsMessageResponse(Boolean result) {
        this.contactus.updateMessageResponse(result);
    }

    public void updateValidationsForContactUsPage(ValidationResult result) {
        this.contactus.updateValidations(result);
    }

    public void updateDemoConfigDetails(DemoConfigInfo demoConfigInfo) {
        this.demo.updateConfigDetails(demoConfigInfo);
    }

    public void updateValidationForDownloadPage(ValidationResult result) {
        this.download.updateValidationResult(result);
    }

    public void showReleaseNotesPage() {
        this.relNotes = new ReleaseNotes();
        this.containerPanel.clear();
        this.containerPanel.add(relNotes);        
    }

    public void showInstallInstructions() {
        this.installInstructions = new InstallationSteps();
        this.containerPanel.clear();
        this.containerPanel.add(installInstructions);
    }
  
    interface ContainerUiBinder extends UiBinder<Widget, Container> {
    }
    
    public Container(Index indexPage) {
        this.indexPage = indexPage;   
        initWidget(uiBinder.createAndBindUi(this));
        this.containerPanel.getElement().setId("mainContainerPanel");
        home = new HomePage(indexPage);
        containerPanel.add(home);        
    }
}
