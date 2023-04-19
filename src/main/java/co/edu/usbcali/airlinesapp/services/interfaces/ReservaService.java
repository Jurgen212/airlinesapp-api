package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.ReservaDTO;

import java.util.List;

public interface ReservaService {
    ReservaDTO guardarReserva(ReservaDTO reservaDTO) throws Exception;
    List<ReservaDTO> obtenerReservas();
    List<ReservaDTO> obtenerReservasActivas();
    List<ReservaDTO> obtenerReservasPorIdVuelo(Integer idVuelo) throws Exception;
    List<ReservaDTO> obtenerReservasPorCedula(String cedula) throws Exception;
    ReservaDTO obtenerReservaPorId(Integer id) throws Exception;
    ReservaDTO actualizarReserva(ReservaDTO reservaDTO) throws Exception;
    ReservaDTO eliminarReserva(Integer id) throws Exception;
}
