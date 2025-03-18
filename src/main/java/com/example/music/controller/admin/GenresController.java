package com.example.music.controller.admin;

import com.example.music.dto.GenresRequest;
import com.example.music.entity.comon.ResponseData;
import com.example.music.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GenresController {

    @Autowired
    private GenresService genresService;

    @PostMapping(value = "/save")
    public CompletableFuture<ResponseData> saveGenre(@RequestBody GenresRequest genresDTO) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.saveGenres(genresDTO)));
    }

    @PostMapping(value = "/verify")
    public CompletableFuture<ResponseData> verifyGenres(@RequestBody GenresRequest genresDTO) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.verifyGenres(genresDTO)));
    }

    @PutMapping(value = "/update/{id}")
    public CompletableFuture<ResponseData> updateGenre(@PathVariable String id, @RequestBody GenresRequest genresDTO) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.updateGenres(id, genresDTO)));
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

//    @GetMapping(value = "/get-genres-select")
//    public CompletableFuture<ResponseData> getGenresForSelect() {
//        return CompletableFuture.completedFuture(ResponseData.createResponse(this.genresService.getGenresForSelect()));
//    }

}
