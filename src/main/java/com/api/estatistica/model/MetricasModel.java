package com.api.estatistica.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.api.estatistica.model.submodels.DesvioVar;
import com.api.estatistica.model.submodels.VarianciaVar;

@Document(collection = "metricas")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricasModel {
    @Id
    private String id;
    private VarianciaVar var;
    private DesvioVar dp;
    private double cov;
    private double corr;
    private String classificacaoFinal;
}
