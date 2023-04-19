package co.edu.usbcali.airlinesapp.services.interfaces;

import co.edu.usbcali.airlinesapp.dtos.FacturaDTO;

import java.util.List;

public interface FacturaService {
    FacturaDTO guardarFactura(FacturaDTO facturaDTO) throws Exception;
    List<FacturaDTO> obtenerFacturas();
    List<FacturaDTO> obtenerFacturasActivas();
    List<FacturaDTO> obtenerFacturasPorIdReserva(Integer idReserva) throws Exception;
    FacturaDTO obtenerFacturaPorId(Integer id) throws Exception;
    FacturaDTO actualizarFactura(FacturaDTO facturaDTO) throws Exception;
    FacturaDTO eliminarFactura(Integer id) throws Exception;
}
