package br.com.adotapet.adota_pet.controller;

import jakarta.validation.Valid;
import br.com.adotapet.adota_pet.dto.AnuncioCriadoDTO;
import br.com.adotapet.adota_pet.dto.CriarAnuncioDTO;
import br.com.adotapet.adota_pet.dto.DetalhesAnuncioDTO;
import br.com.adotapet.adota_pet.service.AnuncioAnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {

    private final AnuncioAnimalService anuncioAnimalService;

    public AnuncioController(AnuncioAnimalService anuncioAnimalService) {
        this.anuncioAnimalService = anuncioAnimalService;
    }

    @PostMapping
    public ResponseEntity<AnuncioCriadoDTO> criarAnuncio(@Valid @RequestBody CriarAnuncioDTO dados, @AuthenticationPrincipal Jwt jwt) {

        Integer idUsuario = Integer.valueOf(jwt.getSubject());
        AnuncioCriadoDTO anuncio = anuncioAnimalService.criarAnuncio(dados, idUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(anuncio);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AnuncioCriadoDTO> editarAnuncio(@PathVariable Integer id, @Valid @RequestBody CriarAnuncioDTO dados, @AuthenticationPrincipal Jwt jwt) {

        Integer idUsuario = Integer.valueOf(jwt.getSubject());

        AnuncioCriadoDTO anuncio = anuncioAnimalService.editarAnuncio(id, dados, idUsuario);

        return ResponseEntity.ok(anuncio);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesAnuncioDTO> visualizarAnuncio(@PathVariable Integer id) {
        DetalhesAnuncioDTO detalhes = anuncioAnimalService.buscarPorId(id);
        return ResponseEntity.ok(detalhes);
    }

    @GetMapping
    public ResponseEntity<List<AnuncioCriadoDTO>> listarAnunciosDisponiveis() {
        List<AnuncioCriadoDTO> anuncios = anuncioAnimalService.listarAnunciosDisponiveis();

        return ResponseEntity.ok(anuncios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAnuncio(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt) {

        Integer idUsuario = Integer.valueOf(jwt.getSubject());

        anuncioAnimalService.excluirAnuncio(id, idUsuario);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/adotar")
    public ResponseEntity<Void> marcarComoAdotado(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt) {

        Integer idUsuario = Integer.valueOf(jwt.getSubject());

        anuncioAnimalService.marcarComoAdotado(id, idUsuario);

        return ResponseEntity.noContent().build();
    }
}