package com.example.music.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IService <T, ID> {

    Page<T> getObject(String status, Pageable pageable);

    T insert(T obj);

    T update(ID id, T obj);

    T delete(ID id);

    T detail(ID id);

    Map<ID, T> select(String status);

}
