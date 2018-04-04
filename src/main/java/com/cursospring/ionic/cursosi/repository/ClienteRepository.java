package com.cursospring.ionic.cursosi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursospring.ionic.cursosi.dto.ClienteDTO;
import com.cursospring.ionic.cursosi.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	@Transactional(readOnly=true)
	public Cliente findByEmail(String email);
	
	@Query("SELECT new com.cursospring.ionic.cursosi.dto.ClienteDTO(c.id, c.nome, c.email) FROM Cliente c ")
	public List<ClienteDTO> findNomeAndEmail();
	
}
