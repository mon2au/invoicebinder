/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.server.core.pdf;

import com.invoicebinder.server.core.shell.ShellExecute;
import com.invoicebinder.server.core.shell.ShellExecuteResult;

import java.io.IOException;

public class PDFManager {
    public static ShellExecuteResult convertHTMLToPDF(String wkHtmlToPdfAppPath, String htmlFilePath, String pdfFilePath) throws IOException, InterruptedException {
        return ShellExecute.executeCommand(wkHtmlToPdfAppPath + " -s A4" + " " + htmlFilePath + " " + pdfFilePath);
    }
}
