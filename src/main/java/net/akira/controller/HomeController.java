package net.akira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.akira.model.Categoria;
import net.akira.model.Perfil;
import net.akira.model.Usuario;
import net.akira.model.Vacante;
import net.akira.service.ICategoriasService;
import net.akira.service.IUsuariosService;
import net.akira.service.IVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private ICategoriasService serviceCategorias;

    @Autowired
    private IVacantesService serviceVacantes;

    @Autowired
    private IUsuariosService serviceUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/tabla")
    public String mostrarTabla(Model model) {
        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes", lista);

        return "tabla";

    }

    @GetMapping("/detalle")
    public String mostarDetalle(Model model) {
        Vacante vacante = new Vacante();
        vacante.setNombre("Ing.Sist");
        vacante.setDescripcion("Se solicita Ingeniero");
        vacante.setFecha(new Date());
        vacante.setSalario(9700.0);
        Categoria categoria = new Categoria();
        vacante.setCategoria(categoria);
        model.addAttribute("vacante", vacante);

        return "detalle";

    }

    @GetMapping("/listado")
    public String mostrarListado(Model model) {
        List<String> lista = new LinkedList<String>();

        lista.add("Ing. Sistemas");
        lista.add("Aux. Contabilidad");
        lista.add("Vendedor");
        lista.add("Arquitecto");

        model.addAttribute("empleos", lista);
        return "listado";

    }

    @GetMapping("/")
    public String mostrarhome(Model model) {
        /*
        model.addAttribute("mensaje", "Bienvenidos a Empleos App");
        model.addAttribute("fecha" , new Date());
         */

        //reemplazado por metodo setGenericos (serviceVacantes.buscarDestacadas)
        //List<Vacante> lista = serviceVacantes.buscarTodas();
        //model.addAttribute("vacantes", lista);
        return "home";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "formLogin";
    }

    @GetMapping("/logout")
    public String cerrarSession(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler
                = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login";

    }

    @GetMapping("/index")
    public String mostrarIndex(Authentication auth, HttpSession session) {
        String username = auth.getName();
        System.out.println("Nombre de usuario: " + username);

        for (GrantedAuthority rol : auth.getAuthorities()) {
            System.out.println("ROL: " + rol.getAuthority());
        }

        if (session.getAttribute("usuario") == null) {
            Usuario usuario = serviceUsuario.buscarPorUsername(username);
            usuario.setPassword(null);
            System.out.println("usuario: " + usuario);
            session.setAttribute("usuario", usuario);
        }
        return "redirect:/";

    }

    //Metodos USUARIOS
    @GetMapping("/signup")
    public String registrarse(Usuario usuario) {
        
        return "usuarios/formRegistro";
    }

    @PostMapping("/signup")
    public String guardarUsuario(Usuario usuario, BindingResult result, RedirectAttributes attributes, Date fechaRegistro) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Error:" + error.getDefaultMessage());
            }
            return "usuarios/formRegistro";
        }
        String pwdPlano = usuario.getPassword();
        String pwdEncriptado = passwordEncoder.encode(pwdPlano);
        usuario.setPassword(pwdEncriptado);
        Perfil per1 = new Perfil();
        per1.setId(4);

        usuario.setEstatus(1);
        usuario.agregar(per1);
        usuario.setFechaRegistro(new Date());
        serviceUsuario.guardar(usuario);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        System.out.println(usuario);
        return "redirect:/";

    }

    //Metodo search para formulario de busqueda de ofertas de trabajo
    @GetMapping("/search")
    //parametro para el databinding del objeto (search)
    public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {

        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Vacante> example = Example.of(vacante, matcher);
        List<Vacante> lista = serviceVacantes.buscarByExample(example);
        model.addAttribute("vacantes", lista);
        System.out.println("Buscando por: " + vacante);
        return "home";
    }

    /*
    utileria para encriptar passwords con el algoritmo BCprypt en el controlador
    sireve para encriptar passwords que ya estan dentro de la DB como planos.
     */
    @GetMapping("/bcrypt/{texto}")
    //al hace la peticion al metodo se regresara el texto
    @ResponseBody
    public String encriptar(@PathVariable("texto") String texto) {
        return texto + "Encriptado en BCrypt: " + passwordEncoder.encode(texto);

    }

    /*
    *InitBinder para Strings si los detecta vacios en el DataBinding los setea a "null"
    @Param Binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public void setGenericos(Model model) {
        //objeto tipo Vacante para databinding del formulario de "Busqueda"    
        Vacante vacanteSearch = new Vacante();
        vacanteSearch.reset();
        model.addAttribute("search", vacanteSearch);
        model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
        model.addAttribute("categorias", serviceCategorias.buscarTodas());
    }

}
