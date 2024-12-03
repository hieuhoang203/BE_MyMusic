package com.example.music.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.music.dao.AlbumSelect;
import com.example.music.dto.AlbumDTO;
import com.example.music.entity.Album;
import com.example.music.entity.User;
import com.example.music.repositories.AlbumRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AlbumService implements IService<Album, Long> {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Map params = ObjectUtils.asMap(
            "folder", "imageAlbum",
            "resource_type", "image"
    );

    @Autowired
    private Cloudinary cloudinary;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public Page<Album> getObject(String status, Pageable pageable) {
        return this.albumRepository.getAlbum(status, pageable);
    }

    @Override
    public Album insert(Album album) {
        this.albumRepository.save(album);
        return album;
    }

    @Override
    public Album update(Long id, Album album) {
        this.albumRepository.save(album);
        return album;
    }

    @Override
    public Album delete(Long id) {
        this.albumRepository.updateStatus(id);
        return this.albumRepository.findById(id).orElse(null);
    }

    @Override
    public Album detail(Long id) {
        return this.albumRepository.findById(id).orElse(null);
    }

    @Override
    public Map<Long, Album> select(String status) {
        return this.albumRepository.select(status).stream().collect(Collectors.toMap(Album::getId, Function.identity()));
    }

    public List<AlbumSelect> getAlbumSelect() {
        return this.albumRepository.getAlbumForSelect();
    }

    public List<AlbumSelect> getAlbumSelect(Integer artis) {
        return this.albumRepository.getAlbumForSelect(artis);
    }

    public Album insertAlbum(AlbumDTO albumDTO) throws IOException, ParseException {
        Map r = this.cloudinary.uploader().upload(albumDTO.getAvatar().getBytes(), params);
        User artis = this.userRepository.findById(albumDTO.getArtis()).orElse(null);
        Album album = Album.builder().name(albumDTO.getName())
                .avatar(r.get("secure_url").toString())
                .artis(artis)
                .release_date(new Date(simpleDateFormat.parse(albumDTO.getRelease_date()).getTime()))
                .date_create(new Date(new java.util.Date().getTime()))
                .status(Status.Activate)
                .build();
        this.albumRepository.save(album);
        return album;
    }

    public Album updateAlbum(Long id, AlbumDTO albumDTO) throws Exception {
        Album album = this.albumRepository.findById(id).orElse(null);
        User artis = this.userRepository.findById(albumDTO.getArtis()).orElse(null);
        assert album != null;
        if (albumDTO.getAvatar() != null) {
            String urlCloudinary = album.getAvatar();
            int start = urlCloudinary.indexOf("imageAlbum/");
            int end = urlCloudinary.lastIndexOf(".");
            cloudinary.api().deleteResources(Arrays.asList(urlCloudinary.substring(start, end)),
                    ObjectUtils.asMap("type", "upload", "resource_type", "image"));
            Map r = this.cloudinary.uploader().upload(albumDTO.getAvatar().getBytes(), params);
            album.setAvatar(r.get("secure_url").toString());
        }
        album.setName(albumDTO.getName());
        album.setArtis(artis);
        album.setRelease_date(new Date(simpleDateFormat.parse(albumDTO.getRelease_date()).getTime()));
        album.setDate_create(new Date(new java.util.Date().getTime()));
        this.albumRepository.save(album);
        return album;
    }

}
