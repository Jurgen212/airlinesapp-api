package co.edu.usbcali.airlinesapp.services.implementation;

import co.edu.usbcali.airlinesapp.domain.Aeropuerto;
import co.edu.usbcali.airlinesapp.dtos.AeropuertoDTO;
import co.edu.usbcali.airlinesapp.mappers.AeropuertoMapper;
import co.edu.usbcali.airlinesapp.repository.AeropuertoRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.AeropuertoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AeropuertoServiceImpl implements AeropuertoService {
    private final AeropuertoRepository aeropuertoRepository;

    public AeropuertoServiceImpl(AeropuertoRepository aeropuertoRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
    }

    public void validarAeropuertoDTO(AeropuertoDTO aeropuertoDTO) throws Exception {
        if (aeropuertoDTO == null) {
            throw new Exception("El aeropuerto no puede ser nulo");
        } if (aeropuertoDTO.getNombre() == null || aeropuertoDTO.getNombre().isBlank() || aeropuertoDTO.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del aeropuerto no puede ser nulo  o vacío");
        } if (aeropuertoDTO.getIata() == null || aeropuertoDTO.getIata().isBlank() || aeropuertoDTO.getIata().trim().isEmpty()) {
            throw new Exception("El IATA del aeropuerto no puede ser nulo o vacío");
        } if (aeropuertoDTO.getUbicacion() == null || aeropuertoDTO.getUbicacion().isBlank() || aeropuertoDTO.getUbicacion().trim().isEmpty()) {
            throw new Exception("La ubicación del aeropuerto no puede ser nula o vacía");
        } if (aeropuertoDTO.getEstado() == null || aeropuertoDTO.getEstado().isBlank() || aeropuertoDTO.getEstado().trim().isEmpty()) {
            throw new Exception("El estado del aeropuerto no puede ser nulo o vacío");
        }
    }

    @Override
    public AeropuertoDTO guardarAeropuerto(AeropuertoDTO aeropuertoDTO) throws Exception {
        validarAeropuertoDTO(aeropuertoDTO);

        Aeropuerto aeropuerto = AeropuertoMapper.dtoToDomain(aeropuertoDTO);

        return AeropuertoMapper.domainToDTO(aeropuertoRepository.save(aeropuerto));
    }

    @Override
    public List<AeropuertoDTO> obtenerAeropuertos() {
        return AeropuertoMapper.domainToDTOList(aeropuertoRepository.findAll());
    }

    @Override
    public List<AeropuertoDTO> obtenerAeropuertosActivos() {
        return AeropuertoMapper.domainToDTOList(aeropuertoRepository.findAllByEstado("A"));
    }

    @Override
    public AeropuertoDTO obtenerAeropuertoPorId(Integer id) throws Exception {
        if (!aeropuertoRepository.existsById(id)) {
            throw new Exception("El aeropuerto con id " + id + " no existe");
        }

        return AeropuertoMapper.domainToDTO(aeropuertoRepository.getReferenceById(id));
    }

    @Override
    public AeropuertoDTO actualizarAeropuerto(AeropuertoDTO aeropuertoDTO) throws Exception {
        validarAeropuertoDTO(aeropuertoDTO);

        AeropuertoDTO aeropuertoSavedDTO = obtenerAeropuertoPorId(aeropuertoDTO.getIdAeropuerto());

        aeropuertoSavedDTO.setNombre(aeropuertoDTO.getNombre());
        aeropuertoSavedDTO.setEstado(aeropuertoDTO.getEstado());
        aeropuertoSavedDTO.setIata(aeropuertoDTO.getIata());
        aeropuertoSavedDTO.setUbicacion(aeropuertoDTO.getUbicacion());
        aeropuertoSavedDTO.setEstado(aeropuertoDTO.getEstado());

        return guardarAeropuerto(aeropuertoSavedDTO);
    }

    @Override
    public AeropuertoDTO eliminarAeropuerto(Integer id) throws Exception {
        AeropuertoDTO aeropuertoSavedDTO = obtenerAeropuertoPorId(id);

        if (aeropuertoSavedDTO == null) {
            throw new Exception("El aeropuerto no existe");
        }

        aeropuertoSavedDTO.setEstado("I");

        return guardarAeropuerto(aeropuertoSavedDTO);
    }
}
