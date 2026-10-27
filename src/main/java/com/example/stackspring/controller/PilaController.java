package com.example.stackspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("/pila")

public class PilaController {
    private Stack<String> pile = new Stack<>();
    @PostMapping
    public String agregar(@RequestParam String valor) {
        pile.push(valor);
        return "Se agrego: " + valor;
    }

    @GetMapping("/pop")
    public String sacar() {
        if (pile.isEmpty()) {
            return "La pila esta vacia";
        }
        return "Se saco: " + pile.pop();
    }

    @GetMapping
    public List<String> verPila() {

        return pile;
    }

    @GetMapping("/peek")
    public String verTope() {
        if (pile.isEmpty()) {
            return "La pila esta vacia";
        }
        return pile.peek();
    }

}