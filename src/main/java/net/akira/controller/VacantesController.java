
package net.akira.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.akira.model.Vacante;
import net.akira.service.IVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
    
    @Autowired
    private IVacantesService serviceVacantes;
    
    
    @GetMapping("/index")
    public String mostrarIndex (Model model){
    
        //1 obtener todas las vacantes (recuperadas con clase de servicio
        List<Vacante> lista = serviceVacantes.buscarTodas();
       //2 agragar al modelo (vista) el listado de vacantes
        model.addAttribute("vacantes" , lista);
        return "/vacantes/listVacantes";
    } 
    
    @GetMapping("/create")
    public String crear(Vacante vacante){
    return "vacantes/formVacante";
    }
    
    
    @PostMapping("/save")
    public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes){
    if(result.hasErrors()){
        for(ObjectError error: result.getAllErrors()){
    System.out.println("Error:" + error.getDefaultMessage());
    }
    return "vacantes/formVacante";
    }
    
        serviceVacantes.guardar(vacante);
        attributes.addFlashAttribute("msg","Registro Guardado");
        System.out.println(vacante);
        return "redirect:/vacantes/index";
    } 
    
    /*
    @PostMapping("/save")
    public String guardar(@RequestParam("nombre") String nombre,@RequestParam("descripcion") String descripcion,@RequestParam("estatus") String status,
                          @RequestParam("fecha") String fecha, @RequestParam("destacado") int destacado,
                          @RequestParam("salario") double salario,@RequestParam("detalles") String detalles ){
        System.out.println("Nombre: " + nombre);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Status: " + status);
        System.out.println("Fecha: " + fecha);
        System.out.println("Destacado: " + destacado);
        System.out.println("Salario: " + salario);
        System.out.println("Detalles: " + detalles);
        
    return "vacantes/listVacantes";
    }
    */
    @GetMapping("/delete")
    public String eliminar(@RequestParam("id") int idVacante, Model model){
    System.out.println("Borrando vacante con id: " + idVacante);
    model.addAttribute("id", idVacante);
            return "mensaje";
    }
    
    @GetMapping("/view/{id}")
    public String verDetalles(@PathVariable("id")int idVacante, Model model){
        
        Vacante vacante = serviceVacantes.buscarPorId (idVacante);
        
        System.out.print("idVacante: " + idVacante);
        model.addAttribute("vacante", vacante );
        return "detalle";
    }
    
    @InitBinder
    public void initbinder(WebDataBinder webDataBinder){
    
        SimpleDateFormat dateFormat = new SimpleDateFormat ("dd-mm-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor (dateFormat , false));
    }
    
}
