package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.RolUsuario;
import co.edu.usbcali.airlinesapp.domain.Usuario;
import co.edu.usbcali.airlinesapp.dtos.UsuarioDTO;
import co.edu.usbcali.airlinesapp.repository.UsuarioRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.UsuarioService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UsuarioServiceImplTest {
    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void obtenerUsuariosOk() {
        RolUsuario rolUsuario = RolUsuario.builder()
                .idRolUsuario(1)
                .descripcion("Cliente")
                .estado("A")
                .build();

        Usuario.builder()
                .idUsuario(1)
                .rolUsuario(rolUsuario)
                .cedula("123456789")
                .nombre("Santiago")
                .apellido("García")
                .correo("santiagogarcia@gmail.com")
                .estado("A")
                .build();

        List<Usuario> usuarios = Arrays.asList(Usuario.builder()
                        .idUsuario(1)
                        .rolUsuario(rolUsuario)
                        .cedula("123456789")
                        .nombre("Santiago")
                        .apellido("García")
                        .correo("santiagogarcia@gmail.com")
                        .estado("A")
                        .build(),
                Usuario.builder()
                        .idUsuario(2)
                        .rolUsuario(rolUsuario)
                        .cedula("987654321")
                        .nombre("Juan")
                        .apellido("Pérez")
                        .correo("juanperez@gmail.com")
                        .estado("A")
                        .build());

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioDTO> usuariosDTO = usuarioService.obtenerUsuarios();

        assertEquals(2, usuariosDTO.size());
        assertEquals("123456789", usuariosDTO.get(0).getCedula());
    }

    @Test
    public void obtenerUsuariosNotOk() {
        List<Usuario> usuarios = Arrays.asList();

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioDTO> usuariosDTO = usuarioService.obtenerUsuarios();

        assertEquals(0, usuariosDTO.size());
    }

    @Test
    public void obtenerUsuarioPorIdOk() throws Exception {
        RolUsuario rolUsuario = RolUsuario.builder()
                .idRolUsuario(1)
                .descripcion("Cliente")
                .estado("A")
                .build();

        Usuario usuario = Usuario.builder()
                .idUsuario(1)
                .rolUsuario(rolUsuario)
                .cedula("123456789")
                .nombre("Santiago")
                .apellido("García")
                .correo("santiagogarcia@gmail.com")
                .estado("A")
                .build();

        Mockito.when(usuarioRepository.existsById(1)).thenReturn(true);
        Mockito.when(usuarioRepository.getReferenceById(1)).thenReturn(usuario);

        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(1);

        assertEquals(1, usuarioDTO.getIdUsuario());
    }

    @Test
    public void obtenerUsuarioPorIdNotOk() throws Exception {
        Mockito.when(usuarioRepository.existsById(1)).thenReturn(false);

        assertThrows(java.lang.Exception.class, () -> usuarioService.obtenerUsuarioPorId(1));
    }
}
