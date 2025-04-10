package com.example.music.genres;

import com.example.music.comon.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/genres")
@CrossOrigin("*")
@RequiredArgsConstructor
public class GenresController {

    private final GenresService genresService;

    @PostMapping(value = "/save")
    public ResponseData saveGenres(@RequestBody GenresRequest dto) {
        return ResponseData.createResponse(this.genresService.saveGenres(dto));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseData updateGenres(@PathVariable String id, @RequestBody GenresRequest dto) {
        return ResponseData.createResponse(this.genresService.updateGenres(id, dto));
    }

    @DeleteMapping(value = "/change-status/{id}")
    public ResponseData changeStatus(@PathVariable String id, @RequestParam(name = "status", defaultValue = "ShutDown") String status) {
        return ResponseData.createResponse(this.genresService.changeStatus(id, status));
    }

    @GetMapping(value = "/search/{id}")
    public ResponseData search(@PathVariable String id) {
        return ResponseData.createResponse(this.genresService.searchGenres(id));
    }

    @GetMapping(value = "/getAll")
    public ResponseData getAll(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseData.createResponse(this.genresService.getAll(pageable));
    }

    @GetMapping(value = "/get-genres-select")
    public ResponseData getGenresForSelect() {
        return ResponseData.createResponse(this.genresService.getGenresForSelect());
    }

}
