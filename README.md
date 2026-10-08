# smartdrop-support-service

> **SmartDrop â€” IoT Liquid Monitoring & Quality Management**  
> UPC â€” Fundamentos de Arquitectura de Software (2026-20)  
> Autor: **Luis Alonso Huaco Oliva**

## ðŸ“‹ Descripcion
SmartDrop Support Microservice: Alertas de emergencia, Tickets de soporte e Incidentes

## ðŸš€ Ejecucion Rapida (Zero Friction)
Para iniciar el servicio localmente:
``bash
# En Windows PowerShell
./mvnw spring-boot:run
``

* **Puerto Local:** $(System.Collections.Hashtable.Port)
* **Swagger UI:** [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)
* **OpenAPI Docs:** [http://localhost:8084/v3/api-docs](http://localhost:8084/v3/api-docs)
* **Health Check Probe:** [http://localhost:8084/api/v1/health](http://localhost:8084/api/v1/health)

## ðŸ§ª Pruebas Automatizadas
``bash
./mvnw test
``
