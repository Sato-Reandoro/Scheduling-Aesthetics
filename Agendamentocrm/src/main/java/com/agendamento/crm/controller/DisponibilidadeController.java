package com.agendamento.crm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendamento.crm.controller.service.DisponibilidadeService;
import com.agendamento.crm.model.Disponibilidade;
import com.agendamento.crm.model.user.DisponibilidadeDTO;
import com.agendamento.crm.model.user.DisponibilidadeMapper;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {
    
    // Injete os serviços necessários, como o serviço para gerenciar a disponibilidade dos funcionários
	private final DisponibilidadeService disponibilidadeService;
	
	
	
    @Autowired
    public DisponibilidadeController(DisponibilidadeService disponibilidadeService) {
        this.disponibilidadeService = disponibilidadeService;
    }
    @Autowired
    private DisponibilidadeMapper disponibilidadeMapper; // Certifique-se de que a classe DisponibilidadeMapper seja anotada com @Component ou similar

    @GetMapping
    public ResponseEntity<List<DisponibilidadeDTO>> listarDisponibilidade() {
        List<Disponibilidade> disponibilidades = disponibilidadeService.listarDisponibilidade();
        List<DisponibilidadeDTO> disponibilidadeDTOs = disponibilidades.stream()
                .map(DisponibilidadeMapper::toDTO) // Usando o método estático
                .collect(Collectors.toList());
        return new ResponseEntity<>(disponibilidadeDTOs, HttpStatus.OK);
    }


    @PostMapping
    public DisponibilidadeDTO criarDisponibilidade(@RequestBody DisponibilidadeDTO disponibilidadeDTO) {
        // Converte o DTO para uma entidade Disponibilidade
        Disponibilidade disponibilidade = mapToEntity(disponibilidadeDTO);

        // Salva a entidade no banco de dados
        disponibilidade = disponibilidadeService.criarDisponibilidade(disponibilidadeDTO); // Passe o DisponibilidadeDTO

        // Converte a entidade de volta para um DTO e a retorna como resposta
        return mapToDTO(disponibilidade);
    }


    private DisponibilidadeDTO mapToDTO(Disponibilidade disponibilidade) {
        DisponibilidadeDTO dto = new DisponibilidadeDTO();
        dto.setId(disponibilidade.getId());
        // Mapeie outros atributos da entidade para o DTO aqui

        return dto;
    }

    private Disponibilidade mapToEntity(DisponibilidadeDTO disponibilidadeDTO) {
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setId(disponibilidadeDTO.getId());
        // Mapeie outros atributos do DTO para a entidade aqui

        return disponibilidade;
    }
}