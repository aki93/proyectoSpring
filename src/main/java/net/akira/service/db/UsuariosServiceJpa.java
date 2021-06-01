/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.akira.service.db;

import java.util.List;
import net.akira.model.Usuario;
import net.akira.repository.UsuariosRepository;
import net.akira.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ice Cold
 */

//ANOTACIONES DE LA CLASE DE SERVICIO
@Service
@Primary
public class UsuariosServiceJpa implements IUsuariosService {
    //metodos de repositorio
    @Autowired
    UsuariosRepository usuarioRepo;

    @Override
    public void guardar(Usuario usuario) {
        usuarioRepo.save(usuario);
    }

    @Override
    public void eliminar(Integer idUsuario) {
       usuarioRepo.deleteById(idUsuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepo.findAll();
    }

    @Override
    public Usuario buscarPorUsername(String username) {
       return usuarioRepo.findByUsername(username);

    }
    
}
