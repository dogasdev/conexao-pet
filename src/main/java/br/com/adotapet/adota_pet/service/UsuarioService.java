package br.com.adotapet.adota_pet.service;

import br.com.adotapet.adota_pet.dto.CadastroUsuarioDTO;
import br.com.adotapet.adota_pet.entity.Usuario;
import br.com.adotapet.adota_pet.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario cadastrar(CadastroUsuarioDTO dados) {

        if (usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado.");
        }

        if (usuarioRepository.existsByUsername(dados.getUsername())) {
            throw new IllegalArgumentException(
                    "Este username já está cadastrado.");
        }

        if (usuarioRepository.existsByCpf(dados.getCpf())) {
            throw new IllegalArgumentException(
                    "Este CPF já está cadastrado.");
        }

        Usuario usuario = new Usuario();

        usuario.setUsername(dados.getUsername());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        usuario.setTelefone(dados.getTelefone());
        usuario.setNome(dados.getNome());
        usuario.setSobrenome(dados.getSobrenome());
        usuario.setCpf(dados.getCpf());
        usuario.setCidade(dados.getCidade());

        return usuarioRepository.save(usuario);
    }
}
