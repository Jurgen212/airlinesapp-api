package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.AeropuertoDTO;

import java.util.List;

public interface AeropuertoService {
    AeropuertoDTO guardarAeropuerto(AeropuertoDTO aeropuertoDTO) throws Exception;
    List<AeropuertoDTO> obtenerAeropuertos();
    List<AeropuertoDTO> obtenerAeropuertosActivos();
    AeropuertoDTO obtenerAeropuertoPorId(Integer id) throws Exception;
    AeropuertoDTO actualizarAeropuerto(AeropuertoDTO aeropuertoDTO) throws Exception;
    AeropuertoDTO eliminarAeropuerto(Integer id) throws Exception;
}
