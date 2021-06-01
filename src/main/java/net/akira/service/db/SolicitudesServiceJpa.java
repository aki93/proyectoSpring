/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.akira.service.db;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import net.akira.model.Solicitud;
import net.akira.repository.SolicitudesRepository;
import net.akira.service.ISolicitudesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary

public class SolicitudesServiceJpa implements ISolicitudesService {
    
    @Autowired
    SolicitudesRepository solicitudesRepo;

	@Override
	public void guardar(Solicitud solicitud) {
		solicitudesRepo.save(solicitud);
		
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		solicitudesRepo.deleteById(idSolicitud);
		
	}

	@Override
	public List<Solicitud> buscarTodas() {
		
		return solicitudesRepo.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> optional = solicitudesRepo.findById(idSolicitud);
                if (optional.isPresent()){
                return optional.get();
                }
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return solicitudesRepo.findAll(page);
	}	

}
