package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.Aeropuerto;
import co.edu.usbcali.airlinesapp.domain.Avion;
import co.edu.usbcali.airlinesapp.domain.Trayecto;
import co.edu.usbcali.airlinesapp.domain.Vuelo;
import co.edu.usbcali.airlinesapp.dtos.TrayectoDTO;
import co.edu.usbcali.airlinesapp.repository.TrayectoRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.TrayectoService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TrayectoServiceImplTest {
    @Autowired
    private TrayectoService trayectoService;

    @MockBean
    private TrayectoRepository trayectoRepository;

    @Test
    public void obtenerTrayectosOk() {
        Aeropuerto aeropuerto = Aeropuerto.builder()
                .idAeropuerto(1)
                .nombre("Aeropuerto Internacional El Dorado")
                .iata("BOG")
                .ubicacion("Bogotá")
                .estado("A")
                .build();

        Avion avion = Avion.builder()
                .idAvion(1)
                .modelo("Boeing 737")
                .aerolinea("Avianca")
                .estado("A")
                .build();

        Vuelo vuelo = Vuelo.builder()
                .idVuelo(1)
                .aeropuertoOrigen(aeropuerto)
                .aeropuertoDestino(aeropuerto)
                .precio(100000)
                .horaSalida(new Date())
                .horaLlegada(new Date())
                .precioAsientoVip(50000)
                .precioAsientoNormal(30000)
                .precioAsientoBasico(10000)
                .estado("A")
                .build();

        Trayecto.builder()
                .idTrayecto(1)
                .avion(avion)
                .aeropuertoOrigen(aeropuerto)
                .aeropuertoDestino(aeropuerto)
                .horaSalida(new Date())
                .horaLlegada(new Date())
                .vuelo(vuelo)
                .estado("A")
                .build();

        List<Trayecto> trayectos = Arrays.asList(Trayecto.builder()
                        .idTrayecto(1)
                        .avion(avion)
                        .aeropuertoOrigen(aeropuerto)
                        .aeropuertoDestino(aeropuerto)
                        .horaSalida(new Date())
                        .horaLlegada(new Date())
                        .vuelo(vuelo)
                        .estado("A")
                        .build(),
                Trayecto.builder()
                        .idTrayecto(2)
                        .avion(avion)
                        .aeropuertoOrigen(aeropuerto)
                        .aeropuertoDestino(aeropuerto)
                        .horaSalida(new Date())
                        .horaLlegada(new Date())
                        .vuelo(vuelo)
                        .estado("A")
                        .build());

        Mockito.when(trayectoRepository.findAll()).thenReturn(trayectos);

        List<TrayectoDTO> trayectosDTO = trayectoService.obtenerTrayectos();

        assertEquals(2, trayectosDTO.size());
        assertEquals(1, trayectosDTO.get(0).getIdTrayecto());
    }

    @Test
    public void obtenerTrayectosNotOk() {
        List<Trayecto> trayectos = Arrays.asList();

        Mockito.when(trayectoRepository.findAll()).thenReturn(trayectos);

        List<TrayectoDTO> trayectosDTO = trayectoService.obtenerTrayectos();

        assertEquals(0, trayectosDTO.size());
    }

    @Test
    public void obtenerTrayectosPorIdOk() throws Exception {
        Aeropuerto aeropuerto = Aeropuerto.builder()
                .idAeropuerto(1)
                .nombre("Aeropuerto Internacional El Dorado")
                .iata("BOG")
                .ubicacion("Bogotá")
                .estado("A")
                .build();

        Avion avion = Avion.builder()
                .idAvion(1)
                .modelo("Boeing 737")
                .aerolinea("Avianca")
                .estado("A")
                .build();

        Vuelo vuelo = Vuelo.builder()
                .idVuelo(1)
                .aeropuertoOrigen(aeropuerto)
                .aeropuertoDestino(aeropuerto)
                .precio(100000)
                .horaSalida(new Date())
                .horaLlegada(new Date())
                .precioAsientoVip(50000)
                .precioAsientoNormal(30000)
                .precioAsientoBasico(10000)
                .estado("A")
                .build();

        Trayecto trayecto = Trayecto.builder()
                .idTrayecto(1)
                .avion(avion)
                .aeropuertoOrigen(aeropuerto)
                .aeropuertoDestino(aeropuerto)
                .horaSalida(new Date())
                .horaLlegada(new Date())
                .vuelo(vuelo)
                .estado("A")
                .build();

        Mockito.when(trayectoRepository.existsById(1)).thenReturn(true);
        Mockito.when(trayectoRepository.getReferenceById(1)).thenReturn(trayecto);

        TrayectoDTO trayectoDTO = trayectoService.obtenerTrayectoPorId(1);

        assertEquals(1, trayectoDTO.getIdTrayecto());
    }

    @Test
    public void obtenerTrayectosPorIdNotOk() {
        Mockito.when(trayectoRepository.existsById(1)).thenReturn(false);

        assertThrows(java.lang.Exception.class, () -> trayectoService.obtenerTrayectoPorId(1));
    }
}
