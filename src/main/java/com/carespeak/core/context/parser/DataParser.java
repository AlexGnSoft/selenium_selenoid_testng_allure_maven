package com.carespeak.core.context.parser;

import com.carespeak.domain.entities.client.Client;

/**
 * Interface for parsing different system entities (e.g. patient, program)
 */
public interface DataParser {

    <T> T parseEntity(Client client);

}
