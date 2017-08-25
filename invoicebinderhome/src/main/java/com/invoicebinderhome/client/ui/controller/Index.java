/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinderhome.client.ui.controller;

import com.invoicebinderhome.client.ui.container.Container;
import com.invoicebinderhome.client.ui.footer.Footer;
import com.invoicebinderhome.client.ui.hdrmenu.HdrMenu;
import com.invoicebinderhome.shared.enums.Menu;
import com.invoicebinderhome.shared.model.DemoConfigInfo;
import com.invoicebinderhome.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class Index extends Composite {
    
    private static final IndexUiBinder uiBinder = GWT.create(IndexUiBinder.class);
    private final HdrMenu hdrMenu;
    private final Container container;
    private final Footer footer;
    @UiField HTMLPanel indexPanel;
    
    public HTMLPanel getIndexPanel() {
        return indexPanel;    
    }

    public Container getContainer() {
        return container;
    }
    
    public void showHome() {
        this.hdrMenu.setActiveMenuItem(Menu.HOME);
        this.container.showHome();
    }
    
    public void showTour() {
        this.hdrMenu.setActiveMenuItem(Menu.TOUR);
        this.container.showTour();
    }

    public void showDownloadPage() {
        this.hdrMenu.setActiveMenuItem(Menu.DOWNLOAD);        
        this.container.showDownload();
    }    

    public void showContactUs() {
        this.hdrMenu.setActiveMenuItem(Menu.CONTACTUS);        
        this.container.showContactUs();
    }
    
    public void showTermsPage() {   
        this.hdrMenu.setActiveMenuItem();        
        this.container.showTermsPage();
    }
    
    public void showDemoPage() {
        this.hdrMenu.setActiveMenuItem(Menu.DEMO);               
        this.container.showDemoPage();
    }

    public void showReleaseNotesPage() {            
        this.container.showReleaseNotesPage();
    }    

    public void updateContactUsMessageResponse(Boolean result) {
        this.container.updateContactUsMessageResponse(result);
    }
    
    public void updateValidationsForContactUsPage(ValidationResult result) {
        this.container.updateValidationsForContactUsPage(result);
    }

    public void updateDemoConfigDetails(DemoConfigInfo demoConfigInfo) {
        this.container.updateDemoConfigDetails(demoConfigInfo);
    }

    public void updateValidationsForDownloadPage(ValidationResult result) {
        this.container.updateValidationForDownloadPage(result);
    }

    public void showInstallationInstructions() {
        this.container.showInstallInstructions();
    }
 
    interface IndexUiBinder extends UiBinder<Widget, Index> {
    }
    
    public Index() {
        initWidget(uiBinder.createAndBindUi(this));
        hdrMenu = new HdrMenu();
        container = new Container(this);
        footer = new Footer();
        this.indexPanel.getElement().setId("mainIndexPanel");
        //add items to the index panel
        this.indexPanel.add(hdrMenu);
        this.indexPanel.add(container);
        this.indexPanel.add(footer);
    }
}
