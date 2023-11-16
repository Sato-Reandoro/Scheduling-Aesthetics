package com.agendamento.crm.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendamento.crm.controller.service.DisponibilidadeService;
import com.agendamento.crm.model.AreasCorpo;
import com.agendamento.crm.model.Disponibilidade;
import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.model.Procedimentos;
import com.agendamento.crm.model.user.DisponibilidadeDTO;
import com.agendamento.crm.model.user.DisponibilidadeMapper;
import com.agendamento.crm.repository.AreasCorpoRepository;
import com.agendamento.crm.repository.DisponibilidadeRepository;
import com.agendamento.crm.repository.FuncionariosRepository;
import com.agendamento.crm.repository.ProcedimentosRepository;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {
    
	private final DisponibilidadeService disponibilidadeService;
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final AreasCorpoRepository areasCorpoRepository;
    private final FuncionariosRepository funcionarioRepository;
    private final ProcedimentosRepository procedimentoRepository;

    // Injete os serviços necessários, como o serviço para gerenciar a disponibilidade dos funcionários
    public DisponibilidadeController(
            DisponibilidadeService disponibilidadeService,
            DisponibilidadeRepository disponibilidadeRepository,
            AreasCorpoRepository areasCorpoRepository,
            FuncionariosRepository funcionarioRepository,
            ProcedimentosRepository procedimentoRepository) {

        this.disponibilidadeService = disponibilidadeService;
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.areasCorpoRepository = areasCorpoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.procedimentoRepository = procedimentoRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DisponibilidadeDTO>> listarDisponibilidade() {
        List<Disponibilidade> disponibilidades = disponibilidadeService.listarDisponibilidade();
        List<DisponibilidadeDTO> disponibilidadeDTOs = disponibilidades.stream()
                .map(DisponibilidadeMapper::toDTO) // Usando o método estático
                .collect(Collectors.toList());
        return new ResponseEntity<>(disponibilidadeDTOs, HttpStatus.OK);
    }


    @PostMapping("/criar-disponibilidade")
    public ResponseEntity<?> criarDisponibilidade(@RequestBody DisponibilidadeDTO disponibilidadeDTO) {
        try {
        	// Valide e mapeie os dados do DTO para a entidade Disponibilidade
        	Disponibilidade novaDisponibilidade = new DisponibilidadeMapper().toEntity(disponibilidadeDTO);


            // Verifique se a data da disponibilidade está dentro do intervalo permitido (até dois meses à frente)
            LocalDateTime dataLimite = LocalDateTime.now().plusMonths(2);
            if (novaDisponibilidade.getDataHora().isAfter(dataLimite)) {
                throw new IllegalArgumentException("A data da disponibilidade deve estar dentro do intervalo de até dois meses à frente.");
            }
            
            // Verifique se há conflito com outra disponibilidade existente para o mesmo funcionário
            List<Disponibilidade> disponibilidadesConflitantes = disponibilidadeRepository
                    .findAllByFuncionarioAndDataHoraBetween(
                            novaDisponibilidade.getFuncionario(),
                            novaDisponibilidade.getDataHora(),
                            novaDisponibilidade.getDataFim());

            if (!disponibilidadesConflitantes.isEmpty()) {
                throw new RuntimeException("Conflito de disponibilidade para o mesmo funcionário na mesma data e hora.");
            }

            // Verifique se o funcionário e o procedimento existem
            Funcionarios funcionario = funcionarioRepository.findById(novaDisponibilidade.getFuncionario().getId())
                    .orElseThrow(() -> new NoSuchElementException("Funcionário não encontrado."));
            Procedimentos procedimento = procedimentoRepository.findById(novaDisponibilidade.getProcedimento().getId())
                    .orElseThrow(() -> new NoSuchElementException("Procedimento não encontrado."));

            // Verifique se a área do corpo existe
            AreasCorpo areaCorpo = areasCorpoRepository.findById(disponibilidadeDTO.getAreasCorpoId())
                    .orElseThrow(() -> new NoSuchElementException("Área do corpo não encontrada."));

            // Associe a área do corpo à disponibilidade
            novaDisponibilidade.setAreasCorpo(areaCorpo);

            // Defina o funcionário e procedimento na disponibilidade
            novaDisponibilidade.setFuncionario(funcionario);
            novaDisponibilidade.setProcedimento(procedimento);

            // Outras validações e lógica podem ser adicionadas aqui

            // Salve a disponibilidade no banco de dados
            novaDisponibilidade = disponibilidadeRepository.save(novaDisponibilidade);

            return ResponseEntity.ok(novaDisponibilidade);
        } catch (Exception e) {
            // Em caso de falha, retorna uma resposta HTTP 400 Bad Request com a mensagem de erro
            return ResponseEntity.badRequest().body("Falha ao criar a disponibilidade: " + e.getMessage());
        }
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
 // ... Outras partes do código não alteradas ...

    @PutMapping("/atualizar-disponibilidade/{id}")
    public ResponseEntity<?> editarDisponibilidade(@PathVariable("id") Long id, @RequestBody DisponibilidadeDTO disponibilidadeDTO) {
        try {
            // Valide e mapeie os dados do DTO para a entidade Disponibilidade
            Disponibilidade disponibilidade = new DisponibilidadeMapper().toEntity(disponibilidadeDTO);

            // Verifique se a disponibilidade existe
            if (!disponibilidadeRepository.existsById(id)) {
                throw new NoSuchElementException("Disponibilidade não encontrada.");
            }

            // Outras validações e lógica podem ser adicionadas aqui

            // Atualize a disponibilidade no banco de dados
            disponibilidadeRepository.save(disponibilidade);

            return ResponseEntity.ok(disponibilidade);
        } catch (Exception e) {
            // Em caso de falha, retorna uma resposta HTTP 400 Bad Request com a mensagem de erro
            return ResponseEntity.badRequest().body("Falha ao editar a disponibilidade: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluir-disponibilidade/{id}")
    public ResponseEntity<?> excluirDisponibilidade(@PathVariable("id") Long id) {
        try {
            // Verifique se a disponibilidade existe
            if (!disponibilidadeRepository.existsById(id)) {
                throw new NoSuchElementException("Disponibilidade não encontrada.");
            }

            // Exclua a disponibilidade do banco de dados
            disponibilidadeRepository.deleteById(id);

            return ResponseEntity.ok("Disponibilidade excluída com sucesso.");
        } catch (Exception e) {
            // Em caso de falha, retorna uma resposta HTTP 400 Bad Request com a mensagem de erro
            return ResponseEntity.badRequest().body("Falha ao excluir a disponibilidade: " + e.getMessage());
        }
    }
}