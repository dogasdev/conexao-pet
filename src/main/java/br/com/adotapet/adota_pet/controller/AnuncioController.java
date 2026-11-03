package br.com.adotapet.adota_pet.controller;

import br.com.adotapet.adota_pet.dto.AnuncioCriadoDTO;
import br.com.adotapet.adota_pet.dto.CriarAnuncioDTO;
import br.com.adotapet.adota_pet.service.AnuncioAnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {

    private final AnuncioAnimalService anuncioAnimalService;

    public AnuncioController(AnuncioAnimalService anuncioAnimalService) {
        this.anuncioAnimalService = anuncioAnimalService;
    }

    @PostMapping
    public ResponseEntity<AnuncioCriadoDTO> criarAnuncio(@RequestBody CriarAnuncioDTO dados, @AuthenticationPrincipal Jwt jwt) {

        Integer idUsuario = Integer.valueOf(jwt.getSubject());
        AnuncioCriadoDTO anuncio = anuncioAnimalService.criarAnuncio(dados, idUsuario
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(anuncio);
    }
}