
package net.akira.service;

import java.util.LinkedList;
import java.util.List;
import net.akira.model.Categoria;
import org.springframework.stereotype.Service;

@Service
public class CategoriasServiceImpl implements ICategoriasService {
    
    private List<Categoria> lista = null;
    
    public CategoriasServiceImpl (){
    lista = new LinkedList<Categoria>();
    
    Categoria categoria1 = new Categoria();
    categoria1.setId(1);
    categoria1.setNombre("Ventas");
    categoria1.setDescripcion("Trabajos en sector Ventas");
    
    Categoria categoria2 = new Categoria();
    categoria2.setId(2);
    categoria2.setNombre("Contabilidad");
    categoria2.setDescripcion("Trabajos en sector Contabilidad");
    
    Categoria categoria3 = new Categoria();
    categoria3.setId(3);
    categoria3.setNombre("Comunicacion");
    categoria3.setDescripcion("Trabajos en sector Comunicacion");
    
    Categoria categoria4 = new Categoria();
    categoria4.setId(4);
    categoria4.setNombre("Arquitectura");
    categoria4.setDescripcion("Trabajos en sector Arquitectura");
    
    Categoria categoria5 = new Categoria();
    categoria5.setId(5);
    categoria5.setNombre("Educacion");
    categoria5.setDescripcion("Trabajps en Educacion");
    
    Categoria categoria6 = new Categoria();
    categoria6.setId(6);
    categoria6.setNombre("Desarrollo de software");
    categoria6.setDescripcion("trabajo para programadores");
    
    lista.add(categoria1);
    lista.add(categoria2);
    lista.add(categoria3);
    lista.add(categoria4);
    lista.add(categoria5);
    lista.add(categoria6);
  
    }

    @Override
    public void guardar(Categoria categoria) {
        lista.add(categoria);
        
    }

    @Override
    public List<Categoria> buscarTodas() {
        return lista;
    }

    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        
        for (Categoria c : lista)
            if(c.getId() == idCategoria){
        return c;
    }
        
        return null;
    }
    
    
    
}
