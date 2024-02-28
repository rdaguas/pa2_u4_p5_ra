package com.uce.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uce.edu.repository.modelo.Persona;
import com.uce.edu.service.IPersonaService;


// http://localhost:8080/personas/buscarTodos
@Controller
@RequestMapping("/personas")
public class PersonaController {
	
	@Autowired
	private IPersonaService iPersonaService;
	// cuando viaje el modelo en el response
	//GET Para buscar una lista 
	@GetMapping("/buscarTodos")
	public String buscarTodos(Model modelo) {
		List<Persona> lista = this.iPersonaService.buscarTodos();
		modelo.addAttribute("personas", lista);
		return "vistaListaPersonas";
	}
	// cuando viaje el modelo en el response
	//Path
	//GET Para buscar//consulta
	// /buscarPorCedula/1724340391
	// http://localhost:8080/personas/buscarPorCedula/1724340391
	@GetMapping("/buscarPorCedula/{cedulaPersona}")
	public String buscarPorCedula(@PathVariable("cedulaPersona") String cedula, Model modelo) {
		Persona persona = this.iPersonaService.buscarPorCedula(cedula);
		modelo.addAttribute("persona",persona);
		return "vistaPersona";
	}
	//PUT para actualizar
	// cuando viaje el modelo en el request
	@PutMapping("/actualizar/{cedulaPersona}")
	public String actualizar(@PathVariable("cedulaPersona") String cedula, Persona persona) {
		Persona perAux = this.iPersonaService.buscarPorCedula(cedula);
		perAux.setApellido(persona.getApellido());
		perAux.setNombre(persona.getNombre());
		perAux.setCedula(persona.getCedula());
		perAux.setGenero(persona.getGenero());
		this.iPersonaService.actualizar(perAux);
		return "redirect:/personas/buscarTodos";
	}
	

	
	//POST Para guardar
	@PostMapping("/insertar")
	public String insertar(Persona persona) {
		this.iPersonaService.guardar(persona);
		return "redirect:/personas/buscarTodos";
	}
	@GetMapping("/vistaNuevaPersona")
	public String mostrarNuevaPersona(Model modelo) {
		modelo.addAttribute("persona", new Persona());
		return "vistaNuevaPersona";
	}

	//DELETE para borrar
	@DeleteMapping("/borrar/{cedula}")
	public String borrar(@PathVariable("cedula") String cedula) {
		this.iPersonaService.borrarPorCedula(cedula);
		return "redirect:/personas/buscarTodos";
	}
}
