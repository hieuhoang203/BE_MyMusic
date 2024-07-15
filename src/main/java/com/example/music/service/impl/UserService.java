package com.example.music.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.music.dao.ArtisDAO;
import com.example.music.dao.ArtisSelect;
import com.example.music.dao.UserDAO;
import com.example.music.dto.UserDTO;
import com.example.music.entity.Account;
import com.example.music.entity.User;
import com.example.music.entity.comon.Role;
import com.example.music.entity.comon.Status;
import com.example.music.repositories.AccountRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.service.IService;
import com.example.music.service.JavaMailService;
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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService implements IService<User, Integer> {

    private static final Map params = ObjectUtils.asMap(
            "folder", "avatar",
            "resource_type", "image"
    );

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JavaMailService javaMailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public Page<User> getObject(String status, Pageable pageable) {
        return this.repository.getUser(status, pageable);
    }

    @Override
    public User insert(User user) {
        return this.repository.save(user);
    }

    @Override
    public User update(Integer s, User obj) {
        return null;
    }


    @Override
    public User delete(Integer id) {
        this.repository.updateStatus(id);
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public User detail(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Map<Integer, User> select(String status) {
        Map<Integer, User> userMap;
        userMap = this.repository.select(status).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        return userMap;
    }

    public User insert(UserDTO userDTO) throws IOException, ParseException {

        Map result = cloudinary.uploader().upload(userDTO.getAvatar().getBytes(), params);
        Random random = new Random(1000000);
        Account account = Account.builder().login(userDTO.getEmail().trim())
                .pass(passwordEncoder.encode(random.nextInt() + ""))
                .role(userDTO.getRole().trim().equalsIgnoreCase("Admin") ? Role.ADMIN
                        : userDTO.getRole().trim().equalsIgnoreCase("Artis") ? Role.ARTIS : Role.USER)
                .date_create(new Date(new java.util.Date().getTime()))
                .status(Status.Activate)
                .build();
        this.accountRepository.save(account);

        User user = User.builder().id(userDTO.getId())
                .name(userDTO.getName().trim())
                .gender(Boolean.valueOf(userDTO.getGender()))
                .birthday(new Date(simpleDateFormat.parse(userDTO.getBirthday()).getTime()))
                .avatar(result.get("secure_url").toString())
                .account(account)
                .date_create(new Date(new java.util.Date().getTime()))
                .status(Status.Activate)
                .build();
        this.repository.save(user);
        javaMailService.sendPassWord(userDTO.getEmail(), "User creation successful!", random.toString(), userDTO.getName());
        return user;
    }


    public User update(Integer id, UserDTO userDTO) throws Exception {
        User user = this.repository.findById(id).orElse(null);
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
        account.setRole(userDTO.getRole().trim().equalsIgnoreCase("Admin") ? Role.ADMIN
                : userDTO.getRole().trim().equalsIgnoreCase("Artis") ? Role.ARTIS : Role.USER);
        user.setName(userDTO.getName().trim());
        user.setBirthday(new Date(simpleDateFormat.parse(userDTO.getBirthday()).getTime()));
        user.setDate_update(new Date(new java.util.Date().getTime()));
        user.setGender(Boolean.valueOf(userDTO.getGender()));
        this.accountRepository.save(account);
        this.repository.save(user);

        return user;
    }

    public Map<Integer, UserDAO> getNewUserOrArtis(String role) {
        Map<Integer, UserDAO> userMap;
        userMap = this.repository.getNewUserOrArtis(role).stream().collect(Collectors.toMap(UserDAO::getId, Function.identity()));
        return userMap;
    }

    public Page<UserDAO> getAllUser(Pageable pageable) {
        return this.repository.getAllUser(pageable);
    }

    public Page<UserDAO> getUserByStatus(String status, Pageable pageable) {
        return this.repository.getUserByStatus(status, pageable);
    }

    public Page<ArtisDAO> getAllArtis(Pageable pageable) {
        return this.repository.getAllArtis(pageable);
    }

    public Page<ArtisDAO> getArtisByStatus(String status, Pageable pageable) {
        return this.repository.getArtisByStatus(status, pageable);
    }

    public User updateStatusUser(Integer id, String account, String status) {
        this.repository.updateStatusUser(id, status);
        this.accountRepository.updateStatusAccount(account, status);
        return this.repository.findById(id).orElse(null);
    }

    public List<ArtisSelect> getArtisForSelect() {
        return this.repository.getArtisForSelect();
    }

    public List<String> getEmailUser() {
        return this.repository.getEmailUser();
    }

}
