package co.edu.usbcali.airlinesapp.services.implementation;

import co.edu.usbcali.airlinesapp.domain.Trayecto;
import co.edu.usbcali.airlinesapp.dtos.TrayectoDTO;
import co.edu.usbcali.airlinesapp.mappers.AeropuertoMapper;
import co.edu.usbcali.airlinesapp.mappers.AvionMapper;
import co.edu.usbcali.airlinesapp.mappers.TrayectoMapper;
import co.edu.usbcali.airlinesapp.mappers.VueloMapper;
import co.edu.usbcali.airlinesapp.repository.TrayectoRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.AeropuertoService;
import co.edu.usbcali.airlinesapp.services.interfaces.AvionService;
import co.edu.usbcali.airlinesapp.services.interfaces.TrayectoService;

import co.edu.usbcali.airlinesapp.services.interfaces.VueloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrayectoServiceImpl implements TrayectoService {
    private final TrayectoRepository trayectoRepository;
    private final AvionService avionService;
    private final AeropuertoService aeropuertoService;
    private final VueloService vueloService;

    public TrayectoServiceImpl(TrayectoRepository trayectoRepository, AvionService avionService, AeropuertoService aeropuertoService, VueloService vueloService) {
        this.trayectoRepository = trayectoRepository;
        this.avionService = avionService;
        this.aeropuertoService = aeropuertoService;
        this.vueloService = vueloService;
    }

    public void validarTrayectoDTO(TrayectoDTO trayectoDTO) throws Exception {
        if (trayectoDTO == null) {
            throw new Exception("El trayecto no puede ser nulo");
        } if (trayectoDTO.getIdAvion() == null || trayectoDTO.getIdAvion() <= 0) {
            throw new Exception("El id del avión no puede ser nulo o menor o igual a cero");
        } if (trayectoDTO.getIdAeropuertoOrigen() == null || trayectoDTO.getIdAeropuertoOrigen() <= 0) {
            throw new Exception("El id del aeropuerto de origen no puede ser nulo o menor o igual a cero");
        } if (trayectoDTO.getIdAeropuertoDestino() == null || trayectoDTO.getIdAeropuertoDestino() <= 0) {
            throw new Exception("El id del aeropuerto de destino no puede ser nulo o menor o igual a cero");
        } if (trayectoDTO.getHoraSalida() == null) {
            throw new Exception("La hora de salida del trayecto no puede ser nula");
        } if (trayectoDTO.getHoraLlegada() == null) {
            throw new Exception("La hora de llegada del trayecto no puede ser nula");
        } if (trayectoDTO.getIdVuelo() == null || trayectoDTO.getIdVuelo() <= 0) {
            throw new Exception("El id del vuelo no puede ser nulo o menor o igual a cero");
        } if (trayectoDTO.getEstado() == null || trayectoDTO.getEstado().isBlank() || trayectoDTO.getEstado().trim().isEmpty()) {
            throw new Exception("El estado del trayecto no puede ser nulo o vacío");
        }
    }

    @Override
    public TrayectoDTO guardarTrayecto(TrayectoDTO trayectoDTO) throws Exception {
        validarTrayectoDTO(trayectoDTO);

        Trayecto trayecto = TrayectoMapper.dtoToDomain(trayectoDTO);

        trayecto.setAvion(AvionMapper.dtoToDomain(avionService.obtenerAvionPorId(trayectoDTO.getIdAvion())));
        trayecto.setAeropuertoOrigen(AeropuertoMapper.dtoToDomain(aeropuertoService.obtenerAeropuertoPorId(trayectoDTO.getIdAeropuertoOrigen())));
        trayecto.setAeropuertoDestino(AeropuertoMapper.dtoToDomain(aeropuertoService.obtenerAeropuertoPorId(trayectoDTO.getIdAeropuertoDestino())));
        trayecto.setVuelo(VueloMapper.dtoToDomain(vueloService.obtenerVueloPorId(trayectoDTO.getIdVuelo())));

        return TrayectoMapper.domainToDTO(trayectoRepository.save(trayecto));
    }

    @Override
    public List<TrayectoDTO> obtenerTrayectos() {
        return TrayectoMapper.domainToDTOList(trayectoRepository.findAll());
    }

    @Override
    public List<TrayectoDTO> obtenerTrayectosActivos() {
        return TrayectoMapper.domainToDTOList(trayectoRepository.findAllByEstado("A"));
    }

    @Override
    public TrayectoDTO obtenerTrayectoPorId(Integer id) throws Exception {
        if (!trayectoRepository.existsById(id)) {
            throw new Exception("El trayecto con id " + id + " no existe");
        }

        return TrayectoMapper.domainToDTO(trayectoRepository.getReferenceById(id));
    }

    @Override
    public TrayectoDTO actualizarTrayecto(TrayectoDTO trayectoDTO) throws Exception {
        validarTrayectoDTO(trayectoDTO);

        TrayectoDTO trayectoSavedDTO = obtenerTrayectoPorId(trayectoDTO.getIdTrayecto());

        trayectoSavedDTO.setHoraSalida(trayectoDTO.getHoraSalida());
        trayectoSavedDTO.setHoraLlegada(trayectoDTO.getHoraLlegada());
        trayectoSavedDTO.setEstado(trayectoDTO.getEstado());

        return guardarTrayecto(trayectoSavedDTO);
    }

    @Override
    public TrayectoDTO eliminarTrayecto(Integer id) throws Exception {
        TrayectoDTO trayectoSavedDTO = obtenerTrayectoPorId(id);

        if (trayectoSavedDTO == null) {
            throw new Exception("El trayecto no existe");
        }

        trayectoSavedDTO.setEstado("I");

        return guardarTrayecto(trayectoSavedDTO);
    }
}
