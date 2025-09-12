package br.com.imbazzu.listatelefonicacjs.controller;

import br.com.imbazzu.listatelefonicacjs.dto.PaginacaoResponse;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaEditarDto;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaRequestDto;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaResponseDto;
import br.com.imbazzu.listatelefonicacjs.dto.endereco.EnderecoResponse;
import br.com.imbazzu.listatelefonicacjs.mapper.EmpresaMapper;
import br.com.imbazzu.listatelefonicacjs.service.EmpresaService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("empresa")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpresaResponseDto> salvar(@RequestBody @Valid EmpresaRequestDto empresa) {
        var empresaCriada = service.salvar(empresa);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(empresaCriada.id()).toUri();
        return ResponseEntity.created(uri).body(empresaCriada);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpresaResponseDto> editar(@PathVariable Long id,
                                                     @RequestBody @Valid EmpresaRequestDto dto) {
        var empresa = service.editar(id,dto);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping("{id}")
    @PermitAll
    public ResponseEntity<EmpresaResponseDto> buscarPorId(@PathVariable Long id) {
        var empresa = service.buscarPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("buscar")
    @PermitAll
    public ResponseEntity<PaginacaoResponse<EmpresaResponseDto>> buscar(@RequestParam(defaultValue = "") String texto,
                                                                        @RequestParam(defaultValue = "0") int pagina) {
        var empresas = service.buscar(texto, pagina);
        return ResponseEntity.ok(empresas);
    }
}
