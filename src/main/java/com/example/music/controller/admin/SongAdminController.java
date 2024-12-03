package com.example.music.controller.admin;

import com.example.music.dto.SongDTO;
import com.example.music.dto.WorkDTO;
import com.example.music.entity.Song;
import com.example.music.entity.comon.ResponseData;
import com.example.music.service.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/song/admin")
@CrossOrigin("*")
public class SongAdminController {

    @Autowired
    private SongService songService;

    @GetMapping(value = "")
    public ResponseEntity<Page<Song>> getObject(@RequestParam(name = "status", defaultValue = "Wait") String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        try {
            Pageable pageable = PageRequest.of(page, 3);
            return new ResponseEntity<>(this.songService.getObject(status, pageable), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
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
    public CompletableFuture<ResponseData> insert(@ModelAttribute SongDTO songDTO) throws IOException {
        return CompletableFuture.completedFuture(ResponseData.createResponse(songService.adminInsertSong(songDTO)));
    }

    @PutMapping(value = "/update/{id}")
    public CompletableFuture<ResponseData> update(@PathVariable String id, @ModelAttribute SongDTO songDTO) throws Exception {
        return CompletableFuture.completedFuture(ResponseData.createResponse(songService.adminUpdateSong(id, songDTO)));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Song> delete(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.songService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<WorkDTO> search(@PathVariable String id) {
        try {
            Song song = this.songService.detail(id);
            WorkDTO songDTO = WorkDTO.builder()
                    .name(song.getName())
                    .album(song.getAlbum() != null ? song.getAlbum().getId() : null)
                    .artis(song.getOwns())
                    .genres(song.getSongGenres())
                    .duration(song.getDuration())
                    .build();
            return new ResponseEntity<>(songDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
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
