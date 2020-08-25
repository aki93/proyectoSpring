
package net.akira.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import net.akira.model.Vacante;
import net.akira.service.IVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
@Controller
public class HomeController {
    
    @Autowired
    private IVacantesService serviceVacantes;
    
    @GetMapping("/tabla")
    public String mostrarTabla(Model model){
    List<Vacante> lista = serviceVacantes.buscarTodas();
    
    model.addAttribute("vacantes", lista);
    
    return "tabla";
    
    }
    
    @GetMapping("/detalle")
    public String mostarDetalle(Model model){
        Vacante vacante = new Vacante();
        vacante.setNombre("Ing.Sist");
        vacante.setDescripcion("Se solicita Ingeniero");
        vacante.setFecha(new Date());
        vacante.setSalario(9700.0);
        model.addAttribute("vacante", vacante);
        
        return "detalle";
    
    }
    
    @GetMapping("/listado")
    public String mostrarListado(Model model){
    List<String> lista = new LinkedList<String>();
    
    lista.add("Ing. Sistemas");
    lista.add("Aux. Contabilidad");
    lista.add("Vendedor");
    lista.add("Arquitecto");
    
    model.addAttribute("empleos", lista);
    return "listado";
    
    }
    
    @GetMapping("/")
    public String mostrarhome(Model model){
        /*
        model.addAttribute("mensaje", "Bienvenidos a Empleos App");
        model.addAttribute("fecha" , new Date());
        */
        List<Vacante> lista = serviceVacantes.buscarTodas();
    
        model.addAttribute("vacantes", lista);
        
    return "home";
}
    
    /* Borrado en leccion 39
    /Se reemplaza con la Inteface de Servicio
    /Metodos @Autowired (en Controlador) y @Service (en VacantesServiceImpl, Clase de Servicio)
    private List<Vacante> getVacantes(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
    List<Vacante> lista = new LinkedList<Vacante>();
    
    try{
    Vacante vacante1 = new Vacante();
    vacante1.setImagen("empresa1.png");
    vacante1.setId(1);
    vacante1.setNombre("Ingeniero Civil");
    vacante1.setDescripcion("Se solicita ing.civil");
    vacante1.setFecha(sdf.parse("08-02-2019"));
    vacante1.setSalario(8500);
    vacante1.setDestacado(1);
    
    Vacante vacante2 = new Vacante();
    vacante2.setImagen("empresa2.png");
    vacante2.setId(2);
    vacante2.setNombre("Ingeniero electrico");
    vacante2.setDescripcion("Se solicita ing.electrico");
    vacante2.setFecha(sdf.parse("09-02-2019"));
    vacante2.setSalario(9500);
    vacante2.setDestacado(0);
    
    Vacante vacante3 = new Vacante();
    vacante3.setId(3);
    vacante3.setNombre("Ingeniero industrial");
    vacante3.setDescripcion("Se solicita ing.industrial");
    vacante3.setFecha(sdf.parse("03-02-2019"));
    vacante3.setSalario(11500);
    vacante3.setDestacado(0);
    
    Vacante vacante4 = new Vacante();
    vacante4.setImagen("empresa4.png");
    vacante4.setId(4);
    vacante4.setNombre("Ingeniero agronomo");
    vacante4.setDescripcion("Se solicita ing.agronomo");
    vacante4.setFecha(sdf.parse("25-02-2019"));
    vacante4.setSalario(12500);
    vacante4.setDestacado(1);
    
    lista.add(vacante1);
    lista.add(vacante2);
    lista.add(vacante3);
    lista.add(vacante4);
    
    
    }catch (ParseException e){
    System.out.println("Error: " + e.getMessage());
    }
    return lista;
    
    }
    */
}
