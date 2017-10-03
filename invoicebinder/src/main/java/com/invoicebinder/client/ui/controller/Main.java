package com.invoicebinder.client.ui.controller;

import com.invoicebinder.client.service.config.ConfigServiceCallbacks;
import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.service.login.LoginServiceClientImpl;
import com.invoicebinder.client.ui.container.Container;
import com.invoicebinder.client.ui.footer.Footer;
import com.invoicebinder.client.ui.header.Header;
import com.invoicebinder.client.ui.menu.Menu;
import com.invoicebinder.client.ui.pages.invoices.view.PreviewInvoice;
import com.invoicebinder.shared.enums.AutoLoginViews;
import com.invoicebinder.shared.enums.config.ApplicationSettingsItems;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.localstore.LocalStore;
import com.invoicebinder.shared.enums.report.Report;
import com.invoicebinder.shared.model.*;
import com.invoicebinder.shared.enums.startup.HomePageSettings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static com.invoicebinder.shared.misc.Utils.getParamFromHref;

public class Main extends Composite {
    
    private static final MainUiBinder uiBinder = GWT.create(MainUiBinder.class);
    private Header hdr;
    private Menu mnu;
    private Container container;
    private Footer footer;
    private PreviewInvoice previewInvoice;
    private final ConfigServiceClientImpl configService;
    private final LoginServiceClientImpl loginService;

    /**
     ***** Application variables. ****** 
     * These variables are set during app initialization
     * and can be used anywhere during the life of the app.
     */
    private final String accountName;   
    private String userName;
    private String userEmail;
    private Boolean isInitialized;
    private String planCode; 
    private Integer planClientCount;
    private final Storage clientStorage;
    private final InstallationInfo installInfo;
    @UiField
    protected HTMLPanel mainPanel;

    interface MainUiBinder extends UiBinder<Widget, Main> {
    }
    
