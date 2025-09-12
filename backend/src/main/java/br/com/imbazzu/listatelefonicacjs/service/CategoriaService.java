package br.com.imbazzu.listatelefonicacjs.service;

import br.com.imbazzu.listatelefonicacjs.dto.PaginacaoResponse;
import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaCriarDto;
import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaRequestDto;
import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaResponseDto;
import br.com.imbazzu.listatelefonicacjs.entity.Categoria;
import br.com.imbazzu.listatelefonicacjs.excecoes.categoria.CategoriaJaCadastradaExcecao;
import br.com.imbazzu.listatelefonicacjs.excecoes.categoria.CategoriaListaVaziaExcecao;
import br.com.imbazzu.listatelefonicacjs.excecoes.categoria.CategoriaNaoEncontradaExcecao;
import br.com.imbazzu.listatelefonicacjs.mapper.CategoriaMapper;
import br.com.imbazzu.listatelefonicacjs.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repo;

    @Transactional(readOnly = true)
    public Set<Categoria> buscarCategoriasPorIds(Set<CategoriaRequestDto> dto) {
        if(dto == null || dto.isEmpty()){
            throw new CategoriaListaVaziaExcecao("Lista dos Ids das Categorias vazia");
        }
        var ids = dto.stream().map(CategoriaRequestDto::id).collect(Collectors.toSet());
       var categorias = new HashSet<>(repo.findAllById(ids));

        var idsRetornadosDoBanco = categorias.stream().map(Categoria::getId).toList();

        var idsNaoEncontrados=dto.stream().map(CategoriaRequestDto::id)
                .filter(idsRetornadosDoBanco::contains).toList();

        if (idsNaoEncontrados.isEmpty()) {
            throw new CategoriaListaVaziaExcecao(
                    "Categorias não encontradas para os IDs: " + idsNaoEncontrados
            );
        }
        return categorias;
    }

    @Transactional(readOnly = true)
    public PaginacaoResponse<CategoriaResponseDto> buscarPorNome(String nome, int numPagina) {
        var pageConfig = PageRequest.of(numPagina, 10, Sort.by("nome").ascending());

        var categorias = repo.findAllByNomeContainingIgnoreCase(nome,pageConfig);
        return CategoriaMapper.responseDto(categorias);
    }


    @Transactional(readOnly = true)
    protected Categoria buscarEntidadePorId(Long id) {
        return repo.findById(id).orElseThrow(
                ()->new CategoriaNaoEncontradaExcecao("Id da Categoria não encontrado, id: " +id)
        );
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDto buscarPorId(Long id) {
        return CategoriaMapper.responseDto(buscarEntidadePorId(id));
    }

    @Transactional
    public void excluirPorId(Long id) {
        var categoria = buscarEntidadePorId(id);
        repo.delete(categoria);
    }

    @Transactional
    public CategoriaResponseDto salvar(CategoriaCriarDto dto) {
        verificarDuplicado(dto.nome());

        var categoria = CategoriaMapper.toEntity(dto);
        return CategoriaMapper.responseDto(repo.save(categoria));
    }

    @Transactional(readOnly = true)
    public boolean verificarCategoriaCadastrada(String nome) {
        return repo.existsByNomeIgnoreCase(nome);
    }

    @Transactional
    public CategoriaResponseDto editar(Long id, CategoriaCriarDto dto) {
        var categoriaAtual = buscarEntidadePorId(id);
        if(!categoriaAtual.getNome().equals(dto.nome())){
            verificarDuplicado(dto.nome());
        }
        categoriaAtual = CategoriaMapper.toEntity(dto);
        categoriaAtual.setId(id);
        return CategoriaMapper.responseDto(repo.save(categoriaAtual));
    }

    @Transactional(readOnly = true)
    protected void verificarDuplicado(String nome) {
        if(verificarCategoriaCadastrada(nome)){
            throw new CategoriaJaCadastradaExcecao("Categoria Ja cadastrada, nome: " + nome);
        }
    }
}
