package co.edu.usbcali.airlinesapp.services.implementation;

import co.edu.usbcali.airlinesapp.domain.Reserva;
import co.edu.usbcali.airlinesapp.dtos.ReservaDTO;
import co.edu.usbcali.airlinesapp.mappers.AsientoMapper;
import co.edu.usbcali.airlinesapp.mappers.ReservaMapper;
import co.edu.usbcali.airlinesapp.mappers.UsuarioMapper;
import co.edu.usbcali.airlinesapp.mappers.VueloMapper;
import co.edu.usbcali.airlinesapp.repository.ReservaRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.AsientoService;
import co.edu.usbcali.airlinesapp.services.interfaces.ReservaService;

import co.edu.usbcali.airlinesapp.services.interfaces.UsuarioService;
import co.edu.usbcali.airlinesapp.services.interfaces.VueloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReservaServiceImpl implements ReservaService {
    private final ReservaRepository reservaRepository;
    private final VueloService vueloService;
    private final AsientoService asientoService;
    private final UsuarioService usuarioService;

    public ReservaServiceImpl(ReservaRepository reservaRepository, VueloService vueloService, AsientoService asientoService, UsuarioService usuarioService) {
        this.reservaRepository = reservaRepository;
        this.vueloService = vueloService;
        this.asientoService = asientoService;
        this.usuarioService = usuarioService;
    }

    public void validarReservaDTO(ReservaDTO reservaDTO) throws Exception {
        if (reservaDTO == null) {
            throw new Exception("La reserva no puede ser nula");
        } if (reservaDTO.getIdVuelo() == null) {
            throw new Exception("El vuelo de la reserva no puede ser nulo");
        } if (reservaDTO.getIdAsiento() == null) {
            throw new Exception("El asiento de la reserva no puede ser nulo");
        } if (reservaDTO.getIdUsuario() == null) {
            throw new Exception("El usuario de la reserva no puede ser nulo");
        } if (reservaDTO.getPrecioTotal() < 0) {
            throw new Exception("El precio total de la reserva no puede ser menor a cero");
        } if (reservaDTO.getEstadoPago() == null || reservaDTO.getEstadoPago().isBlank() || reservaDTO.getEstadoPago().trim().isEmpty()) {
            throw new Exception("El estado de pago de la reserva no puede ser nulo o vacío");
        } if (reservaDTO.getFecha() == null) {
            throw new Exception("La fecha de la reserva no puede ser nula");
        } if (reservaDTO.getEstado() == null || reservaDTO.getEstado().isBlank() || reservaDTO.getEstado().trim().isEmpty()) {
            throw new Exception("El estado de la reserva no puede ser nulo o vacío");
        }
    }

    @Override
    public ReservaDTO guardarReserva(ReservaDTO reservaDTO) throws Exception {
        validarReservaDTO(reservaDTO);

        Reserva reserva = ReservaMapper.dtoToDomain(reservaDTO);

        reserva.setVuelo(VueloMapper.dtoToDomain(vueloService.obtenerVueloPorId(reservaDTO.getIdVuelo())));
        reserva.setAsiento(AsientoMapper.dtoToDomain(asientoService.obtenerAsientoPorId(reservaDTO.getIdAsiento())));
        reserva.setUsuario(UsuarioMapper.dtoToDomain(usuarioService.obtenerUsuarioPorId(reservaDTO.getIdUsuario())));

        return ReservaMapper.domainToDTO(reservaRepository.save(reserva));
    }

    @Override
    public List<ReservaDTO> obtenerReservas() {
        return ReservaMapper.domainToDTOList(reservaRepository.findAll());
    }

    @Override
    public List<ReservaDTO> obtenerReservasActivas() {
        return ReservaMapper.domainToDTOList(reservaRepository.findAllByEstado("A"));
    }

    @Override
    public ReservaDTO obtenerReservaPorId(Integer id) throws Exception {
        if (!reservaRepository.existsById(id)) {
            throw new Exception("La reserva con id " + id + " no existe");
        }

        return ReservaMapper.domainToDTO(reservaRepository.getReferenceById(id));
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorIdVuelo(Integer idVuelo) throws Exception {
        vueloService.obtenerVueloPorId(idVuelo);

        return ReservaMapper.domainToDTOList(reservaRepository.findAllByVuelo_IdVuelo(idVuelo));
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorCedula(String cedula) throws Exception {
        usuarioService.obtenerUsuarioPorCedula(cedula);

        return ReservaMapper.domainToDTOList(reservaRepository.findAllByUsuario_Cedula(cedula));
    }

    @Override
    public ReservaDTO actualizarReserva(ReservaDTO reservaDTO) throws Exception {
        validarReservaDTO(reservaDTO);

        ReservaDTO reservaSavedDTO = obtenerReservaPorId(reservaDTO.getIdReserva());

        reservaSavedDTO.setPrecioTotal(reservaDTO.getPrecioTotal());
        reservaSavedDTO.setEstadoPago(reservaDTO.getEstadoPago());
        reservaSavedDTO.setFecha(reservaDTO.getFecha());
        reservaSavedDTO.setEstado(reservaDTO.getEstado());

        return guardarReserva(reservaSavedDTO);
    }

    @Override
    public ReservaDTO eliminarReserva(Integer id) throws Exception {
        ReservaDTO reservaSavedDTO = obtenerReservaPorId(id);

        if (reservaSavedDTO == null) {
            throw new Exception("La reserva no existe");
        }

        reservaSavedDTO.setEstado("I");

        return guardarReserva(reservaSavedDTO);
    }
}
