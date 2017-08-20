/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.server.core.exception;

/**
 *
 * @author mon2
 */
public enum ExceptionType {
    DataAccessException,
    ServiceException,
    PropertiesLoadException,
    PropertiesSetException,
    BusinessLogicException,
    SystemException
}
