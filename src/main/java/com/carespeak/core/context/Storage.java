package com.carespeak.core.context;

import com.carespeak.core.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class Storage<T> {

    private ThreadLocal<T> storage = new ThreadLocal<>();
    private List<T> entitiesList = new ArrayList<>();

    public T getEntity() {
        T obj = storage.get();
        if (obj == null) {
            synchronized (entitiesList) {
                T operand = entitiesList.get(0);
                storage.set(operand);
                entitiesList.remove(operand);
            }
        }
        return storage.get();
    }

    public void setEntities(List<T> entities) {
        entitiesList.addAll(entities);
    }

    public int getEntityListSize() {
        Logger.info("Get entity list size..");
        int size = entitiesList.size();
        Logger.info("Entity list size - " + size);
        return size;
    }

}
