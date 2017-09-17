/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.controller;

/**
 *
 * @author mon2
 */
public enum Views {
    //installation views
    install,
    installdbtest,
    installdbcreate,
    installappconfig,
    installappemail,
    
    //standard views
    login,
    home,
    services,
    aboutus,
    contactus,

    //autologin views
    autologin,
    auto_showinvoice,
    auto_showpaypal_notify,

    //authenticated views others
    logout,
    dashboard,
    
    //settings tabs
    settings_business,
    settings_email,
    settings_customattribs,
    settings_application,
    settings_account,
    settings_sysinfo,
    settings_templates,
    
    //authenticated views users
    users,
    newuser,
    edituser,
    useraccountdetails,
    
    //authenticated views clients
    clients,
    newclient,
    editclient,

    //authenticated views products
    products,
    newproduct,
    editproduct,
    
    //authenticated views expenses
    expenses,
    newexpense,
    editexpense,   
    
    //authenticated views invoices
    invoices,
    newinvoice,
    editinvoice,
    showinvoice,
    previewinvoice,
    
    //authenticated views payments
    payments,
    newpayment,   
    
    //authenticated views reports
    reports,
    rptincomeexpense,
    rptpopularproducts,
    rptsalespayments,
    
    //session
    sessionexpired
}

