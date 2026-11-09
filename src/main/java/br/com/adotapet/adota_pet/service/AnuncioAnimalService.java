
    package br.com.adotapet.adota_pet.service;

    import br.com.adotapet.adota_pet.dto.DetalhesAnuncioDTO;
    import br.com.adotapet.adota_pet.dto.AnuncioCriadoDTO;
    import br.com.adotapet.adota_pet.dto.CriarAnuncioDTO;
    import br.com.adotapet.adota_pet.entity.Animal;
    import br.com.adotapet.adota_pet.entity.AnuncioAnimal;
    import br.com.adotapet.adota_pet.entity.Usuario;
    import br.com.adotapet.adota_pet.enums.StatusAnuncio;
    import br.com.adotapet.adota_pet.repository.AnimalRepository;
    import br.com.adotapet.adota_pet.repository.AnuncioAnimalRepository;
    import br.com.adotapet.adota_pet.repository.UsuarioRepository;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class AnuncioAnimalService {

        private final AnimalRepository animalRepository;
        private final AnuncioAnimalRepository anuncioAnimalRepository;
        private final UsuarioRepository usuarioRepository;

        public AnuncioAnimalService(
                AnimalRepository animalRepository,
                AnuncioAnimalRepository anuncioAnimalRepository,
                UsuarioRepository usuarioRepository) {

            this.animalRepository = animalRepository;
            this.anuncioAnimalRepository = anuncioAnimalRepository;
            this.usuarioRepository = usuarioRepository;
        }

        @Transactional
        public AnuncioCriadoDTO criarAnuncio(CriarAnuncioDTO dados, Integer idUsuario) {

            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Usuário não encontrado."));

            AnuncioAnimal anuncio = new AnuncioAnimal();

            anuncio.setUsuario(usuario);
            anuncio.setDescricao(dados.getDescricao());
            anuncio.setFotoAnimal(dados.getFotoAnimal());

            anuncioAnimalRepository.save(anuncio);

            Animal animal = new Animal();

            animal.setNome(dados.getNome());
            animal.setIdade(dados.getIdade());

            if (dados.getUnidadeIdade() != null) {
                animal.setUnidadeIdade(dados.getUnidadeIdade());
            }

            if (dados.getIdadeAproximada() != null) {
                animal.setIdadeAproximada(dados.getIdadeAproximada());
            }

            animal.setEspecie(dados.getEspecie());
            animal.setSexo(dados.getSexo());
            animal.setPorte(dados.getPorte());

            animal.setAnuncio(anuncio);
            anuncio.setAnimal(animal);

            animalRepository.save(animal);

            AnuncioCriadoDTO resposta = new AnuncioCriadoDTO(
                    anuncio.getId(),
                    animal.getId()
            );

            resposta.setNome(animal.getNome());
            resposta.setIdade(animal.getIdade());
            resposta.setUnidadeIdade(animal.getUnidadeIdade());
            resposta.setIdadeAproximada(animal.getIdadeAproximada());
            resposta.setEspecie(animal.getEspecie());
            resposta.setSexo(animal.getSexo());
            resposta.setPorte(animal.getPorte());

            resposta.setDescricao(anuncio.getDescricao());
            resposta.setFotoAnimal(anuncio.getFotoAnimal());
            resposta.setDataPublicacao(anuncio.getDataPublicacao());
            resposta.setStatus(anuncio.getStatus());

            return resposta;
        }

        public List<AnuncioCriadoDTO> listarAnunciosDisponiveis() {

            List<AnuncioAnimal> anuncios =
                    anuncioAnimalRepository.findByStatus(StatusAnuncio.disponivel);

            List<AnuncioCriadoDTO> respostas = new ArrayList<>();

            for (AnuncioAnimal anuncio : anuncios) {

                Animal animal = anuncio.getAnimal();

                AnuncioCriadoDTO resposta = new AnuncioCriadoDTO(
                        anuncio.getId(),
                        animal.getId()
                );

                resposta.setNome(animal.getNome());
                resposta.setIdade(animal.getIdade());
                resposta.setUnidadeIdade(animal.getUnidadeIdade());
                resposta.setIdadeAproximada(animal.getIdadeAproximada());
                resposta.setEspecie(animal.getEspecie());
                resposta.setSexo(animal.getSexo());
                resposta.setPorte(animal.getPorte());

                resposta.setDescricao(anuncio.getDescricao());
                resposta.setFotoAnimal(anuncio.getFotoAnimal());
                resposta.setDataPublicacao(anuncio.getDataPublicacao());
                resposta.setStatus(anuncio.getStatus());

                respostas.add(resposta);
            }

            return respostas;
        }

        @Transactional(readOnly = true)
        public DetalhesAnuncioDTO buscarPorId(Integer id) {

            AnuncioAnimal anuncio = anuncioAnimalRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Anúncio não encontrado."));

            Animal animal = anuncio.getAnimal();
            Usuario usuario = anuncio.getUsuario();

            return new DetalhesAnuncioDTO(
                    anuncio.getId(),
                    animal.getNome(),
                    animal.getIdade(),
                    animal.getUnidadeIdade(),
                    animal.getIdadeAproximada(),
                    animal.getEspecie(),
                    animal.getSexo(),
                    animal.getPorte(),
                    anuncio.getDescricao(),
                    anuncio.getFotoAnimal(),
                    anuncio.getDataPublicacao(),
                    anuncio.getStatus(),
                    usuario.getNome(),
                    usuario.getCidade()
            );
        }

        @Transactional
        public AnuncioCriadoDTO editarAnuncio(
                Integer idAnuncio,
                CriarAnuncioDTO dados,
                Integer idUsuario) {

            AnuncioAnimal anuncio = anuncioAnimalRepository.findById(idAnuncio)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Anúncio não encontrado."));

            if (!anuncio.getUsuario().getId().equals(idUsuario)) {
                throw new IllegalArgumentException(
                        "Você não tem permissão para editar este anúncio.");
            }

            if (anuncio.getStatus() != StatusAnuncio.disponivel) {
                throw new IllegalArgumentException(
                        "Não é possível editar um anúncio que já foi adotado.");
            }

            Animal animal = anuncio.getAnimal();

            animal.setNome(dados.getNome());
            animal.setIdade(dados.getIdade());
            animal.setUnidadeIdade(dados.getUnidadeIdade());
            animal.setIdadeAproximada(dados.getIdadeAproximada());
            animal.setEspecie(dados.getEspecie());
            animal.setSexo(dados.getSexo());
            animal.setPorte(dados.getPorte());

            anuncio.setDescricao(dados.getDescricao());
            anuncio.setFotoAnimal(dados.getFotoAnimal());

            return criarAnuncioDTOResposta(anuncio, animal);
        }

        private AnuncioCriadoDTO criarAnuncioDTOResposta(
                AnuncioAnimal anuncio,
                Animal animal) {

            AnuncioCriadoDTO resposta = new AnuncioCriadoDTO(
                    anuncio.getId(),
                    animal.getId()
            );

            resposta.setNome(animal.getNome());
            resposta.setIdade(animal.getIdade());
            resposta.setUnidadeIdade(animal.getUnidadeIdade());
            resposta.setIdadeAproximada(animal.getIdadeAproximada());
            resposta.setEspecie(animal.getEspecie());
            resposta.setSexo(animal.getSexo());
            resposta.setPorte(animal.getPorte());

            resposta.setDescricao(anuncio.getDescricao());
            resposta.setFotoAnimal(anuncio.getFotoAnimal());
            resposta.setDataPublicacao(anuncio.getDataPublicacao());
            resposta.setStatus(anuncio.getStatus());

            return resposta;
        }

        @Transactional
        public void excluirAnuncio(Integer idAnuncio, Integer idUsuario) {

            AnuncioAnimal anuncio = anuncioAnimalRepository.findById(idAnuncio)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Anúncio não encontrado."));

            if (!anuncio.getUsuario().getId().equals(idUsuario)) {
                throw new IllegalArgumentException(
                        "Você não tem permissão para excluir este anúncio.");
            }

            anuncioAnimalRepository.delete(anuncio);
        }

        @Transactional
        public void marcarComoAdotado(Integer idAnuncio, Integer idUsuario) {

            AnuncioAnimal anuncio = anuncioAnimalRepository.findById(idAnuncio)
                    .orElseThrow(() -> new IllegalArgumentException("Anúncio não encontrado."));

            if (!anuncio.getUsuario().getId().equals(idUsuario)) {
                throw new IllegalArgumentException("Você não tem permissão para marcar este anúncio como adotado.");
            }

            anuncioAnimalRepository.delete(anuncio);
        }
    }
