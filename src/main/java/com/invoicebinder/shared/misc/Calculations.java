/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.shared.misc;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author mon2
 */
public class Calculations {
    public static Date getFinancialYearStartDate() {
        Date date = null;
        Calendar now = Calendar.getInstance();
        String startDate;
        int yearVal;
        int monthVal;
        SimpleDateFormat formatter;
        monthVal = now.get(Calendar.MONTH);
        yearVal = now.get(Calendar.YEAR);
        if (monthVal <= 6) {
            yearVal--;
        }

        try {
            startDate = String.valueOf(yearVal) + "-07-01";            
            formatter = new SimpleDateFormat("yyyy-MM-dd");            
            date = formatter.parse(startDate);
        } catch (java.text.ParseException ex) {
            ServerLogManager.writeErrorLog(Calculations.class, getFormattedExceptionMessage(ExceptionType.BusinessLogicException, ex));              
        }
        return date;
    }

    public static Date getFinancialYearEndDate() {
        Date date = null;
        Calendar now = Calendar.getInstance();
        String startDate;
        int yearVal;
        int monthVal;        
        SimpleDateFormat formatter;
        monthVal = now.get(Calendar.MONTH);     
        yearVal = now.get(Calendar.YEAR);
        if (monthVal > 6) {
            yearVal++;
        }        

        try {
            startDate = String.valueOf(yearVal) + "-06-30";
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(startDate);
        } catch (java.text.ParseException ex) {
            ServerLogManager.writeErrorLog(Calculations.class, getFormattedExceptionMessage(ExceptionType.BusinessLogicException, ex));   
        }
        return date;
    }    

    public static Date getLastMonthStartDate() {
        Date date = null;
        Calendar now = Calendar.getInstance();
        String startDate;
        int monthVal;
        int yearVal;        
        SimpleDateFormat formatter;
        monthVal = now.get(Calendar.MONTH);
        yearVal = now.get(Calendar.YEAR);
        
        if (monthVal == 0) {
            yearVal --;
            monthVal = 12;
        }

        try {
            startDate = String.valueOf(yearVal) + "-" + String.valueOf(monthVal) + "-01";            
            formatter = new SimpleDateFormat("yyyy-MM-dd");            
            date = formatter.parse(startDate);
        } catch (java.text.ParseException ex) {
            ServerLogManager.writeErrorLog(Calculations.class, getFormattedExceptionMessage(ExceptionType.BusinessLogicException, ex));   
        }
        return date;
    }

    public static Object getLastMonthEndDate() {
        Date date = null;
        Calendar now = Calendar.getInstance();
        String strDate;
        int monthVal;
        int yearVal;          
        int dateVal;
        SimpleDateFormat formatter;
        monthVal = now.get(Calendar.MONTH);
        dateVal = now.getActualMaximum(Calendar.DAY_OF_MONTH);
        yearVal = now.get(Calendar.YEAR);

        if (monthVal == 0) {
            yearVal --;
            monthVal = 12;
        }
        
        try {
            strDate = String.valueOf(yearVal) + "-" + String.valueOf(monthVal) + "-" + String.valueOf(dateVal);            
            formatter = new SimpleDateFormat("yyyy-MM-dd");            
            date = formatter.parse(strDate);
        } catch (java.text.ParseException ex) {
            ServerLogManager.writeErrorLog(Calculations.class, getFormattedExceptionMessage(ExceptionType.BusinessLogicException, ex));  
        }
        return date;
    }

    public static Date getThisMonthStartDate() {
        Date date = null;
        Calendar now = Calendar.getInstance();
        String startDate;
        int monthVal;
        SimpleDateFormat formatter;
        monthVal = now.get(Calendar.MONTH) + 1;

        try {
            startDate = String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf(monthVal) + "-01";            
            formatter = new SimpleDateFormat("yyyy-MM-dd");            
            date = formatter.parse(startDate);
        } catch (java.text.ParseException ex) {
            ServerLogManager.writeErrorLog(Calculations.class, getFormattedExceptionMessage(ExceptionType.BusinessLogicException, ex));   
        }
        return date;
    }

    public static Object getThisMonthEndDate() {
        Date date = null;
        Calendar now = Calendar.getInstance();
        String strDate;
        int monthVal;
        int dateVal;
        SimpleDateFormat formatter;
        now.setTime(new Date());
        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
        monthVal = now.get(Calendar.MONTH) + 1;
        dateVal = now.get(Calendar.DATE);        
        
        try {
            strDate = String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf(monthVal) + "-" + String.valueOf(dateVal);            
            formatter = new SimpleDateFormat("yyyy-MM-dd");            
            date = formatter.parse(strDate);
        } catch (java.text.ParseException ex) {
            ServerLogManager.writeErrorLog(Calculations.class, getFormattedExceptionMessage(ExceptionType.BusinessLogicException, ex));    
        }
        return date;
    }
}
