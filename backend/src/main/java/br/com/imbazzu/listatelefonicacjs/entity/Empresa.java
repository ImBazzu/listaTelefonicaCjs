package br.com.imbazzu.listatelefonicacjs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Empresa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "empresa_categoria",
            joinColumns = @JoinColumn(name = "empresa_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Telefone> telefones = new HashSet<>();


    @Column(length = 500)
    private String descricao;


    public Empresa(String nome, Set<Telefone> telefones) {
        this.nome = nome;
        telefones.forEach(this::adicionarTelefone);
    }

    public void adicionarTelefone(Telefone telefone) {
        telefones.add(telefone);
        telefone.setEmpresa(this);
    }

    public void removerTelefone(Telefone telefone) {
        telefones.remove(telefone);
        telefone.setEmpresa(null);
    }

    public void adicionarCategorias(Set<Categoria> categorias) {
        categorias.forEach(this::adicionarCategoria);
    }
    public void adicionarCategoria(Categoria categoria) {
        categorias.add(categoria);
        categoria.getEmpresa().add(this);
    }

    public void removerCategoria(Categoria categoria) {
        categorias.remove(categoria);
        categoria.getEmpresa().remove(this);
    }
}
