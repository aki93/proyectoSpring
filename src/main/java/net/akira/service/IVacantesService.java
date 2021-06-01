/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.akira.service;

import java.util.List;
import net.akira.model.Vacante;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Nikkai
 */
public interface IVacantesService {
    List<Vacante> buscarTodas();
    Vacante buscarPorId(Integer idVacante);
    void guardar (Vacante vacante);
    List<Vacante> buscarDestacadas();
    void borrarVacante (Integer idVacante);
    List<Vacante> buscarByExample(Example<Vacante> example);
    Page<Vacante> buscarTodasPage(Pageable page);
}
