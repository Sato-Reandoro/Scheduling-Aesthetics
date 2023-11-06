package com.agendamento.crm.controller.service;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agendamento.crm.model.Disponibilidade;
import com.agendamento.crm.model.Funcionarios;
import com.agendamento.crm.model.Procedimentos;
import com.agendamento.crm.model.user.DisponibilidadeDTO;
import com.agendamento.crm.model.user.DisponibilidadeMapper;
import com.agendamento.crm.repository.DisponibilidadeRepository;
import com.agendamento.crm.repository.FuncionariosRepository;
import com.agendamento.crm.repository.ProcedimentosRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibilidadeService {
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final DisponibilidadeMapper disponibilidadeMapper;
    private final FuncionariosRepository funcionarioRepository;
    private final ProcedimentosRepository procedimentoRepository;

    @Autowired
    public DisponibilidadeService(DisponibilidadeRepository disponibilidadeRepository, 
                                 DisponibilidadeMapper disponibilidadeMapper, 
                                 FuncionariosRepository funcionarioRepository, 
                                 ProcedimentosRepository procedimentoRepository) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.disponibilidadeMapper = disponibilidadeMapper;
        this.funcionarioRepository = funcionarioRepository;
        this.procedimentoRepository = procedimentoRepository;
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


        // Verifique se o funcionário e o procedimento existem
        Funcionarios funcionario = funcionarioRepository.findById(disponibilidade.getFuncionario().getId())
                .orElseThrow(() -> new NoSuchElementException("Funcionário não encontrado."));
        Procedimentos procedimento = procedimentoRepository.findById(disponibilidade.getProcedimento().getId())
                .orElseThrow(() -> new NoSuchElementException("Procedimento não encontrado."));


        // Defina o funcionário e procedimento na disponibilidade
        disponibilidade.setFuncionario(funcionario);
        disponibilidade.setProcedimento(procedimento);

        // Outras validações e lógica podem ser adicionadas aqui

        // Salve a disponibilidade no banco de dados
        disponibilidade = disponibilidadeRepository.save(disponibilidade);

        return disponibilidade;
    }

    public List<Disponibilidade> listarDisponibilidade() {
        return disponibilidadeRepository.findAll();
    }
    
    
    
}
