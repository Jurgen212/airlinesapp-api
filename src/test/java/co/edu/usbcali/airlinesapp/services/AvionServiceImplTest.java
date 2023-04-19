package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.Avion;
import co.edu.usbcali.airlinesapp.dtos.AvionDTO;
import co.edu.usbcali.airlinesapp.repository.AvionRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.AvionService;

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
public class AvionServiceImplTest {
    @Autowired
    private AvionService avionService;

    @MockBean
    private AvionRepository avionRepository;

    @Test
    public void obtenerAvionesOk() {
        Avion.builder()
                .idAvion(1)
                .modelo("Boeing 737")
                .aerolinea("Avianca")
                .estado("A")
                .build();

        List<Avion> aviones = Arrays.asList(Avion.builder()
                        .idAvion(1)
                        .modelo("Boeing 737")
                        .aerolinea("Avianca")
                        .estado("A")
                        .build(),
                Avion.builder()
                        .idAvion(2)
                        .modelo("Boeing 747")
                        .aerolinea("Wingo")
                        .estado("A")
                        .build());

        Mockito.when(avionRepository.findAll()).thenReturn(aviones);

        List<AvionDTO> avionesDTO = avionService.obtenerAviones();

        assertEquals(2, avionesDTO.size());
        assertEquals("Boeing 737", aviones.get(0).getModelo());
    }

    @Test
    public void obtenerAvionesNotOk() {
        List<Avion> aviones = Arrays.asList();

        Mockito.when(avionRepository.findAll()).thenReturn(aviones);

        List<AvionDTO> avionesDTO = avionService.obtenerAviones();

        assertEquals(0, avionesDTO.size());
    }

    @Test
    public void obtenerAvionPorIdOk() throws Exception {
        Avion avion = Avion.builder()
                .idAvion(1)
                .modelo("Boeing 737-800")
                .aerolinea("Avianca")
                .estado("A")
                .build();

        Mockito.when(avionRepository.existsById(1)).thenReturn(true);
        Mockito.when(avionRepository.getReferenceById(1)).thenReturn(avion);

        AvionDTO avionDTO = avionService.obtenerAvionPorId(1);

        assertEquals(1, avionDTO.getIdAvion());
    }

    @Test
    public void obtenerAvionPorIdNotOk() {
        Mockito.when(avionRepository.existsById(1)).thenReturn(false);

        assertThrows(java.lang.Exception.class, () -> avionService.obtenerAvionPorId(1));
    }
}
