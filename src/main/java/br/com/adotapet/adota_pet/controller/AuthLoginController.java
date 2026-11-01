package br.com.adotapet.adota_pet.controller;

import br.com.adotapet.adota_pet.dto.LoginDTO;
import br.com.adotapet.adota_pet.dto.LoginResponseDTO;
import br.com.adotapet.adota_pet.entity.Usuario;
import br.com.adotapet.adota_pet.service.JwtService;
import br.com.adotapet.adota_pet.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthLoginController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthLoginController(
            UsuarioService usuarioService,
            JwtService jwtService
    ) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO dados) {

        try {
            Usuario usuario = usuarioService.login(dados);

            LoginResponseDTO resposta = new LoginResponseDTO();

            resposta.setId(usuario.getId());
            resposta.setUsername(usuario.getUsername());
            resposta.setNome(usuario.getNome());
            resposta.setEmail(usuario.getEmail());

            String token = jwtService.gerarToken(
                    String.valueOf(usuario.getId())
            );

            resposta.setToken(token);

            return ResponseEntity.ok(resposta);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).build();
        }
    }
}