/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.controller;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public class ViewManager {
    private static ArrayList<String> getStandardViews() {
        ArrayList<String> viewList = new ArrayList<>();
        viewList.add(Views.home.toString());
        viewList.add(Views.services.toString());
        viewList.add(Views.contactus.toString());
        viewList.add(Views.aboutus.toString());
        return viewList;
    }

    private static ArrayList<String> getAutoAuthenticatedViews() {
        ArrayList<String> viewList = new ArrayList<>();
        viewList.add(Views.auto_showinvoice.toString());
        viewList.add(Views.autologin.toString());
        return viewList;
    }

    private static ArrayList<String> getAuthenticatedViews() {
        ArrayList<String> viewList = new ArrayList<>();
        viewList.add(Views.logout.toString());
        viewList.add(Views.dashboard.toString());
        viewList.add(Views.settings_business.toString());
        viewList.add(Views.settings_email.toString());
        viewList.add(Views.settings_application.toString());
        viewList.add(Views.settings_customattribs.toString());
        viewList.add(Views.settings_account.toString());       
        viewList.add(Views.settings_sysinfo.toString());   
        viewList.add(Views.clients.toString());
        viewList.add(Views.newclient.toString());
        viewList.add(Views.editclient.toString());
        viewList.add(Views.products.toString());
        viewList.add(Views.newproduct.toString());
        viewList.add(Views.editproduct.toString());
        viewList.add(Views.expenses.toString());
        viewList.add(Views.newexpense.toString());
        viewList.add(Views.editexpense.toString());
        viewList.add(Views.invoices.toString());
        viewList.add(Views.newinvoice.toString());
        viewList.add(Views.editinvoice.toString());
        viewList.add(Views.showinvoice.toString());
        viewList.add(Views.previewinvoice.toString());
        viewList.add(Views.useraccountdetails.toString());
        viewList.add(Views.reports.toString());
        viewList.add(Views.rptincomeexpense.toString());
        viewList.add(Views.rptsalespayments.toString());
        viewList.add(Views.newuser.toString());
        viewList.add(Views.users.toString());
        viewList.add(Views.payments.toString());
        viewList.add(Views.newpayment.toString());        
        return viewList;
    }
    
    public static boolean isViewAuthenticated(String viewToken) {
        Boolean result = false;

        for (String view : getAuthenticatedViews()) {
            if (viewToken.contains(view)) {
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    public static boolean isViewStandard(String viewToken) {
        Boolean result = false;

        for (String view : getStandardViews()) {
            if (viewToken.contains(view)) {
                result = true;
                break;
            }
        }
        
        return result;
    }

    public static boolean isViewAutoAuthenticated(String viewToken) {
        Boolean result = false;

        for (String view : getAutoAuthenticatedViews()) {
            if (viewToken.contains(view)) {
                result = true;
                break;
            }
        }

        return result;
    }
}
