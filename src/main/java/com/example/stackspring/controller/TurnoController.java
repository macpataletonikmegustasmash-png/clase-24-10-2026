package com.example.stackspring.controller;

import com.example.stackspring.model.Turno;
import com.example.stackspring.model.TurnoRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Queue;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private int contador = 0;

    @PostMapping
    public Turno pedirTurno(@RequestBody TurnoRequest request) {
        contador++;
        Turno turno = new Turno(contador, request.getNombre());

        return turno;
    }

    @GetMapping("/siguiente")
    public Turno siguiente() {

        return null;
    }

    @GetMapping
    public Queue<Turno> verCola() {

        return null;
    }
}
