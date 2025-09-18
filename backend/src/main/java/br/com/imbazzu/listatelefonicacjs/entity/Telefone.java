package br.com.imbazzu.listatelefonicacjs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "telefones")
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String numero;

    private boolean principal= false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;

    public Telefone(String numero, boolean principal) {
        this.numero = numero;
        this.principal = principal;
    }
}
