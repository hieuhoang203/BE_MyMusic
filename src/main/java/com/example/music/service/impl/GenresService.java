package com.example.music.service.impl;

import com.example.music.dao.GenresSelect;
import com.example.music.entity.Genres;
import com.example.music.repositories.GenresRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GenresService implements IService<Genres, Byte> {
    
    @Autowired
    private GenresRepository repository;

    @Override
    public Page<Genres> getObject(String status, Pageable pageable) {
        return this.repository.getGenres(status, pageable);
    }

    @Override
    public Genres insert(Genres genres) {
        this.repository.save(genres);
        return genres;
    }

    @Override
    public Genres update(Byte id, Genres genres) {
        this.repository.save(genres);
        return genres;
    }

    @Override
    public Genres delete(Byte id) {
        this.repository.updateStatus(id);
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Genres detail(Byte id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Map<Byte, Genres> select(String status) {
        Map<Byte, Genres> genresMap;
        genresMap = this.repository.select(status).stream().collect(Collectors.toMap(Genres::getId, Function.identity()));
        return genresMap;
    }

    public Page<Genres> getAll(Pageable pageable) {
        return this.repository.getAll(pageable);
    }

    public Genres returnStatus(Byte id) {
        this.repository.returnStatus(id);
        return this.repository.findById(id).orElse(null);
    }

    public List<GenresSelect> getGenresForSelect() {
        return this.repository.getGenresForSelect();
    }

}
