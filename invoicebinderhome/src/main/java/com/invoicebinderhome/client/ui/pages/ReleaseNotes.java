/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinderhome.client.ui.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class ReleaseNotes extends Composite {
    
    @UiField HTMLPanel relNotesPanel;
    private final Frame frmReleaseNotes;
    
    private static final ReleaseNotesUiBinder uiBinder = GWT.create(ReleaseNotesUiBinder.class);

    interface ReleaseNotesUiBinder extends UiBinder<Widget, ReleaseNotes> {
    } 
    
    public ReleaseNotes() {
        initWidget(uiBinder.createAndBindUi(this));
        
        frmReleaseNotes = new Frame();
        frmReleaseNotes.setStyleName("release-notes-frame");
        frmReleaseNotes.setSize("100%", "100%");
        frmReleaseNotes.setHeight("700px");
        frmReleaseNotes.setUrl("release/releasenotes.html");     
        relNotesPanel.add(frmReleaseNotes);
    }   
}
