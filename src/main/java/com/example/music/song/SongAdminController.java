package com.example.music.song;

import com.example.music.comon.ResponseData;
import io.lettuce.core.dynamic.annotation.Param;
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

@RestController
@RequestMapping(value = "/song")
@CrossOrigin("*")
public class SongAdminController {

    @Autowired
    private SongService songService;

    @GetMapping(value = "")
    public ResponseData getSongByStatus(
            @RequestParam(name = "status", defaultValue = "Wait") String status,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 3);
        return ResponseData.createResponse(this.songService.getSongByStatus(status, pageable));
    }

    @PostMapping(value = "/save")
    public ResponseData insert(
            @ModelAttribute SongRequest songDTO,
            @Param("type") Byte type
    ) {
        return ResponseData.createResponse(songService.adminInsertSong(songDTO, type));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseData update(
            @PathVariable String id,
            @ModelAttribute SongRequest songDTO,
            @Param("type") Byte type
    ) throws Exception {
        return ResponseData.createResponse(songService.adminUpdateSong(id, songDTO, type));
    }

    @GetMapping(value = "/search/{id}")
    public ResponseData search(
            @PathVariable String id
    ) {
        return ResponseData.createResponse(songService.detailSong(id));
    }

    @GetMapping(value = "/get-all-song")
    public ResponseData getAllSong(
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        System.out.println(page);
        Pageable pageable = PageRequest.of(page, 3);
        return ResponseData.createResponse(songService.getAllSong(pageable));
    }

    @GetMapping(value = "/update-song-status")
    public ResponseData updateSongStatus(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "status") String status
    ) {
        return ResponseData.createResponse(songService.changeStatusSong(id, status));
    }

}
