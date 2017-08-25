/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.core.misc;

public class PasswordGenerator {
    
    public static String generatePassword() {
        String passwd;
        int len=8;
        char[] pwd = new char[len];
        int c = 'A';
        int rand;
        for (int i=0; i < 8; i++)
        {
            rand = (int)(Math.random() * 3);
            switch(rand) {
                case 0: c = '0' + (int)(Math.random() * 10); break;
                case 1: c = 'a' + (int)(Math.random() * 26); break;
                case 2: c = 'A' + (int)(Math.random() * 26); break;
            }
            pwd[i] = (char)c;
        }
        passwd = new String(pwd);
        return passwd;
    }
}