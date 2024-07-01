package com.example.music.controller;

import com.example.music.dao.AlbumSelect;
import com.example.music.dto.AlbumDTO;
import com.example.music.entity.Album;
import com.example.music.service.impl.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/album")
@CrossOrigin("*")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping(value = "")
    public Page<Album> getObject(@RequestParam(name = "status", defaultValue = "Activate") String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return this.albumService.getObject(status, pageable);
    }

    @GetMapping(value = "/select")
    public ResponseEntity<Map<Long, Album>> select(@RequestParam(value = "status", defaultValue = "Activate", required = false) String status) {
        try {
            return new ResponseEntity<>(this.albumService.select(status), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Album> insert(@ModelAttribute AlbumDTO album) {
        try {
            return new ResponseEntity<>(this.albumService.insertAlbum(album), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Album> update(@ModelAttribute Long id, @RequestBody Album album) {
        try {
            return new ResponseEntity<>(this.albumService.update(id, album), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(album, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Album> delete(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.albumService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Album> search(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.albumService.detail(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/admin")
    public ResponseEntity<List<AlbumSelect>> getAllForSelectInput() {
        try {
            return new ResponseEntity<>(this.albumService.getAlbumSelect(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/artis/{artis}")
    public ResponseEntity<List<AlbumSelect>> getAllForSelectInput(@PathVariable Integer artis) {
        try {
            return new ResponseEntity<>(this.albumService.getAlbumSelect(artis), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
