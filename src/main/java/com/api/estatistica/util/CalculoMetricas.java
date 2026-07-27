package com.api.estatistica.util;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.springframework.stereotype.Component;

import com.api.estatistica.DTO.MetricasInput;
import com.api.estatistica.DTO.subDTO.DesvioVarDTO;
import com.api.estatistica.DTO.subDTO.VarianciaVarDTO;
import com.api.estatistica.DTO.MetricasDTO;

@Component
public class CalculoMetricas{

    public MetricasDTO calcularMetricas(MetricasInput metricasInput) {
        //calcular a variância
        double var_um = new Variance().evaluate(metricasInput.getDadosUm().stream().mapToDouble(Double::doubleValue).toArray());
        double var_dois = new Variance().evaluate(metricasInput.getDadosDois().stream().mapToDouble(Double::doubleValue).toArray());
        
        //desvio padrão
        double dp_um = new StandardDeviation().evaluate(metricasInput.getDadosUm().stream().mapToDouble(Double::doubleValue).toArray());
        double dp_dois = new StandardDeviation().evaluate(metricasInput.getDadosDois().stream().mapToDouble(Double::doubleValue).toArray());

        //covariância
        double cov = new Covariance()
        .covariance(metricasInput.getDadosUm().stream().mapToDouble(Double::doubleValue).toArray(), 
        metricasInput.getDadosDois().stream().mapToDouble(Double::doubleValue).toArray());

        //correlação
        double corr = new PearsonsCorrelation()
        .correlation(metricasInput.getDadosUm().stream().mapToDouble(Double::doubleValue).toArray(),
        metricasInput.getDadosDois().stream().mapToDouble(Double::doubleValue).toArray());

        //classificação
        String classificacaoFinal = corr>0?"Positiva":corr<0?"Negativa":"Estável";

        return MetricasDTO.builder()
        .var(VarianciaVarDTO.builder().variancia_um(var_um).variancia_dois(var_dois).build())
        .dp(DesvioVarDTO.builder().desvio_um(dp_um).desvio_dois(dp_dois).build())
        .cov(cov)
        .corr(corr)
        .classificacaoFinal(classificacaoFinal)
        .build();
    }
}



