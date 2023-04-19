package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.Aeropuerto;
import co.edu.usbcali.airlinesapp.domain.Vuelo;
import co.edu.usbcali.airlinesapp.dtos.VueloDTO;
import co.edu.usbcali.airlinesapp.repository.VueloRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.VueloService;

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
public class VueloServiceImplTest {
    @Autowired
    private VueloService vueloService;

    @MockBean
    private VueloRepository vueloRepository;

    @Test
    public void obtenerVuelosOk() {
        Aeropuerto aeropuerto = Aeropuerto.builder()
                .idAeropuerto(1)
                .nombre("Aeropuerto Internacional El Dorado")
                .iata("BOG")
                .ubicacion("Bogotá")
                .estado("A")
                .build();

        Vuelo.builder()
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

        List<Vuelo> vuelos = Arrays.asList(Vuelo.builder()
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
                        .build(),
                Vuelo.builder()
                        .idVuelo(2)
                        .aeropuertoOrigen(aeropuerto)
                        .aeropuertoDestino(aeropuerto)
                        .precio(150000)
                        .horaSalida(new Date())
                        .horaLlegada(new Date())
                        .precioAsientoVip(80000)
                        .precioAsientoNormal(50000)
                        .precioAsientoBasico(30000)
                        .estado("A")
                        .build());

        Mockito.when(vueloRepository.findAll()).thenReturn(vuelos);

        List<VueloDTO> vuelosDTO = vueloService.obtenerVuelos();

        assertEquals(2, vuelosDTO.size());
        assertEquals(100000, vuelosDTO.get(0).getPrecio());
    }

    @Test
    public void obtenerVuelosNotOk() {
        List<Vuelo> vuelos = Arrays.asList();

        Mockito.when(vueloRepository.findAll()).thenReturn(vuelos);

        List<VueloDTO> vuelosDTO = vueloService.obtenerVuelos();

        assertEquals(0, vuelosDTO.size());
    }

    @Test
    public void obtenerVueloPorIdOk() throws Exception {
        Aeropuerto aeropuerto = Aeropuerto.builder()
                .idAeropuerto(1)
                .nombre("Aeropuerto Internacional El Dorado")
                .iata("BOG")
                .ubicacion("Bogotá")
                .estado("A")
                .build();

        Vuelo vuelo = Vuelo.builder()
                .idVuelo(1)
                .aeropuertoOrigen(aeropuerto)
                .aeropuertoDestino(aeropuerto)
                .precio(1000000)
                .horaSalida(new Date())
                .horaLlegada(new Date())
                .precioAsientoVip(50000)
                .precioAsientoNormal(30000)
                .precioAsientoBasico(10000)
                .estado("A")
                .build();

        Mockito.when(vueloRepository.existsById(1)).thenReturn(true);
        Mockito.when(vueloRepository.getReferenceById(1)).thenReturn(vuelo);

        VueloDTO vueloDTO = vueloService.obtenerVueloPorId(1);

        assertEquals(1, vueloDTO.getIdVuelo());
    }

    @Test
    public void obtenerVueloPorIdNotOk() {
        Mockito.when(vueloRepository.existsById(1)).thenReturn(false);

        assertThrows(java.lang.Exception.class, () -> vueloService.obtenerVueloPorId(1));
    }
}
