/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.client.ui.history;

import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.client.ui.controller.Views;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class HistoryHandler  implements ValueChangeHandler<String>{
        private final Index indexPanel;

    public HistoryHandler(Index index) {
        this.indexPanel = index;
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String historyToken = event.getValue();

        if(historyToken.equals(Views.home.toString())) {
            indexPanel.showHome();
        }        
        if(historyToken.equals(Views.tour.toString())) {
            indexPanel.showTour();
        }
        if(historyToken.equals(Views.contactus.toString())) {
            indexPanel.showContactUs();
        }
        if(historyToken.contains(Views.terms.toString())) {
            indexPanel.showTermsPage();
        }
        if(historyToken.contains(Views.demo.toString())) {
            indexPanel.showDemoPage();
        }        
        if(historyToken.contains(Views.download.toString())) {
            indexPanel.showDownloadPage();
        }
        if(historyToken.contains(Views.releasenotes.toString())) {
            indexPanel.showReleaseNotesPage();
        }
        if(historyToken.contains(Views.installinstructions.toString())) {
            indexPanel.showInstallationInstructions();
        }
    }
}
