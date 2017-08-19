/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.shared.misc;

/**
 *
 * @author msushil
 */
public class OS {
    private static final String OS = System.getProperty("os.name").toLowerCase();
    
    public static boolean isWindows() {
        return (OS.contains("win"));
    }
    
    public static boolean isMac() {
        return (OS.contains("mac"));
    }
    
    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 );
    }
    
    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }
    
    
}
