package co.edu.usbcali.airlinesapp.services.implementation;

import co.edu.usbcali.airlinesapp.domain.Asiento;
import co.edu.usbcali.airlinesapp.dtos.AsientoDTO;
import co.edu.usbcali.airlinesapp.mappers.AsientoMapper;
import co.edu.usbcali.airlinesapp.mappers.AvionMapper;
import co.edu.usbcali.airlinesapp.mappers.TipoAsientoMapper;
import co.edu.usbcali.airlinesapp.repository.AsientoRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.AsientoService;

import co.edu.usbcali.airlinesapp.services.interfaces.AvionService;
import co.edu.usbcali.airlinesapp.services.interfaces.TipoAsientoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AsientoServiceImpl implements AsientoService {
    private final AsientoRepository asientoRepository;
    private final TipoAsientoService tipoAsientoService;
    private final AvionService avionService;

    public AsientoServiceImpl(AsientoRepository asientoRepository, TipoAsientoService tipoAsientoService, AvionService avionService) {
        this.asientoRepository = asientoRepository;
        this.tipoAsientoService = tipoAsientoService;
        this.avionService = avionService;
    }

    public void validarAsientoDTO(AsientoDTO asientoDTO) throws Exception {
        if (asientoDTO == null) {
            throw new Exception("El asiento no puede ser nulo");
        } if (asientoDTO.getIdTipoAsiento() == null || asientoDTO.getIdTipoAsiento() <= 0) {
            throw new Exception("El id del tipo de asiento no puede ser nulo o menor o igual a cero");
        } if (asientoDTO.getIdAvion() == null || asientoDTO.getIdAvion() <= 0) {
            throw new Exception("El id del avión no puede ser nulo o menor o igual a cero");
        } if (asientoDTO.getUbicacion() == null || asientoDTO.getUbicacion().isBlank() || asientoDTO.getUbicacion().trim().isEmpty()) {
            throw new Exception("La ubicación del asiento no puede ser nula o vacía");
        } if (asientoDTO.getEstado() == null || asientoDTO.getEstado().isBlank() || asientoDTO.getEstado().trim().isEmpty()) {
            throw new Exception("El estado del asiento no puede ser nulo o vacío");
        }
    }

    @Override
    public AsientoDTO guardarAsiento(AsientoDTO asientoDTO) throws Exception {
        validarAsientoDTO(asientoDTO);

        Asiento asiento = AsientoMapper.dtoToDomain(asientoDTO);

        asiento.setTipoAsiento(TipoAsientoMapper.dtoToDomain(tipoAsientoService.obtenerTipoAsientoPorId(asientoDTO.getIdTipoAsiento())));
        asiento.setAvion(AvionMapper.dtoToDomain(avionService.obtenerAvionPorId(asientoDTO.getIdAvion())));

        return AsientoMapper.domainToDTO(asientoRepository.save(asiento));
    }

    @Override
    public List<AsientoDTO> obtenerAsientos() {
        return AsientoMapper.domainToDTOList(asientoRepository.findAll());
    }

    @Override
    public List<AsientoDTO> obtenerAsientosActivos() {
        return AsientoMapper.domainToDTOList(asientoRepository.findAllByEstado("A"));
    }

    @Override
    public AsientoDTO obtenerAsientoPorId(Integer id) throws Exception {
        if (!asientoRepository.existsById(id)) {
            throw new Exception("El asiento con id " + id + " no existe");
        }

        return AsientoMapper.domainToDTO(asientoRepository.getReferenceById(id));
    }

    @Override
    public AsientoDTO actualizarAsiento(AsientoDTO asientoDTO) throws Exception {
        validarAsientoDTO(asientoDTO);

        AsientoDTO asientoSavedDTO = obtenerAsientoPorId(asientoDTO.getIdAsiento());

        asientoSavedDTO.setUbicacion(asientoDTO.getUbicacion());
        asientoSavedDTO.setEstado(asientoDTO.getEstado());

        return guardarAsiento(asientoSavedDTO);
    }

    @Override
    public AsientoDTO eliminarAsiento(Integer id) throws Exception {
        AsientoDTO asientoSavedDTO = obtenerAsientoPorId(id);

        if (asientoSavedDTO == null) {
            throw new Exception("El asiento no existe");
        }

        asientoSavedDTO.setEstado("I");

        return guardarAsiento(asientoSavedDTO);
    }
}
