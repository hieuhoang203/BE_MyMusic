package com.example.music.controller.artis;

import com.example.music.entity.Song;
import com.example.music.service.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/artis/song")
@CrossOrigin("*")
public class SongArtisController {

    @Autowired
    private SongService songService;

    @GetMapping(value = "")
    public Page<Song> getObject(@RequestParam(name = "status", defaultValue = "Activate") String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 3);
        return this.songService.getObject(status, pageable);
    }

    @GetMapping(value = "/select")
    public ResponseEntity<Map<Long, Song>> select(@RequestParam(value = "status", defaultValue = "Activate") String status) {
        try {
            return new ResponseEntity<>(this.songService.select(status), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Song> insert(@RequestBody Song song) {
        try {
            return new ResponseEntity<>(this.songService.insert(song), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Song> update(@PathVariable Long id, @RequestBody Song song) {
        try {
            return new ResponseEntity<>(this.songService.update(id, song), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Song> delete(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.songService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/search/{id}")
    public ResponseEntity<Song> search(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.songService.detail(id), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
