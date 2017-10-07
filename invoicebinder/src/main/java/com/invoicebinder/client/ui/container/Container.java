package com.invoicebinder.client.ui.container;

import com.invoicebinder.client.service.config.ConfigServiceCallbacks;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.installation.Install;
import com.invoicebinder.client.ui.installation.InstallAppConfig;
import com.invoicebinder.client.ui.installation.InstallAppEmail;
import com.invoicebinder.client.ui.installation.InstallDBCreate;
import com.invoicebinder.client.ui.installation.InstallDBTest;
import com.invoicebinder.client.ui.pages.clients.Clients;
import com.invoicebinder.client.ui.pages.clients.NewClient;
import com.invoicebinder.client.ui.pages.config.Configuration;
import com.invoicebinder.client.ui.pages.dashboard.Dashboard;
import com.invoicebinder.client.ui.pages.expenses.Expenses;
import com.invoicebinder.client.ui.pages.expenses.NewExpense;
import com.invoicebinder.client.ui.pages.invoices.Invoices;
import com.invoicebinder.client.ui.pages.invoices.NewInvoice;
import com.invoicebinder.client.ui.pages.invoices.view.ViewInvoice;
import com.invoicebinder.client.ui.pages.payments.NewPayment;
import com.invoicebinder.client.ui.pages.payments.Payments;
import com.invoicebinder.client.ui.pages.products.NewProduct;
import com.invoicebinder.client.ui.pages.products.Products;
import com.invoicebinder.client.ui.pages.reports.Reports;
import com.invoicebinder.client.ui.pages.unrestricted.AboutUs;
import com.invoicebinder.client.ui.pages.unrestricted.ContactUs;
import com.invoicebinder.client.ui.pages.unrestricted.HomePage;
import com.invoicebinder.client.ui.pages.unrestricted.LoginBox;
import com.invoicebinder.client.ui.pages.unrestricted.Services;
import com.invoicebinder.client.ui.pages.users.NewUser;
import com.invoicebinder.client.ui.pages.users.UserDetails;
import com.invoicebinder.client.ui.pages.users.Users;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.invoice.ViewInvoicePageMode;
import com.invoicebinder.shared.enums.report.Report;
import com.invoicebinder.shared.enums.startup.HomePageSettings;
import com.invoicebinder.shared.model.AuthenticationResult;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.DashStatsInfo;
import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.ProductInfo;
import com.invoicebinder.shared.model.ServerValidationResult;
import com.invoicebinder.shared.model.UserInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.invoicebinder.shared.model.ViewInvoiceInfo;
import com.invoicebinder.shared.model.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("LeakingThisInConstructor")
public class Container extends Composite {
    
    private static final ContainerUiBinder uiBinder = GWT
            .create(ContainerUiBinder.class);
    private HomePage home;
    private AboutUs aboutUs;
    private ContactUs contactUs;
    private Services services;
    private LoginBox login;
    private Dashboard dashboard;
    private Clients clients;
    private Products products;
    private NewClient newClient;
    private NewProduct newProduct;
    private Expenses expenses;
    private NewExpense newExpense;
    private Invoices invoices;
    private NewInvoice newInvoice;
    private ViewInvoice viewInvoice;
    private Configuration config;
    private Main mainPanel;
    private Reports reports;
    private UserDetails userAcct;
    private Users users;
    private NewUser newUser;
    private Install install;
    private InstallDBTest installDBTest;
    private InstallDBCreate installDBCreate;
    private InstallAppEmail installAppEmail;
    private InstallAppConfig installAppConfig;
    private Payments payments;
    private NewPayment newPayment;
    
    @UiField HTMLPanel containerPanel;
    
