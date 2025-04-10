package com.example.music.album;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.music.comon.Constant;
import com.example.music.comon.Message;
import com.example.music.comon.Result;
import com.example.music.comon.SelectValue;
import com.example.music.user.User;
import com.example.music.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final UserRepository userRepository;

    private static final Map params = ObjectUtils.asMap(
            "folder", "imageAlbum",
            "resource_type", "image"
    );

    private final Cloudinary cloudinary;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public Map<Object, Object> getAlbumSelect() {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            List<SelectValue> data = this.albumRepository.getAlbumForSelect();
            if (!data.isEmpty()) {
                finalResult.put(Constant.RESPONSE_KEY.DATA, data);
            } else {
                finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy ra data album cho ô select! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHEN_GETTING_ALBUM_DATA_FOR_SELECT_CELL.getCode(), false, Message.ERROR_WHEN_GETTING_ALBUM_DATA_FOR_SELECT_CELL.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getAlbumSelect(String artis) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            List<SelectValue> data = this.albumRepository.getAlbumForSelect(artis);
            if (!data.isEmpty()) {
                finalResult.put(Constant.RESPONSE_KEY.DATA, data);
            } else {
                finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy ra data album cho ô select! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHEN_GETTING_ALBUM_DATA_FOR_SELECT_CELL.getCode(), false, Message.ERROR_WHEN_GETTING_ALBUM_DATA_FOR_SELECT_CELL.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, new ArrayList<>());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> insertAlbum(AlbumRequest albumDTO) throws IOException, ParseException {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Map r = this.cloudinary.uploader().upload(albumDTO.getAvatar().getBytes(), params);
            User artis = this.userRepository.findById(albumDTO.getArtis()).orElse(null);
            Album album = Album.builder().name(albumDTO.getName())
                    .avatar(r.get("secure_url").toString())
                    .artis(artis)
                    .release_date(new Date(simpleDateFormat.parse(albumDTO.getRelease_date()).getTime()))
                    .create_date(new Timestamp(new java.util.Date().getTime()))
                    .status(Constant.Status.Activate)
                    .build();
            this.albumRepository.save(album);
            finalResult.put(Constant.RESPONSE_KEY.DATA, album);
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện thêm mới album! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_ADDING_NEW_ALBUM.getCode(), false, Message.ERROR_WHILE_ADDING_NEW_ALBUM.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, albumDTO);
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> updateAlbum(String  id, AlbumRequest albumDTO) throws Exception {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
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
            album.setUpdate_date(new Timestamp(new java.util.Date().getTime()));
            album.setUpdate_by(artis.getName());
            this.albumRepository.save(album);
            finalResult.put(Constant.RESPONSE_KEY.DATA, album);
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện cập nhật album! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_UPDATING_ALBUM.getCode(), false, Message.ERROR_WHILE_UPDATING_ALBUM.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, albumDTO);
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> changeStatusAlbum(String id, String status) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Optional<Album> album = albumRepository.findById(id);
            if (album.isPresent()) {
                album.get().setStatus(status);
                album.get().setUpdate_date(new Timestamp(new java.util.Date().getTime()));
                album.get().setUpdate_by(album.get().getArtis().getName());
                albumRepository.save(album.get());
            } else {
                result = new Result(Message.ALBUM_DOES_NOT_EXIST.getCode(), false, Message.ALBUM_DOES_NOT_EXIST.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện thay đổi trạng thái album! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_CHANGING_ALBUM_STATUS.getCode(), false, Message.ERROR_WHILE_CHANGING_ALBUM_STATUS.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, new Album());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> detailAlbum(String id) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

}
