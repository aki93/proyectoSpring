
package net.akira.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.akira.model.Vacante;
import net.akira.service.ICategoriasService;
import net.akira.service.IVacantesService;
import net.akira.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
    
    @Value("${empleosapp.ruta.imagenes}")
    private String ruta;
    
    @Autowired
    private IVacantesService serviceVacantes;
    
    @Autowired
    private ICategoriasService serviceCategorias;
    
    
    @GetMapping("/index")
    public String mostrarIndex (Model model){
    
        //1 obtener todas las vacantes (recuperadas con clase de servicio
        List<Vacante> lista = serviceVacantes.buscarTodas();
       //2 agragar al modelo (vista) el listado de vacantes
        model.addAttribute("vacantes" , lista);
        return "/vacantes/listVacantes";
    } 
    
    @GetMapping("/create")
    public String crear(Vacante vacante, Model model){
        return "vacantes/formVacante";
    }
    
    
    @PostMapping("/save")
    public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes,
                          @RequestParam ("archivoImagen")  MultipartFile multiPart ){
    if(result.hasErrors()){
        for(ObjectError error: result.getAllErrors()){
    System.out.println("Error:" + error.getDefaultMessage());
    }
    return "vacantes/formVacante";
    }
    
        if (!multiPart.isEmpty()) {
//String ruta = "/empleos/img-vacantes/"; // Linux/MAC

            //linea comentada porque se agrego @Value al ppio del controlador
            //String ruta = "c:/empleos/img-vacantes/"; // Windows
            String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
            if (nombreImagen != null) { // La imagen si se subio
// Procesamos la variable nombreImagen
                vacante.setImagen(nombreImagen);
            }
        }

        serviceVacantes.guardar(vacante);
        attributes.addFlashAttribute("msg","Registro Guardado");
        System.out.println(vacante);
        return "redirect:/vacantes/indexPage";
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
    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") Integer paco, Model model, RedirectAttributes attributes){

        System.out.println("Borrando vacante con id: " + paco);
        serviceVacantes.borrarVacante(paco);
            return "redirect:/vacantes/indexPage";
        }
    
    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id")int idVacante,Model model){
        Vacante vacante = serviceVacantes.buscarPorId(idVacante);
        model.addAttribute("vacante", vacante);
        return "vacantes/formVacante";
    }
    
    
    @GetMapping("/view/{id}")
    public String verDetalles(@PathVariable("id")int idVacante, Model model){
        
        Vacante vacante = serviceVacantes.buscarPorId (idVacante);
        
        System.out.print("idVacante: " + idVacante);
        model.addAttribute("vacante", vacante );
        return "detalle";
    }
    
    @GetMapping("/indexPage")
    public String mostrarIndexPaginado(Model model, Pageable page){
        Page<Vacante> lista = serviceVacantes.buscarTodasPage(page);
        model.addAttribute("vacantes", lista);
        return "/vacantes/listVacantes";
    }
    
    @ModelAttribute
    public void setGenericos(Model model){
    model.addAttribute("categorias", serviceCategorias.buscarTodas() );
    }
    
    @InitBinder
    public void initbinder(WebDataBinder webDataBinder){
    
        SimpleDateFormat dateFormat = new SimpleDateFormat ("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor (dateFormat , false));
    }
    
}