    private HomePageSettings homePageMode;
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public HomePage getHome() {
        return home;
    }
    public void setHome(HomePage home) {
        this.home = home;
    }
    public AboutUs getAboutus() {
        return aboutUs;
    }
    public void setAboutus(AboutUs aboutUs) {
        this.aboutUs = aboutUs;
    }
    public ContactUs getContactUs() {
        return contactUs;
    }
    public void setContactUs(ContactUs contactUs) {
        this.contactUs = contactUs;
    }
    public Services getServices() {
        return services;
    }
    public void setServices(Services services) {
        this.services = services;
    }
    public LoginBox getLogin() {
        return login;
    }
    public void setLogin(LoginBox login) {
        this.login = login;
    }
    public Dashboard getDashboard() {
        return dashboard;
    }
    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
    public Clients getClients() {
        return clients;
    }
    public void setClients(Clients clients) {
        this.clients = clients;
    }
    public Object getMainPanel() {
        return mainPanel;
    }
    public void setMainPanel(Main mainPanel) {
        this.mainPanel = mainPanel;
    }
    public HTMLPanel getContainerPanel() {
        return containerPanel;
    }
    public void setContainerPanel(HTMLPanel ContainerPanel) {
        this.containerPanel = ContainerPanel;
    }
    public AboutUs getAboutUs() {
        return aboutUs;
    }
    public void setAboutUs(AboutUs aboutUs) {
        this.aboutUs = aboutUs;
    }
    public Products getProducts() {
        return products;
    }
    public void setProducts(Products products) {
        this.products = products;
    }
    public NewClient getNewClient() {
        return newClient;
    }
    public void setNewClient(NewClient newClient) {
        this.newClient = newClient;
    }
    public NewProduct getNewProduct() {
        return newProduct;
    }
    public void setNewProduct(NewProduct newProduct) {
        this.newProduct = newProduct;
    }
    public Expenses getExpenses() {
        return expenses;
    }
    public void setExpenses(Expenses expenses) {
        this.expenses = expenses;
    }
    public NewExpense getNewExpense() {
        return newExpense;
    }
    public void setNewReceipt(NewExpense newExpense) {
        this.newExpense = newExpense;
    }
    public Invoices getInvoices() {
        return invoices;
    }
    public void setInvoices(Invoices invoices) {
        this.invoices = invoices;
    }
    public NewInvoice getNewInvoice() {
        return newInvoice;
    }
    public void setNewInvoice(NewInvoice newInvoice) {
        this.newInvoice = newInvoice;
    }
    public ViewInvoice getViewInvoice() {
        return viewInvoice;
    }
    public void setViewInvoice(ViewInvoice viewInvoice) {
        this.viewInvoice = viewInvoice;
    }
    public Configuration getConfig() {
        return config;
    }
    public void setConfig(Configuration config) {
        this.config = config;
    }
    public Reports getReports() {
        return reports;
    }
    public void setReports(Reports reports) {
        this.reports = reports;
    }
    public UserDetails getUserAcct() {
        return userAcct;
    }
    public void setUserAcct(UserDetails userAcct) {
        this.userAcct = userAcct;
    }
    public Users getUsers() {
        return users;
    }
    public void setUsers(Users users) {
        this.users = users;
    }
    public NewUser getNewUser() {
        return newUser;
    }
    public void setNewUser(NewUser newUser) {
        this.newUser = newUser;
    }
    public Install getInstall() {
        return install;
    }
    public void setInstall(Install install) {
        this.install = install;
    }
    public InstallDBTest getInstallDBTest() {
        return installDBTest;
    }
    public void setInstallDBTest(InstallDBTest installDBTest) {
        this.installDBTest = installDBTest;
    }
    public InstallDBCreate getInstallDBCreate() {
        return installDBCreate;
    }
    public void setInstallDBCreate(InstallDBCreate installDBCreate) {
        this.installDBCreate = installDBCreate;
    }
    public InstallAppEmail getInstallAppEmail() {
        return installAppEmail;
    }
    public void setInstallAppEmail(InstallAppEmail installAppEmail) {
        this.installAppEmail = installAppEmail;
    }
    public Payments getPayments() {
        return payments;
    }
    public void setPayments(Payments payments) {
        this.payments = payments;
    }
    public NewPayment getNewPayment() {
        return newPayment;
    }
    public void setNewPayment(NewPayment newPayment) {
        this.newPayment = newPayment;
    }
    public InstallAppConfig getInstallAppConfig() {
        return installAppConfig;
    }
    public void setInstallAppConfig(InstallAppConfig installAppConfig) {
        this.installAppConfig = installAppConfig;
    }
    // </editor-fold>
    
    public void loadExpense(ExpenseInfo expenseInfo) {
        this.newExpense.populateExpenseInfo(expenseInfo);
    }
    
    public void refreshProducts() {
        this.products.refresh();
    }
    
    public void refreshExpenses() {
        this.expenses.refresh();
    }

    public void updateTestEmail(Boolean result) {
        this.config.updateTestEmail(result);
    }

    public void updateSystemInfo(SystemInfo systemInfo) {
        this.config.setSystemInfo(systemInfo);
    }

    public void updateAppSettingConfigForDashboard(ConfigurationSection configurationSection, ArrayList<ConfigData> arrayList) {
        this.dashboard.setConfigData(configurationSection, arrayList);
    }  

