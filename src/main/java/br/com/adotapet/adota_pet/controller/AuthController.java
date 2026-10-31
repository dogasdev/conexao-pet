package br.com.adotapet.adota_pet.controller;

import br.com.adotapet.adota_pet.dto.CadastroInstituicaoDTO;
import br.com.adotapet.adota_pet.dto.CadastroUsuarioDTO;
import br.com.adotapet.adota_pet.service.InstituicaoService;
import br.com.adotapet.adota_pet.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final InstituicaoService instituicaoService;

    public AuthController(
            UsuarioService usuarioService,
            InstituicaoService instituicaoService) {
        this.usuarioService = usuarioService;
        this.instituicaoService = instituicaoService;
    }

    @PostMapping("/cadastro/usuario")
    public ResponseEntity<Map<String, String>> cadastrarUsuario(
            @RequestBody CadastroUsuarioDTO dados) {

        try {
            usuarioService.cadastrar(dados);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "mensagem",
                            "Usuário cadastrado com sucesso!"
                    ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/cadastro/instituicao")
    public ResponseEntity<Map<String, String>> cadastrarInstituicao(
            @RequestBody CadastroInstituicaoDTO dados) {

        try {
            instituicaoService.cadastrar(dados);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "mensagem",
                            "Instituição cadastrada com sucesso!"
                    ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}
