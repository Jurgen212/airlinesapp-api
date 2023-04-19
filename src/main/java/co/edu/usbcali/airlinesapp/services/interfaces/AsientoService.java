package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.AsientoDTO;

import java.util.List;

public interface AsientoService {
    AsientoDTO guardarAsiento(AsientoDTO asientoDTO) throws Exception;
    List<AsientoDTO> obtenerAsientos();
    List<AsientoDTO> obtenerAsientosActivos();
    AsientoDTO obtenerAsientoPorId(Integer id) throws Exception;
    AsientoDTO actualizarAsiento(AsientoDTO asientoDTO) throws Exception;
    AsientoDTO eliminarAsiento(Integer id) throws Exception;
}
