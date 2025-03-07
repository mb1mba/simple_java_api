package com.api.app.services;

import java.util.List;

public interface Service<T> {
    void createEntity(T entity);

    List<T> getEntities();

    Class<T> getEntityType();

    void deleteEntity(String id);

    boolean doesEntityExist(String id);
}
