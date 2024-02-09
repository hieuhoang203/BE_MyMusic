package com.example.music.controller.impl;

import com.example.music.controller.IController;
import com.example.music.entity.Song;
import com.example.music.service.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/song")
public class SongController implements IController<Song, Long> {
    
    @Autowired
    private SongService songService;
    
    @Override
    @GetMapping(value = "")
    public Map<Long, Song> select(@RequestParam(value = "status", defaultValue = "DANG_HOAT_DONG") String status) {
        return this.songService.select(status);
    }

    @Override
    @PostMapping(value = "/save")
    public Song insert(@RequestBody Song song) {
        return this.songService.insert(song);
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public Song update(@PathVariable Long id, @RequestBody Song song) {
        return this.songService.update(id, song);
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public Song delete(@PathVariable Long id) {
        return this.songService.delete(id);
    }

    @Override
    @DeleteMapping(value = "/search/{id}")
    public Song search(@PathVariable Long id) {
        return this.songService.detail(id);
    }
    
}
