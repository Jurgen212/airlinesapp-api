package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.RolUsuario;
import co.edu.usbcali.airlinesapp.dtos.RolUsuarioDTO;
import co.edu.usbcali.airlinesapp.repository.RolUsuarioRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.RolUsuarioService;

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
public class RolUsuarioServiceImplTest {
    @Autowired
    private RolUsuarioService rolUsuarioService;

    @MockBean
    private RolUsuarioRepository rolUsuarioRepository;

    @Test
    public void obtenerRolUsuariosOk() {
        RolUsuario.builder()
                .idRolUsuario(1)
                .descripcion("Cliente")
                .estado("A")
                .build();

        List<RolUsuario> rolUsuarios = Arrays.asList(RolUsuario.builder()
                        .idRolUsuario(1)
                        .descripcion("Cliente")
                        .estado("A")
                        .build(),
                RolUsuario.builder()
                        .idRolUsuario(2)
                        .descripcion("Administrador")
                        .estado("A")
                        .build());

        Mockito.when(rolUsuarioRepository.findAll()).thenReturn(rolUsuarios);

        List<RolUsuarioDTO> rolUsuariosDTO = rolUsuarioService.obtenerRolUsuarios();

        assertEquals(2, rolUsuariosDTO.size());
        assertEquals("Cliente", rolUsuariosDTO.get(0).getDescripcion());
    }

    @Test
    public void obtenerRolUsuariosNotOk() {
        List<RolUsuario> rolUsuarios = Arrays.asList();

        Mockito.when(rolUsuarioRepository.findAll()).thenReturn(rolUsuarios);

        List<RolUsuarioDTO> rolUsuariosDTO = rolUsuarioService.obtenerRolUsuarios();

        assertEquals(0, rolUsuariosDTO.size());
    }

    @Test
    public void obtenerRolUsuarioPorIdOk() throws Exception {
        RolUsuario rolUsuario = RolUsuario.builder()
                .idRolUsuario(1)
                .descripcion("Cliente")
                .estado("A")
                .build();

        Mockito.when(rolUsuarioRepository.existsById(1)).thenReturn(true);
        Mockito.when(rolUsuarioRepository.getReferenceById(1)).thenReturn(rolUsuario);

        RolUsuarioDTO rolUsuarioDTO = rolUsuarioService.obtenerRolUsuarioPorId(1);

        assertEquals(1, rolUsuarioDTO.getIdRolUsuario());
    }

    @Test
    public void obtenerRolUsuarioPorIdNotOk() {
        Mockito.when(rolUsuarioRepository.existsById(1)).thenReturn(false);

        assertThrows(java.lang.Exception.class, () -> rolUsuarioService.obtenerRolUsuarioPorId(1));
    }
}
