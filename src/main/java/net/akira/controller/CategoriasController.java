
package net.akira.controller;

import java.util.List;
import net.akira.model.Categoria;
import net.akira.service.ICategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
    
    @Autowired
    private ICategoriasService serviceCategorias;

    
    @GetMapping("/index")
    public String mostrarIndex(Model model) {
        List<Categoria> lista = serviceCategorias.buscarTodas();
        model.addAttribute("categorias", lista);
        return "categorias/listCategorias";
    }
    @GetMapping("/create")
    public String crear(Categoria categoria) {
        return "categorias/formCategoria";
    }
    @PostMapping("/save")
    public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()){
        for(ObjectError error: result.getAllErrors()){
    System.out.println("Error:" + error.getDefaultMessage());
    }
        return "categorias/formCategoria";
        }
        serviceCategorias.guardar(categoria);
        attributes.addFlashAttribute("msg","Registro Guardado");
        System.out.println(categoria);
      
        return "redirect:/categorias/indexPage";
    }
    
    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id")Integer idCategoria, Model model){
    
        System.out.println("Borrando Categoria con id:" + idCategoria);
        serviceCategorias.eliminar(idCategoria);
        
    return "redirect:/categorias/indexPage";
            

}
    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id")Integer idCategoria,Model model){
    Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
    model.addAttribute("categoria" , categoria );
    System.out.println("Editando categoria con id:" + idCategoria);
        
        return "categorias/formCategoria";
    }
    
    @GetMapping ("/indexPage")
    public String mostrarIndexPage (Model model, Pageable page){
    Page<Categoria> lista = serviceCategorias.buscarTogasPage(page);
    model.addAttribute("categorias" , lista);
    return "categorias/listCategorias";
    }
}
