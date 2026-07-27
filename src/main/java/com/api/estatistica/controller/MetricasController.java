package com.api.estatistica.controller;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import com.api.estatistica.DTO.MetricasInput;
import com.api.estatistica.DTO.MetricasDTO;
import com.api.estatistica.service.MetricasService;;

@RestController
@RequestMapping("/metricas")
@RequiredArgsConstructor
public class MetricasController {
    
    private final MetricasService metricasService;

    @PostMapping
    public String calcularMetricas(@RequestBody MetricasInput metricasInput){
        MetricasDTO metricasOutput = metricasService.salvarMetricas(metricasInput);
        return "Variância: 1 =" + metricasOutput.getVar().getVariancia_um() + "- 2 =" + metricasOutput.getVar().getVariancia_dois() + "\n" +
               "Desvio Padrão: 1 =" + metricasOutput.getDp().getDesvio_um() + "- 2 =" +metricasOutput.getDp().getDesvio_dois() + "\n" +
               "Covariância: " + metricasOutput.getCov() + "\n" +
               "Correlação: " + metricasOutput.getCorr() + "\n" +
               "Classificação Final: " + metricasOutput.getClassificacaoFinal();
    }
}
