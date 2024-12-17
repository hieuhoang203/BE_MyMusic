package com.example.music.service;

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
import com.example.music.service.JavaMailService;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;
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

    public Map<Object, Object> detailUser(String id) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Optional<User> user = userRepository.findById(id);
            if (!user.isEmpty()) {
                UserDTO userDTO = UserDTO.builder()
                        .id(user.get().getId())
                        .name(user.get().getName())
                        .gender(user.get().getGender() != null ? user.get().getGender().toString() : "")
                        .email(user.get().getAccount().getLogin())
                        .birthday(user.get().getBirthday() != null ? user.get().getBirthday().toString() : user.get().getCreate_date().toString())
                        .role(user.get().getAccount().getRole().toString())
                        .build();
                finalResult.put(Constant.RESPONSE_KEY.DATA, userDTO);
            } else {
                finalResult.put(Constant.RESPONSE_KEY.DATA, new UserDTO());
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy ra chi tiết người dùng! {} " + e.getMessage());
            result = new Result(Message.ERROR_RETRIEVING_USER_DETAILS.getCode(), false, Message.ERROR_RETRIEVING_USER_DETAILS.getMessage());
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

    public Map<Object, Object> changeStatusUser(String id, String status) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                user.get().setStatus(status);
                userRepository.save(user.get());
                Optional<Account> account = accountRepository.findAccountByLogin(user.get().getAccount().getLogin());
                if (account.isPresent()) {
                    account.get().setStatus(status);
                    accountRepository.save(account.get());
                }
            } else {
                System.out.println("Không tìm thấy người dùng có id là : " + id);
                result = new Result(Message.UNABLE_TO_UPDATE_USER_STATUS.getCode(), false, Message.UNABLE_TO_UPDATE_USER_STATUS.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện cập nhật trạng thái người dùng! {} " + e.getMessage());
            result = new Result(Message.UNABLE_TO_UPDATE_USER_STATUS.getCode(), false, Message.UNABLE_TO_UPDATE_USER_STATUS.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> update(String id, UserDTO userDTO) throws Exception {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            User user = this.userRepository.findById(id).orElse(null);
            if (userDTO.getAvatar() != null) {
                if (user.getAvatar() != null) {
                    String urlCloudinary = user.getAvatar();
                    int start = urlCloudinary.indexOf("avatar/");
                    int end = urlCloudinary.lastIndexOf(".");
                    cloudinary.api().deleteResources(Arrays.asList(urlCloudinary.substring(start, end)),
                            ObjectUtils.asMap("type", "upload", "resource_type", "image"));
                    Map cloud = cloudinary.uploader().upload(userDTO.getAvatar().getBytes(), params);
                    user.setAvatar(cloud.get("secure_url").toString());
                } else {
                    Map cloud = cloudinary.uploader().upload(userDTO.getAvatar().getBytes(), params);
                    user.setAvatar(cloud.get("secure_url").toString());
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
            finalResult.put(Constant.RESPONSE_KEY.DATA, user);
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện cập nhật thông tin người dùng! {} " + e.getMessage());
            result = new Result(Message.SYSTEM_ERROR.getCode(), false, Message.SYSTEM_ERROR.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getNewUserOrArtis(String role) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Map<Integer, UserDAO> userMap = this.userRepository.getNewUserOrArtis(role).stream().collect(Collectors.toMap(UserDAO::getId, Function.identity()));
            finalResult.put(Constant.RESPONSE_KEY.DATA, userMap);
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy ra danh sách người dùng mới !{} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_GETTING_NEW_USER_LIST.getCode(), false, Message.ERROR_WHILE_GETTING_NEW_USER_LIST.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getAllUser(Pageable pageable) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Page<UserDAO> data = this.userRepository.getAllUser(pageable);
            finalResult.put(Constant.RESPONSE_KEY.DATA, data);
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy ra danh sách người dùng !{} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_GETTING_USER_LIST.getCode(), false, Message.ERROR_WHILE_GETTING_USER_LIST.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getUserByStatus(String status, Pageable pageable) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Page<UserDAO> data = this.userRepository.getUserByStatus(status, pageable);
            finalResult.put(Constant.RESPONSE_KEY.DATA, data);
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy ra danh sách người dùng !{} " + e.getMessage());
            result = new Result(Message.ERROR_WHILE_GETTING_USER_LIST.getCode(), false, Message.ERROR_WHILE_SEARCHING_FOR_SONGS.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getAllArtis(Pageable pageable) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Page<ArtisDAO> data = this.userRepository.getAllArtis(pageable);
            finalResult.put(Constant.RESPONSE_KEY.DATA, data);
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy danh sách tác giả !{} " + e.getMessage());
            result = new Result(Message.ERROR_GETTING_AUTHOR_LIST.getCode(), false, Message.ERROR_GETTING_AUTHOR_LIST.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getArtisByStatus(String status, Pageable pageable) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Page<ArtisDAO> data = this.userRepository.getArtisByStatus(status, pageable);
            finalResult.put(Constant.RESPONSE_KEY.DATA, data);
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện lấy danh sách tác giả !{} " + e.getMessage());
            result = new Result(Message.ERROR_GETTING_AUTHOR_LIST.getCode(), false, Message.ERROR_GETTING_AUTHOR_LIST.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> updateStatusUser(String id, String account, String status) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            this.userRepository.updateStatusUser(id, status);
            this.accountRepository.updateStatusAccount(account, status);
            finalResult.put(Constant.RESPONSE_KEY.DATA, this.userRepository.findById(id).orElse(null));
        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện cập nhật trạng thái người dùng! {} " + e.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getArtisForSelect() {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            List<ArtisSelect> data = this.userRepository.getArtisForSelect();
            finalResult.put(Constant.RESPONSE_KEY.DATA, data);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy ra data tác giả trong select! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHEN_GETTING_AUTHOR_DATA_TO_FILL_IN_SELECT_BOX.getCode(), false, Message.ERROR_WHEN_GETTING_AUTHOR_DATA_TO_FILL_IN_SELECT_BOX.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getEmailUser() {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            List<String> data = this.userRepository.getEmailUser();;
            finalResult.put(Constant.RESPONSE_KEY.DATA, data);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy ra data tác giả trong select! {} " + e.getMessage());
            result = new Result(Message.ERROR_WHEN_GETTING_AUTHOR_DATA_TO_FILL_IN_SELECT_BOX.getCode(), false, Message.ERROR_WHEN_GETTING_AUTHOR_DATA_TO_FILL_IN_SELECT_BOX.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

}
