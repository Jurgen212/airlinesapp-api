package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.VueloDTO;

import java.util.List;

public interface VueloService {
    VueloDTO guardarVuelo(VueloDTO vueloDTO) throws Exception;
    List<VueloDTO> obtenerVuelos();
    List<VueloDTO> obtenerVuelosActivos();
    VueloDTO obtenerVueloPorId(Integer id) throws Exception;
    VueloDTO actualizarVuelo(VueloDTO vueloDTO) throws Exception;
    VueloDTO eliminarVuelo(Integer id) throws Exception;
}
