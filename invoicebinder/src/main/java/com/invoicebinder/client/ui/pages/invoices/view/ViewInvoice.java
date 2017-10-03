/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.invoices.view;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.invoicebinder.client.service.config.ConfigServiceCallbacks;
import com.invoicebinder.client.service.utility.UtilityServiceClientImpl;
import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.enums.AutoLoginViews;
import com.invoicebinder.shared.enums.config.EmailConfigItems;
import com.invoicebinder.shared.enums.invoice.InvoiceMode;
import com.invoicebinder.shared.enums.invoice.ViewInvoicePageMode;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.misc.VariableManager;
import com.invoicebinder.shared.model.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.datepicker.client.DateBox;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.invoice.InvoiceStatus;
import com.invoicebinder.shared.misc.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mon2
 */
public class ViewInvoice extends Composite {

    private static final ViewInvoiceUiBinder uiBinder = GWT.create(ViewInvoiceUiBinder.class);
    @UiField protected VerticalPanel mainPanel;
    @UiField protected HorizontalPanel contentPanel;
    @UiField protected Label lblHeader;
    @UiField protected Label lblInvoiceHeader;
    @UiField protected Button btnPrint;
    @UiField protected Button btnExportPdf;
    @UiField protected Button btnSendEmail;
    @UiField protected Button btnProcessPayment;
    @UiField protected Button btnChangeTemplate;
    @UiField protected Button btnPayPaypal;
    @UiField protected DisclosurePanel discPnlEmail;
    @UiField protected Label lblInvoiceStatus;
    @UiField protected TableRowElement trEmail;
    @UiField protected TableRowElement trEmailDisc;
    @UiField protected TableRowElement trRecordPayment;
    @UiField protected TableRowElement trRecordPaymentSpacing;
    @UiField protected TableRowElement trChangeTemplate;
    @UiField protected TableRowElement trChangeTemplateSpacing;
    @UiField protected TableRowElement trPaypalPayment;
    @UiField protected TableRowElement trPaypalPaymentSpacing;
    @UiField protected FormElement frmPaypal;
    @UiField protected InputElement ppBusiness;
    @UiField protected InputElement ppCurrency;
    @UiField protected InputElement ppAmount;
    @UiField protected InputElement ppItemName;
    @UiField protected InputElement ppNotifyUrl;

    private final VerticalPanel invoicePanel;
    private final Invoice invoice;
    private final TextBox txtEmail;
    private final TextArea txtEmailMessage;
    private final Button btnEmailSend;
    private final DateBox paymentDate;
    private final Label lblEmailResult;
    private final Label lblMarkPaidResult;
    private final UtilityServiceClientImpl utilityService;
    private final ConfigServiceClientImpl configService;
    private final Main main;
    private final ViewInvoicePageMode mode;
    private final Loading loading;

    private long invoiceId = 0;
    private long clientId = 0;
    private String clientName;
    private String invoiceNumber;
    private String companyName;
    private String paymentAmount;
    private String currencyCode;
    private String authToken;
    private Boolean attachInvoicePDF;

    interface ViewInvoiceUiBinder extends UiBinder<Widget, ViewInvoice> {
    }

