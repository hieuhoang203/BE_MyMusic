package com.example.music.controller;

import java.util.Map;

public interface IController <T, ID>{

    Map<ID, T> select(String status);

    T insert(T object);

    T update(ID id, T object);

    T delete(ID id);

    T search(ID id);

}
