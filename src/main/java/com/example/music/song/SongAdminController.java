package com.example.music.song;

import com.example.music.comon.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/song/admin")
@CrossOrigin("*")
public class SongAdminController {

    @Autowired
    private SongService songService;

    @GetMapping(value = "")
    public CompletableFuture<ResponseData> getObject(@RequestParam(name = "status", defaultValue = "Wait") String status, @RequestParam(name = "page", defaultValue = "0") Long page) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.songService.getSongByStatus(status, page)));
    }

    @PostMapping(value = "/save")
    public CompletableFuture<ResponseData> insert(@ModelAttribute SongRequest songDTO) throws IOException {
        return CompletableFuture.completedFuture(ResponseData.createResponse(songService.adminInsertSong(songDTO)));
    }

    @PutMapping(value = "/update/{id}")
    public CompletableFuture<ResponseData> update(@PathVariable String id, @ModelAttribute SongRequest songDTO) throws Exception {
        return CompletableFuture.completedFuture(ResponseData.createResponse(songService.adminUpdateSong(id, songDTO)));
    }

    @GetMapping(value = "/search/{id}")
    public CompletableFuture<ResponseData> search(@PathVariable String id) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(songService.detailSong(id)));
    }

    @GetMapping(value = "/get-all-song")
    public CompletableFuture<ResponseData> getAllSong(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 3);
        return CompletableFuture.completedFuture(ResponseData.createResponse(songService.getAllSong(pageable)));
    }

    @GetMapping(value = "/update-song-status")
    public CompletableFuture<ResponseData> updateSongStatus(@RequestParam(name = "id") String id, @RequestParam(name = "status") String status) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(songService.changeStatusSong(id, status)));
    }

}