    public ViewInvoice(Main main) {
        this(main, ViewInvoicePageMode.VIEWINVOICE_ADMIN_USER);
    }
    public ViewInvoice(Main main, ViewInvoicePageMode mode) {
        this.main = main;
        this.mode = mode;
        this.loading = new Loading();
        this.attachInvoicePDF = false;
        initWidget(uiBinder.createAndBindUi(this));
        this.utilityService = new UtilityServiceClientImpl(GWT.getModuleBaseURL() + "services/utility", this.main);
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        this.txtEmail = new TextBox();
        this.txtEmail.getElement().setId("txtEmail");
        this.txtEmailMessage = new TextArea();
        this.txtEmailMessage.getElement().setId("txtEmailMessage");
        this.btnEmailSend = new Button();
        this.paymentDate = new DateBox();
        this.invoicePanel = new VerticalPanel();
        this.lblEmailResult = new Label();
        this.lblMarkPaidResult = new Label();
        this.lblEmailResult.setVisible(false);
        this.lblMarkPaidResult.setVisible(false);

        this.invoiceId = Integer.parseInt(Utils.getParamFromHref("invoiceId"));
        this.invoice = new Invoice(main, invoiceId, InvoiceMode.VIEWINVOICEDETAILS);
        this.lblHeader.getElement().setInnerHTML("<h5><span>View Invoice</span></h5>");
        this.lblInvoiceHeader.getElement().setInnerHTML("<h6><span>Invoice Details</span></h6>");

        invoicePanel.add(invoice);
        contentPanel.insert(invoicePanel, 0);
        discPnlEmail.setContent(getEmailContent());
        //event handlers
        this.btnPrint.addClickHandler(new DefaultClickHandler(this));
        this.btnExportPdf.addClickHandler(new DefaultClickHandler(this));
        this.btnSendEmail.addClickHandler(new DefaultClickHandler(this));
        this.btnProcessPayment.addClickHandler(new DefaultClickHandler(this));
        this.btnEmailSend.addClickHandler(new DefaultClickHandler(this));
        this.btnChangeTemplate.addClickHandler(new DefaultClickHandler(this));
        this.btnPayPaypal.addClickHandler(new DefaultClickHandler(this));
        setStyles();
        setMode();
        this.configService.loadEmailConfigData(ConfigServiceCallbacks.EmailConfigTargetPage.ViewInvoicePage);
    }

    private void setMode() {
        switch (this.mode) {
            case VIEWINVOICE_AUTOLOGIN_USER: {
                trEmail.removeFromParent();
                trEmailDisc.removeFromParent();
                trRecordPayment.removeFromParent();
                trRecordPaymentSpacing.removeFromParent();
                trChangeTemplate.removeFromParent();
                trChangeTemplateSpacing.removeFromParent();
            }
            break;

            case VIEWINVOICE_ADMIN_USER: {
                trPaypalPayment.removeFromParent();
                trPaypalPaymentSpacing.removeFromParent();
            }
            break;
        }
    }

    private void setStyles() {
        invoicePanel.setStyleName("invoice-main-container");
        mainPanel.setStyleName("container");
        txtEmail.setStyleName("small-text-box");
        txtEmailMessage.setStyleName("text-area");
        paymentDate.setStyleName("small-text-box");
        btnPrint.setStyleName("appbutton-default");
        btnExportPdf.setStyleName("appbutton-default");
        btnProcessPayment.setStyleName("appbutton-default");
        btnSendEmail.setStyleName("appbutton-default");
        btnChangeTemplate.setStyleName("appbutton-default");
        btnPayPaypal.setStyleName("appbutton-default");
        btnPrint.setWidth("150px");
        btnExportPdf.setWidth("150px");
        btnProcessPayment.setWidth("150px");
        btnSendEmail.setWidth("150px");
        btnEmailSend.setStyleName("appbutton-default");

    }
    private VerticalPanel getEmailContent() {
        VerticalPanel emailContent = new VerticalPanel();
        emailContent.setSpacing(Constants.PANEL_CELL_SPACING);
        emailContent.setWidth("210px");
        txtEmailMessage.setSize("200px", "150px");
        btnEmailSend.setText("Send");
        emailContent.add(new Label("Client email address"));
        emailContent.add(txtEmail);
        emailContent.add(new Label("Message"));
        emailContent.add(txtEmailMessage);
        emailContent.add(btnEmailSend);
        emailContent.add(lblEmailResult);
        return emailContent;
    }
    private String generateContentsForInvoicePDF() {
        StringBuilder contents = new StringBuilder();

        contents.append("<html>");
        contents.append("<head>");
        contents.append("<link href=\"").append(GWT.getHostPageBaseURL()).append("css/default.css\" rel=\"stylesheet\" type=\"text/css\" />");
        contents.append("</head>");
        contents.append("<body>");
        contents.append(invoice.getElement().getInnerHTML());
        contents.append("</body>");
        contents.append("</html>");
        return contents.toString();
    }

