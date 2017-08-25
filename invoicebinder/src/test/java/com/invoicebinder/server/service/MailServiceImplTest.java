/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.service;

import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.MailInfo;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author mon2
 */
public class MailServiceImplTest {
    
    public MailServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sendContactUsMail method, of class MailServiceImpl.
     */
    @Test
    @Ignore
    public void testSendContactUsMail() {
        //System.out.println("sendContactUsMail");
        //MailInfo info = null;
        //MailServiceImpl instance = new MailServiceImpl();
        //Boolean expResult = null;
        //Boolean result = instance.sendContactUsMail(info);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of sendInvoiceMail method, of class MailServiceImpl.
     */
    @Test
    @Ignore
    public void testSendInvoiceMail() {
        System.out.println("sendInvoiceMail");
        MailInfo mailInfo = new MailInfo();
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        MailServiceImpl instance = new MailServiceImpl();

        mailInfo.setRecipientEmail("manssster@gmail.com");
        mailInfo.setSubject("testSendInvoiceMail");
        mailInfo.setCompanyName("Demo Company");
        mailInfo.setMessage("Dear Test Person,<br/>Please find attached your invoice for AUD123.11. Click \"View Online\" to see your invoice online.");

        invoiceInfo.setInvoiceNumber("INV-12321");
        invoiceInfo.setAuthToken("9364b6ce-5e18-11e7-907b-a6006ad3dba0");

        Boolean expResult = true;
        Boolean result = instance.sendInvoiceMail(mailInfo, invoiceInfo, "http://invoicebinder.com");
        assertEquals(expResult, result);
    }

    /**
     * Test of sendTestEmail method, of class MailServiceImpl.
     */
    @Test
    @Ignore
    public void testSendTestEmail() {
        System.out.println("sendTestEmail");
        String recipientEmail = "manssster@gmail.com";
        EmailPropertiesInfo emailConfig = new EmailPropertiesInfo();
        emailConfig.setEmailUsername("manssster@gmail.com");
        emailConfig.setEmailPassword("Lambdabunker3");
        emailConfig.setEmailHost("smtp.gmail.com");
        emailConfig.setEmailFromAddress("manssster@gmail.com");
        emailConfig.setEmailAuthType("SMTPS");
        emailConfig.setEmailSSL(true);
        emailConfig.setEmailPort("465");

        MailServiceImpl instance = new MailServiceImpl();
        Boolean expResult = true;
        Boolean result = instance.sendTestEmail(recipientEmail, emailConfig);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendForgotPasswordMail method, of class MailServiceImpl.
     */
    @Test
    @Ignore
    public void testSendForgotPasswordMail() {
        System.out.println("sendForgotPasswordMail");//
        MailInfo info = new MailInfo();
        
        info.setRecipientEmail("manssster@gmail.com");
        info.setNewPassword("ABCD1234");
        info.setSubject("Password reset");
        MailServiceImpl instance = new MailServiceImpl();
        boolean expResult = true;
        boolean result = instance.sendForgotPasswordMail(info);
        assertEquals(expResult, result);
    }
}
