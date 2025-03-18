package com.example.music.controller.admin;

import com.example.music.dto.UserRequest;
import com.example.music.entity.comon.ResponseData;
import com.example.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/user/admin")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/verify")
    public CompletableFuture<ResponseData> verifyUser(@RequestParam(name = "type") Byte type, @ModelAttribute UserRequest userDTO) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.verifyUser(type, userDTO)));
    }

    @PostMapping(value = "/save")
    public CompletableFuture<ResponseData> insert(@ModelAttribute UserRequest userDTO) throws IOException, ParseException {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.insert(userDTO)));
    }

    @PutMapping(value = "/update/{id}")
    public CompletableFuture<ResponseData> update(@PathVariable String id, @ModelAttribute UserRequest userDTO) throws Exception {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.update(id, userDTO)));
    }

    @PutMapping(value = "/change-status/{id}")
    public CompletableFuture<ResponseData> delete(@PathVariable String id, @RequestParam(name = "status") String status) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.changeStatusUser(id, status)));
    }

    @GetMapping(value = "/search/{id}")
    public CompletableFuture<ResponseData> search(@PathVariable String id) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.detailUser(id)));
    }

    @GetMapping(value = "/new-user/{role}")
    public CompletableFuture<ResponseData> getNewUserOrArts(@PathVariable String role) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.getNewUserOrArtis(role)));
    }

//    @GetMapping(value = "/get-all-artis")
//    public CompletableFuture<ResponseData> getAllArtis(@RequestParam(name = "page", defaultValue = "0") Integer page) {
//        Pageable pageable = PageRequest.of(page, 5);
//        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.getAllArtis(pageable)));
//    }
//
//    @GetMapping(value = "/get-all-artis/{status}")
//    public CompletableFuture<ResponseData> getArtisByStatus(@PathVariable String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
//        Pageable pageable = PageRequest.of(page, 5);
//        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.getArtisByStatus(status, pageable)));
//    }

    @GetMapping(value = "/get-all-user")
    public CompletableFuture<ResponseData> getAllUer(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.getAllUser(pageable)));
    }

    @GetMapping(value = "/get-all-user/{status}")
    public CompletableFuture<ResponseData> getUserByStatus(@PathVariable String status, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.getUserByStatus(status, pageable)));
    }

    @GetMapping(value = "/update-status")
    public CompletableFuture<ResponseData> updateStatus(@RequestParam(name = "id") String id, @RequestParam(name = "account") String account, @RequestParam(name = "status") String status) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.updateStatusUser(id, account, status)));
    }

//    @GetMapping(value = "/get-artis-select")
//    public CompletableFuture<ResponseData> getArtisSelect() {
//        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.getArtisForSelect()));
//    }

    @GetMapping(value = "/get-email")
    public CompletableFuture<ResponseData> getEmailUser() {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.userService.getEmailUser()));
    }

}
