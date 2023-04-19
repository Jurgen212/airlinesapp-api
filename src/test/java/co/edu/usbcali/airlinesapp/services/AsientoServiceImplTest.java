package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.Asiento;
import co.edu.usbcali.airlinesapp.domain.Avion;
import co.edu.usbcali.airlinesapp.domain.TipoAsiento;
import co.edu.usbcali.airlinesapp.dtos.AsientoDTO;
import co.edu.usbcali.airlinesapp.repository.AsientoRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.AsientoService;

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
public class AsientoServiceImplTest {
    @Autowired
    private AsientoService asientoService;

    @MockBean
    private AsientoRepository asientoRepository;

    @Test
    public void obtenerAsientosOk() {
        TipoAsiento tipoAsiento = TipoAsiento.builder()
                .idTipoAsiento(1)
                .descripcion("Ejecutivo")
                .estado("A")
                .build();

        Avion avion = Avion.builder()
                .idAvion(1)
                .modelo("Boeing 737")
                .aerolinea("Avianca")
                .estado("A")
                .build();

        Asiento.builder()
                .idAsiento(1)
                .tipoAsiento(tipoAsiento)
                .avion(avion)
                .ubicacion("A1")
                .estado("A")
                .build();

        List<Asiento> asientos = List.of(Asiento.builder()
                        .idAsiento(1)
                        .tipoAsiento(tipoAsiento)
                        .avion(avion)
                        .ubicacion("A1")
                        .estado("A")
                        .build(),
                Asiento.builder()
                        .idAsiento(2)
                        .tipoAsiento(tipoAsiento)
                        .avion(avion)
                        .ubicacion("A2")
                        .estado("A")
                        .build());

        Mockito.when(asientoRepository.findAll()).thenReturn(asientos);

        List<AsientoDTO> asientosDTO = asientoService.obtenerAsientos();

        assertEquals(2, asientosDTO.size());
        assertEquals("A1", asientosDTO.get(0).getUbicacion());
    }

    @Test
    public void obtenerAsientosNotOk() {
        List<Asiento> asientos = Arrays.asList();

        Mockito.when(asientoRepository.findAll()).thenReturn(asientos);

        List<AsientoDTO> asientosDTO = asientoService.obtenerAsientos();

        assertEquals(0, asientosDTO.size());
    }

    @Test
    public void obtenerAsientoPorIdOk() throws Exception {
        TipoAsiento tipoAsiento = TipoAsiento.builder()
                .idTipoAsiento(1)
                .descripcion("Ejecutivo")
                .estado("A")
                .build();

        Avion avion = Avion.builder()
                .idAvion(1)
                .modelo("Boeing 737")
                .aerolinea("Avianca")
                .estado("A")
                .build();

        Asiento asiento = Asiento.builder()
                .idAsiento(1)
                .tipoAsiento(tipoAsiento)
                .avion(avion)
                .ubicacion("A1")
                .estado("A")
                .build();

        Mockito.when(asientoRepository.existsById(1)).thenReturn(true);
        Mockito.when(asientoRepository.getReferenceById(1)).thenReturn(asiento);

        AsientoDTO asientoDTO = asientoService.obtenerAsientoPorId(1);

        assertEquals(1, asientoDTO.getIdAsiento());
    }

    @Test
    public void obtenerAsientoPorIdNotOk() {
        Mockito.when(asientoRepository.existsById(1)).thenReturn(false);

        assertThrows(java.lang.Exception.class, () -> asientoService.obtenerAsientoPorId(1));
    }
}
