package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Admin;
import com.elissir.proyecto.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // Aquí funciona el .save de JPA
    public Admin crearAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    // Aquí findAll encuentra todos los elementos, sería interesante buscar la query con la que se hace esto
    public List<Admin> listarAdmins(){
        return adminRepository.findAll();
    }

    public Admin obtenerAdminPorId(Long id){
        return adminRepository.findById(id).orElse(null);
    }


    // Recibo un objeto y con base a lo que hay ahí, simplemente actualizo el que ya existe
    public Admin actualizarAdmin(Long id, Admin admin){
        if (adminRepository.existsById(id)){
            // Busco a la admin en la base de datos con toda su info
            Admin modificada = adminRepository.findById(id).orElse(null);
            // La modifico y la devuelvo
            modificada.setNombre(admin.getNombre());
            modificada.setContrasenia(admin.getContrasenia());
            return adminRepository.save(modificada);
        }
        return null;
    }

    // Recibo un id y elimino el objeto que tenga ese id
    public void eliminarAdmin(Long id){
        adminRepository.deleteById(id);
    }
}
