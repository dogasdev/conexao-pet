package br.com.adotapet.adota_pet.entity;

import br.com.adotapet.adota_pet.enums.Especie;
import br.com.adotapet.adota_pet.enums.Porte;
import br.com.adotapet.adota_pet.enums.Sexo;
import br.com.adotapet.adota_pet.enums.UnidadeIdade;
import jakarta.persistence.*;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 60)
    private String nome;

    @Column(name = "idade", nullable = false)
    private Integer idade;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_idade", nullable = false)
    private UnidadeIdade unidadeIdade = UnidadeIdade.anos;

    @Column(name = "idade_aproximada", nullable = false)
    private Boolean idadeAproximada = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false)
    private Especie especie;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "porte", nullable = false)
    private Porte porte;

    @OneToOne
    @JoinColumn(name = "id_anuncio", nullable = false, unique = true)
    private AnuncioAnimal anuncio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public UnidadeIdade getUnidadeIdade() {
        return unidadeIdade;
    }

    public void setUnidadeIdade(UnidadeIdade unidadeIdade) {
        this.unidadeIdade = unidadeIdade;
    }

    public Boolean getIdadeAproximada() {
        return idadeAproximada;
    }

    public void setIdadeAproximada(Boolean idadeAproximada) {
        this.idadeAproximada = idadeAproximada;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public AnuncioAnimal getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioAnimal anuncio) {
        this.anuncio = anuncio;
    }
}