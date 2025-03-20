package com.example.music.user;

import com.example.music.comon.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/verify")
    public ResponseData verifyUser(@RequestParam(name = "type") Byte type, @ModelAttribute UserRequest userDTO) {
        return ResponseData.createResponse(this.userService.verifyUser(type, userDTO));
    }

    @PostMapping(value = "/save")
    public ResponseData insert(@ModelAttribute UserRequest userDTO) throws IOException, ParseException {
        return ResponseData.createResponse(this.userService.insert(userDTO));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseData update(@PathVariable String id, @ModelAttribute UserRequest userDTO) throws Exception {
        return ResponseData.createResponse(this.userService.update(id, userDTO));
    }

    @PutMapping(value = "/change-status/{id}")
    public ResponseData delete(@PathVariable String id, @RequestParam(name = "status") String status) {
        return ResponseData.createResponse(this.userService.changeStatusUser(id, status));
    }

    @GetMapping(value = "/search/{id}")
    public ResponseData search(@PathVariable String id) {
        return ResponseData.createResponse(this.userService.detailUser(id));
    }

    @GetMapping(value = "/new-user/{role}")
    public ResponseData getNewUserOrArts(@PathVariable String role) {
        return ResponseData.createResponse(this.userService.getNewUserOrArtis(role));
    }

//    @GetMapping(value = "/get-all-artis")
//    public ResponseData getAllArtis(@RequestParam(name = "page", defaultValue = "0") Integer page) {
//        Pageable pageable = PageRequest.of(page, 5);
//        return ResponseData.createResponse(this.userService.getAllArtis(pageable));
//    }
//
//    @GetMapping(value = "/get-all-artis/{status}")
//    public ResponseData getArtisByStatus(@PathVariable String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
//        Pageable pageable = PageRequest.of(page, 5);
//        return ResponseData.createResponse(this.userService.getArtisByStatus(status, pageable));
//    }

    @GetMapping(value = "/get-all-user")
    public ResponseData getAllUer(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseData.createResponse(this.userService.getAllUser(pageable));
    }

    @GetMapping(value = "/get-all-user/{status}")
    public ResponseData getUserByStatus(@PathVariable String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseData.createResponse(this.userService.getUserByStatus(status, pageable));
    }

    @GetMapping(value = "/update-status")
    public ResponseData updateStatus(@RequestParam(name = "id") String id, @RequestParam(name = "account") String account, @RequestParam(name = "status") String status) {
        return ResponseData.createResponse(this.userService.updateStatusUser(id, account, status));
    }

//    @GetMapping(value = "/get-artis-select")
//    public ResponseData getArtisSelect() {
//        return ResponseData.createResponse(this.userService.getArtisForSelect());
//    }

    @GetMapping(value = "/get-email")
    public ResponseData getEmailUser() {
        return ResponseData.createResponse(this.userService.getEmailUser());
    }

}
