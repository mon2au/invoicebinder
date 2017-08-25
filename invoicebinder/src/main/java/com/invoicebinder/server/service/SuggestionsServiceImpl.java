/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.service;

import com.invoicebinder.client.service.suggestion.SuggestionsService;
import com.invoicebinder.server.dataaccess.CountryDAO;
import com.invoicebinder.server.dataaccess.ClientDAO;
import com.invoicebinder.server.dataaccess.CurrencyDAO;
import com.invoicebinder.server.dataaccess.ProductDAO;
import com.invoicebinder.shared.entity.client.Client;
import com.invoicebinder.shared.entity.city.City;
import com.invoicebinder.shared.entity.currency.Currency;
import com.invoicebinder.shared.entity.item.Product;
import com.invoicebinder.shared.suggestion.ClientSuggestion;
import com.invoicebinder.shared.suggestion.CurrencySuggestion;
import com.invoicebinder.shared.suggestion.ProductSuggestion;
import com.invoicebinder.shared.suggestion.CitySuggestion;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("suggestions")
public class SuggestionsServiceImpl extends RemoteServiceServlet implements SuggestionsService {
    
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private ProductDAO prodDAO;
    @Autowired
    private CurrencyDAO currDAO;
    
    @Override
    public Response getCitySuggestions(Request req, String countryCode, String stateCode) {
        CountryDAO cityDAO = new CountryDAO();
        Response response=new Response();
        List<City> matchingCities;
        
        ArrayList<CitySuggestion> suggestions=new ArrayList<>();
        
        matchingCities = cityDAO.getAllMatchingCities(req.getQuery(), countryCode, stateCode, req.getLimit());
        for (City city : matchingCities) {
            suggestions.add(new CitySuggestion(city.getCityName() + " - " + city.getPostCode() , city.getId(), city.getPostCode()));            
        }

        response.setSuggestions(suggestions);
        return response;
    }

    @Override
    public Response getClientSuggestions(Request req) {
        Response response=new Response();
        List<Client> matchingClients;
        String clientText;
        
        ArrayList<ClientSuggestion> suggestions=new ArrayList<>();
        
        matchingClients = clientDAO.getAllMatchingClients(req.getQuery());
        for (Client client : matchingClients) {
            clientText = client.getFirstName();
            
            if (client.getLastName() != null) {
                clientText += " " + client.getLastName();
            }
            
           suggestions.add(new ClientSuggestion(clientText, client.getId(), client.getFirstName(), client.getLastName()));            
        }

        response.setSuggestions(suggestions);
        return response;
    }

    @Override
    public Response getProductSuggestions(Request req) {
        Response response=new Response();
        List<Product> matchingProds;
        
        ArrayList<ProductSuggestion> suggestions=new ArrayList<>();
        
        matchingProds = prodDAO.getAllMatchingProducts(req.getQuery());
        for (Product prod : matchingProds) {
           suggestions.add(new ProductSuggestion(prod.getName(), prod.getId(), prod.getDescription(), prod.getUnitprice()));            
        }

        response.setSuggestions(suggestions);
        return response;
    }

    @Override
    public Response getCurrencySuggestions(Request req) {
        Response response=new Response();
        List<Currency> matchingCurrencies;
        
        ArrayList<CurrencySuggestion> suggestions=new ArrayList<>();
        
        matchingCurrencies = currDAO.getAllMatchingCurrencies(req.getQuery());
        for (Currency curr : matchingCurrencies) {
           suggestions.add(new CurrencySuggestion(curr.getCode() + " - " + curr.getName(), curr.getId(), curr.getName(), curr.getCode()));            
        }

        response.setSuggestions(suggestions);
        return response;
    }
}
