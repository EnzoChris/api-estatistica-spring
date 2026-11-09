package com.api.estatistica.controller;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import com.api.estatistica.DTO.MetricasInput;
import com.api.estatistica.DTO.MetricasDTO;
import com.api.estatistica.service.MetricasService;

@RestController
@RequestMapping("/metricas")
@RequiredArgsConstructor
public class MetricasController {
    
    private final MetricasService metricasService;

    @PostMapping
    public MetricasDTO calcularMetricas(@RequestBody MetricasInput metricasInput){
        return metricasService.salvarMetricas(metricasInput);
    }
}
