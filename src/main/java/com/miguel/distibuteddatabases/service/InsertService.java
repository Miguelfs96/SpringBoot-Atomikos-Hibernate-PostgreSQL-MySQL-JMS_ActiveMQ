package com.miguel.distibuteddatabases.service;

import com.miguel.distibuteddatabases.model.Direccion;
import com.miguel.distibuteddatabases.model.Persona;

import java.util.List;

public interface InsertService {

    void save (Persona pers, Direccion dir);

    List<Persona> mostrarPersona();

    List<Direccion> mostrarDireccion();
}