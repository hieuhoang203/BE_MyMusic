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

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/genres")
@CrossOrigin("*")
@RequiredArgsConstructor
public class GenresController {

    private final GenresService genresService;

    @PostMapping(value = "/save")
    public CompletableFuture<ResponseData> saveGenres(@RequestBody GenresRequest dto) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.saveGenres(dto)));
    }

    @PostMapping(value = "/verify")
    public CompletableFuture<ResponseData> verifyGenres(@RequestBody GenresRequest dto) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.verifyGenres(dto)));
    }

    @PutMapping(value = "/update/{id}")
    public CompletableFuture<ResponseData> updateGenres(@PathVariable String id, @RequestBody GenresRequest dto) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.updateGenres(id, dto)));
    }

    @DeleteMapping(value = "/change-status/{id}")
    public CompletableFuture<ResponseData> changeStatus(@PathVariable String id, @RequestParam(name = "status", defaultValue = "ShutDown") String status) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.changeStatus(id, status)));
    }

    @GetMapping(value = "/search/{id}")
    public CompletableFuture<ResponseData> search(@PathVariable String id) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.searchGenre(id)));
    }

    @GetMapping(value = "/getAll")
    public CompletableFuture<ResponseData> getAll(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.getAll(pageable)));
    }

    @GetMapping(value = "/get-genres-select")
    public CompletableFuture<ResponseData> getGenresForSelect() {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.getGenresForSelect()));
    }

}
