/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.server.crypto;

import com.invoicebinderhome.shared.misc.Constants;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author mon2
 */
public class Encrypt {
    public static String getEncryptedString(String input) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(Constants.ENC_PASS);
        return textEncryptor.encrypt(input);  
    }
}
