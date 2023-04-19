package co.edu.usbcali.airlinesapp.services.implementation;

import co.edu.usbcali.airlinesapp.domain.Factura;
import co.edu.usbcali.airlinesapp.dtos.FacturaDTO;
import co.edu.usbcali.airlinesapp.mappers.FacturaMapper;
import co.edu.usbcali.airlinesapp.mappers.ReservaMapper;
import co.edu.usbcali.airlinesapp.repository.FacturaRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.FacturaService;
import co.edu.usbcali.airlinesapp.services.interfaces.ReservaService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FacturaServiceImpl implements FacturaService {
    private final FacturaRepository facturaRepository;
    private final ReservaService reservaService;

    public FacturaServiceImpl(FacturaRepository facturaRepository, ReservaService reservaService) {
        this.facturaRepository = facturaRepository;
        this.reservaService = reservaService;
    }

    public void validarFacturaDTO(FacturaDTO facturaDTO) throws Exception {
        if (facturaDTO == null) {
            throw new Exception("La factura no puede ser nula");
        } if (facturaDTO.getIdReserva() == null || facturaDTO.getIdReserva() <= 0) {
            throw new Exception("El id de la reserva no puede ser nulo o menor o igual a cero");
        } if (facturaDTO.getFecha() == null) {
            throw new Exception("La fecha de la factura no puede ser nula");
        } if (facturaDTO.getEstado() == null || facturaDTO.getEstado().isBlank() || facturaDTO.getEstado().trim().isEmpty()) {
            throw new Exception("El estado de la factura no puede ser nulo o vacío");
        }
    }

    @Override
    public FacturaDTO guardarFactura(FacturaDTO facturaDTO) throws Exception {
        validarFacturaDTO(facturaDTO);

        Factura factura = FacturaMapper.dtoToDomain(facturaDTO);

        factura.setReserva(ReservaMapper.dtoToDomain(reservaService.obtenerReservaPorId(facturaDTO.getIdReserva())));

        return FacturaMapper.domainToDTO(facturaRepository.save(factura));
    }

    @Override
    public List<FacturaDTO> obtenerFacturas() {
        return FacturaMapper.domainToDTOList(facturaRepository.findAll());
    }

    @Override
    public List<FacturaDTO> obtenerFacturasActivas() {
        return FacturaMapper.domainToDTOList(facturaRepository.findAllByEstado("A"));
    }

    @Override
    public FacturaDTO obtenerFacturaPorId(Integer id) throws Exception {
        if (!facturaRepository.existsById(id)) {
            throw new Exception("La factura con id " + id + " no existe");
        }

        return FacturaMapper.domainToDTO(facturaRepository.getReferenceById(id));
    }

    @Override
    public List<FacturaDTO> obtenerFacturasPorIdReserva(Integer idReserva) throws Exception {
        reservaService.obtenerReservaPorId(idReserva);

        return FacturaMapper.domainToDTOList(facturaRepository.findAllByReserva_IdReserva(idReserva));
    }

    @Override
    public FacturaDTO actualizarFactura(FacturaDTO facturaDTO) throws Exception {
        validarFacturaDTO(facturaDTO);

        FacturaDTO facturaSavedDTO = obtenerFacturaPorId(facturaDTO.getIdFactura());

        facturaSavedDTO.setFecha(facturaDTO.getFecha());
        facturaSavedDTO.setEstado(facturaDTO.getEstado());

        return guardarFactura(facturaSavedDTO);
    }

    @Override
    public FacturaDTO eliminarFactura(Integer id) throws Exception {
        FacturaDTO facturaSavedDTO = obtenerFacturaPorId(id);

        if (facturaSavedDTO == null) {
            throw new Exception("La factura no existe");
        }

        facturaSavedDTO.setEstado("I");

        return guardarFactura(facturaSavedDTO);
    }
}