    public void updateInvoiceDetails(ViewInvoiceInfo viewInvoiceInfo) {
        String amount = String.valueOf(viewInvoiceInfo.getInvoiceInfo().getAmount());
        //example//[INVOICEBINDER_URL]/view=[AUTOLOGIN_VIEW]&amount=[AUTH_AMOUNT]&token=[AUTH_TOKEN]&invnum=[INVOICE_NUMBER]&invoiceId=[INVOICE_ID]
        String notifyUrl = GWT.getHostPageBaseURL() +"index.html#autologin/view=" + AutoLoginViews.paypalnotify.toString() +
                "&amount=" + viewInvoiceInfo.getInvoiceInfo().getAmount().toString() +
                "&token=" + viewInvoiceInfo.getInvoiceInfo().getAuthToken() +
                "&invnum=" + viewInvoiceInfo.getInvoiceInfo().getInvoiceNumber() +
                "&invoiceId=" + String.valueOf(viewInvoiceInfo.getInvoiceInfo().getId());


        //update view invoice page information and invoice page.
        this.setEmailMessage(viewInvoiceInfo);
        this.invoice.updateInvoiceDetails(viewInvoiceInfo);

        //set paypal form data
        frmPaypal.setAction(viewInvoiceInfo.getPayPalSubmitUrl());
        ppBusiness.setValue(viewInvoiceInfo.getPaypalEmail());
        ppCurrency.setValue(viewInvoiceInfo.getCurrencyCode());
        ppAmount.setValue(amount);
        ppItemName.setValue("Payment for Invoice: " + viewInvoiceInfo.getInvoiceInfo().getInvoiceNumber());
        ppNotifyUrl.setValue(notifyUrl);

        //set client data.
        this.invoiceNumber = viewInvoiceInfo.getInvoiceInfo().getInvoiceNumber();
        this.authToken = viewInvoiceInfo.getInvoiceInfo().getAuthToken();
        this.companyName = viewInvoiceInfo.getBusinessInfo().getCompanyName();
        this.clientId = viewInvoiceInfo.getInvoiceInfo().getClientId();
        this.clientName = viewInvoiceInfo.getInvoiceInfo().getClientName();
        this.paymentAmount = amount;
        this.currencyCode = viewInvoiceInfo.getCurrencyCode();
        this.lblInvoiceStatus.setText("Status: " + viewInvoiceInfo.getInvoiceInfo().getInvoiceStatus());
        this.lblInvoiceStatus.setStyleName("invoice-status");

        if (viewInvoiceInfo.getInvoiceInfo().getInvoiceStatus().equals(InvoiceStatus.PAID.toString())) {
            this.btnProcessPayment.setEnabled(false);
            this.btnProcessPayment.setStyleName("appbutton-default-disabled");
        }
    }

