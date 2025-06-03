package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.services.mapper.AbstractServiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public abstract class AbstractBusinessService <E, ID, DTO,  REPO extends JpaRepository<E,ID> ,
        MAPPER extends AbstractServiceMapper<E,DTO>>  {
    private final REPO repo;
    private final MAPPER serviceMapper;


    protected AbstractBusinessService(REPO repo, MAPPER mapper) {
        this.repo = repo;
        this.serviceMapper = mapper;
    }
    //Buscamos por id
    public Optional<E> findById(ID id){return  this.repo.findById(id);}
    //Lista de todos los DTOs buscarTodos devolvera lista y paginas
    public List<DTO> findAllDTO(){
        return  this.serviceMapper.toDto(this.repo.findAll());
    }

    public List<E> findAll(){
        return  this.repo.findAll();
    }
    public Set<E> findAllSet(){
        Set<E> eSet = new HashSet<E>(this.repo.findAll());
        return  eSet;
    }

    public Set<DTO> findAllDTOSet(){
        Set<DTO> dtos = new HashSet<DTO>(this.serviceMapper.toDto(this.repo.findAll()));
        return  dtos;
    }

    public Page<DTO> findAllPageable(Pageable pageable){
        return  repo.findAll(pageable).map(this.serviceMapper::toDto);
    }

    //Buscar por id
    public Optional<DTO> findByIdDTO(ID id){

        return this.repo.findById(id).map(this.serviceMapper::toDto);
    }
    //Guardar
    public DTO save(DTO dto) throws Exception {
        //Traduzco del dto con datos de entrada a la entity
        final E entity = serviceMapper.toEntity(dto);
        //Guardo el la base de datos
        E savedEntity =  repo.save(entity);
        //Traducir la entity a DTO para devolver el DTO
        return serviceMapper.toDto(savedEntity);
    }
    //Guardar
    public DTO guardarEntidadDto(E entity)  {
        //Guardo el la base de datos
        E savedEntity =  repo.save(entity);
        //Traducir la entity a DTO para devolver el DTO
        return serviceMapper.toDto(savedEntity);
    }
    //Guardar
    public E saveEntity(E entity) throws Exception {
        //Guardo el la base de datos
        E savedEntity =  repo.save(entity);
        //Traducir la entity a DTO para devolver el DTO
        return savedEntity;
    }
    public void  saveAllDTO(List<DTO> dtos) throws Exception {
        Iterator<DTO> it = dtos.iterator();

        // mientras al iterador queda proximo juego
        while(it.hasNext()){
            //Obtenemos la password de a entity
            //Datos del usuario
            E e = serviceMapper.toEntity(it.next());
            repo.save(e);
        }
    }
    //eliminar un registro
    public void deleteById(ID id){
        this.repo.deleteById(id);
    }
    //Obtener el mapper
    public MAPPER getMapper(){return  serviceMapper;}
    //Obtener el repo
    public REPO getRepo(){return  repo;}
}