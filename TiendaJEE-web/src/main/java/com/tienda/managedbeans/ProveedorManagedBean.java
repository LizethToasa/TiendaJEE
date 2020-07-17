
package com.tienda.managedbeans;

import com.entidades.session.CargoFacadeLocal;
import com.tienda.entidades.Cargo;
import com.tienda.entidades.Proveedor;
import com.tienda.entidades.ProveedorFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "proveedorManagedBean")
@ViewScoped
public class ProveedorManagedBean implements Serializable, MnagedbeanInterface<Proveedor> {
    //PASO 1
    @EJB
    private ProveedorFacadeLocal proveedorFacadeLocal;
    
    
    //VARIABLE DE TIPO LISTACARGOS
    private List<Proveedor> listaProveedor;
    
    //OBJETO DE TIPO CARGO
    private Proveedor proveedor;
    
    //PASO 2
    @PostConstruct
    public void init(){
    //lista de los cargos que esta en la base de datos 
    listaProveedor=proveedorFacadeLocal.findAll();
    }
    
    //constructor
    public ProveedorManagedBean() {
    }

    @Override
    public void nuevo() {
        proveedor = new Proveedor();      
    }

    @Override
    public void grabar() {
        try {
            if (proveedor.getCodigo() == null) {
                proveedorFacadeLocal.create(proveedor);
            } else {
                proveedorFacadeLocal.edit(proveedor);
            }
            proveedor = null;
            listaProveedor = proveedorFacadeLocal.findAll();
            //LUEGO UN MENSAJE
            mostrarMensajeTry("Informacion Exitosa", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeTry("Ocurrio un  Error", FacesMessage.SEVERITY_ERROR);
        }       
    }

    @Override
    public void seleccionar(Proveedor p) {
        proveedor = p;       
    }

    @Override
    public void eliminar(Proveedor p) {
        try {
            proveedorFacadeLocal.remove(p);
            listaProveedor = proveedorFacadeLocal.findAll();
            mostrarMensajeTry("Informacion Exitosa", FacesMessage.SEVERITY_INFO);           
        } catch (Exception e) {
            mostrarMensajeTry("Ocurrio un  Error", FacesMessage.SEVERITY_ERROR);
        }        
    }

    @Override
    public void cancelar() {
        proveedor = null;
        
    }

    public List<Proveedor> getListaProveedor() {
        return listaProveedor;
    }

    public void setListaProveedor(List<Proveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ProveedorFacadeLocal getProveedorFacadeLocal() {
        return proveedorFacadeLocal;
    }

    public void setProveedorFacadeLocal(ProveedorFacadeLocal proveedorFacadeLocal) {
        this.proveedorFacadeLocal = proveedorFacadeLocal;
    }
     
}
