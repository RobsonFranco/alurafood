package br.com.alurafood.pagamentos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.model.Pagamentos;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentosRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PagamentoService {
	
@Autowired
private PagamentosRepository pagamentoRep;

@Autowired
private ModelMapper modelMapper;

public Page<PagamentoDto> obterTodos(Pageable paginacao){
	return pagamentoRep.findAll(paginacao).map(p -> modelMapper.map(p, PagamentoDto.class));
}

public PagamentoDto obterporId(Long id){
	Pagamentos pagamento = pagamentoRep.findById(id).orElseThrow(() -> new EntityNotFoundException());
	return modelMapper.map(pagamento, PagamentoDto.class);
}

public PagamentoDto criarPagamento(PagamentoDto dto) {
	Pagamentos pagamento = modelMapper.map(dto, Pagamentos.class);
	pagamento.setStatus(Status.CRIADO);
	pagamentoRep.save(pagamento);
	return modelMapper.map(pagamento, PagamentoDto.class);
}

public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
	Pagamentos pagamento = modelMapper.map(dto,  Pagamentos.class);
	pagamento.setId(id);
	pagamento.setStatus(Status.ALTERADO);
	pagamentoRep.save(pagamento);
	return modelMapper.map(pagamento, PagamentoDto.class);
}

public void excluirPagamento(Long id) {
	pagamentoRep.deleteById(id);
}


}
