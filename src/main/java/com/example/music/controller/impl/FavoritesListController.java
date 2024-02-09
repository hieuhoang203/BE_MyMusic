package com.example.music.controller.impl;

import com.example.music.entity.FavoritesList;
import com.example.music.service.IService;
import com.example.music.service.impl.FavoritesListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/favorites-list")
public class FavoritesListController implements IService<FavoritesList, Long> {

    @Autowired
    private FavoritesListService favoritesListService;

    @Override
    @PostMapping(value = "/save")
    public FavoritesList insert(@RequestBody FavoritesList favoritesList) {
        return this.favoritesListService.insert(favoritesList);
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public FavoritesList update(@PathVariable Long id, @RequestBody FavoritesList favoritesList) {
        return this.favoritesListService.update(id, favoritesList);
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public FavoritesList delete(@PathVariable Long id) {
        return this.favoritesListService.delete(id);
    }

    @Override
    @GetMapping(value = "/search/{id}")
    public FavoritesList detail(@PathVariable Long id) {
        return this.favoritesListService.delete(id);
    }

    @Override
    @GetMapping(value = "")
    public Map<Long, FavoritesList> select(@RequestParam(value = "status", defaultValue = "DANG_HOAT_DONG", required = false) String status) {
        return this.favoritesListService.select(status);
    }

}
