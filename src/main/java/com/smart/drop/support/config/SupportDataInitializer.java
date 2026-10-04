package com.smart.drop.support.config;

import com.smart.drop.support.infrastructure.persistence.jpa.entities.AlertEntity;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.AlertJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SupportDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SupportDataInitializer.class);

    private final AlertJpaRepository alertRepository;

    public SupportDataInitializer(AlertJpaRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public void run(String... args) {
        if (alertRepository.count() > 0) {
            log.info("[Support Service] Semillero omitido: Ya existen alertas registradas.");
            return;
        }

        log.info("[Support Service] Sembrando alertas de prueba...");

        // Alerta 1 (Residencial)
        AlertEntity alert1 = new AlertEntity();
        alert1.setUserId(1);
        alert1.setType("NIGHT_LEAK");
        alert1.setTitle("Alerta de Fuga Nocturna en Cisterna");
        alert1.setDescription("Descenso volumétrico anómalo de 12 cm/h detectado entre las 02:00 y 04:00 AM.");
        alert1.setResolved(false);
        alert1.setCreatedAt(LocalDateTime.now().minusHours(3));
        alertRepository.save(alert1);

        // Alerta 2 (Cervecero)
        AlertEntity alert2 = new AlertEntity();
        alert2.setUserId(2);
        alert2.setType("THERMAL_EXCURSION");
        alert2.setTitle("Desviación Térmica Crítica en Lote Pale Ale");
        alert2.setDescription("Temperatura superó 21.8°C (umbral máx. 20.5°C) por más de 30 minutos continuos.");
        alert2.setResolved(false);
        alert2.setCreatedAt(LocalDateTime.now().minusMinutes(45));
        alertRepository.save(alert2);

        log.info("[Support Service] Sembrado completado: 2 alertas iniciales creadas.");
    }
}
