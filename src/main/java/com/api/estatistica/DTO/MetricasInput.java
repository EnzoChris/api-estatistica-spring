package com.api.estatistica.DTO;
import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricasInput {
    private List<Double> dadosUm;
    private List<Double> dadosDois;
}
