/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.shared.misc;

import java.sql.Timestamp;

/**
 *
 * @author mon2
 */
public class Utils {
    public static Timestamp getMaxDateTimeValue() {
        Timestamp ts = Timestamp.valueOf("9999-12-31 23:59:59");
        return ts;
    }   
}
