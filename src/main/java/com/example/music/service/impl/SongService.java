package com.example.music.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.music.dto.SongDTO;
import com.example.music.entity.Album;
import com.example.music.entity.Genres;
import com.example.music.entity.Own;
import com.example.music.entity.Song;
import com.example.music.entity.SongGenres;
import com.example.music.entity.User;
import com.example.music.entity.comon.Constant;
import com.example.music.entity.comon.Message;
import com.example.music.entity.comon.Result;
import com.example.music.repositories.AlbumRepository;
import com.example.music.repositories.GenresRepository;
import com.example.music.repositories.OwnRepository;
import com.example.music.repositories.SongGenresRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    private final AlbumRepository albumRepository;

    private final GenresRepository genresRepository;

    private final UserRepository userRepository;

    private final Cloudinary cloudinary;

    private final SongGenresRepository songGenresRepository;

    private final OwnRepository ownRepository;

    private static final Map paramsImage = ObjectUtils.asMap(
            "folder", "imageSong",
            "resource_type", "image"
    );

    private static final Map paramsSong = ObjectUtils.asMap(
            "folder", "songs",
            "resource_type", "video"
    );

    public Map<Object, Object> verifySong(Byte type ,SongDTO songDTO) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        Boolean check = true;

        try {
            if (type == 1 && songDTO.getAvatar().isEmpty()) {
                result = new Result(Message.PHOTO_CANNOT_BE_BLANK.getCode(), false, Message.PHOTO_CANNOT_BE_BLANK.getMessage());
                check = false;
            }

            if (type == 1 && songDTO.getSound().isEmpty()) {
                result = new Result(Message.AUDIO_FILE_CANNOT_BE_BLANK.getCode(), false, Message.AUDIO_FILE_CANNOT_BE_BLANK.getMessage());
                check = false;
            }

            if (songDTO.getName().isEmpty()) {
                result = new Result(Message.SONG_NAME_IS_BLANK.getMessage(), false, Message.SONG_NAME_IS_BLANK.getMessage());
                check = false;
            }

            if (songDTO.getDuration() <= 0 || songDTO.getDuration() == null) {
                result = new Result(Message.INVALID_SONG_DURATION.getCode(), false, Message.INVALID_SONG_DURATION.getMessage());
                check = false;
            }

            for (String genreId : songDTO.getGenres()) {
                Genres genres = genresRepository.findById(genreId).orElse(null);
                if (genres == null) {
                    result = new Result(Message.MUSIC_GENRE_DOES_NOT_EXIST.getCode(), false, Message.MUSIC_GENRE_DOES_NOT_EXIST.getMessage());
                    check = false;
                    break;
                }
            }

            for (String artisId : songDTO.getArtis()) {
                User user = userRepository.findById(artisId).orElse(null);
                if (user == null) {
                    result = new Result(Message.AUTHOR_DOES_NOT_EXIST.getCode(), false, Message.AUTHOR_DOES_NOT_EXIST.getMessage());
                    check = false;
                    break;
                }
            }
            if (check) {
                finalResult.put(Constant.RESPONSE_KEY.DATA, songDTO);
            } else {
                finalResult.put(Constant.RESPONSE_KEY.DATA, new Song());
            }
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi xác thực thông tin! {} " + e.getMessage());
            result = new Result(Message.UNABLE_TO_VERIFY_INFORMATION.getCode(), false, Message.UNABLE_TO_VERIFY_INFORMATION.getMessage());
        }

        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getAllSong(Pageable pageable) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Page<Song> songs = this.songRepository.getAllSong(pageable);
            if (songs.isEmpty()) {
                result = new Result(Message.LIST_IS_EMPTY.getCode(), false, Message.LIST_IS_EMPTY.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.DATA, null);
                return finalResult;
            } else {
                finalResult.put(Constant.RESPONSE_KEY.DATA, songs);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy ra danh sách bài hát! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_RETRIEVING_SONG_LIST.getCode(), false, Message.ERROR_WHILE_RETRIEVING_SONG_LIST.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> changeStatusSong(String id, String status) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Song song = songRepository.findById(id).orElse(null);
            if (song == null) {
                result = new Result(Message.SONG_DOES_NOT_EXIST.getCode(), false, Message.SONG_DOES_NOT_EXIST.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
                finalResult.put(Constant.RESPONSE_KEY.DATA, new Song());
            } else {
                song.setStatus(status);
                songRepository.save(song);
                finalResult.put(Constant.RESPONSE_KEY.DATA, song);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện cập nhật trạng thái bài hát! {} " + e.getMessage());
            result = new Result(Message.CANNOT_UPDATE_STATUS.getCode(), false, Message.CANNOT_UPDATE_STATUS.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> detailSong(String id) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Song song = songRepository.findById(id).orElse(null);
            if (song == null) {
                result = new Result(Message.SONG_DOES_NOT_EXIST.getCode(), false, Message.SONG_DOES_NOT_EXIST.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
                finalResult.put(Constant.RESPONSE_KEY.DATA, new Song());
                return finalResult;
            } else {
                finalResult.put(Constant.RESPONSE_KEY.DATA, song);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện tìm kiếm bài hát! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_SEARCHING_FOR_SONGS.getCode(), false, Message.ERROR_WHILE_SEARCHING_FOR_SONGS.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> adminInsertSong(SongDTO songDTO) throws IOException {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();

        try {
            Set<SongGenres> songGenresSet = new HashSet<>();
            Set<Own> ownSet = new HashSet<>();

            Map resultImage = cloudinary.uploader().upload(songDTO.getAvatar().getBytes(), paramsImage);
            Map resultSound = cloudinary.uploader().upload(songDTO.getSound().getBytes(), paramsSong);

            Album album = albumRepository.findById(songDTO.getAlbum()).orElse(null);

            Song song = Song.builder()
                    .id(UUID.randomUUID().toString())
                    .name(songDTO.getName())
                    .avatar(resultImage.get("secure_url").toString())
                    .url(resultSound.get("secure_url").toString())
                    .duration(songDTO.getDuration())
                    .album(album)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .view(0)
                    .status(Constant.Status.Activate)
                    .build();
            saveSong(songDTO, songGenresSet, ownSet, song);
            finalResult.put(Constant.RESPONSE_KEY.DATA, song);
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi thực hiện tạo bài hát mới! {}: " + songDTO.toString());
            result = new Result(Message.CANNOT_CREATE_NEW_SONG.getCode(), false, Message.CANNOT_CREATE_NEW_SONG.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> adminUpdateSong(String id, SongDTO songDTO) throws Exception {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        Set<SongGenres> songGenresSet = new HashSet<>();
        Set<Own> ownSet = new HashSet<>();
        try {
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
            Album album = songDTO.getAlbum() != null ? albumRepository.findById(songDTO.getAlbum()).orElse(null) : null;
            song.setAlbum(album);
            song.setDuration(songDTO.getDuration());
            song.setName(songDTO.getName());
            saveSong(songDTO, songGenresSet, ownSet, song);
            finalResult.put(Constant.RESPONSE_KEY.DATA, song);
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi thực hiệ cập nhật bài hát ! {} " + songDTO.toString());
            result = new Result(Message.CANNOT_UPDATE_SONG.getCode(), false, Message.CANNOT_UPDATE_SONG.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    private void saveSong(SongDTO songDTO, Set<SongGenres> songGenresSet, Set<Own> ownSet, Song song) {
        this.songRepository.save(song);

        for (String genresId : songDTO.getGenres()) {
            Genres genres = genresRepository.findById(genresId).orElse(null);
            SongGenres songGenres = SongGenres.builder()
                    .id(UUID.randomUUID().toString())
                    .genres(genres)
                    .song(song)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .create_by("SUBLIME_SYSTEM")
                    .status(Constant.Status.Activate)
                    .build();
            songGenresSet.add(songGenres);
        }

        this.songGenresRepository.saveAll(songGenresSet);

        for (String artisId : songDTO.getArtis()) {
            User artis = userRepository.findById(artisId).orElse(null);
            Own own = Own.builder()
                    .id(UUID.randomUUID().toString())
                    .author(artis)
                    .work(song)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .create_by("SUBLIME_SYSTEM")
                    .build();
            ownSet.add(own);
        }

        this.ownRepository.saveAll(ownSet);
    }

}
