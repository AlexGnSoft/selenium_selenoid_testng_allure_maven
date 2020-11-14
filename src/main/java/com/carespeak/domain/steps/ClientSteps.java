package com.carespeak.domain.steps;

import com.carespeak.domain.entities.common.Day;
import com.carespeak.domain.entities.client.Client;

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

    ClientSteps addAutoResponder(Client client, String message, String from, String to, boolean isAllDay, Day... days);


}
