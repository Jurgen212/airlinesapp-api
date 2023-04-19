package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.AvionDTO;

import java.util.List;

public interface AvionService {
    AvionDTO guardarAvion(AvionDTO avionDTO) throws Exception;
    List<AvionDTO> obtenerAviones();
    List<AvionDTO> obtenerAvionesActivos();
    AvionDTO obtenerAvionPorId(Integer id) throws Exception;
    AvionDTO actualizarAvion(AvionDTO avionDTO) throws Exception;
    AvionDTO eliminarAvion(Integer id) throws Exception;
}
