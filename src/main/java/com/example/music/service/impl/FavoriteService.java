package com.example.music.service.impl;

import com.example.music.entity.Favorite;
import com.example.music.repositories.FavoriteRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FavoriteService implements IService<Favorite, Long> {

    @Autowired
    private FavoriteRepository favoritesListRepository;


    @Override
    public Page<Favorite> getObject(String status, Pageable pageable) {
        return null;
    }

    @Override
    public Favorite insert(Favorite favorites) {
        this.favoritesListRepository.save(favorites);
        return favorites;
    }

    @Override
    public Favorite update(Long id, Favorite favorites) {
        this.favoritesListRepository.save(favorites);
        return favorites;
    }

    @Override
    public Favorite delete(Long id) {
        this.favoritesListRepository.updateStatus(id);
        return this.favoritesListRepository.findById(id).orElse(null);
    }

    @Override
    public Favorite detail(Long id) {
        return this.favoritesListRepository.findById(id).orElse(null);
    }

    @Override
    public Map<Long, Favorite> select(String status) {
        return this.favoritesListRepository.select(status).stream().collect(Collectors.toMap(Favorite::getId, Function.identity()));
    }

}
