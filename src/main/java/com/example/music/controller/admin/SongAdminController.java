package com.example.music.controller.admin;

import com.example.music.dto.SongDTO;
import com.example.music.dto.WorkDTO;
import com.example.music.entity.Genres;
import com.example.music.entity.Song;
import com.example.music.entity.User;
import com.example.music.service.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<Song> insert(@ModelAttribute SongDTO songDTO) {
        try {
            return new ResponseEntity<>(this.songService.adminInsertSong(songDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Song> update(@PathVariable Long id, @ModelAttribute SongDTO songDTO) {
        try {
            return new ResponseEntity<>(this.songService.adminUpdateSong(id, songDTO), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println(songDTO.getAvatar());
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

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<WorkDTO> search(@PathVariable Long id) {
        try {
            Song song = this.songService.detail(id);
            WorkDTO songDTO = WorkDTO.builder()
                    .name(song.getName())
                    .album(song.getAlbum() != null ? song.getAlbum().getId() : null)
                    .artis(song.getArtis())
                    .genres(song.getGenres())
                    .duration(song.getDuration())
                    .build();
            return new ResponseEntity<>(songDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/get-all-song")
    public ResponseEntity<Page<Song>> getAllSong(@RequestParam(name = "page", defaultValue = "0") Integer page){
        try {
            Pageable pageable = PageRequest.of(page, 3);
            return new ResponseEntity<>(this.songService.getAllSong(pageable), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/update-song-status")
    public ResponseEntity<String> updateSongStatus(@RequestParam(name = "id") Long id, @RequestParam(name = "status") String status) {
        try {
            this.songService.updateSongStatus(status, id);
            return new ResponseEntity<>("Success update status!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error update status!", HttpStatus.BAD_REQUEST);
        }
    }

}
