/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.server.service;

import com.invoicebinder.client.service.payment.PaymentService;
import com.invoicebinder.server.dataaccess.PaymentDAO;
import com.invoicebinder.shared.entity.payment.Payment;
import com.invoicebinder.shared.enums.payment.PaymentStatus;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.PaymentInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("payment")
public class PaymentServiceImpl extends RemoteServiceServlet implements
        PaymentService {
    
    @Autowired
    private PaymentDAO paymentDAO;
    
    @Override
    public ArrayList<PaymentInfo> getAllPaymentsInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String filter, PaymentStatus status) {
        ArrayList<PaymentInfo> allPaymentsInfo;
        ArrayList<Payment> paymentList;
        PaymentInfo info;
        
        paymentList = paymentDAO.getAllPaymentsInfo(start, length, sortList, filter, status);
        allPaymentsInfo = new ArrayList<>();
        for(Payment payment : paymentList) {
            info = new PaymentInfo();
            info.setClientInfo(new ClientInfo(payment.getClient().getFirstName(), payment.getClient().getLastName()));
            info.setId(payment.getId());
            info.setAmount(payment.getAmount());
            info.setPaymentDate(payment.getPaymentDate());
            info.setCurrencyCode(payment.getCurrencyCode());
            info.setPaymentStatus(payment.getPaymentStatus());
            info.setPaymentType(payment.getPaymentType());
            info.setReferenceNo(payment.getReferenceNo());
            allPaymentsInfo.add(info);
        }
        return allPaymentsInfo;
    }
    
    @Override
    public int getPaymentsCount(String filter, PaymentStatus status) {
        return paymentDAO.getPaymentsCount(filter, status);
    }

    @Override
    public void savePayment(PaymentInfo info) {
        paymentDAO.savePayment(info);
    }   
}
