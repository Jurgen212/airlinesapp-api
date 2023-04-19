package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.RolUsuarioDTO;

import java.util.List;

public interface RolUsuarioService {
    RolUsuarioDTO guardarRolUsuario(RolUsuarioDTO rolUsuarioDTO) throws Exception;
    List<RolUsuarioDTO> obtenerRolUsuarios();
    List<RolUsuarioDTO> obtenerRolUsuariosActivos();
    RolUsuarioDTO obtenerRolUsuarioPorId(Integer id) throws Exception;
    RolUsuarioDTO actualizarRolUsuario(RolUsuarioDTO rolUsuarioDTO) throws Exception;
    RolUsuarioDTO eliminarRolUsuario(Integer id) throws Exception;
}
