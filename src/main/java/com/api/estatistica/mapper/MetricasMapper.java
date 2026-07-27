package com.api.estatistica.mapper;
import org.springframework.stereotype.Component;

import com.api.estatistica.DTO.MetricasDTO;
import com.api.estatistica.DTO.subDTO.DesvioVarDTO;
import com.api.estatistica.DTO.subDTO.VarianciaVarDTO;
import com.api.estatistica.model.MetricasModel;
import com.api.estatistica.model.submodels.DesvioVar;
import com.api.estatistica.model.submodels.VarianciaVar;

@Component
public class MetricasMapper {
    
    public MetricasModel toModel(MetricasDTO dto){
        return MetricasModel.builder()
        .var(VarianciaVar.builder()
            .variancia_um(dto.getVar().getVariancia_um())
            .variancia_dois(dto.getVar().getVariancia_dois())
            .build())
        .dp(DesvioVar.builder()
            .desvio_um(dto.getDp().getDesvio_um())
            .desvio_dois(dto.getDp().getDesvio_dois())
            .build())
        .cov(dto.getCov())
        .corr(dto.getCorr())
        .build();
    }

    public MetricasDTO toDTO(MetricasModel model){
        return MetricasDTO.builder()
        .var(VarianciaVarDTO.builder()
            .variancia_um(model.getVar().getVariancia_um())
            .variancia_dois(model.getVar().getVariancia_dois())
            .build())
        .dp(DesvioVarDTO.builder()
            .desvio_um(model.getDp().getDesvio_um())
            .desvio_dois(model.getDp().getDesvio_dois())
            .build())
        .cov(model.getCov())
        .corr(model.getCorr())
        .build();
    }
}
