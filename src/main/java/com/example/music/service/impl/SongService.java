package com.example.music.service.impl;

import com.example.music.entity.Song;
import com.example.music.repositories.SongRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SongService implements IService<Song, Long> {

    @Autowired
    private SongRepository songRepository;

    @Override
    public Song insert(Song song) {
        this.songRepository.save(song);
        return song;
    }

    @Override
    public Song update(Long id, Song song) {
        this.songRepository.save(song);
        return song;
    }

    @Override
    public Song delete(Long id) {
        this.songRepository.updateStatus(id);
        return this.songRepository.findById(id).orElse(null);
    }

    @Override
    public Song detail(Long id) {
        return this.songRepository.findById(id).orElse(null);
    }

    @Override
    public Map<Long, Song> select(String status) {
        return this.songRepository.select(status).stream().collect(Collectors.toMap(Song::getId, Function.identity()));
    }

}
