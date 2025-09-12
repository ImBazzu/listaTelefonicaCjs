package br.com.imbazzu.listatelefonicacjs.controller;

import br.com.imbazzu.listatelefonicacjs.dto.PaginacaoResponse;
import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaCriarDto;
import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaResponseDto;
import br.com.imbazzu.listatelefonicacjs.service.CategoriaService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid CategoriaCriarDto dto) {
        var categoria= service.salvar(dto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponseDto> editar(@PathVariable Long id, @RequestBody @Valid CategoriaCriarDto dto) {
        var categoria = service.editar(id,dto);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("{id}")
    @PermitAll
    public ResponseEntity<CategoriaResponseDto> buscarPorId(@PathVariable Long id) {
        var categoria = service.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("buscar")
    @PermitAll
    public ResponseEntity<PaginacaoResponse<CategoriaResponseDto>>
    buscar(@RequestParam String texto,
           @RequestParam(defaultValue = "0") int pagina) {
        var categorias = service.buscarPorNome(texto,pagina);
        return ResponseEntity.ok(categorias);
    }
}
