package com.api.estatistica.DTO.subDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesvioVarDTO {
    private double desvio_um;
    private double desvio_dois;
}
