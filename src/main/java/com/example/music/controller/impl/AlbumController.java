package com.example.music.controller.impl;

import com.example.music.controller.IController;
import com.example.music.entity.Album;
import com.example.music.service.impl.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/album")
public class AlbumController implements IController<Album, Long> {

    @Autowired
    private AlbumService albumService;

    @Override
    @GetMapping(value = "")
    public Map<Long, Album> select(@RequestParam(value = "status", defaultValue = "DANG_HOAT_DONG", required = false) String status) {
        return this.albumService.select(status);
    }

    @Override
    @PostMapping(value = "/save")
    public Album insert(@RequestBody Album album) {
        return this.albumService.insert(album);
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public Album update(@PathVariable Long id, @RequestBody Album album) {
        return this.albumService.update(id, album);
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public Album delete(@PathVariable Long id) {
        return this.albumService.delete(id);
    }

    @Override
    @GetMapping(value = "/search/{id}")
    public Album search(@PathVariable Long id) {
        return this.albumService.detail(id);
    }

}
