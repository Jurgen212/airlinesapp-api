package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.TrayectoDTO;

import java.util.List;

public interface TrayectoService {
    TrayectoDTO guardarTrayecto(TrayectoDTO trayectoDTO) throws Exception;
    List<TrayectoDTO> obtenerTrayectos();
    List<TrayectoDTO> obtenerTrayectosActivos();
    TrayectoDTO obtenerTrayectoPorId(Integer id) throws Exception;
    TrayectoDTO actualizarTrayecto(TrayectoDTO trayectoDTO) throws Exception;
    TrayectoDTO eliminarTrayecto(Integer id) throws Exception;
}
