package com.example.music.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.music.dto.SongDTO;
import com.example.music.entity.Album;
import com.example.music.entity.Genres;
import com.example.music.entity.Song;
import com.example.music.entity.User;
import com.example.music.entity.comon.Status;
import com.example.music.repositories.AlbumRepository;
import com.example.music.repositories.GenresRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SongService implements IService<Song, Long> {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GenresRepository genresRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    private static final Map paramsImage = ObjectUtils.asMap(
            "folder", "imageSong",
            "resource_type", "image"
    );

    private static final Map paramsSong = ObjectUtils.asMap(
            "folder", "songs",
            "resource_type", "video"
    );

    @Override
    public Page<Song> getObject(String status, Pageable pageable) {
        return this.songRepository.getSong(status, pageable);
    }

    @Override
    public Song insert(Song song) {
        this.songRepository.save(song);
        return song;
    }

    @Override
    public Song update(Long id, Song song) {
        this.songRepository.save(song);
        return song;
    }

    @Override
    public Song delete(Long id) {
        this.songRepository.updateStatus(id);
        return this.songRepository.findById(id).orElse(null);
    }

    @Override
    public Song detail(Long id) {
        return this.songRepository.findById(id).orElse(null);
    }

    @Override
    public Map<Long, Song> select(String status) {
        return this.songRepository.select(status).stream().collect(Collectors.toMap(Song::getId, Function.identity()));
    }

    public Page<Song> getAllSong(Pageable pageable) {
        return this.songRepository.getAllSong(pageable);
    }

    public void updateSongStatus(String status, Long id) {
        this.songRepository.updateStatus(status, id);
    }

    public Song adminInsertSong(SongDTO songDTO) throws IOException {
        Set<Genres> genres = new HashSet<>();
        Set<User> artis = new HashSet<>();
        Map resultImage = cloudinary.uploader().upload(songDTO.getAvatar().getBytes(), paramsImage);
        Map resultSound = cloudinary.uploader().upload(songDTO.getSound().getBytes(), paramsSong);
        for (Byte genresIndex : songDTO.getGenres()) {
            genres.add(genresRepository.findById(genresIndex).orElse(null));
        }
        for (Integer artisIndex : songDTO.getArtis()) {
            artis.add(userRepository.findById(artisIndex).orElse(null));
        }
        Album album = albumRepository.findById(songDTO.getAlbum()).orElse(null);
        Song song = Song.builder().name(songDTO.getName())
                .avatar(resultImage.get("secure_url").toString())
                .url(resultSound.get("secure_url").toString())
                .duration(songDTO.getDuration())
                .album(album)
                .artis(artis)
                .genres(genres)
                .date_create(new Date(new java.util.Date().getTime()))
                .view(0)
                .status(Status.Activate)
                .build();
        this.songRepository.save(song);
        return song;
    }

    public Song adminUpdateSong(Long id, SongDTO songDTO) throws Exception {
        Set<Genres> genres = new HashSet<>();
        Set<User> artis = new HashSet<>();
        Song song = this.songRepository.findById(id).orElse(null);
        assert song != null;
        if (songDTO.getAvatar() != null) {
            String urlCloudinary = song.getAvatar();
            int start = urlCloudinary.indexOf("imageSong/");
            int end = urlCloudinary.lastIndexOf(".");
            cloudinary.api().deleteResources(Arrays.asList(urlCloudinary.substring(start, end)),
                    ObjectUtils.asMap("type", "upload", "resource_type", "image"));
            Map resultImage = cloudinary.uploader().upload(songDTO.getAvatar().getBytes(), paramsImage);
            song.setAvatar(resultImage.get("secure_url").toString());
        }
        if (songDTO.getSound() != null) {
            String urlCloudinary = song.getAvatar();
            int start = urlCloudinary.indexOf("songs/");
            int end = urlCloudinary.lastIndexOf(".");
            cloudinary.api().deleteResources(Arrays.asList(urlCloudinary.substring(start, end)),
                    ObjectUtils.asMap("type", "upload", "resource_type", "video"));
            Map resultSound = cloudinary.uploader().upload(songDTO.getSound().getBytes(), paramsSong);
            song.setUrl(resultSound.get("secure_url").toString());
        }
        for (Byte genresIndex : songDTO.getGenres()) {
            genres.add(genresRepository.findById(genresIndex).orElse(null));
        }
        for (Integer artisIndex : songDTO.getArtis()) {
            artis.add(userRepository.findById(artisIndex).orElse(null));
        }
        Album album = songDTO.getAlbum() != null ? albumRepository.findById(songDTO.getAlbum()).orElse(null) : null;
        song.setArtis(artis);
        song.setGenres(genres);
        song.setAlbum(album);
        song.setDuration(songDTO.getDuration());
        song.setName(songDTO.getName());
        this.songRepository.save(song);
        return song;
    }

}
