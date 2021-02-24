package com.carespeak.core.driver.reporter;

import java.util.concurrent.Callable;

public interface IElementInteractionsReporter {

    <T> T reportAction(String message, Callable<T> actionFunction);

}
