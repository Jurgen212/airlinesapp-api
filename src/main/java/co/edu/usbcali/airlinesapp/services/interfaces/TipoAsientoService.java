package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.TipoAsientoDTO;

import java.util.List;

public interface TipoAsientoService {
    TipoAsientoDTO guardarTipoAsiento(TipoAsientoDTO tipoAsientoDTO) throws Exception;
    List<TipoAsientoDTO> obtenerTipoAsientos();
    List<TipoAsientoDTO> obtenerTipoAsientosActivos();
    TipoAsientoDTO obtenerTipoAsientoPorId(Integer id) throws Exception;
    TipoAsientoDTO actualizarTipoAsiento(TipoAsientoDTO tipoAsientoDTO) throws Exception;
    TipoAsientoDTO eliminarTipoAsiento(Integer id) throws Exception;
}