    public void updateNewInvoiceClientTableEmptyMessage() {
        this.newInvoice.showEmptyClientTableMessage();
    }

    public void updateNewInvoiceProductTableEmptyMessage() {
        this.newInvoice.showEmptyProductTableMessage();
    }

    public void updateInvoicesStatusForInvoicesPage(Boolean aBoolean) {
        this.invoices.setInvoiceStatus(aBoolean);
    }

    public void updateDBTestResult(ServerValidationResult result) {
        this.installDBTest.updateDBTestResult(result);
    }

    public void updateDBCreateResult(Boolean result) {
        this.installDBCreate.updateDBCreateResult(result);
    }

    public void updateDefaultDataCreateResult(Boolean result) {
        this.installDBCreate.updateDefaultDataCreateResult(result);
    }

    public void updateDBCreateProcedureResult(Boolean result) {
        this.installDBCreate.updateDBCreateProcedureResult(result);
    }

    public void updateDBCreateConfigResult(Boolean result) {
        this.installDBCreate.updateDBCreateConfigResult(result);
    }

    public void updateEmailTestResult(ServerValidationResult result) {
        this.installAppEmail.updateEmailTestResult(result);
    }

    public void setHomePageMode(HomePageSettings homePageMode) {
        this.homePageMode = homePageMode;
    }
   
    public enum ContainerType {
        StandardContainer, AuthenticatedContainer, EmptyContainer
    }
    
    interface ContainerUiBinder extends UiBinder<Widget, Container> {
    }
    
