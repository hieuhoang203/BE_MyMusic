package com.example.music.controller.admin;

import com.example.music.dao.GenresSelect;
import com.example.music.dto.GenresDTO;
import com.example.music.entity.Genres;
import com.example.music.entity.comon.Status;
import com.example.music.service.impl.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/genres/admin")
@CrossOrigin("*")
public class GenresController {

    @Autowired
    private GenresService genresService;

    @GetMapping(value = "")
    public ResponseEntity<Page<Genres>> getObject(@RequestParam(name = "status") String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        try {
            Pageable pageable = PageRequest.of(page, 5);
            return new ResponseEntity<>(this.genresService.getObject(status, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/select")
    public ResponseEntity<Map<Byte, Genres>> select(@RequestParam(name = "status", defaultValue = "Activate") String status) {
        try {
            return new ResponseEntity<>(this.genresService.select(status), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Genres> insert(@RequestBody GenresDTO genresDAO) {
        try {
            Genres genres = Genres.builder().code(genresDAO.getCode().trim())
                    .name(genresDAO.getName().trim())
                    .date_create(new Date(new java.util.Date().getTime()))
                    .status(Status.Activate).build();
            this.genresService.insert(genres);
            return new ResponseEntity<>(genres, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Genres> update(@PathVariable Byte id, @RequestBody GenresDTO genresDAO) {
        try {
            Genres genres = this.genresService.detail(id);
            genres.setCode(genresDAO.getCode().trim());
            genres.setName(genresDAO.getName().trim());
            genres.setDate_update(new Date(new java.util.Date().getTime()));
            return new ResponseEntity<>(this.genresService.update(id, genres), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Genres> delete(@PathVariable Byte id) {
        try {
            return new ResponseEntity<>(this.genresService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/return/{id}")
    public ResponseEntity<Genres> returnStatus(@PathVariable Byte id) {
        try {
            return new ResponseEntity<>(this.genresService.returnStatus(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Genres> search(@PathVariable Byte id) {
        try {
            return new ResponseEntity<>(this.genresService.detail(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<Page<Genres>> getAll(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        try {
            Pageable pageable = PageRequest.of(page, 5);
            return new ResponseEntity<>(this.genresService.getAll(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get-genres-select")
    public ResponseEntity<List<GenresSelect>> getGenresForSelect() {
        try {
            return new ResponseEntity<>(this.genresService.getGenresForSelect(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
