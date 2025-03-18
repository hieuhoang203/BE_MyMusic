package com.example.music.song;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.music.album.Album;
import com.example.music.album.AlbumRepository;
import com.example.music.comon.Constant;
import com.example.music.comon.Message;
import com.example.music.comon.Result;
import com.example.music.genres.Genres;
import com.example.music.genres.GenresRepository;
import com.example.music.own.Own;
import com.example.music.own.OwnRepository;
import com.example.music.own.WorkRequest;
import com.example.music.song_genres.SongGenres;
import com.example.music.song_genres.SongGenresRepository;
import com.example.music.user.User;
import com.example.music.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

    public Map<Object, Object> verifySong(Byte type ,SongRequest dto) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        Boolean check = true;

        try {
            if (type == 1 && dto.getAvatar().isEmpty()) {
                result = new Result(Message.PHOTO_CANNOT_BE_BLANK.getCode(), false, Message.PHOTO_CANNOT_BE_BLANK.getMessage());
                check = false;
            }

            if (type == 1 && dto.getSound().isEmpty()) {
                result = new Result(Message.AUDIO_FILE_CANNOT_BE_BLANK.getCode(), false, Message.AUDIO_FILE_CANNOT_BE_BLANK.getMessage());
                check = false;
            }

            if (dto.getName().isEmpty()) {
                result = new Result(Message.SONG_NAME_IS_BLANK.getMessage(), false, Message.SONG_NAME_IS_BLANK.getMessage());
                check = false;
            }

            if (dto.getDuration() <= 0 || dto.getDuration() == null) {
                result = new Result(Message.INVALID_SONG_DURATION.getCode(), false, Message.INVALID_SONG_DURATION.getMessage());
                check = false;
            }

            for (String genreId : dto.getGenres()) {
                Genres genres = genresRepository.findById(genreId).orElse(null);
                if (genres == null) {
                    result = new Result(Message.MUSIC_GENRE_DOES_NOT_EXIST.getCode(), false, Message.MUSIC_GENRE_DOES_NOT_EXIST.getMessage());
                    check = false;
                    break;
                }
            }

            for (String artisId : dto.getArtis()) {
                User user = userRepository.findById(artisId).orElse(null);
                if (user == null) {
                    result = new Result(Message.AUTHOR_DOES_NOT_EXIST.getCode(), false, Message.AUTHOR_DOES_NOT_EXIST.getMessage());
                    check = false;
                    break;
                }
            }
            if (check) {
                finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
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
                finalResult.put(Constant.RESPONSE_KEY.DATA, new WorkRequest());
                return finalResult;
            } else {
                WorkRequest dto = WorkRequest.builder()
                        .name(song.getName())
                        .album(song.getAlbum() != null ? song.getAlbum().getId() : null)
                        .artis(song.getOwns())
                        .genres(song.getSongGenres())
                        .duration(song.getDuration())
                        .build();
                finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện tìm kiếm bài hát! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_SEARCHING_FOR_SONGS.getCode(), false, Message.ERROR_WHILE_SEARCHING_FOR_SONGS.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Map<Object, Object> adminInsertSong(SongRequest dto) throws IOException {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();

        try {
            Set<SongGenres> songGenresSet = new HashSet<>();
            Set<Own> ownSet = new HashSet<>();

            Map resultImage = cloudinary.uploader().upload(dto.getAvatar().getBytes(), paramsImage);
            Map resultSound = cloudinary.uploader().upload(dto.getSound().getBytes(), paramsSong);

            Album album = albumRepository.findById(dto.getAlbum()).orElse(null);

            Song song = Song.builder()
                    .id(UUID.randomUUID().toString())
                    .name(dto.getName())
                    .avatar(resultImage.get("secure_url").toString())
                    .url(resultSound.get("secure_url").toString())
                    .duration(dto.getDuration())
                    .album(album)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .create_by("SUBLIME_SYSTEM")
                    .view(0)
                    .status(Constant.Status.Activate)
                    .build();
            saveSong(dto, songGenresSet, ownSet, song);
            finalResult.put(Constant.RESPONSE_KEY.DATA, song);
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi thực hiện tạo bài hát mới! {}: " + dto.toString());
            result = new Result(Message.CANNOT_CREATE_NEW_SONG.getCode(), false, Message.CANNOT_CREATE_NEW_SONG.getMessage());
            throw e;
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Map<Object, Object> adminUpdateSong(String id, SongRequest dto) throws Exception {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        Set<SongGenres> songGenresSet = new HashSet<>();
        Set<Own> ownSet = new HashSet<>();
        try {
            Song song = this.songRepository.findById(id).orElse(null);
            assert song != null;
            if (dto.getAvatar() != null) {
                String urlCloudinary = song.getAvatar();
                int start = urlCloudinary.indexOf("imageSong/");
                int end = urlCloudinary.lastIndexOf(".");
                cloudinary.api().deleteResources(Arrays.asList(urlCloudinary.substring(start, end)),
                        ObjectUtils.asMap("type", "upload", "resource_type", "image"));
                Map resultImage = cloudinary.uploader().upload(dto.getAvatar().getBytes(), paramsImage);
                song.setAvatar(resultImage.get("secure_url").toString());
            }
            if (dto.getSound() != null) {
                String urlCloudinary = song.getAvatar();
                int start = urlCloudinary.indexOf("songs/");
                int end = urlCloudinary.lastIndexOf(".");
                cloudinary.api().deleteResources(Arrays.asList(urlCloudinary.substring(start, end)),
                        ObjectUtils.asMap("type", "upload", "resource_type", "video"));
                Map resultSound = cloudinary.uploader().upload(dto.getSound().getBytes(), paramsSong);
                song.setUrl(resultSound.get("secure_url").toString());
            }
            Album album = dto.getAlbum() != null ? albumRepository.findById(dto.getAlbum()).orElse(null) : null;
            song.setAlbum(album);
            song.setDuration(dto.getDuration());
            song.setName(dto.getName());
            song.setUpdate_date(new Date(new java.util.Date().getTime()));
            song.setUpdate_by("SUBLIME_SYSTEM");
            saveSong(dto, songGenresSet, ownSet, song);
            finalResult.put(Constant.RESPONSE_KEY.DATA, song);
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi thực hiệ cập nhật bài hát ! {} " + dto.toString());
            result = new Result(Message.CANNOT_UPDATE_SONG.getCode(), false, Message.CANNOT_UPDATE_SONG.getMessage());
            throw e;
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    private void saveSong(SongRequest dto, Set<SongGenres> songGenresSet, Set<Own> ownSet, Song song) {
        this.songRepository.save(song);

        for (String genresId : dto.getGenres()) {
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

        for (String artisId : dto.getArtis()) {
            User artis = userRepository.findById(artisId).orElse(null);
            Own own = Own.builder()
                    .id(UUID.randomUUID().toString())
                    .author(artis)
                    .work(song)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .create_by("SUBLIME_SYSTEM")
                    .status(Constant.Status.Activate)
                    .build();
            ownSet.add(own);
        }

        this.ownRepository.saveAll(ownSet);
    }

    public Map<Object, Object> getSongByStatus(String status, Long pageable) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            List<Song> songs = this.songRepository.getSong(status);
            if (songs.isEmpty()) {
                finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
            } else {
                finalResult.put(Constant.RESPONSE_KEY.DATA, songs.stream().skip(pageable * 3).limit(3).toList());
            }
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi thực hiện lấy danh sách bài hát theo trạng thái! {} " + e.getMessage());
            result = new Result(Message.SYSTEM_ERROR.getCode(), false, Message.SYSTEM_ERROR.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

}
