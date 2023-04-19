package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.*;
import co.edu.usbcali.airlinesapp.dtos.FacturaDTO;
import co.edu.usbcali.airlinesapp.repository.FacturaRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.FacturaService;

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
public class FacturaServiceImplTest {
    @Autowired
    private FacturaService facturaService;

    @MockBean
    private FacturaRepository facturaRepository;

    @Test
    public void obtenerFacturasOk() {
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

        Factura.builder()
                .idFactura(1)
                .reserva(reserva)
                .fecha(new Date())
                .estado("A")
                .build();

        List<Factura> facturas = Arrays.asList(Factura.builder()
                        .idFactura(1)
                        .reserva(reserva)
                        .fecha(new Date())
                        .estado("A")
                        .build(),
                Factura.builder()
                        .idFactura(2)
                        .reserva(reserva)
                        .fecha(new Date())
                        .estado("A")
                        .build());

        Mockito.when(facturaRepository.findAll()).thenReturn(facturas);

        List<FacturaDTO> facturasDTO = facturaService.obtenerFacturas();

        assertEquals(2, facturasDTO.size());
        assertEquals(1, facturasDTO.get(0).getIdFactura());
    }

    @Test
    public void obtenerFacturasNotOk() {
        List<Factura> facturas = Arrays.asList();

        Mockito.when(facturaRepository.findAll()).thenReturn(facturas);

        List<FacturaDTO> facturasDTO = facturaService.obtenerFacturas();

        assertEquals(0, facturasDTO.size());
    }

    @Test
    public void obtenerFacturaPorIdOk() throws Exception {
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

        Factura factura = Factura.builder()
                .idFactura(1)
                .reserva(reserva)
                .fecha(new Date())
                .estado("A")
                .build();

        Mockito.when(facturaRepository.existsById(1)).thenReturn(true);
        Mockito.when(facturaRepository.getReferenceById(1)).thenReturn(factura);

        FacturaDTO facturaDTO = facturaService.obtenerFacturaPorId(1);

        assertEquals(1, facturaDTO.getIdFactura());
    }

    @Test
    public void obtenerFacturaPorIdNotOk() {
        Mockito.when(facturaRepository.existsById(1)).thenReturn(false);

        assertThrows(java.lang.Exception.class, () -> facturaService.obtenerFacturaPorId(1));
    }
}
