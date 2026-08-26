package com.example.movieapi.controller;

import com.example.movieapi.dto.FavoriteDto.FavoriteResponse;
import com.example.movieapi.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{movieId}")
    public ResponseEntity<FavoriteResponse> addFavorite(@PathVariable Long movieId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.addFavorite(movieId));

    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites() {

        return ResponseEntity.status(HttpStatus.OK).body(favoriteService.getFavorites());

    }

    @DeleteMapping ("/{movieId}")

    public ResponseEntity<Void> removeFavorite(@PathVariable Long movieId) {
        favoriteService.removeFavorite(movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
