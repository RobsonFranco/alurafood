package br.com.alurafood.pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alurafood.pagamentos.model.Pagamentos;

public interface PagamentosRepository extends JpaRepository<Pagamentos, Long> {

}
