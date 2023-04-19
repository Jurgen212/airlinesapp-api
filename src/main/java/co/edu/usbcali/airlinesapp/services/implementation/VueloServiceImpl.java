package co.edu.usbcali.airlinesapp.services.implementation;

import co.edu.usbcali.airlinesapp.domain.Vuelo;
import co.edu.usbcali.airlinesapp.dtos.VueloDTO;
import co.edu.usbcali.airlinesapp.mappers.AeropuertoMapper;
import co.edu.usbcali.airlinesapp.mappers.VueloMapper;
import co.edu.usbcali.airlinesapp.repository.VueloRepository;
import co.edu.usbcali.airlinesapp.services.interfaces.AeropuertoService;
import co.edu.usbcali.airlinesapp.services.interfaces.VueloService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
@Slf4j
public class VueloServiceImpl implements VueloService {
    private final VueloRepository vueloRepository;
    private final AeropuertoService aeropuertoService;

    public VueloServiceImpl(VueloRepository vueloRepository, AeropuertoService aeropuertoService) {
        this.vueloRepository = vueloRepository;
        this.aeropuertoService = aeropuertoService;
    }

    public void validarVueloDTO(VueloDTO vueloDTO) throws Exception {
        if (vueloDTO == null) {
            throw new Exception("El vuelo no puede ser nulo");
        } if (vueloDTO.getIdAeropuertoOrigen() == null || vueloDTO.getIdAeropuertoOrigen() <= 0) {
            throw new Exception("El id del aeropuerto de origen no puede ser nulo o menor o igual a cero");
        } if (vueloDTO.getIdAeropuertoDestino() == null || vueloDTO.getIdAeropuertoDestino() <= 0) {
            throw new Exception("El id del aeropuerto de destino no puede ser nulo o menor o igual a cero");
        } if (vueloDTO.getPrecio() < 0) {
            throw new Exception("El precio del vuelo no puede ser menor a cero");
        } if (vueloDTO.getHoraSalida() == null) {
            throw new Exception("La hora de salida del vuelo no puede ser nula");
        } if (vueloDTO.getHoraLlegada() == null) {
            throw new Exception("La hora de llegada del vuelo no puede ser nula");
        } if (vueloDTO.getPrecioAsientoVip() < 0) {
            throw new Exception("El precio del asiento vip no puede ser menor a cero");
        } if (vueloDTO.getPrecioAsientoNormal() < 0) {
            throw new Exception("El precio del asiento normal no puede ser menor a cero");
        } if (vueloDTO.getPrecioAsientoBasico() < 0) {
            throw new Exception("El precio del asiento básico no puede ser menor a cero");
        } if (vueloDTO.getEstado() == null || vueloDTO.getEstado().isBlank() || vueloDTO.getEstado().trim().isEmpty()) {
            throw new Exception("El estado del vuelo no puede ser nulo o vacío");
        }
    }

    @Override
    public VueloDTO guardarVuelo(VueloDTO vueloDTO) throws Exception {
        validarVueloDTO(vueloDTO);

        Vuelo vuelo = VueloMapper.dtoToDomain(vueloDTO);

        vuelo.setAeropuertoOrigen(AeropuertoMapper.dtoToDomain(aeropuertoService.obtenerAeropuertoPorId(vueloDTO.getIdAeropuertoOrigen())));
        vuelo.setAeropuertoDestino(AeropuertoMapper.dtoToDomain(aeropuertoService.obtenerAeropuertoPorId(vueloDTO.getIdAeropuertoDestino())));
        return VueloMapper.domainToDTO(vueloRepository.save(vuelo));
    }

    @Override
    public List<VueloDTO> obtenerVuelos() {
        return VueloMapper.domainToDTOList(vueloRepository.findAll());
    }

    @Override
    public List<VueloDTO> obtenerVuelosActivos() {
        return VueloMapper.domainToDTOList(vueloRepository.findAllByEstado("A"));
    }

    @Override
    public VueloDTO obtenerVueloPorId(Integer id) throws Exception {
        if (!vueloRepository.existsById(id)) {
            throw new Exception("El vuelo con id " + id + " no existe");
        }

        return VueloMapper.domainToDTO(vueloRepository.getReferenceById(id));
    }

    @Override
    public VueloDTO actualizarVuelo(VueloDTO vueloDTO) throws Exception {
        validarVueloDTO(vueloDTO);

        VueloDTO vueloSavedDTO = obtenerVueloPorId(vueloDTO.getIdVuelo());

        vueloSavedDTO.setPrecio(vueloDTO.getPrecio());
        vueloSavedDTO.setHoraSalida(vueloDTO.getHoraSalida());
        vueloSavedDTO.setHoraLlegada(vueloDTO.getHoraLlegada());
        vueloSavedDTO.setPrecioAsientoVip(vueloDTO.getPrecioAsientoVip());
        vueloSavedDTO.setPrecioAsientoNormal(vueloDTO.getPrecioAsientoNormal());
        vueloSavedDTO.setPrecioAsientoBasico(vueloDTO.getPrecioAsientoBasico());
        vueloSavedDTO.setEstado(vueloDTO.getEstado());

        return guardarVuelo(vueloSavedDTO);
    }

    @Override
    public VueloDTO eliminarVuelo(Integer id) throws Exception {
        VueloDTO vueloSavedDTO = obtenerVueloPorId(id);

        if (vueloSavedDTO == null) {
            throw new Exception("El vuelo no existe");
        }

        vueloSavedDTO.setEstado("I");

        return guardarVuelo(vueloSavedDTO);
    }
}
