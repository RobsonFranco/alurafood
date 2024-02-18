package br.com.alurafood.pagamentos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alurafood.pagamentos.dto.PagamentoDto;

import br.com.alurafood.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
	
	@Autowired
	private PagamentoService pagService;
	

	@GetMapping("/listar")
	public Page<PagamentoDto> listar(@PageableDefault(size = 10) Pageable paginacao){
		return pagService.obterTodos(paginacao);
	}
	
	@GetMapping("/pagamento/{id}")
	public ResponseEntity<PagamentoDto> obterporID(@PathVariable("id") @NotNull Long id){
		PagamentoDto dto = pagService.obterporId(id);
		return ResponseEntity.ok(dto);
		
	}
	
	@PostMapping
	public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder) {
		PagamentoDto pagamento = pagService.criarPagamento(dto);
		URI endereco = uriBuilder.path("/criarPagamento/{id}").buildAndExpand(pagamento.getId()).toUri();
		return ResponseEntity.created(endereco).body(pagamento);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<PagamentoDto> alterar(@RequestBody @Valid PagamentoDto dto, @PathVariable @NotNull Long id){
		PagamentoDto atualizado = pagService.atualizarPagamento(id, dto);
		return ResponseEntity.ok(atualizado);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<PagamentoDto> deletar (@PathVariable @NotNull Long id){
		pagService.excluirPagamento(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
