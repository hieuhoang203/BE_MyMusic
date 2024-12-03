package com.example.music.controller.admin;

import com.example.music.dao.ArtisDAO;
import com.example.music.dao.ArtisSelect;
import com.example.music.dao.UserDAO;
import com.example.music.dto.UserDTO;
import com.example.music.entity.User;
import com.example.music.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user/admin")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public Page<User> getObject(@RequestParam(name = "status", defaultValue = "Activate") String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(0, 5);
        return this.userService.getObject(status, pageable);
    }

    @GetMapping(value = "/select")
    public ResponseEntity<Map<Integer, User>> select(@RequestParam(value = "status", defaultValue = "Activate") String status) {
        try {
            return new ResponseEntity<>(this.userService.select(status), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<User> insert(@ModelAttribute UserDTO userDTO) {
        try {
            return new ResponseEntity<>(this.userService.insert(userDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id, @ModelAttribute UserDTO userDTO) {
        try {
            return new ResponseEntity<>(this.userService.update(id, userDTO), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(this.userService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<UserDTO> search(@PathVariable Integer id) {
        try {
            User user = this.userService.detail(id);
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .gender(user.getGender() != null ? user.getGender().toString() : "")
                    .email(user.getAccount().getLogin())
                    .birthday(user.getBirthday() != null ? user.getBirthday().toString() : user.getCreate_date().toString())
                    .role(user.getAccount().getRole().toString())
                    .build();
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/new-user/{role}")
    public ResponseEntity<Map<Integer, UserDAO>> getNewUserOrArts(@PathVariable String role) {
        try {
            return new ResponseEntity<>(this.userService.getNewUserOrArtis(role), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get-all-artis")
    public Page<ArtisDAO> getAllArtis(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return this.userService.getAllArtis(pageable);
    }

    @GetMapping(value = "/get-all-artis/{status}")
    public Page<ArtisDAO> getArtisByStatus(@PathVariable String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return this.userService.getArtisByStatus(status, pageable);
    }

    @GetMapping(value = "/get-all-user")
    public ResponseEntity<Page<UserDAO>> getAllUer(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        try {
            Pageable pageable = PageRequest.of(page, 5);
            return new ResponseEntity<>(this.userService.getAllUser(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get-all-user/{status}")
    public Page<UserDAO> getUserByStatus(@PathVariable String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return this.userService.getUserByStatus(status, pageable);
    }

    @GetMapping(value = "/update-status")
    public ResponseEntity<User> updateStatus(@RequestParam(name = "id") String id, @RequestParam(name = "account") String account, @RequestParam(name = "status") String status) {
        try {
            return new ResponseEntity<>(this.userService.updateStatusUser(id, account, status), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get-artis-select")
    public ResponseEntity<List<ArtisSelect>> getArtisSelect() {
        try {
            return new ResponseEntity<>(this.userService.getArtisForSelect(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get-email")
    public ResponseEntity<List<String>> getEmailUser(){
        try {
            return new ResponseEntity<>(this.userService.getEmailUser(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
