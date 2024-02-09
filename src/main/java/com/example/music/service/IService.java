package com.example.music.service;

import java.util.Map;

public interface IService <T, ID> {

    T insert(T obj);

    T update(ID id, T obj);

    T delete(ID id);

    T detail(ID id);

    Map<ID, T> select(String status);

}
