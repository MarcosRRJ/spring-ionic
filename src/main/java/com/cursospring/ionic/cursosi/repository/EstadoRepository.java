package com.cursospring.ionic.cursosi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursospring.ionic.cursosi.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{

}
