package net.akira.controller;

import java.util.List;
import net.akira.model.Usuario;
import net.akira.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    private IUsuariosService servicesUsuario;

    
    @GetMapping("/index")
	public String mostrarIndex(Model model) {

    	List<Usuario> lista = servicesUsuario.buscarTodos();
        model.addAttribute("usuarios", lista);
    	
    	return "usuarios/listUsuarios";
	}
    
    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		    	
		
            System.out.println("Borrando usuario con id: " + idUsuario);
            servicesUsuario.eliminar(idUsuario);
                
    	// Ejercicio.
    	
		return "redirect:/usuarios/index";
	}
}
