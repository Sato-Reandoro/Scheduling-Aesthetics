package com.agendamento.crm.controller.service;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;


import com.agendamento.crm.model.Disponibilidade;
import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.model.Procedimentos;
import com.agendamento.crm.model.user.DisponibilidadeDTO;
import com.agendamento.crm.model.user.DisponibilidadeMapper;
import com.agendamento.crm.repository.AgendamentosRepository;
import com.agendamento.crm.repository.DisponibilidadeRepository;
import com.agendamento.crm.repository.FuncionariosRepository;
import com.agendamento.crm.repository.ProcedimentosRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibilidadeService {
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final DisponibilidadeMapper disponibilidadeMapper;
    private final FuncionariosRepository funcionarioRepository;
    private final ProcedimentosRepository procedimentoRepository;
    private final AgendamentosRepository agendamentoRepository;
    
    
 
 

   
    public DisponibilidadeService(DisponibilidadeRepository disponibilidadeRepository, 
                                 DisponibilidadeMapper disponibilidadeMapper, 
                                 FuncionariosRepository funcionarioRepository, 
                                 ProcedimentosRepository procedimentoRepository,
                                 AgendamentosRepository agendamentoRepository) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.disponibilidadeMapper = disponibilidadeMapper;
        this.funcionarioRepository = funcionarioRepository;
        this.procedimentoRepository = procedimentoRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<DisponibilidadeDTO> listarDisponibilidades() {
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findAll();

        // Converte as entidades Disponibilidade em objetos DisponibilidadeDTO
        List<DisponibilidadeDTO> dtos = disponibilidades.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return dtos;
    }

    private DisponibilidadeDTO mapToDTO(Disponibilidade disponibilidade) {
        DisponibilidadeDTO dto = new DisponibilidadeDTO();
        dto.setId(disponibilidade.getId());
        // Defina outros atributos do DTO aqui com base nos atributos da entidade.

        return dto;
    }

    public Disponibilidade criarDisponibilidade(DisponibilidadeDTO disponibilidadeDTO) {
    	// Valide e mapeie os dados do DTO para a entidade Disponibilidade
        Disponibilidade disponibilidade = disponibilidadeMapper.toEntity(disponibilidadeDTO);

        // Verifique se a data da disponibilidade está dentro do intervalo permitido (até dois meses à frente)
        LocalDateTime dataLimite = LocalDateTime.now().plusMonths(2);
        if (disponibilidade.getDataHora().isAfter(dataLimite)) {
            throw new IllegalArgumentException("A data da disponibilidade deve estar dentro do intervalo de até dois meses à frente.");
        }
        
        // Verifique se há conflito com outra disponibilidade existente para o mesmo funcionário
        List<Disponibilidade> disponibilidadesConflitantes = disponibilidadeRepository
                .findAllByFuncionarioAndDataHoraBetween(
                        disponibilidade.getFuncionario(),
                        disponibilidade.getDataHora(),
                        disponibilidade.getDataFim());

        if (!disponibilidadesConflitantes.isEmpty()) {
            throw new RuntimeException("Conflito de disponibilidade para o mesmo funcionário na mesma data e hora.");
        }
        

        // Verifique se o funcionário e o procedimento existem
        Funcionarios funcionario = funcionarioRepository.findById(disponibilidade.getFuncionario().getId())
                .orElseThrow(() -> new NoSuchElementException("Funcionário não encontrado."));
        

     // Defina o funcionário na disponibilidade
        disponibilidade.setFuncionario(funcionario);

     // Associe os procedimentos à disponibilidade
        List<Procedimentos> procedimentos = procedimentoRepository.findAllById(disponibilidadeDTO.getProcedimentoId());
        disponibilidade.setProcedimentos(new HashSet<>(procedimentos));

        // Outras validações e lógica podem ser adicionadas aqui

        // Salve a disponibilidade no banco de dados
        disponibilidade = disponibilidadeRepository.save(disponibilidade);
   

        return disponibilidade;
    }

  
      
    
    public List<Disponibilidade> listarDisponibilidade() {
        return disponibilidadeRepository.findAll();
    }
    
    
    public List<Disponibilidade> listarDisponibilidadesPorFuncionarioEData(Funcionarios funcionario, LocalDate data) {
        return disponibilidadeRepository.findAllByFuncionarioAndDataHoraBetween(
                funcionario,
                data.atStartOfDay(),
                data.plusDays(1).atStartOfDay()
        );
    }
 // Método para verificar disponibilidade do funcionário
    public boolean verificarDisponibilidade(Funcionarios funcionario, LocalDateTime dataHoraProximaSessao) {
        // Consulta as disponibilidades do funcionário na data fornecida
        List<Disponibilidade> disponibilidades = disponibilidadeRepository
            .findAllByFuncionarioAndDataHoraBetween(
                funcionario,
                dataHoraProximaSessao,
                dataHoraProximaSessao.plusDays(1)
            );

        // Se a lista de disponibilidades for nula, inicializa como uma lista vazia
        disponibilidades = disponibilidades != null ? disponibilidades : new ArrayList<>();

        // Outras lógicas de verificação, se necessário

        // Verifica se existe alguma disponibilidade para o horário desejado
        boolean disponibilidadeDisponivel = disponibilidades.stream().anyMatch(disponibilidade -> {
            // Verifica se a disponibilidade está disponível e não está agendada
            return disponibilidade.getStatus().equals("disponível") && !agendamentoRepository.existsByDataAgendamentoAndHoraAgendamento(
                dataHoraProximaSessao.toLocalDate(),
                dataHoraProximaSessao.toLocalTime()
            );
        });

        return disponibilidadeDisponivel;
    }
}
