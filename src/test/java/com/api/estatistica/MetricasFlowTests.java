package com.api.estatistica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.estatistica.DTO.MetricasDTO;
import com.api.estatistica.DTO.MetricasInput;
import com.api.estatistica.controller.MetricasController;
import com.api.estatistica.controller.MetricasExceptionHandler;
import com.api.estatistica.mapper.MetricasMapper;
import com.api.estatistica.repository.MetricasRepository;
import com.api.estatistica.service.MetricasService;
import com.api.estatistica.util.CalculoMetricas;

class MetricasFlowTests {

    private final AtomicInteger salvamentos = new AtomicInteger();
    private final MetricasRepository repository = (MetricasRepository) Proxy.newProxyInstance(
            MetricasRepository.class.getClassLoader(), new Class<?>[] { MetricasRepository.class },
            (proxy, method, args) -> {
                if (method.getName().equals("save")) {
                    salvamentos.incrementAndGet();
                    return args[0];
                }
                throw new UnsupportedOperationException(method.getName());
            });
    private final MetricasService service = new MetricasService(repository, new CalculoMetricas(), new MetricasMapper());
    private final MockMvc mvc = MockMvcBuilders.standaloneSetup(new MetricasController(service))
            .setControllerAdvice(new MetricasExceptionHandler()).build();

    @Test
    void calculaPersisteERetornaResultadoCompleto() throws Exception {
        mvc.perform(post("/metricas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dadosUm\":[1,2,3],\"dadosDois\":[2,4,6]}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.var.variancia_um").value(1.0))
            .andExpect(jsonPath("$.var.variancia_dois").value(4.0))
            .andExpect(jsonPath("$.dp.desvio_um").value(1.0))
            .andExpect(jsonPath("$.dp.desvio_dois").value(2.0))
            .andExpect(jsonPath("$.cov").value(2.0))
            .andExpect(jsonPath("$.corr").value(1.0))
            .andExpect(jsonPath("$.classificacaoFinal").value("Positiva"));
        assertEquals(1, salvamentos.get());
    }

    @Test
    void classificaCorrelacaoNegativa() {
        MetricasDTO resultado = service.salvarMetricas(
                new MetricasInput(List.of(1.0, 2.0, 3.0), List.of(6.0, 4.0, 2.0)));
        assertEquals(-1.0, resultado.getCorr(), 1e-12);
        assertEquals("Negativa", resultado.getClassificacaoFinal());
        assertEquals(1, salvamentos.get());
    }

    @Test
    void rejeitaSeriesDeTamanhosDiferentesSemSalvar() throws Exception {
        mvc.perform(post("/metricas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dadosUm\":[1,2,3],\"dadosDois\":[2,4]}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.erro").exists());
        assertEquals(0, salvamentos.get());
    }

    @Test
    void rejeitaSeriesConstantesNulasECurtas() {
        assertThrows(IllegalArgumentException.class, () -> service.salvarMetricas(
                new MetricasInput(List.of(1.0, 1.0), List.of(2.0, 3.0))));
        assertThrows(IllegalArgumentException.class, () -> service.salvarMetricas(
                new MetricasInput(null, List.of(2.0, 3.0))));
        assertThrows(IllegalArgumentException.class, () -> service.salvarMetricas(
                new MetricasInput(List.of(1.0), List.of(2.0))));
        assertEquals(0, salvamentos.get());
    }
}
