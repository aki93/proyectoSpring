package net.akira.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import net.akira.model.Solicitud;
import net.akira.model.Usuario;
import net.akira.model.Vacante;
import net.akira.service.IUsuariosService;
import net.akira.service.IVacantesService;
import net.akira.service.db.SolicitudesServiceJpa;
import net.akira.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
    
    @Autowired
    private SolicitudesServiceJpa serviceSolicitudes;
    
    @Autowired
	private IVacantesService serviceVacantes;
    
    @Autowired
   	private IUsuariosService serviceUsuarios;
    

    /**
     * EJERCICIO: Declarar esta propiedad en el archivo application.properties.
     * El valor sera el directorio en donde se guardarán los archivos de los
     * Curriculums Vitaes de los usuarios.
     */
    @Value("${empleosapp.ruta.cv}")
    private String ruta;

    /**
     * Metodo que muestra la lista de solicitudes sin paginacion Seguridad: Solo
     * disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
     *
     * @return
     */
    @GetMapping("/index")
    public String mostrarIndex(Model model) {
        List<Solicitud> lista = serviceSolicitudes.buscarTodas();
        model.addAttribute("solicitudes",lista);
        // EJERCICIO
        return "solicitudes/listSolicitudes";

    }

    /**
     * Metodo que muestra la lista de solicitudes con paginacion Seguridad: Solo
     * disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
     *
     * @return
     */
    @GetMapping("/indexPaginate")
    public String mostrarIndexPaginado() {

        // EJERCICIO
        return "solicitudes/listSolicitudes";

    }

    /**
     * Método para renderizar el formulario para aplicar para una Vacante
     * Seguridad: Solo disponible para un usuario con perfil USUARIO.
     *
     * @return
     */
    @GetMapping("/create/{id}")
    public String crear(@PathVariable("id") Integer idvacante , Model model) {

        // EJERCICIO
        Vacante solicitud = serviceVacantes.buscarPorId(idvacante);
        model.addAttribute("solicitud", solicitud);
        return "solicitudes/formSolicitud";

    }

    /**
     * Método que guarda la solicitud enviada por el usuario en la base de datos
     * Seguridad: Solo disponible para un usuario con perfil USUARIO.
     *
     * @return
     */
    @PostMapping("/save")
    public String guardar(Solicitud solicitud, BindingResult result, RedirectAttributes attributes,HttpSession session,
            @RequestParam ("archivoCV") MultipartFile multiPart,Model model, Authentication authentication ){
        
        // Recuperamos el username que inicio sesión
	String username = authentication.getName();
        
        if(result.hasErrors()){
        for(ObjectError error: result.getAllErrors()){
    System.out.println("Error:" + error.getDefaultMessage());
    }
        return "solicitudes/formSolicitudes";
        }
       if (!multiPart.isEmpty()) {

            String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
            if (nombreArchivo != null) { // La imagen si se subio

                solicitud.setArchivo(nombreArchivo);
            }
        }
       Usuario usuario = serviceUsuarios.buscarPorUsername(username);
       
       ;
       solicitud.setFecha(new Date());
       solicitud.setUsuario(usuario);
       serviceSolicitudes.guardar(solicitud);
       attributes.addFlashAttribute("msg","Registro Guardado");
        // EJERCICIO
        return "redirect:/";

    }

    /**
     * Método para eliminar una solicitud Seguridad: Solo disponible para
     * usuarios con perfil ADMINISTRADOR/SUPERVISOR.
     *
     * @return
     */
    @GetMapping("/delete/{id}")
    public String eliminar() {

        // EJERCICIO
        return "redirect:/solicitudes/indexPaginate";

    }

    /**
     * Personalizamos el Data Binding para todas las propiedades de tipo Date
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
