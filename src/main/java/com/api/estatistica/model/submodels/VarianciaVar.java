package com.api.estatistica.model.submodels;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VarianciaVar {
    private double variancia_um;
    private double variancia_dois;
}
