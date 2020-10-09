/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.akira.repository;

import org.springframework.data.repository.CrudRepository;

import net.akira.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 *
 * @author Ice Cold
 */
//public interface CategoriasRepository extends CrudRepository<Categoria, Integer>
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
    
}
