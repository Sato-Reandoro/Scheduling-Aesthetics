package com.agendamento.crm.model.user;

import org.springframework.stereotype.Component;

import com.agendamento.crm.model.Disponibilidade;

@Component
public class DisponibilidadeMapper {
    public static DisponibilidadeDTO toDTO(Disponibilidade disponibilidade) {
        DisponibilidadeDTO dto = new DisponibilidadeDTO();
        dto.setId(disponibilidade.getId());
        dto.setFuncionarioId(disponibilidade.getFuncionario().getId());
        dto.setProcedimentoId(disponibilidade.getProcedimento().getId());
        dto.setDataHora(disponibilidade.getDataHora());
        dto.setDataFim(disponibilidade.getDataFim());
        dto.setStatus(disponibilidade.getStatus());
        dto.setObservacoes(disponibilidade.getObservacoes());
        dto.setCapacidade(disponibilidade.getCapacidade());
        dto.setClienteId(disponibilidade.getCliente().getId());
        dto.setMotivoCancelamento(disponibilidade.getMotivoCancelamento());
        // Mapeie outros atributos, se necessário
        return dto;
    }

    public Disponibilidade toEntity(DisponibilidadeDTO dto) {
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setId(dto.getId());
        // Faça o mapeamento reverso dos atributos
        return disponibilidade;
    }
}