    public Container(Main mainPanel) {
        //the container will accept template data and
        //inject data into the appropriate objects.
        
        //we need to init Main and  Login pages so that we can set the UI based
        //on settings.
        this.mainPanel = mainPanel;
        this.login = new LoginBox(mainPanel);
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    public void setContainerType(ContainerType type) {
        if (null != type) switch (type) {
            case StandardContainer:
                home = new HomePage(mainPanel);
                this.containerPanel.clear();
                this.containerPanel.add(home);
                break;
            case AuthenticatedContainer:
                if (dashboard == null) {
                    dashboard = new Dashboard(mainPanel);
                }   this.containerPanel.clear();
                this.containerPanel.add(dashboard);
                break;
            case EmptyContainer:
                this.containerPanel.clear();
                break;
            default:
                break;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Callback Updates">
    public void hideLoginDialog() {
        this.login.hide();
    }
    public void updateLoginDialog(AuthenticationResult result) {
        this.login.updateLoginDialog(result);
    }
    public void refreshClients() {
        this.clients.refresh();
    }
    public void refreshUsers() {
        this.users.refresh();
    }
    public void loadClient(ClientInfo clientInfo) {
        this.newClient.populateClientInfo(clientInfo);
    }
    public void loadProduct(ProductInfo productInfo) {
        this.newProduct.populateProductInfo(productInfo);
    }
    public void loadInvoice(InvoiceInfo invoiceInfo) {
        this.newInvoice.populateInvoiceInfo(invoiceInfo);
    }
    public void loadAutonumForInvoice(String num) {
        this.newInvoice.SetInvoiceNumber(num);
    }
    public void loadUserDetails(UserInfo userInfo) {
        this.userAcct.populateUserDetails(userInfo);
    }
    public void updateNewInvoiceConfig(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        this.newInvoice.setConfigData(section, arrayList);
    }
    public void updateSocialMediaConfigForHomePage(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        this.home.setConfigData(section, arrayList);
    }
    public void updateConfigurationPagesConfig(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        this.config.setConfiguration(section, arrayList);
    }
    public void updateConfigurationPagesStatus(ConfigurationSection section, Boolean status) {
        this.config.updateConfigurationStatus(section, status);
    }
    public void updateCustomAttrDataForNewInvoicePage(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        this.newInvoice.setConfigData(section, arrayList);
    }
    public void updateEmailConfigForViewInvoicePage(ArrayList<ConfigData> list) {
        this.viewInvoice.updateEmailConfig(list);
    }
    public void updateClientsCount(int count) {
        this.clients.updateTableCount(count);
    }
    public void updatePaymentsCount(int count) {
        this.payments.updateTableCount(count);
    }
    public void updateUsersCount(Integer count) {
        this.users.updateTableCount(count);
    }
    public void updateInvoicesCount(Integer count) {
        this.invoices.updateTableCount(count);
    }
    public void updateOverdueInvoicesCount(Integer count) {
        this.dashboard.updateTableCount(count);
    }
    public void updateProductsCount(Integer count) {
        this.products.updateTableCount(count);
    }
    public void updateExpensesCount(Integer count) {
        this.expenses.updateTableCount(count);
    }
    public void updateInvoiceDetailsForInvoicePage(ViewInvoiceInfo viewInvoiceInfo) {
        this.viewInvoice.updateInvoiceDetails(viewInvoiceInfo);
    }
    public void updateContactUsMail(boolean result) {
        this.contactUs.updateMailResponse(result);
    }
    public void updatePasswordSaveStatus(Boolean status) {
        this.userAcct.updatePasswordSaveStatus(status);
    }
    public void updateInvoiceMail(Boolean result) {
        this.viewInvoice.updateMailResponse(result);
    }
    public void updateInvoiceStatusForViewInvoicePage(Boolean status) {
        this.viewInvoice.updateStatus(status);
    }
    public void updateForgotPasswordDialog(String result) {
        this.login.updateForgotPasswordDialog(result);
    }
    public void updateReportData(List list, Report report) {
        this.reports.setReportData(list, report);
    }
    public void updateDashboardIncomeAndExpenseReport(List list) {
        this.dashboard.setReportData(list);
    }
    public void updateDashboardStats(DashStatsInfo dashStatsInfo) {
        this.dashboard.updateStats(dashStatsInfo);
    }
    public void updateInvoiceTemplateName(ConfigServiceCallbacks.TemplateNameTargetPage page, ArrayList<ConfigData> arrayList) {
        switch (page) {
            case ConfigPage: {
                this.config.setActiveInvoiceTemplate(arrayList);
            }
            break;
            case InvoicePage: {
                this.viewInvoice.createInvoiceBasedOnInvoiceTemplateForInvoicePage(arrayList);
            }

        }

    }       
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Views">
    public void showLoginDialog() {
        showLoginDialog(true);
    }
    public void showLoginDialog(Boolean showCloseButton) {
        this.login.clearLogin();
        this.login.center();
        this.login.show();
        this.login.setLoginFocus();
        this.login.setCloseVisible(showCloseButton);
    }
    public void showConfiguration(ConfigurationSection forceDisplayToSection) {
        this.config = new Configuration(mainPanel);
        this.config.setActiveTab(forceDisplayToSection);
        this.containerPanel.clear();
        this.containerPanel.add(config);
    }
  
    public void clear() {
        this.containerPanel.clear();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Do View Validations">      
    public void doValidation(Views view, ValidationResult result) {
        switch (view) {
            case newpayment: { 
                this.newPayment.updateValidationStatus(result);              
                break;
            }
            case installdbcreate: {
                this.installDBCreate.updateValidationStatus(result);                
                break;
            }
            case useraccountdetails: {
                this.userAcct.setValidationResult(result);
                break;
            }
            case showinvoice: {
                this.viewInvoice.setValidationResult(result);
                break;
            }
            case newinvoice: {
                this.newInvoice.setValidationResult(result);
                break;
            }
            case newclient: {
                this.newClient.setValidationResult(result);                
                break;
            }
            case newexpense: {
                this.newExpense.setValidationResult(result);                
                break;
            }
            case newproduct: {
                this.newProduct.setValidationResult(result);                
                break;
            }
            case installdbtest: {
                this.installDBTest.updateValidationStatus(result);                
                break;
            }
            case installappemail: {
                this.installAppEmail.updateValidationStatus(result);
                break;
            }
            case installappconfig: {
                this.installAppConfig.updateValidationStatus(result);
                break;
            }
        }
    }  
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Show View">    
    public void show(Views view) {
        this.clear();
        switch (view) {
            case payments: {
                this.payments = new Payments(mainPanel);
                this.containerPanel.add(payments);
                break;
            }
            case newpayment: {
                this.newPayment = new NewPayment(mainPanel);
                this.containerPanel.add(newPayment);
                break;
            }
            case useraccountdetails: {
                this.userAcct = new UserDetails(mainPanel);
                this.containerPanel.add(userAcct);                
                break;
            }
            case users: {
                this.users = new Users(mainPanel);
                this.containerPanel.add(users);
                break;
            }
            case newuser: {
                this.newUser = new NewUser(mainPanel);
                this.containerPanel.add(newUser);                
                break;
            } 
            case rptsalespayments: {
                this.reports = new Reports(mainPanel);
                this.containerPanel.add(reports);
                this.reports.showSalesAndPayment();                
                break;
            }
            case rptincomeexpense: {
                this.reports = new Reports(mainPanel);
                this.containerPanel.add(reports);
                this.reports.showIncomeAndExpense();                
                break;
            }
            case reports: {
                this.reports = new Reports(mainPanel);
                this.containerPanel.add(reports);
                this.reports.showIncomeAndExpense();              
                break;
            }
            case showinvoice: {
                this.viewInvoice = new ViewInvoice(mainPanel);
                this.containerPanel.add(viewInvoice);                
                break;
            }
            case editinvoice: {
                this.newInvoice = new NewInvoice(mainPanel);
                this.containerPanel.add(newInvoice);                
                break;
            }
            case newinvoice: {
                this.newInvoice = new NewInvoice(mainPanel);
                this.containerPanel.add(newInvoice);                
                break;
            }
            case invoices: {
                this.invoices = new Invoices(mainPanel);
                this.containerPanel.add(invoices);
                break;
            }
            case dashboard: {
                if (this.dashboard == null) {
                    this.dashboard = new Dashboard(mainPanel);
                }
                this.containerPanel.add(dashboard);                
                break;
            }
            case products: {
                this.products = new Products(mainPanel);
                this.containerPanel.add(products);                
                break;
            }
            case expenses: {
                this.expenses = new Expenses(mainPanel);
                this.containerPanel.add(expenses);                
                break;
            }
            case newexpense: {
                this.newExpense = new NewExpense(mainPanel);
                this.containerPanel.add(newExpense);                
                break;
            }
            case editexpense: {
                this.newExpense = new NewExpense(mainPanel);
                this.containerPanel.add(newExpense);                
                break;
            }   
            case newclient: {
                this.newClient = new NewClient(mainPanel);
                this.containerPanel.add(newClient);                
                break;
            }
            case editclient: {
                this.newClient = new NewClient(mainPanel);
                this.containerPanel.add(newClient);                
                break;
            }
            case newproduct: {
                this.newProduct = new NewProduct(mainPanel);
                this.containerPanel.add(newProduct);                
                break;
            }
            case editproduct: {
                this.newProduct = new NewProduct(mainPanel);
                this.containerPanel.add(newProduct);                
                break;
            }
            case home: {
                this.containerPanel.add(home);                
                break;
            }
            case install: {
                this.install = new Install();
                this.containerPanel.add(install);        
                break;
            }
            case installappemail: {
                this.installAppEmail = new InstallAppEmail(mainPanel);
                this.containerPanel.add(installAppEmail);                
                break;
            }
            case installappconfig: {
                this.installAppConfig = new InstallAppConfig(mainPanel);
                this.containerPanel.add(installAppConfig);                
                break;
            }
            case installdbtest: {
                this.installDBTest = new InstallDBTest(mainPanel);
                this.containerPanel.add(installDBTest);                
                break;
            }
            case installdbcreate: {
                this.installDBCreate = new InstallDBCreate(mainPanel);
                this.containerPanel.add(installDBCreate);                
                break;
            }
            case services: {
                this.services = new Services();
                this.containerPanel.add(services);                
                break;
            }
            case contactus: {
                this.contactUs = new ContactUs(mainPanel);
                this.containerPanel.add(contactUs);                
                break;
            }
            case aboutus: {
                this.aboutUs = new AboutUs();
                this.containerPanel.add(aboutUs);                
                break;
            }   
            case clients: {
                this.clients = new Clients(mainPanel);
                this.containerPanel.add(clients);     
                break;
            }
            case settings_application: {
                this.showConfiguration(ConfigurationSection.ApplicationSettings);
                break;
            }
            case settings_customattribs: {
                this.showConfiguration(ConfigurationSection.CustomAttributes);                
                break;
            }
            case settings_email: {
                this.showConfiguration(ConfigurationSection.Email);                
                break;
            }
            case settings_templates: {
                this.showConfiguration(ConfigurationSection.InvoiceTemplates);                
                break;
            }   
            case settings_sysinfo: {
                this.showConfiguration(ConfigurationSection.SystemInformation);                    
                break;
            }
            case settings_business: {
                this.showConfiguration(ConfigurationSection.Business);
                break;
            }
            case auto_showinvoice: {
                this.viewInvoice = new ViewInvoice(mainPanel, ViewInvoicePageMode.VIEWINVOICE_AUTOLOGIN_USER);
                this.containerPanel.add(viewInvoice);
                break;
            }
//            case auto_showpaypal_notify: {
//                this.viewInvoice = new ViewInvoice(mainPanel, ViewInvoicePageMode.VIEWINVOICE_PAYPAL_NOTIFY);
//                this.containerPanel.add(viewInvoice);
//                break;
//            }
        }
    }
    // </editor-fold>    
}

