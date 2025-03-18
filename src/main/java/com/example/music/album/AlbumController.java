package com.example.music.album;

import com.example.music.comon.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/album")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

//    @GetMapping(value = "/select")
//    public ResponseEntity<Map<String, Album>> select(@RequestParam(value = "status", defaultValue = "Activate", required = false) String status) {
//        try {
//            return new ResponseEntity<>(this.albumService.select(status), HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping(value = "/save")
//    public ResponseEntity<Album> insert(@ModelAttribute AlbumDTO album) {
//        try {
//            return new ResponseEntity<>(this.albumService.insertAlbum(album), HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping(value = "/update/{id}")
//    public ResponseEntity<Album> update(@ModelAttribute String id, @RequestBody Album album) {
//        try {
//            return new ResponseEntity<>(this.albumService.update(id, album), HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(album, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping(value = "/change-status/{id}")
//    public ResponseEntity<Album> delete(@PathVariable String id) {
//        try {
//            return new ResponseEntity<>(this.albumService.delete(id), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(value = "/search/{id}")
//    public CompletableFuture<ResponseData> search(@PathVariable String id) {
//        try {
//            return new ResponseEntity<>(this.albumService.detail(id), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping(value = "/admin")
    public CompletableFuture<ResponseData> getAllForSelectInput() {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.albumService.getAlbumSelect()));
    }

    @GetMapping(value = "/artis/{artis}")
    public CompletableFuture<ResponseData> getAllForSelectInput(@PathVariable String artis) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.albumService.getAlbumSelect(artis)));
    }

}
