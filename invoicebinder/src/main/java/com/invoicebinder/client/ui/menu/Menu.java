package com.invoicebinder.client.ui.menu;

import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.misc.Utils;
import com.invoicebinder.shared.model.AuthenticationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;

public class Menu extends Composite {
    
    private static final MenuUiBinder uiBinder = GWT.create(MenuUiBinder.class);
    
    public void UpdateMenuStatus(AuthenticationResult result) {
        Date expires = new Date((new Date()).getTime() + Constants.COOKIE_TIMEOUT);
        Cookies.setCookie("AUTH_USERNAME", result.getUsername(), expires);
        updateMenuText(result.getUsername());
    }
    
    public enum MenuType {
        StandardMenu, AuthenticatedMenu, HiddenMenu
    }
    
    interface MenuUiBinder extends UiBinder<Widget, Menu> {
    }
    
    @UiField HTMLPanel MenuPanel;
    @UiField DivElement Menu;
    @UiField DivElement AuthMenu;
    @UiField Anchor Home;
    @UiField Anchor Services;
    @UiField Anchor AboutUs;
    @UiField Anchor ContactUs;
    @UiField Anchor Login;
    @UiField Anchor Dashboard;
    @UiField Anchor Clients;
    @UiField Anchor Invoices;
    @UiField Anchor Payments;
    @UiField Anchor Expenses;
    @UiField Anchor Products;
    @UiField Anchor Reports;
    @UiField Image Settings;
    @UiField MenuBar mainMenu;
    @UiField MenuItem loginItem;
    @UiField MenuBar subMenu;
    
    private final Main mainPanel;
    
    public Menu(Main main) {
        initWidget(uiBinder.createAndBindUi(this));
        this.mainPanel = main;
        this.Menu.setId("menu");
        this.AuthMenu.setId("authmenu");
    }
    
    public void setMenuType(MenuType type){
        if (type == MenuType.StandardMenu) {
            mainPanel.setVisible(true);
            CreateMenu();
        }
        else if (type == MenuType.AuthenticatedMenu) {
            CreateMenuForAuthenticatedUser();
        }
    }
    
    private void CreateMenu() {
        Menu.getStyle().setDisplay(Display.BLOCK);
        AuthMenu.getStyle().setDisplay(Display.NONE);
        
        Home.setHTML("<a href=\"#\">Home</a>");
        Services.setHTML("<a href=\"#\">Services</a>");
        AboutUs.setHTML("<a href=\"#\">About Us</a>");
        ContactUs.setHTML("<a href=\"#\">Contact Us</a>");
        Login.setHTML("<a href=\"#\">Login</a>");
        
        Home.addClickHandler(new HomeClickHandler());
        Services.addClickHandler(new ServicesClickHandler());
        AboutUs.addClickHandler(new AboutUsClickHandler());
        ContactUs.addClickHandler(new ContactUsClickHandler());
        Login.addClickHandler(new LoginClickHandler());
    }
    
    private void CreateMenuForAuthenticatedUser() {
        MenuItem logoutItem;
        MenuItem userDetailsItem;
        MenuItem accountInfoItem;
        MenuItem changePlanItem;
        Command logoutCommand;
        Command userDetailsCommand;
        Command accountInfoCommand;
        Command changePlanCommand;
        
        //set logout command
        logoutCommand = new Command() {
            @Override
            public void execute() {
                History.newItem(Views.logout.toString());
            }
        };
        
        //set commands
        userDetailsCommand = new Command() {
            @Override
            public void execute() {
                History.newItem(Views.useraccountdetails.toString());
            }
        };
        
        accountInfoCommand = new Command() {
            @Override
            public void execute() {
                History.newItem(Views.settings_account.toString());
            }
        };
        
        
        Menu.getStyle().setDisplay(Display.NONE);
        AuthMenu.getStyle().setDisplay(Display.BLOCK);
        
        Dashboard.setHTML("<a href=\"#\">Dashboard</a>");
        Clients.setHTML("<a href=\"#\">Clients</a>");
        Invoices.setHTML("<a href=\"#\">Invoices</a>");
        Payments.setHTML("<a href=\"#\">Payments</a>");
        Expenses.setHTML("<a href=\"#\">Expenses</a>");
        Products.setHTML("<a href=\"#\">Products</a>");
        Reports.setHTML("<a href=\"#\">Reports</a>");
        Settings.setUrl("images/icons/settings.png");
        
        //login menu
        mainMenu.setFocusOnHoverEnabled(true);
        mainMenu.setWidth("100px");
        subMenu.setStyleName("subMenu");
    
        updateMenuText(Cookies.getCookie("AUTH_USERNAME"));
        
        logoutItem = new MenuItem("Logout", logoutCommand);
        logoutItem.setStyleName("subItem");
        userDetailsItem = new MenuItem("User Details", userDetailsCommand);
        userDetailsItem.setStyleName("subItem");
        accountInfoItem = new MenuItem("Account Details", accountInfoCommand);
        accountInfoItem.setStyleName("subItem");            
        subMenu.clearItems();
        subMenu.insertItem(logoutItem, 0);
        if (!Utils.isDemoApplication(true)) {
            subMenu.insertItem(accountInfoItem, 0);
        }        
        subMenu.insertItem(userDetailsItem, 0);
        
        Dashboard.addClickHandler(new DashboardClickHandler());
        Clients.addClickHandler(new ClientsClickHandler());
        Invoices.addClickHandler(new InvoicesClickHandler());
        Payments.addClickHandler(new PaymentsClickHandler());
        Expenses.addClickHandler(new ExpensesClickHandler());
        Products.addClickHandler(new ProductsClickHandler());
        Reports.addClickHandler(new ReportsClickHandler());
        Settings.addClickHandler(new SettingsClickHandler());
    }
    
    private void updateMenuText(String authUserName) {
        if (authUserName != null) {
            loginItem.setText("[" + authUserName + "]");
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class HomeClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.home.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class ServicesClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.services.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class AboutUsClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.aboutus.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class ContactUsClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.contactus.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class LoginClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            ((Main)mainPanel).showLoginDialog();
        }
    }
    private class DashboardClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.dashboard.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class ClientsClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.clients.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class ProductsClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.products.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class InvoicesClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.invoices.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class PaymentsClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.payments.toString());
            event.getNativeEvent().preventDefault();
        }
    }    
    private class ExpensesClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.expenses.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class ReportsClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.reports.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    private class SettingsClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            History.newItem(Views.settings_business.toString());
            event.getNativeEvent().preventDefault();
        }
    }
    // </editor-fold>
}

