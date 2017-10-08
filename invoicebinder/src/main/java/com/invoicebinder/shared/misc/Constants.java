/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.misc;

import java.math.BigDecimal;

/**
 *
 * @author mon2
 */
public class Constants {
    //Application Constants
    public static final String TITLE = "Invoice Binder";
    public static final String DEFAULT_BUSINESS_NUM_LABEL = "Business No: ";
    public static final int COOKIE_TIMEOUT = 1000 * 60 * 60 * 24; //one day
    public static final String DEMOAPP_BASE_URL = "demo";
    public static final String BASEAPP_URL = "index.html";
    public static final String HEROKUAPP_URL = "herokuapp";
    public static final String DEMOAPP_MESSAGE = "Action not allowed in demo mode.";
    public static final String CONTEXT_FILENAME = "contextFile";
    public static final String DEFAULT_DBNAME = "invoicebinder";
    
    //Grid Constants
    public static final String EMPTY_STRING = "";
    public static final String EMPTY_DATATABLE_MESSAGE = "no data to display.";
    public static final String STANDARD_GRID_HEIGHT = "575px";
    public static final String STANDARD_GRID_WIDTH = "100%";
    public static final int STANDARD_GRID_PAGESIZE = 20;
    public static final int DASH_GRID_PAGESIZE = 10;
    
    //Form Constants
    public static final int FORM_WIDTH = 500;
    public static final int FORM_ROW_HEIGHT = 20;
    public static final int INVOICE_ITEMS_FORM_WIDTH = 800;
    public static final int PANEL_CELL_SPACING = 5;
    
    //Config Panel Constants
    public static final String CONFIG_PANEL_WIDTH = "400px";
    
    //System Constants
    public static final int LOGO_IMAGE_HEIGHT = 120;
    public static final int LOGO_IMAGE_WIDTH = 240;
    
    //Reports
    public static final int REPORT_DATERANGE_SHOWMONTHS = 6;

    //Encryption
    public static final String ENC_PASS = "encrypt";
    
    //Dates
    public static final String MAX_ENDDATE_LABEL = "Ongoing...";
    
    //Invoice Constants
    public static final BigDecimal TAX_PERCENTAGE = new BigDecimal(.1);
    public static String DEFAULT_TAX_LABEL = "TAX";
    public static String DEFAULT_INVOICETEMPLATE = "Standard";
    public static String DEFAULT_CURRENCY_CODE = "USD";
}
