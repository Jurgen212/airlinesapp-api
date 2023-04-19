package co.edu.usbcali.airlinesapp.services.implementation;

import co.edu.usbcali.airlinesapp.domain.Usuario;
import co.edu.usbcali.airlinesapp.dtos.UsuarioDTO;
import co.edu.usbcali.airlinesapp.mappers.RolUsuarioMapper;
import co.edu.usbcali.airlinesapp.mappers.UsuarioMapper;
import co.edu.usbcali.airlinesapp.repository.UsuarioRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.RolUsuarioService;
import co.edu.usbcali.airlinesapp.services.interfaces.UsuarioService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolUsuarioService rolUsuarioService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolUsuarioService rolUsuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.rolUsuarioService = rolUsuarioService;
    }

    public void validarUsuarioDTO(UsuarioDTO usuarioDTO) throws Exception {
        if (usuarioDTO == null) {
            throw new Exception("El usuario no puede ser nulo");
        } if (usuarioDTO.getIdRolUsuario() == null || usuarioDTO.getIdRolUsuario() <= 0) {
            throw new Exception("El id del rol del usuario no puede ser nulo o menor o igual a cero");
        } if (usuarioDTO.getCedula() == null || usuarioDTO.getCedula().isBlank() || usuarioDTO.getNombre().trim().isEmpty()) {
            throw new Exception("La cédula del usuario no puede ser nula o vacía");
        } if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().isBlank() || usuarioDTO.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del usuario no puede ser nulo o vacío");
        } if (usuarioDTO.getApellido() == null || usuarioDTO.getApellido().isBlank() || usuarioDTO.getApellido().trim().isEmpty()) {
            throw new Exception("El apellido del usuario no puede ser nulo o vacío");
        } if (usuarioDTO.getCorreo() == null || usuarioDTO.getCorreo().isBlank() || usuarioDTO.getCorreo().trim().isEmpty()) {
            throw new Exception("El correo del usuario no puede ser nulo o vacío");
        } if (usuarioDTO.getEstado() == null || usuarioDTO.getEstado().isBlank() || usuarioDTO.getEstado().trim().isEmpty()) {
            throw new Exception("El estado del usuario no puede ser nulo o vacío");
        }
    }

    @Override
    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        validarUsuarioDTO(usuarioDTO);

        Usuario usuario = UsuarioMapper.dtoToDomain(usuarioDTO);

        usuario.setRolUsuario(RolUsuarioMapper.dtoToDomain(rolUsuarioService.obtenerRolUsuarioPorId(usuarioDTO.getIdRolUsuario())));

        return UsuarioMapper.domainToDTO(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios() {
        return UsuarioMapper.domainToDTOList(usuarioRepository.findAll());
    }

    @Override
    public List<UsuarioDTO> obtenerUsuariosActivos() {
        return UsuarioMapper.domainToDTOList(usuarioRepository.findAllByEstado("A"));
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(Integer id) throws Exception {
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("El usuario con id " + id + " no existe");
        }

        return UsuarioMapper.domainToDTO(usuarioRepository.getReferenceById(id));
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorCedula(String cedula) throws Exception {
        if (!usuarioRepository.existsByCedula(cedula)) {
            return null;
        }

        return UsuarioMapper.domainToDTO(usuarioRepository.findByCedula(cedula));
    }

    @Override
    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        validarUsuarioDTO(usuarioDTO);

        UsuarioDTO usuarioSavedDTO = obtenerUsuarioPorId(usuarioDTO.getIdUsuario());

        usuarioSavedDTO.setCedula(usuarioDTO.getCedula());
        usuarioSavedDTO.setNombre(usuarioDTO.getNombre());
        usuarioSavedDTO.setApellido(usuarioDTO.getApellido());
        usuarioSavedDTO.setCorreo(usuarioDTO.getCorreo());
        usuarioSavedDTO.setEstado(usuarioDTO.getEstado());

        return guardarUsuario(usuarioSavedDTO);
    }

    @Override
    public UsuarioDTO eliminarUsuario(Integer id) throws Exception {
        UsuarioDTO usuarioSavedDTO = obtenerUsuarioPorId(id);

        if (usuarioSavedDTO == null) {
            throw new Exception("El usuario no existe");
        }

        usuarioSavedDTO.setEstado("I");

        return guardarUsuario(usuarioSavedDTO);
    }
}
