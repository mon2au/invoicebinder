/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client;

import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.client.ui.history.HistoryHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Main entry point.
 *
 * @author mon2
 */
public class Main implements EntryPoint {
    private final Index index = new Index();
    private HistoryHandler history;
    public String initToken = History.getToken();    

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {
        Window.enableScrolling(true);
        Window.setMargin("0px");
        if (initToken.length() == 0) {
            History.newItem("home");
        }
        
        RootPanel.get().add(index);
        history = new HistoryHandler(index);
        History.addValueChangeHandler(history);
        History.fireCurrentHistoryState();
    }
}
