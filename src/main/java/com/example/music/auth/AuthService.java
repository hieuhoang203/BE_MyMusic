package com.example.music.auth;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.example.music.comon.Constant;
import com.example.music.comon.Message;
import com.example.music.comon.Result;
import com.example.music.data.DetailAccount;
import com.example.music.security.JwtService;
import com.example.music.user.User;
import com.example.music.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final Cloudinary cloudinary;

    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Map<Object, Object> createAccountUser(AccountRequest request) throws Exception {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            User data = userRepository.getUserByEmail(request.getLogin());
            if (request.getLogin().isEmpty()) {
                result = new Result(Message.INVALID_EMAIL.getCode(), false, Message.INVALID_EMAIL.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
                finalResult.put(Constant.RESPONSE_KEY.DATA, request);
                return finalResult;
            }
            if (request.getPass().isEmpty() || request.getPass().length() < 6) {
                result = new Result(Message.INVALID_PASSWORD.getCode(), false, Message.INVALID_PASSWORD.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
                finalResult.put(Constant.RESPONSE_KEY.DATA, request);
                return finalResult;
            }
            if (data != null) {
                result = new Result(Message.ACCOUNT_ALREADY_EXISTS.getCode(), false, Message.ACCOUNT_ALREADY_EXISTS.getMessage());
                finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
                finalResult.put(Constant.RESPONSE_KEY.DATA, data);
                return finalResult;
            }
            Date create = new Date(System.currentTimeMillis());

            ApiResponse apiResponse = cloudinary.api().resourceByAssetID("6089f07ca3500cc8c9362a3edb3be8d7", ObjectUtils.emptyMap());

            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .name(request.getName())
                    .avatar(apiResponse.get("url").toString())
                    .create_date(create)
                    .login(request.getLogin())
                    .password(passwordEncoder.encode(request.getPass()))
                    .role(String.valueOf(Constant.Role.USER))
                    .create_by(Constant.Create.NTH)
                    .status(Constant.Status.Activate)
                    .build();
            this.userRepository.save(user);
            finalResult.put(Constant.RESPONSE_KEY.DATA, jwtService.generateToken(user.getLogin()));
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi tạo mới người dùng {} " + e.getMessage());
            finalResult.put(Constant.RESPONSE_KEY.DATA, request);
            result = new Result(Message.UNABLE_TO_CREATE_ACCOUNT.getCode(), false, Message.UNABLE_TO_CREATE_ACCOUNT.getMessage());
            throw e;
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Map<Object, Object> createAccountArtis(AccountRequest request) throws Exception {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        finalResult.put(Constant.RESPONSE_KEY.DATA, request);
        try {
            ApiResponse apiResponse = cloudinary.api().resourceByAssetID("6089f07ca3500cc8c9362a3edb3be8d7", ObjectUtils.emptyMap());

            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .name(request.getName())
                    .avatar(apiResponse.get("url").toString())
                    .create_date(new Date(new java.util.Date().getTime()))
                    .login(request.getLogin())
                    .password(passwordEncoder.encode(request.getPass()))
                    .role(String.valueOf(Constant.Role.ARTIS))
                    .status(Constant.Status.Activate)
                    .build();
            this.userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi tạo mới tài khoản nghệ sĩ {} " + e.getMessage());
            result = new Result(Message.UNABLE_TO_CREATE_ACCOUNT.getCode(), false, Message.UNABLE_TO_CREATE_ACCOUNT.getMessage());
            throw e;
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> login(LoginRequest login) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        finalResult.put(Constant.RESPONSE_KEY.DATA, login);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPass()));

            if (!authentication.isAuthenticated()) {
                AccountResponse accountResponse = new AccountResponse("Không có quyền truy cập", login.getLogin());
                finalResult.put(Constant.RESPONSE_KEY.DATA, accountResponse);
            } else {
                User user = userRepository.getUserByEmail(login.getLogin());
                if (user != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String token = jwtService.generateToken(authentication.getName());
                    AccountResponse accountResponse = new AccountResponse(token, user.getLogin());
                    finalResult.put(Constant.RESPONSE_KEY.DATA, accountResponse);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = new Result(Message.CANNOT_LOG_IN.getCode(), false, Message.CANNOT_LOG_IN.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getUserResponse(String login) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        finalResult.put(Constant.RESPONSE_KEY.DATA, login);
        try {
            DetailAccount userResponse = userRepository.getUserResponse(login);
            finalResult.put(Constant.RESPONSE_KEY.DATA, userResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = new Result(Message.ACCOUNT_NOT_EXISTS.getCode(), false, Message.ACCOUNT_NOT_EXISTS.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

}
