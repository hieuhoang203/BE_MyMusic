package com.example.music.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.music.dao.ArtisDAO;
import com.example.music.dao.ArtisSelect;
import com.example.music.dao.UserDAO;
import com.example.music.dto.UserDTO;
import com.example.music.entity.Account;
import com.example.music.entity.User;
import com.example.music.entity.comon.Constant;
import com.example.music.entity.comon.Message;
import com.example.music.entity.comon.Result;
import com.example.music.repositories.AccountRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.service.IService;
import com.example.music.service.JavaMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Map params = ObjectUtils.asMap(
            "folder", "avatar",
            "resource_type", "image"
    );

    private final Cloudinary cloudinary;

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final JavaMailService javaMailService;

    private final PasswordEncoder passwordEncoder;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public Map<Object, Object> verifyUser(Byte type, UserDTO userDTO) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        Boolean check = true;
        try {

            if (userDTO.getName().isEmpty()) {
                result = new Result(Message.INVALID_USERNAME.getCode(), false, Message.INVALID_USERNAME.getMessage());
                check = false;
            }

            if (userDTO.getBirthday().isEmpty()) {
                result = new Result(Message.INVALID_DATE_OF_BIRTH.getCode(), false, Message.INVALID_DATE_OF_BIRTH.getMessage());
                check = false;
            }

            if (userDTO.getGender().isEmpty()) {
                result = new Result(Message.INVALID_GENDER.getCode(), false, Message.INVALID_GENDER.getMessage());
                check = false;
            }

            if (type == 2 && userDTO.getAvatar().isEmpty()) {
                result = new Result(Message.PHOTO_CANNOT_BE_BLANK.getCode(), false, Message.PASSWORD_NOT_EXISTS.getMessage());
                check = false;
            }

            if (userDTO.getEmail().isEmpty() || userRepository.getAllEmail().contains(userDTO.getEmail())) {
                result = new Result(Message.EMAIL_USER_EXIST.getCode(), false, Message.EMAIL_USER_EXIST.getMessage());
                check = false;
            }

            if (userDTO.getRole().isEmpty() || !userDTO.getRole().equals(Constant.Role.USER) || !userDTO.getRole().equals(Constant.Role.ADMIN) || !userDTO.getRole().equals(Constant.Role.ARTIS)) {
                result = new Result(Message.INVALID_PERMISSION.getCode(), false, Message.INVALID_PERMISSION.getMessage());
                check = false;
            }

            if (check) {
                finalResult.put(Constant.RESPONSE_KEY.DATA, userDTO);
            } else {
                finalResult.put(Constant.RESPONSE_KEY.DATA, null);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xác thực thông tin người dùng! {} " + e.getMessage());
            result = new Result(Message.UNABLE_TO_VERIFY_INFORMATION.getCode(), false, Message.UNABLE_TO_VERIFY_INFORMATION.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }


    public Map<Object, Object> insert(UserDTO userDTO) throws IOException, ParseException {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Map urlAvt = cloudinary.uploader().upload(userDTO.getAvatar().getBytes(), params);
            Random random = new Random(1000000);
            Account account = Account.builder().login(userDTO.getEmail().trim())
                    .pass(passwordEncoder.encode(random.nextInt() + ""))
                    .role(userDTO.getRole().trim().equalsIgnoreCase("Admin") ? Constant.Role.ADMIN
                            : userDTO.getRole().trim().equalsIgnoreCase("Artis") ? Constant.Role.ARTIS : Constant.Role.USER)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .status(Constant.Status.Activate)
                    .build();
            this.accountRepository.save(account);

            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .name(userDTO.getName().trim())
                    .gender(Boolean.valueOf(userDTO.getGender()))
                    .birthday(new Date(simpleDateFormat.parse(userDTO.getBirthday()).getTime()))
                    .avatar(urlAvt.get("secure_url").toString())
                    .account(account)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .status(Constant.Status.Activate)
                    .build();
            this.userRepository.save(user);
            javaMailService.sendPassWord(userDTO.getEmail(), "User creation successful!", random.toString(), userDTO.getName());
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện thêm mới người dùng ! {} " + e.getMessage());
            result = new Result(Message.CANNOT_ADD_NEW_USER.getCode(), false, Message.CANNOT_ADD_NEW_USER.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }


    public User update(String id, UserDTO userDTO) throws Exception {
        User user = this.userRepository.findById(id).orElse(null);
        if (userDTO.getAvatar() != null) {
            if (user.getAvatar() != null) {
                String urlCloudinary = user.getAvatar();
                int start = urlCloudinary.indexOf("avatar/");
                int end = urlCloudinary.lastIndexOf(".");
                cloudinary.api().deleteResources(Arrays.asList(urlCloudinary.substring(start, end)),
                        ObjectUtils.asMap("type", "upload", "resource_type", "image"));
                Map result = cloudinary.uploader().upload(userDTO.getAvatar().getBytes(), params);
                user.setAvatar(result.get("secure_url").toString());
            } else {
                Map result = cloudinary.uploader().upload(userDTO.getAvatar().getBytes(), params);
                user.setAvatar(result.get("secure_url").toString());
            }
        }
        assert user != null;
        Account account = user.getAccount();
        account.setRole(userDTO.getRole().trim().equalsIgnoreCase("Admin") ? Constant.Role.ADMIN
                : userDTO.getRole().trim().equalsIgnoreCase("Artis") ? Constant.Role.ARTIS : Constant.Role.USER);
        user.setName(userDTO.getName().trim());
        user.setBirthday(new Date(simpleDateFormat.parse(userDTO.getBirthday()).getTime()));
        user.setUpdate_date(new Date(new java.util.Date().getTime()));
        user.setGender(Boolean.valueOf(userDTO.getGender()));
        this.accountRepository.save(account);
        this.userRepository.save(user);

        return user;
    }

    public Map<Integer, UserDAO> getNewUserOrArtis(String role) {
        Map<Integer, UserDAO> userMap;
        userMap = this.userRepository.getNewUserOrArtis(role).stream().collect(Collectors.toMap(UserDAO::getId, Function.identity()));
        return userMap;
    }

    public Page<UserDAO> getAllUser(Pageable pageable) {
        return this.userRepository.getAllUser(pageable);
    }

    public Page<UserDAO> getUserByStatus(String status, Pageable pageable) {
        return this.userRepository.getUserByStatus(status, pageable);
    }

    public Page<ArtisDAO> getAllArtis(Pageable pageable) {
        return this.userRepository.getAllArtis(pageable);
    }

    public Page<ArtisDAO> getArtisByStatus(String status, Pageable pageable) {
        return this.userRepository.getArtisByStatus(status, pageable);
    }

    public User updateStatusUser(String id, String account, String status) {
        this.userRepository.updateStatusUser(id, status);
        this.accountRepository.updateStatusAccount(account, status);
        return this.userRepository.findById(id).orElse(null);
    }

    public List<ArtisSelect> getArtisForSelect() {
        return this.userRepository.getArtisForSelect();
    }

    public List<String> getEmailUser() {
        return this.userRepository.getEmailUser();
    }

}
