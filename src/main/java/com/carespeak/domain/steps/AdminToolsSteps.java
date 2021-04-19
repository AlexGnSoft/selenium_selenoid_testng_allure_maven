package com.carespeak.domain.steps;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Language;
import com.carespeak.domain.entities.message.Module;

import java.util.List;

public interface AdminToolsSteps extends BaseSteps {

    /**
     * Add new user with provided parameters
     *
     * @param code     unique code for new client
     * @param username client's username
     * @param notes    some notes about the client
     * @param endpoint endpoint to send SMS
     * @param modules  modules to add
     * @return AdminToolsSteps object
     */
    AdminToolsSteps addNewClient(String code, String username, String notes, String endpoint, Module... modules);


    /**
     * Add new user with provided parameters
     *
     * @param client  client object to create
     * @param modules modules to add
     * @return AdminToolsSteps object
     */
    AdminToolsSteps addNewClient(Client client, Module... modules);


    /**
     * Add new user with provided parameters
     *
     * @param client client object to create
     * @return AdminToolsSteps object
     */
    AdminToolsSteps addNewClient(Client client);


    /**
     * Remove new user with provided parameters
     *
     * @param client client object to create
     * @return AdminToolsSteps object
     */
    AdminToolsSteps removeClient(Client client);


    /**
     * Opens client setting page for client by provided client code
     *
     * @param clientCode code to search
     * @param newModules new modules to be set
     * @return AdminToolsSteps object
     */
    AdminToolsSteps editClientsModules(String clientCode, Module... newModules);


    /**
     * Returns client object by code
     *
     * @param code string code
     * @return Client object
     */
    Client getClientByCode(String code);


    /**
     * Go to general settings view for specified client
     *
     * @param code client code to find a client
     * @return AdminToolsSteps object
     */
    AdminToolsSteps goToClientSettings(String code);


    /**
     * Method will work on General Settings view for a client.
     * Go to general settings view for specified client before you specify your client.
     * <p>
     * Each client has default language, and additional.
     * Method adds additional languages for client
     *
     * @param languages languages to add
     * @return AdminToolsSteps object
     */
    AdminToolsSteps addAdditionalLanguage(Language... languages);

    AdminToolsSteps addAdditionalLanguage(List<Language> languages);


    /**
     * Method will work on General Settings view for a client.
     * Go to general settings view for specified client before you specify your client.
     * <p>
     * Each client has default language, and additional.
     * Method removes additional languages for client
     *
     * @param languages languages to remove
     * @return AdminToolsSteps object
     */
    AdminToolsSteps removeAdditionalLanguage(Language... languages);


    /**
     * Method will return additional languages available for selected client.
     * <p>
     * Method will work on General Settings view for a client.
     * Go to general settings view for specified client before you specify your client.
     *
     * @return client's additional languages
     */
    List<Language> getAdditionalLanguages();


    /**
     * Method will return default language for selected client.
     * <p>
     * Method will work on General Settings view for a client.
     * Go to general settings view for specified client before you specify your client.
     *
     * @return client's default language
     */
    Language getDefaultLanguage();

    /**
     * Send SMS to specified endpoint from simulator
     *
     * @param fromPhoneNumber phone number to send sms from
     * @param endpoint        endpoint/SMS aggregator for user
     * @param smsText         text to send
     * @return AdminToolsSteps object
     */
    AdminToolsSteps simulateSMSToClient(String fromPhoneNumber, String endpoint, String smsText);

}
