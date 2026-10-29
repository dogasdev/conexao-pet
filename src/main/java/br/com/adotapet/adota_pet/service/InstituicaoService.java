package br.com.adotapet.adota_pet.service;

import br.com.adotapet.adota_pet.dto.CadastroInstituicaoDTO;
import br.com.adotapet.adota_pet.entity.Instituicao;
import br.com.adotapet.adota_pet.entity.Usuario;
import br.com.adotapet.adota_pet.repository.InstituicaoRepository;
import br.com.adotapet.adota_pet.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public InstituicaoService(
            InstituicaoRepository instituicaoRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.instituicaoRepository = instituicaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void cadastrar(CadastroInstituicaoDTO dados) {

        if (instituicaoRepository.existsByCnpj(dados.getCnpj())) {
            throw new IllegalArgumentException(
                    "Este CNPJ já está cadastrado.");
        }

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

        Instituicao instituicao = new Instituicao();

        instituicao.setNomeInstituicao(dados.getNomeInstituicao());
        instituicao.setCnpj(dados.getCnpj());
        instituicao.setRua(dados.getRua());
        instituicao.setNumero(dados.getNumero());
        instituicao.setBairro(dados.getBairro());
        instituicao.setCep(dados.getCep());
        instituicao.setCidade(dados.getCidade());

        instituicao = instituicaoRepository.save(instituicao);

        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome());
        usuario.setSobrenome(dados.getSobrenome());
        usuario.setCpf(dados.getCpf());
        usuario.setTelefone(dados.getTelefone());
        usuario.setCidade(dados.getCidade());
        usuario.setUsername(dados.getUsername());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        usuario.setInstituicao(instituicao);

        usuarioRepository.save(usuario);
    }
}
