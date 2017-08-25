/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.core.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager {
    public static Boolean writeFile(String fileName, String content) throws IOException {
        Boolean response = false;
        
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
                response = true;
            }
            catch (Exception e) {
                throw e;
            }
        } catch (IOException e) {
            throw e;
        }
        
        return response;
    }
    
    public static File getFile(String fileName, String path) {
        File file = new File(path + "/" + fileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }
    
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
