/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.invoicebindercore.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ShellExecute {
    public static ShellExecuteResult executeCommand(String command) throws IOException, InterruptedException  {
        Process p;
        StringBuilder msg = new StringBuilder();
        String s;
        ShellExecuteResult result = new ShellExecuteResult();
        
        try {
            //assume execution status to true.
            result.setSuccess(true);
            
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            // read the standard output from the command
            msg.append("Command: [").append(command).append("]");
            msg.append("Output: [");
            while ((s = stdInput.readLine()) != null) {
                msg.append(s);
            }
            msg.append("]");
            result.setStandardOutput(msg.toString());
            
            //record any errors.
            if (p.exitValue() != 0) {
                result.setSuccess(false);
                msg = new StringBuilder();
                msg.append("Error: [");
                while ((s = stdError.readLine()) != null) {
                    msg.append(s);
                }
                msg.append("]");                
                result.setErrorOutput(msg.toString());
            }
            
            return result;
        } catch (IOException | InterruptedException ex) {
            result.setSuccess(false);
            throw ex;
        }
    }
    
    public static ShellExecuteResult executeCommand(String... command) throws IOException, InterruptedException  {
        Process p;
        StringBuilder msg = new StringBuilder();
        String s;
        ShellExecuteResult result = new ShellExecuteResult();
        
        try {
            //assume execution status to true.
            result.setSuccess(true);
            
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            // read the standard output from the command
            msg.append("Standard output from command: ").append(Arrays.toString(command));
            while ((s = stdInput.readLine()) != null) {
                msg.append(s);
            }
            result.setStandardOutput(msg.toString());
            
            //record any errors.
            if (p.exitValue() != 0) {
                result.setSuccess(false);
                msg = new StringBuilder();
                msg.append("Here is the standard error of the command (if any): ").append(Arrays.toString(command));
                while ((s = stdError.readLine()) != null) {
                    msg.append(s);
                }
                result.setErrorOutput(msg.toString());
            }
            
            return result;
            
        } catch (IOException | InterruptedException ex) {
            throw ex;
        }
    }
}
