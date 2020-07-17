
package com.tienda.managedbeans;

import com.entidades.session.CargoFacadeLocal;
import com.tienda.entidades.Cargo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;


@Named(value = "cargoManagedBean")
@ViewScoped
public class CargoManagedBean implements Serializable, MnagedbeanInterface<Cargo> {
    //PASO 1
    @EJB
    private CargoFacadeLocal cargoFacadeLocal;
    
    //constructor
    public CargoManagedBean() {
    }
    //VARIABLE DE TIPO LISTACARGOS
    private List<Cargo> listaCargos;
    
    //OBJETO DE TIPO CARGO
    private Cargo cargo;
    
    //PASO 2
    @PostConstruct
    public void init(){
    //lista de los cargos que esta en la base de datos 
    listaCargos=cargoFacadeLocal.findAll();
    }

    @Override
    public void nuevo() {
        cargo = new Cargo();       
    }

    @Override
    public void grabar() {
        
        try {
            if (cargo.getCodigo() == null) {
                cargoFacadeLocal.create(cargo);
            } else {
                cargoFacadeLocal.edit(cargo);
            }

            cargo = null;
            listaCargos = cargoFacadeLocal.findAll();
            //LUEGO UN MENSAJE
            mostrarMensajeTry("Informacion Exitosa", FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            mostrarMensajeTry("Ocurrio un  Error", FacesMessage.SEVERITY_ERROR);
        }    
    }

    @Override
    public void seleccionar(Cargo c) {
        cargo = c;
       
    }

    @Override
    public void eliminar(Cargo c) {
        
        try {
            cargoFacadeLocal.remove(c);
            listaCargos = cargoFacadeLocal.findAll();
            mostrarMensajeTry("Informacion Exitosa", FacesMessage.SEVERITY_INFO);
            
        } catch (Exception e) {
            mostrarMensajeTry("Ocurrio un  Error", FacesMessage.SEVERITY_ERROR);
        }            
    }

    @Override
    public void cancelar() {
        cargo = null;       
    }

    public List<Cargo> getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<Cargo> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    
}