    public Main(String accountName) {
        this.accountName = accountName;
        this.clientStorage = Storage.getLocalStorageIfSupported();
        this.installInfo = new InstallationInfo();
        initWidget(uiBinder.createAndBindUi(this));
        configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this);
        loginService = new LoginServiceClientImpl(GWT.getModuleBaseURL() + "services/login", this);
        this.setIsInitialized(false);
        hdr = new Header();
        mnu = new Menu(this);
        container = new Container(this);
        footer = new Footer();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Header getHdr() {
        return hdr;
    }
    public void setHdr(Header hdr) {
        this.hdr = hdr;
    }
    public Menu getMnu() {
        return mnu;
    }
    public void setMnu(Menu mnu) {
        this.mnu = mnu;
    }
    public Container getContainer() {
        return container;
    }
    public void setContainer(Container container) {
        this.container = container;
    }
    public Footer getFooter() {
        return footer;
    }
    public void setFooter(Footer footer) {
        this.footer = footer;
    }
    public HTMLPanel getMainPanel() {
        return mainPanel;
    }
    public void setMainPanel(HTMLPanel MainPanel) {
        this.mainPanel = MainPanel;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Boolean getIsInitialized() {
        return isInitialized;
    }
    private void setIsInitialized(Boolean isInitialized) {
        this.isInitialized = isInitialized;
    }
    public PreviewInvoice getPreviewInvoice() {
        return previewInvoice;
    }
    public void setPreviewInvoice(PreviewInvoice previewInvoice) {
        this.previewInvoice = previewInvoice;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAccountName() {
        return accountName;
    } 

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public Integer getPlanClientCount() {
        return planClientCount;
    }

    public void setPlanClientCount(Integer planClientCount) {
        this.planClientCount = planClientCount;
    }

    public Storage getClientStorage() {
        return clientStorage;
    }

    public InstallationInfo getInstallInfo() {
        return installInfo;
    }
    // </editor-fold>
    
    private void initInstallSession() {
        container.setContainerType(Container.ContainerType.EmptyContainer);
        container.setHomePageMode(HomePageSettings.INSTALLPAGE);
        this.mainPanel.clear();
        this.mainPanel.add(container);
        this.setIsInitialized(true);
    }
    private void initStandardSession() {
        mnu.setMenuType(Menu.MenuType.StandardMenu);
        container.setContainerType(Container.ContainerType.StandardContainer);
        container.setHomePageMode(HomePageSettings.HOMEPAGE);
        this.mainPanel.clear();
        this.mainPanel.add(hdr);
        this.mainPanel.add(mnu);
        this.mainPanel.add(container);
        this.mainPanel.add(footer);
        this.setIsInitialized(true);
    }
    private void initLoginPageOnlySession() {
        container.setContainerType(Container.ContainerType.EmptyContainer);
        container.setHomePageMode(HomePageSettings.LOGINPAGE);
        this.mainPanel.clear();
        this.setIsInitialized(true);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    public void applyStyle() {
        Document.get().getBody().setClassName("main-body");
    }
    public void applyAppLandingSession(HomePageSettings landingPage) {
        String initToken = History.getToken();
        
        if (landingPage == HomePageSettings.INSTALLPAGE) {
            initInstallSession();            
            if (initToken.length() == 0) {
                History.newItem("install");
            }
        }
        if (landingPage == HomePageSettings.HOMEPAGE) {
            initStandardSession();            
            if (initToken.length() == 0) {
                History.newItem("home");
            }
        }
        if (landingPage == HomePageSettings.LOGINPAGE) {
            initLoginPageOnlySession();
            if (initToken.length() == 0) {
                History.newItem("login");
            }
        }
        //Fire History State.
        History.fireCurrentHistoryState();
    }
    public void applyApplicationSettings(AppSettingsInfo appSettings) {
        this.applyAppLandingSession(appSettings.getAppLandingPage());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Context switching based on authentication">
    public void Logout() {
        configService.loadApplicationSettingsForGettingLogoutPrefs();
    }
    public void ChangeContextToAuthenticatedUser() {
        mnu.setMenuType(Menu.MenuType.AuthenticatedMenu);
        container.setContainerType(Container.ContainerType.AuthenticatedContainer);
        this.mainPanel.clear();
        this.mainPanel.add(mnu);
        this.mainPanel.add(container);
        this.mainPanel.add(footer);
    }
    public void ChangeContextToNonAuthenticatedUser() {
        mnu.setMenuType(Menu.MenuType.StandardMenu);
        container.setContainerType(Container.ContainerType.StandardContainer);
        this.mainPanel.clear();
        this.mainPanel.add(hdr);
        this.mainPanel.add(mnu);
        this.mainPanel.add(container);
        this.mainPanel.add(footer);
    }
    public void ChangeContextToAutoAuthenticatedUser() {
        container.setContainerType(Container.ContainerType.EmptyContainer);
        this.mainPanel.clear();
        this.mainPanel.add(container);
        this.setIsInitialized(true);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Validation Updates via Callback on Pages">
    public void updateDBTestResult(ServerValidationResult result) {
        this.container.updateDBTestResult(result);    
    }
    public void updateEmailTestResult(ServerValidationResult result) {
        this.container.updateEmailTestResult(result);
    }      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="View handlers">   
    public void showLoginDialog() {
        this.container.showLoginDialog();
    }
    public void showLoginDialog(Boolean showCloseButton) {
        this.container.showLoginDialog(showCloseButton);
    }
    public void hideLoginDialog() {
        this.container.hideLoginDialog();
    } 
    private void logoutToHomePage() {
        this.ChangeContextToNonAuthenticatedUser();
        this.container.show(Views.home);
    }
    private void logoutToLoginPage() {
        this.mainPanel.clear();
        this.showLoginDialog(false);
    }
    public void showPreviewInvoice() {
       this.mainPanel.clear();
       this.previewInvoice = new PreviewInvoice(this);
       this.mainPanel.add(previewInvoice);
    }
    public void performAutoLogin() {
        String paramView = getParamFromHref("view");
        if (paramView == null) {
            this.showLoginDialog();
            return;
        }

        AutoLoginViews view = AutoLoginViews.valueOf(paramView);

        switch (view) {
            case viewinvoice:
            case paypalnotify: {
                String amount = getParamFromHref("amount");
                AutoLoginProps loginProps = new AutoLoginProps(getParamFromHref("token"), getParamFromHref("invnum"), new BigDecimal(amount));
                loginService.authenticateAutoLogin(loginProps, view);
            }
            break;

            default: {
                this.showLoginDialog(false);
            }
            break;
        }
    }
    public void show(Views view) {
        this.container.show(view);
    }
    // </editor-fold
    
    // <editor-fold defaultstate="collapsed" desc="Update Screen Contents Handlers">
    public void performLogout(ConfigurationSection configurationSection, ArrayList<ConfigData> arrayList) {
        String landingPage;
        HomePageSettings setting;
        HashMap<String, String> applicationSettings = new HashMap();
        ConfigData data;
        for (ConfigData item : arrayList) {
            data = item;
            applicationSettings.put(data.getKey(), data.getValue());
        }
        
        landingPage = applicationSettings.get(ApplicationSettingsItems.APPLANDINGPAGE.toString());
        if (!landingPage.isEmpty()) {
            setting = HomePageSettings.valueOf(landingPage);
            
            if (setting == HomePageSettings.HOMEPAGE) {
                this.logoutToHomePage();
            }
            if (setting == HomePageSettings.LOGINPAGE) {
                this.logoutToLoginPage();
            }
        }
    }
    public void updateNewInvoiceClientTableEmptyMessage() {
        this.container.updateNewInvoiceClientTableEmptyMessage();
    }
    public void updateNewInvoiceProductTableEmptyMessage() {
        this.container.updateNewInvoiceProductTableEmptyMessage();
    }
    public void updateInvoiceStatusForInvoicesPage(Boolean aBoolean) {
        this.container.updateInvoicesStatusForInvoicesPage(aBoolean);
    }
    public void updateConfigurationPagesConfig(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        this.container.updateConfigurationPagesConfig(section, arrayList);
    }
    public void updateConfigurationPagesStatus(SaveConfigResult result) {
        this.container.updateConfigurationPagesStatus(result.getSection(), result.isStatus());
    }
    public void updateCustomAttrInNewInvoicePage(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        this.container.updateCustomAttrDataForNewInvoicePage(section, arrayList);
    }
    public void updateBusinessConfigData(ConfigurationSection section, ConfigServiceCallbacks.BusinessConfigTargetPage page, ArrayList<ConfigData> arrayList) {
        if (page == ConfigServiceCallbacks.BusinessConfigTargetPage.HomePage) {
            this.container.updateSocialMediaConfigForHomePage(section, arrayList);
        }
        else if(page == ConfigServiceCallbacks.BusinessConfigTargetPage.NewInvoicePage) {
            this.container.updateNewInvoiceConfig(section, arrayList);
        }
    }
    public void updateEmailConfigData(ConfigServiceCallbacks.EmailConfigTargetPage page, ArrayList<ConfigData> list) {
        if (page == ConfigServiceCallbacks.EmailConfigTargetPage.ViewInvoicePage) {
            this.container.updateEmailConfigForViewInvoicePage(list);
        }
    }
    public void updateClientsCount(int count) {
        this.container.updateClientsCount(count);
    }
    public void updateInvoicesCount(Integer count) {
        this.container.updateInvoicesCount(count);
    }
    public void updateOverdueInvoicesCount(Integer count) {
        this.container.updateOverdueInvoicesCount(count);
    }    
    public void updateUsersCount(Integer count) {
        this.container.updateUsersCount(count);
    }
    public void updateProductsCount(Integer count) {
        this.container.updateProductsCount(count);
    }
    public void updateExpensesCount(Integer count) {
        this.container.updateExpensesCount(count);
    }
    public void updateInvoiceDetailsForInvoicePage(ViewInvoiceInfo viewInvoiceInfo) {
        this.container.updateInvoiceDetailsForInvoicePage(viewInvoiceInfo);
    }
    public void updateInvoiceDetailsForInvoicePreviewPage(ViewInvoiceInfo viewInvoiceInfo) {
        this.previewInvoice.updateInvoiceDetailsForInvoicePreviewPage(viewInvoiceInfo);
    }
    public void updateContactUsMail(boolean result) {
        this.container.updateContactUsMail(result);
    }
    public void updateLoginDialog(AuthenticationResult result) {
        this.clientStorage.setItem(LocalStore.CLIENTCOUNT.toString(), String.valueOf(result.getClientCount()));
        this.container.updateLoginDialog(result);
        this.mnu.UpdateMenuStatus(result);
    }
    public void updatePasswordSaveStatus(Boolean status) {
        this.container.updatePasswordSaveStatus(status);
    }
    public void updateInvoiceMail(Boolean result) {
        this.container.updateInvoiceMail(result);
    }
    public void updateTestEmail(Boolean result) {
        this.container.updateTestEmail(result);
    }    
    public void updateInvoiceStatusForViewInvoicePage(Boolean result) {
        this.container.updateInvoiceStatusForViewInvoicePage(result);
    }
    public void updateForgotPasswordDialog(String result) {
        this.container.updateForgotPasswordDialog(result);
    }
    public void updateReportData(List list, Report report) {
        this.container.updateReportData(list, report);
    }    
    public void updateDashboardIncomeAndExpenseReport(List list) {
        this.container.updateDashboardIncomeAndExpenseReport(list);
    }    
    public void updateDashboardStats(DashStatsInfo dashStatsInfo) {
        this.container.updateDashboardStats(dashStatsInfo);
    }
    public void updateSystemInfo(SystemInfo systemInfo) {
        this.container.updateSystemInfo(systemInfo);
    }
    public void updateApplicationConfigForDashboard(ConfigurationSection configurationSection, ArrayList<ConfigData> arrayList) {
        this.container.updateAppSettingConfigForDashboard(configurationSection, arrayList);
    }
    public void updateInvoiceTemplateName(ConfigServiceCallbacks.TemplateNameTargetPage page, ArrayList<ConfigData> data) {
        if (page == ConfigServiceCallbacks.TemplateNameTargetPage.InvoicePreviewPage) {
            this.previewInvoice.createInvoiceBasedOnInvoiceTemplateForInvoicePreviewPage(data);
        }
        else {
            this.getContainer().updateInvoiceTemplateName(page, data);
        }
    }
    public void updateDBCreateResult(Boolean result) {
        this.container.updateDBCreateResult(result);
    }  
    public void updateDefaultDataCreateResult(Boolean result) {
        this.container.updateDefaultDataCreateResult(result);
    }    
    public void updateDBCreateProceduresResult(Boolean result) {
        this.container.updateDBCreateProcedureResult(result);
    }  
    public void updateDBCreateConfigResult(Boolean result) {
        this.container.updateDBCreateConfigResult(result);        
    }    
    public void updatePaymentsCount(Integer result) {
        this.container.updatePaymentsCount(result);
    }
    public void displayAutoLoginView(AuthenticationResult result, AutoLoginViews view) {
        if (result.isAuthenticated()) {
            switch (view) {
                case viewinvoice: {
                    this.show(Views.auto_showinvoice);
                }
                break;
                case paypalnotify: {
                    this.show(Views.auto_showpaypal_notify);
                }
                break;
                default: {
                    this.showLoginDialog(false);
                }
                break;
            }
        }
        else {
            this.showLoginDialog();
        }
    }
    // </editor-fold>
}
