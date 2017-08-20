/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.shared.misc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author mon2
 */
public class VariableManager {
    public static String replaceVariables(String msg, HashMap vars) {
        Iterator it = vars.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            msg = msg.replace(pairs.getKey().toString(), pairs.getValue().toString());
            it.remove();
        }
        
        return msg;
    }
}
