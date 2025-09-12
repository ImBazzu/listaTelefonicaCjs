package br.com.imbazzu.listatelefonicacjs.repository;

import br.com.imbazzu.listatelefonicacjs.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNomeIgnoreCase(String nome);

    Page<Categoria> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
