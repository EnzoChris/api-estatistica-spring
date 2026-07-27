package com.api.estatistica.DTO;
import com.api.estatistica.DTO.subDTO.*;
import com.api.estatistica.model.submodels.DesvioVar;
import com.api.estatistica.model.submodels.VarianciaVar;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MetricasDTO {

    private VarianciaVarDTO var;
    private DesvioVarDTO dp;
    private double cov;
    private double corr;
    private String classificacaoFinal;
}
