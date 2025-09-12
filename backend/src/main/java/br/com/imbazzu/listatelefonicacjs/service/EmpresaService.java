package br.com.imbazzu.listatelefonicacjs.service;

import br.com.imbazzu.listatelefonicacjs.dto.PaginacaoResponse;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaEditarDto;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaRequestDto;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaResponseDto;
import br.com.imbazzu.listatelefonicacjs.dto.endereco.EnderecoResponse;
import br.com.imbazzu.listatelefonicacjs.entity.Empresa;
import br.com.imbazzu.listatelefonicacjs.excecoes.empresa.EmpresaJaExisteExcecao;
import br.com.imbazzu.listatelefonicacjs.excecoes.empresa.EmpresaNaoEncontradaExcecao;
import br.com.imbazzu.listatelefonicacjs.mapper.EmpresaMapper;
import br.com.imbazzu.listatelefonicacjs.mapper.EnderecoMapper;
import br.com.imbazzu.listatelefonicacjs.mapper.PaginacaoMapper;
import br.com.imbazzu.listatelefonicacjs.repository.EmpresaRepository;
import br.com.imbazzu.listatelefonicacjs.repository.EmpresaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository repo;

    private final CategoriaService categoriaService;

    @Transactional
    public EmpresaResponseDto salvar(EmpresaRequestDto dto) {

        verificarDuplicado(dto.nome());

        var empresa = EmpresaMapper.toEntity(dto);

        if(dto.categoriasIds() != null && !dto.categoriasIds().isEmpty()){
            var categorias = categoriaService.buscarCategoriasPorIds(dto.categoriasIds());
            empresa.adicionarCategorias(categorias);
        }
        empresa=repo.save(empresa);
        return EmpresaMapper.toResponse(empresa);
    }

    @Transactional
    public EmpresaResponseDto editar(Long id, EmpresaRequestDto dto){

        var empresaAtual = buscarEntidadePorId(id);
        if(!empresaAtual.getNome().equals(dto.nome())){
            verificarDuplicado(dto.nome());
        }
        empresaAtual = EmpresaMapper.toEntity(dto);
        empresaAtual.setId(id);
        repo.save(empresaAtual);

        return EmpresaMapper.toResponse(empresaAtual);

    }

    @Transactional(readOnly = true)
    public EmpresaResponseDto buscarPorId(Long id) {
        var empresa = buscarEntidadePorId(id);
        return EmpresaMapper.toResponse(empresa);
    }

    @Transactional(readOnly = true)
    public boolean verificarEmpresaCadastrada(String nome) {
        return repo.existsByNome(nome);
    }

    @Transactional(readOnly = true)
    protected Empresa buscarEntidadePorId(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new EmpresaNaoEncontradaExcecao("Empresa não encontrada, id: " + id)
        );
    }

    @Transactional
    public void excluir(Long id) {
        var empresa = buscarEntidadePorId(id);
        repo.delete(empresa);
    }

    @Transactional(readOnly = true)
    public PaginacaoResponse<EmpresaResponseDto> buscar(String termo, int numPagina) {
        var paginaConf = PageRequest.of(numPagina,10, Sort.by("nome").ascending());

        var paginaRetorno =repo.findAll(EmpresaSpecification.buscarPorTermo(termo),paginaConf);
        return PaginacaoMapper.toDto(paginaRetorno);
    }

    @Transactional(readOnly = true)
    protected void verificarDuplicado(String nome) {
        if(verificarEmpresaCadastrada(nome)){
            throw new EmpresaJaExisteExcecao("Empresa ja Cadastrada,  nome: " +nome);
        }
    }
}
