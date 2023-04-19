package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.*;
import co.edu.usbcali.airlinesapp.dtos.ReservaDTO;
import co.edu.usbcali.airlinesapp.repository.ReservaRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.ReservaService;

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
public class ReservaServiceImplTest {
    @Autowired
    private ReservaService reservaService;

    @MockBean
    private ReservaRepository reservaRepository;

    @Test
    public void obtenerReservasOk() {
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
                .precio(100000)
                .horaSalida(new Date())
                .horaLlegada(new Date())
                .precioAsientoVip(50000)
                .precioAsientoNormal(30000)
                .precioAsientoBasico(10000)
                .estado("A")
                .build();

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

        Reserva.builder()
                .idReserva(1)
                .vuelo(vuelo)
                .asiento(asiento)
                .usuario(usuario)
                .precioTotal(100000)
                .estadoPago("A")
                .fecha(new Date())
                .estado("A")
                .build();

        List<Reserva> reservas = Arrays.asList(Reserva.builder()
                        .idReserva(1)
                        .vuelo(vuelo)
                        .asiento(asiento)
                        .usuario(usuario)
                        .precioTotal(100000)
                        .estadoPago("A")
                        .fecha(new Date())
                        .estado("A")
                        .build(),
                Reserva.builder()
                        .idReserva(2)
                        .vuelo(vuelo)
                        .asiento(asiento)
                        .usuario(usuario)
                        .precioTotal(150000)
                        .estadoPago("A")
                        .fecha(new Date())
                        .estado("A")
                        .build());

        Mockito.when(reservaRepository.findAll()).thenReturn(reservas);

        List<ReservaDTO> reservasDTO = reservaService.obtenerReservas();

        assertEquals(2, reservasDTO.size());
        assertEquals(100000, reservasDTO.get(0).getPrecioTotal());
    }

    @Test
    public void obtenerReservasNotOk() {
        List<Reserva> reservas = Arrays.asList();

        Mockito.when(reservaRepository.findAll()).thenReturn(reservas);

        List<ReservaDTO> reservasDTO = reservaService.obtenerReservas();

        assertEquals(0, reservasDTO.size());
    }

    @Test
    public void obtenerReservaPorIdOk() throws Exception {
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
                .precio(100000)
                .horaSalida(new Date())
                .horaLlegada(new Date())
                .precioAsientoVip(50000)
                .precioAsientoNormal(30000)
                .precioAsientoBasico(10000)
                .estado("A")
                .build();

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

        Reserva reserva = Reserva.builder()
                .idReserva(1)
                .vuelo(vuelo)
                .asiento(asiento)
                .usuario(usuario)
                .precioTotal(100000)
                .estadoPago("A")
                .fecha(new Date())
                .estado("A")
                .build();

        Mockito.when(reservaRepository.existsById(1)).thenReturn(true);
        Mockito.when(reservaRepository.getReferenceById(1)).thenReturn(reserva);

        ReservaDTO reservaDTO = reservaService.obtenerReservaPorId(1);

        assertEquals(1, reservaDTO.getIdUsuario());
    }

    @Test
    public void obtenerReservaPorIdNotOk() {
        Mockito.when(reservaRepository.existsById(1)).thenReturn(false);

        assertThrows(java.lang.Exception.class, () -> reservaService.obtenerReservaPorId(1));
    }
}
