/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.invoicebindercore.shell;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHExecute {
    public static boolean execute(String hostName, String userName, int portName, String password, String command) throws JSchException {
        JSch jsch=new JSch();
        try {
            Session session=jsch.getSession(userName, hostName, portName);
            session.setPassword(password);
            session.connect();
            
        } catch (JSchException ex) {
            throw ex;
        }
        return false;
    }
    
}
