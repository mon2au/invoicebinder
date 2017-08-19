/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.enums.invoice;

/**
 *
 * @author mon2
 */
public enum InvoiceStatus {
    DRAFT, //DRAFT - New Invoices are Draft.
    SENT, //SENT - Emailed to Client.
    PAID, //PAID - Payment Made.
    ARCHIVED,  //ARCHIVED - Archived Invoices.
    OVERDUE,
    ALL
}
