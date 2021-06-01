/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.akira.service.db;

import java.util.List;
import java.util.Optional;
import net.akira.model.Vacante;
import net.akira.repository.VacantesRepository;
import net.akira.service.IVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ice Cold

*/
@Service
@Primary
public class VacantesServiceJpa implements IVacantesService {
    
    @Autowired
    VacantesRepository vacantesRepo;

    @Override
    public List<Vacante> buscarTodas() {
        return vacantesRepo.findAll();
    }

    @Override
    public Vacante buscarPorId(Integer idVacante) {
        Optional<Vacante> optional = vacantesRepo.findById(idVacante);
        if(optional.isPresent()){
        return optional.get();
        }
        return null;
    }

    @Override
    public void guardar(Vacante vacante) {
        vacantesRepo.save(vacante);
    }

    @Override
    public List<Vacante> buscarDestacadas() {
        return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
    }

    @Override
    public void borrarVacante(Integer idVacante) {
        vacantesRepo.deleteById(idVacante);
    }

    @Override
    public List<Vacante> buscarByExample(Example<Vacante> example) {
        return vacantesRepo.findAll(example);
    }

    @Override
    public Page<Vacante> buscarTodasPage(Pageable page) {
        return vacantesRepo.findAll(page);
    }
    
}
