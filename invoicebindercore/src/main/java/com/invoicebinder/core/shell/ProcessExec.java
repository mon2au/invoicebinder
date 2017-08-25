/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.core.shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class ProcessExec {
    public static boolean executeScript(String scriptName, String scriptPath, String[] args) throws IOException,InterruptedException  {
        boolean result;
        String parameters[] = new String[args.length + 2];
        int counter = 0;
        
        try {
            parameters[counter] = "/bin/sh";
            counter ++;
            parameters[counter] = "./" + scriptName;
            counter++;
            
            for(String item: args) {
                parameters[counter] = item;
                counter ++;
            }
            
            ProcessBuilder pb = new ProcessBuilder(parameters);
            pb.directory(new File(scriptPath));
            Process p = pb.start();
            int shellExitStatus = p.waitFor();
            result = shellExitStatus == 0;
            return result;
        } catch (IOException | InterruptedException ex) {
            throw ex;
        }
    }
    
    public static boolean executeScriptAsRoot(String rootPassword, String scriptName, String scriptPath, String[] args) throws IOException,InterruptedException {
        boolean result;
        int paramCount = 8 + args.length;
        String parameters[] = new String[paramCount];
        int counter = 0;
        File outFile = new File("/home/mon2/app.txt");
        
        try {
            parameters[counter] = "/bin/sh";
            counter ++;
            parameters[counter] = "-c";
            counter ++;
            parameters[counter] = "echo";
            counter ++;
            parameters[counter] = rootPassword;
            counter ++;
            parameters[counter] = "|";
            counter ++;
            parameters[counter] = "sudo";
            counter ++;
            parameters[counter] = "-S";
            counter++;
            parameters[counter] = "./" + scriptName;
            counter++;
            
            for(String item: args) {
                parameters[counter] = item;
                counter ++;
            }
            
            ProcessBuilder pb = new ProcessBuilder(parameters);
            pb.directory(new File(scriptPath));
            pb.redirectOutput(outFile);
            Process p = pb.start();
            int shellExitStatus = p.waitFor();
            result = shellExitStatus == 0;
            return result;
        } catch (IOException | InterruptedException ex) {
            throw ex;          
        }
    }
    
    public static boolean whoAmI() throws IOException,InterruptedException {
        String parameters[] = new String[1];
        File outFile = new File("/test/test.txt");
        Boolean result;
        
        try {
            parameters[0] = "/usr/bin/whoami";
            
            ProcessBuilder pb = new ProcessBuilder(parameters);
            pb.directory(new File("/test/"));
            pb.redirectOutput(outFile);
            Process p = pb.start();
            int shellExitStatus = p.waitFor();
            result = shellExitStatus == 0;
        } catch (IOException | InterruptedException ex) {
            throw ex;
        }
        return result;
    }
    
    public static void writeScriptFolder(String scriptFolder) throws IOException,InterruptedException {
        
        String fileName = "/home/mon2/script-folder.txt";
        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            writer.println(scriptFolder);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            throw ex;
        }
    }
}
