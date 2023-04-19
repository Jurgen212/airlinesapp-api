package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.TipoAsiento;
import co.edu.usbcali.airlinesapp.dtos.TipoAsientoDTO;
import co.edu.usbcali.airlinesapp.repository.TipoAsientoRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.TipoAsientoService;

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
public class TipoAsientoServiceImplTest {
    @Autowired
    private TipoAsientoService tipoAsientoService;

    @MockBean
    private TipoAsientoRepository tipoAsientoRepository;

    @Test
    public void obtenerTipoAsientosOk() {
        TipoAsiento.builder()
                .idTipoAsiento(1)
                .descripcion("Ejecutivo")
                .estado("A")
                .build();

        List<TipoAsiento> tipoAsientos = Arrays.asList(TipoAsiento.builder()
                        .idTipoAsiento(1)
                        .descripcion("Ejecutivo")
                        .estado("A")
                        .build(),
                TipoAsiento.builder()
                        .idTipoAsiento(2)
                        .descripcion("Económico")
                        .estado("A")
                        .build());

        Mockito.when(tipoAsientoRepository.findAll()).thenReturn(tipoAsientos);

        List<TipoAsientoDTO> tipoAsientosDTO = tipoAsientoService.obtenerTipoAsientos();

        assertEquals(2, tipoAsientosDTO.size());
        assertEquals("Ejecutivo", tipoAsientosDTO.get(0).getDescripcion());
    }

    @Test
    public void obtenerTipoAsientosNotOk() {
        List<TipoAsiento> tipoAsientos = Arrays.asList();

        Mockito.when(tipoAsientoRepository.findAll()).thenReturn(tipoAsientos);

        List<TipoAsientoDTO> tipoAsientosDTO = tipoAsientoService.obtenerTipoAsientos();

        assertEquals(0, tipoAsientosDTO.size());
    }

    @Test
    public void obtenerTipoAsientoPorIdOk() throws Exception {
        TipoAsiento tipoAsiento = TipoAsiento.builder()
                .idTipoAsiento(1)
                .descripcion("Ejecutivo")
                .estado("A")
                .build();

        Mockito.when(tipoAsientoRepository.existsById(1)).thenReturn(true);
        Mockito.when(tipoAsientoRepository.getReferenceById(1)).thenReturn(tipoAsiento);

        TipoAsientoDTO tipoAsientoDTO = tipoAsientoService.obtenerTipoAsientoPorId(1);

        assertEquals("Ejecutivo", tipoAsientoDTO.getDescripcion());
    }

    @Test
    public void obtenerTipoAsientoPorIdNotOk() {
        Mockito.when(tipoAsientoRepository.existsById(1)).thenReturn(false);

        assertThrows(Exception.class, () -> tipoAsientoService.obtenerTipoAsientoPorId(1));
    }
}
