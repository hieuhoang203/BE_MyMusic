package com.example.music.controller;

import com.example.music.entity.Favorite;
import com.example.music.service.impl.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/favorite")
@CrossOrigin("*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoritesListService;

    @PostMapping(value = "/save")
    public ResponseEntity<Favorite> insert(@RequestBody Favorite favorite) {
        try {
            return new ResponseEntity<>(this.favoritesListService.insert(favorite), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(favorite, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Favorite> update(@PathVariable Long id, @RequestBody Favorite favorite) {
        try {
            return new ResponseEntity<>(this.favoritesListService.update(id, favorite), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(favorite, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Favorite> delete(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.favoritesListService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Favorite> search(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.favoritesListService.delete(id), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<Map<Long, Favorite>> select(@RequestParam(value = "status", defaultValue = "Activate", required = false) String status) {
        try {
            return new ResponseEntity<>(this.favoritesListService.select(status), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
