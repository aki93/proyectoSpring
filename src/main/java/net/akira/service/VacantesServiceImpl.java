/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.akira.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import net.akira.model.Vacante;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nikkai
 */
@Service
public class VacantesServiceImpl implements IVacantesService{
    
    private List<Vacante> lista = null;
    //constructor, no devuelve datos, return 
    public VacantesServiceImpl(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
    lista = new LinkedList<Vacante>();
    
    try{
    Vacante vacante1 = new Vacante();
    vacante1.setImagen("empresa1.png");
    vacante1.setId(1);
    vacante1.setNombre("Ingeniero Civil");
    vacante1.setDescripcion("Se solicita ing.civil");
    vacante1.setFecha(sdf.parse("08-02-2019"));
    //vacante1.setSalario(10500);
    vacante1.setDestacado(1);
    
    Vacante vacante2 = new Vacante();
    vacante2.setImagen("empresa2.png");
    vacante2.setId(2);
    vacante2.setNombre("Ingeniero electrico");
    vacante2.setDescripcion("Se solicita ing.electrico");
    vacante2.setFecha(sdf.parse("09-02-2019"));
    //vacante2.setSalario(9500);
    vacante2.setDestacado(0);
    
    Vacante vacante3 = new Vacante();
    vacante3.setId(3);
    vacante3.setNombre("Ingeniero industrial");
    vacante3.setDescripcion("Se solicita ing.industrial");
    vacante3.setFecha(sdf.parse("03-02-2019"));
    //vacante3.setSalario(11500);
    vacante3.setDestacado(0);
    
    Vacante vacante4 = new Vacante();
    vacante4.setImagen("empresa4.png");
    vacante4.setId(4);
    vacante4.setNombre("Ingeniero agronomo");
    vacante4.setDescripcion("Se solicita ing.agronomo");
    vacante4.setFecha(sdf.parse("25-02-2019"));
    //vacante4.setSalario(12500);
    vacante4.setDestacado(1);
    
    lista.add(vacante1);
    lista.add(vacante2);
    lista.add(vacante3);
    lista.add(vacante4);
    
    
    }catch (ParseException e){
    System.out.println("Error: " + e.getMessage());
    }
    
    }

    @Override
    public List<Vacante> buscarTodas() {
        
    return lista;
    }

    @Override
    public Vacante buscarPorId(Integer idVacante) {
       for (Vacante v : lista)
           if (v.getId()== idVacante){
           return v;
           }
       return null;
    }

    @Override
    public void guardar(Vacante vacante) {
    lista.add(vacante);
    }

    @Override
    public List<Vacante> buscarDestacadas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarVacante(Integer idVacante) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vacante> buscarByExample(Example<Vacante> example) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Vacante> buscarTodasPage(Pageable page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
