package com.invoicebinder.client.ui.installation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TerminalPanel extends Composite {
    
    private static final TerminalPanelUiBinder uiBinder = GWT.create(TerminalPanelUiBinder.class);
    @UiField HTMLPanel terminalContent;
    
    interface TerminalPanelUiBinder extends UiBinder<Widget, TerminalPanel> {
    }
    
    public TerminalPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    public void addMessage(String message) {
        Label lblText = new Label();
        lblText.setStyleName("terminalText");
        lblText.setText(message);
        terminalContent.add(lblText);
    }
}
