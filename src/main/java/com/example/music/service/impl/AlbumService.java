package com.example.music.service.impl;

import com.example.music.entity.Album;
import com.example.music.repositories.AlbumRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AlbumService implements IService<Album, Long> {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public Album insert(Album album) {
        this.albumRepository.save(album);
        return album;
    }

    @Override
    public Album update(Long id, Album album) {
        this.albumRepository.save(album);
        return album;
    }

    @Override
    public Album delete(Long id) {
        this.albumRepository.updateStatus(id);
        return this.albumRepository.findById(id).orElse(null);
    }

    @Override
    public Album detail(Long id) {
        return this.albumRepository.findById(id).orElse(null);
    }

    @Override
    public Map<Long, Album> select(String status) {
        return this.albumRepository.select(status).stream().collect(Collectors.toMap(Album::getId, Function.identity()));
    }

}
