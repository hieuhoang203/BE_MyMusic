package com.example.music.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/favorite")
@CrossOrigin("*")
public class FavoriteController {

//    @Autowired
//    private FavoriteService favoritesListService;
//
//    @PostMapping(value = "/save")
//    public ResponseEntity<Favorite> insert(@RequestBody FavoriteDTO favorite) {
//        try {
//            return new ResponseEntity<>(this.favoritesListService.insert(favorite), HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(favorite, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping(value = "/update/{id}")
//    public ResponseEntity<Favorite> update(@PathVariable String id, @RequestBody FavoriteDTO favorite) {
//        try {
//            return new ResponseEntity<>(this.favoritesListService.update(id, favorite), HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(favorite, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping(value = "/delete/{id}")
//    public ResponseEntity<Favorite> delete(@PathVariable String id) {
//        try {
//            return new ResponseEntity<>(this.favoritesListService.delete(id), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(value = "/search/{id}")
//    public ResponseEntity<Favorite> search(@PathVariable String id) {
//        try {
//            return new ResponseEntity<>(this.favoritesListService.delete(id), HttpStatus.FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(value = "")
//    public ResponseEntity<Map<String, Favorite>> select(@RequestParam(value = "status", defaultValue = "Activate", required = false) String status) {
//        try {
//            return new ResponseEntity<>(this.favoritesListService.select(status), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }

}
