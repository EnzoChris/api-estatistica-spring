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
import java.util.List;

@Component
public class CalculoMetricas{

    public MetricasDTO calcularMetricas(MetricasInput metricasInput) {
        if (metricasInput == null) {
            throw new IllegalArgumentException("O corpo da requisição é obrigatório.");
        }
        double[] dadosUm = validarDados(metricasInput.getDadosUm(), "dadosUm");
        double[] dadosDois = validarDados(metricasInput.getDadosDois(), "dadosDois");
        if (dadosUm.length != dadosDois.length) {
            throw new IllegalArgumentException("dadosUm e dadosDois devem ter o mesmo tamanho.");
        }
        if (!temVariacao(dadosUm) || !temVariacao(dadosDois)) {
            throw new IllegalArgumentException("As duas séries devem variar para calcular a correlação.");
        }
        //calcular a variância
        double var_um = new Variance().evaluate(dadosUm);
        double var_dois = new Variance().evaluate(dadosDois);
        
        //desvio padrão
        double dp_um = new StandardDeviation().evaluate(dadosUm);
        double dp_dois = new StandardDeviation().evaluate(dadosDois);

        //covariância
        double cov = new Covariance()
        .covariance(dadosUm, dadosDois);

        //correlação
        double corr = new PearsonsCorrelation()
        .correlation(dadosUm, dadosDois);

        if (!Double.isFinite(var_um) || !Double.isFinite(var_dois)
                || !Double.isFinite(dp_um) || !Double.isFinite(dp_dois)
                || !Double.isFinite(cov) || !Double.isFinite(corr)) {
            throw new IllegalArgumentException("Os valores informados excedem a faixa suportada pelo cálculo.");
        }

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

    private double[] validarDados(List<Double> dados, String nome) {
        if (dados == null || dados.size() < 2) {
            throw new IllegalArgumentException(nome + " deve conter pelo menos dois números.");
        }
        double[] valores = new double[dados.size()];
        for (int i = 0; i < dados.size(); i++) {
            Double valor = dados.get(i);
            if (valor == null || !Double.isFinite(valor)) {
                throw new IllegalArgumentException(nome + " deve conter apenas números finitos.");
            }
            valores[i] = valor;
        }
        return valores;
    }

    private boolean temVariacao(double[] valores) {
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] != valores[0]) {
                return true;
            }
        }
        return false;
    }
}



