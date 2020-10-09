/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.akira.repository;

import java.util.List;

import net.akira.model.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ice Cold
 */
public interface VacantesRepository extends JpaRepository<Vacante,Integer> {
   //QUERY METHODS
    List<Vacante> findByEstatus(String estatus);
    
    List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);
    
    List<Vacante> findBySalarioBetween(double s1 , double s2);
    
    List<Vacante> findByEstatusIn(String[] estatus);
    
    
    
}
