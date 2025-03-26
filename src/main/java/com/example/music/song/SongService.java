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
import com.example.music.genres.GenresResponse;
import com.example.music.own.Own;
import com.example.music.own.OwnRepository;
import com.example.music.own.WorkRequest;
import com.example.music.song_genres.SongGenres;
import com.example.music.song_genres.SongGenresRepository;
import com.example.music.user.ArtisResponse;
import com.example.music.user.User;
import com.example.music.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    public List<SongResponse> convertSongs(List<Song> songs) {
        List<SongResponse> data = new ArrayList<>();
        List<GenresResponse> genres;
        List<ArtisResponse> artis;
        for (Song song : songs) {
            Album album = this.albumRepository.findById(song.getId()).orElse(null);
            genres = this.genresRepository.getGenresBySong(song.getId());
            artis = this.userRepository.getArtisByOwns(song.getId());
            SongResponse songResponse = SongResponse.builder()
                    .id(song.getId())
                    .name(song.getName())
                    .avatar(song.getAvatar())
                    .url(song.getUrl())
                    .view(song.getView())
                    .duration(song.getDuration())
                    .album(album)
                    .genres(genres)
                    .artists(artis)
                    .status(song.getStatus())
                    .build();
            data.add(songResponse);
        }
        return data;
    }

    public SongResponse convertSongs(Song song) {
        List<GenresResponse> genres;
        List<ArtisResponse> artis;
        Album album = this.albumRepository.findById(song.getId()).orElse(null);
        genres = this.genresRepository.getGenresBySong(song.getId());
        artis = this.userRepository.getArtisByOwns(song.getId());
        return SongResponse.builder()
                .id(song.getId())
                .name(song.getName())
                .avatar(song.getAvatar())
                .url(song.getUrl())
                .view(song.getView())
                .duration(song.getDuration())
                .album(album)
                .genres(genres)
                .artists(artis)
                .status(song.getStatus())
                .build();
    }

    public Map<Object, Object> getAllSong(Pageable pageable) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            List<Song> getAllSongs = this.songRepository.getAllSong();
            if (getAllSongs.isEmpty()) {
                finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
            } else {
                List<SongResponse> songResponses = convertSongs(getAllSongs);
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), songResponses.size());

                List<SongResponse> pageContent = songResponses.subList(start, end);
                finalResult.put(Constant.RESPONSE_KEY.DATA, new PageImpl<>(pageContent, pageable, songResponses.size()));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy ra danh sách bài hát! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_RETRIEVING_SONG_LIST.getCode(), false, Message.ERROR_WHILE_RETRIEVING_SONG_LIST.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
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
            finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
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
                finalResult.put(Constant.RESPONSE_KEY.DATA, new WorkRequest());
                return finalResult;
            } else {
                SongResponse dto = convertSongs(song);
                finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện tìm kiếm bài hát! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_SEARCHING_FOR_SONGS.getCode(), false, Message.ERROR_WHILE_SEARCHING_FOR_SONGS.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, new Song());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> adminInsertSong(SongRequest dto, Byte type) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();

        try {

            if (type == 1 && dto.getAvatar().isEmpty()) {
                result = new Result(Message.PHOTO_CANNOT_BE_BLANK.getCode(), false, Message.PHOTO_CANNOT_BE_BLANK.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
            }

            if (type == 1 && dto.getSound().isEmpty()) {
                result = new Result(Message.AUDIO_FILE_CANNOT_BE_BLANK.getCode(), false, Message.AUDIO_FILE_CANNOT_BE_BLANK.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
            }

            if (dto.getName().isEmpty()) {
                result = new Result(Message.SONG_NAME_IS_BLANK.getMessage(), false, Message.SONG_NAME_IS_BLANK.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
            }

            for (String genreId : dto.getGenres()) {
                Genres genres = genresRepository.findById(genreId).orElse(null);
                if (genres == null) {
                    result = new Result(Message.MUSIC_GENRE_DOES_NOT_EXIST.getCode(), false, Message.MUSIC_GENRE_DOES_NOT_EXIST.getMessage());
                    finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
                    break;
                }
            }

            for (String artisId : dto.getArtis()) {
                User user = userRepository.findById(artisId).orElse(null);
                if (user == null) {
                    result = new Result(Message.AUTHOR_DOES_NOT_EXIST.getCode(), false, Message.AUTHOR_DOES_NOT_EXIST.getMessage());
                    finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
                    break;
                }
            }

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
                    .create_date(new Timestamp(new Date().getTime()))
                    .create_by("SUBLIME_SYSTEM")
                    .view(0)
                    .status(Constant.Status.Activate)
                    .build();
            saveSong(dto, songGenresSet, ownSet, song);
            finalResult.put(Constant.RESPONSE_KEY.DATA, song);
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi thực hiện tạo bài hát mới! {}: " + dto.toString());
            result = new Result(Message.CANNOT_CREATE_NEW_SONG.getCode(), false, Message.CANNOT_CREATE_NEW_SONG.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> adminUpdateSong(String id, SongRequest dto, Byte type) throws Exception {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();

        try {

            if (dto.getName().isEmpty()) {
                result = new Result(Message.SONG_NAME_IS_BLANK.getMessage(), false, Message.SONG_NAME_IS_BLANK.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
            }

            for (String genreId : dto.getGenres()) {
                Genres genres = genresRepository.findById(genreId).orElse(null);
                if (genres == null) {
                    result = new Result(Message.MUSIC_GENRE_DOES_NOT_EXIST.getCode(), false, Message.MUSIC_GENRE_DOES_NOT_EXIST.getMessage());
                    finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
                    break;
                }
            }

            for (String artisId : dto.getArtis()) {
                User user = userRepository.findById(artisId).orElse(null);
                if (user == null) {
                    result = new Result(Message.AUTHOR_DOES_NOT_EXIST.getCode(), false, Message.AUTHOR_DOES_NOT_EXIST.getMessage());
                    finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
                    break;
                }
            }

            Set<SongGenres> songGenresSet = new HashSet<>();
            Set<Own> ownSet = new HashSet<>();

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
            song.setUpdate_date(new Timestamp(new Date().getTime()));
            song.setUpdate_by("SUBLIME_SYSTEM");
            saveSong(dto, songGenresSet, ownSet, song);
            finalResult.put(Constant.RESPONSE_KEY.DATA, song);
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi thực hiệ cập nhật bài hát ! {} " + dto.toString());
            result = new Result(Message.CANNOT_UPDATE_SONG.getCode(), false, Message.CANNOT_UPDATE_SONG.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, dto);
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
                    .create_date(new Timestamp(new Date().getTime()))
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
                    .create_date(new Timestamp(new Date().getTime()))
                    .create_by("SUBLIME_SYSTEM")
                    .status(Constant.Status.Activate)
                    .build();
            ownSet.add(own);
        }

        this.ownRepository.saveAll(ownSet);
    }

    public Map<Object, Object> getSongByStatus(String status, Pageable pageable) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();

        try {
            List<Song> getSongByStatus = this.songRepository.select(status);
            if (getSongByStatus.isEmpty()) {
                finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
            } else {
                List<SongResponse> songResponses = convertSongs(getSongByStatus);

                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), songResponses.size());

                List<SongResponse> pageContent = songResponses.subList(start, end);
                finalResult.put(Constant.RESPONSE_KEY.DATA, new PageImpl<>(pageContent, pageable, songResponses.size()));
            }
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi thực hiện lấy danh sách bài hát theo trạng thái! {} " + e.getMessage());
            result = new Result(Message.SYSTEM_ERROR.getCode(), false, Message.SYSTEM_ERROR.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

}
