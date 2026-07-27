package com.api.estatistica.service;
import lombok.*;
import org.springframework.stereotype.Service;
import com.api.estatistica.DTO.MetricasInput;
import com.api.estatistica.DTO.MetricasDTO;
import com.api.estatistica.mapper.MetricasMapper;
import com.api.estatistica.model.MetricasModel;
import com.api.estatistica.repository.MetricasRepository;
import com.api.estatistica.util.CalculoMetricas;


@RequiredArgsConstructor
@Service
public class MetricasService {

    private final MetricasRepository metricasRepository;
    private final CalculoMetricas calculoMetricas;
    private final MetricasMapper metricasMapper;

    public MetricasDTO salvarMetricas(MetricasInput metricasInput) {
        MetricasDTO metricasDto = calculoMetricas.calcularMetricas(metricasInput);
        MetricasModel metricasModel = metricasMapper.toModel(metricasDto);

        return metricasMapper.toDTO(metricasRepository.save(metricasModel));
    }

    
}
