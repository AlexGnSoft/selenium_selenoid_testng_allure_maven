package com.carespeak.domain.steps;

import com.carespeak.domain.entities.client.Client;
import com.carespeak.domain.entities.common.Day;

public interface ClientSteps extends BaseSteps {

    /**
     * Adds new Auto Responder for provided client
     *
     * @param client   - client to add auto responder
     * @param message  - message for Auto response
     * @param isAllDay - is respond will work during the whole day
     * @param days     - days for auto response
     */
    ClientSteps addAutoResponder(Client client, String message, boolean isAllDay, Day... days);


    /**
     * Adds new Auto Responder for provided client
     *
     * @param client   - client to add auto responder
     * @param message  - message for Auto response
     * @param isAllDay - is respond will work during the whole day
     * @param from     - phone number from
     * @param to       - phone number to
     * @param days     - days for auto response
     * @return ClientSteps object
     */
    ClientSteps addAutoResponder(Client client, String message, String from, String to, boolean isAllDay, Day... days);


    /**
     * Adds OptOut Header for specified client with specified message
     * @param client - client to use
     * @param message - message to add
     * @return ClientSteps object
     */
    ClientSteps addOptOutHeader(Client client, String message);


    /**
     * Adds OptOut Body for specified client with specified message
     * @param client - client to use
     * @param message - message to add
     * @return ClientSteps object
     */
    ClientSteps addOptOutBody(Client client, String message);


    /**
     * Adds OptOut Footer for specified client with specified message
     * @param client - client to use
     * @param message - message to add
     * @return ClientSteps object
     */
    ClientSteps addOptOutFooter(Client client, String message);


    ClientSteps addWebhook(Client client, String webhookName, String webhookUrl, Integer interval);
}