    public void setValidationResult(ValidationResult result) {
        Element element;
        element = DOM.getElementById(result.getTagname());
        element.focus();
        ValidationPopup.Show(result.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }

    private void setEmailMessage(ViewInvoiceInfo info) {
        HashMap vars = new HashMap();
        String amount = "";

        if (info.getInvoiceInfo() != null) {

            if (info.getInvoiceInfo().getAmount() != null) {
                amount = info.getInvoiceInfo().getAmount().toString();
            }
            vars.put("[ClientName]", info.getInvoiceInfo().getClientName());
            vars.put("[InvoiceNo]",info.getInvoiceInfo().getInvoiceNumber());
            vars.put("[BusinessName]",info.getBusinessInfo().getCompanyName());
            vars.put("[InvoiceAmount]", amount);

            if (info.getInvoiceEmailMessage() != null) { txtEmailMessage.setText(VariableManager.replaceVariables(info.getInvoiceEmailMessage(), vars)); }
            this.txtEmail.setText(info.getInvoiceInfo().getClientInfo().getEmailAddress());
        }
    }

    public void updateMailResponse(Boolean result) {
        lblEmailResult.setStyleName("message-box");
        lblEmailResult.setWidth("190px");
        lblEmailResult.setVisible(true);

        this.loading.hide();

        if (result) {
            lblEmailResult.addStyleName("success");
            lblEmailResult.setText("Invoice email has been sent successfully.");
        }
        else {
            lblEmailResult.addStyleName("error");
            lblEmailResult.setText("Error sending invoice email.");
        }
    }

    public void updateStatus(Boolean result) {
        lblMarkPaidResult.setStyleName("message-box");
        lblMarkPaidResult.setWidth("190px");
        lblMarkPaidResult.setVisible(true);

        if (result) {
            paymentDate.setValue(null);
            lblMarkPaidResult.addStyleName("success");
            lblMarkPaidResult.setText("Invoice marked as paid.");
        }
        else {
            lblMarkPaidResult.addStyleName("error");
            lblMarkPaidResult.setText("Error changing invoice status.");
        }
    }

    public void updateEmailConfig(ArrayList<ConfigData> list) {

        HashMap<String, String> templateData = new HashMap();
        for (ConfigData data : list) {
            templateData.put(data.getKey(), data.getValue());
        }
        this.attachInvoicePDF = Boolean.valueOf(templateData.get(EmailConfigItems.EMAILINVOICEPDF.toString()));
    }

    public void createInvoiceBasedOnInvoiceTemplateForInvoicePage(ArrayList<ConfigData> templateData) {
        this.invoice.createInvoiceBasedOnInvoiceTemplate(templateData);
    }

    // <editor-fold defaultstate="collapsed" desc="Event Handlers">        
    private class DefaultClickHandler implements ClickHandler {
        ViewInvoice pageReference;

        public DefaultClickHandler(ViewInvoice reference) {
            this.pageReference = reference;
        }

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();

            if (sender == btnPrint) {
                Window.open(GWT.getHostPageBaseURL() + "index.html#previewinvoice/invoiceId=" + invoiceId, "_blank","");
                event.getNativeEvent().preventDefault();
            }
            if (sender == btnExportPdf) {
                String content;
                content = this.pageReference.generateContentsForInvoicePDF();
                utilityService.createPDFFile(content,  this.pageReference.invoiceNumber);
                event.getNativeEvent().preventDefault();
            }
            if (sender == btnEmailSend) {
                if (Utils.isDemoApplication()) return;
                //set invoice information
                InvoiceInfo invoiceInfo = new InvoiceInfo();
                invoiceInfo.setInvoiceNumber(this.pageReference.invoiceNumber);
                invoiceInfo.setAuthToken(this.pageReference.authToken);
                invoiceInfo.setAmount(new BigDecimal(this.pageReference.paymentAmount));
                invoiceInfo.setId(pageReference.invoiceId);

                //set mail information
                MailInfo mailInfo = new MailInfo();
                mailInfo.setSubject("Invoice No - " + this.pageReference.invoiceNumber + " from " + this.pageReference.companyName );
                mailInfo.setCompanyName(this.pageReference.companyName);
                mailInfo.setMessage(txtEmailMessage.getText());
                mailInfo.setRecipientEmail(txtEmail.getText());

                //check if we need to attach PDF.
                if (!attachInvoicePDF) {
                    utilityService.sendInvoiceEmail(invoiceInfo, mailInfo, this.pageReference.main, this.pageReference.loading);
                }
                else {
                    utilityService.sendInvoiceEmailWithPDF(this.pageReference.generateContentsForInvoicePDF(), invoiceInfo, mailInfo, this.pageReference.main, this.pageReference.loading);
                }
                event.getNativeEvent().preventDefault();
            }
            if (sender == btnSendEmail) {
                this.pageReference.discPnlEmail.setOpen(!this.pageReference.discPnlEmail.isOpen());
                event.getNativeEvent().preventDefault();
            }
            if (sender == btnProcessPayment) {
                History.newItem(Views.newpayment.toString() + "/clientId=" + clientId + "&clientName=" + clientName + "&paymentAmount=" + paymentAmount + "&currencyCode=" + currencyCode + "&invoiceNum=" + invoiceNumber);
                event.getNativeEvent().preventDefault();
            }
            if (sender == btnChangeTemplate) {
                History.newItem(Views.settings_templates.toString());
                event.getNativeEvent().preventDefault();
            }
            if (sender == btnPayPaypal) {

                frmPaypal.submit();
                event.getNativeEvent().preventDefault();
            }
        }
    }
    // </editor-fold>    
}