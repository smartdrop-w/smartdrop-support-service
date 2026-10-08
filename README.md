# smartdrop-support-service

> **SmartDrop - IoT Liquid Monitoring & Quality Management**  
> *UPC - Fundamentos de Arquitectura de Software (2026-20)*  
> *Autor Responsable:* **Luis Alonso Huaco Oliva**

---

## Descripcion General

Microservicio de soporte tecnico y gestion de incidencias, encargado de la generacion de alertas criticas de emergencia, resolucion de tickets y notificaciones a usuarios.

---

## Ejecucion en Entorno Local

Para compilar y ejecutar el proyecto localmente sin preconfiguraciones externas:

``powershell
# Compilacion y arranque con Maven Wrapper
./mvnw spring-boot:run
``

## Configuracion de Puertos y Endpoints

* **Puerto Local:** 8084
* **Swagger UI:** [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)
* **OpenAPI Especificacion JSON:** [http://localhost:8084/v3/api-docs](http://localhost:8084/v3/api-docs)
* **Health Check Liveness Probe:** [http://localhost:8084/api/v1/health](http://localhost:8084/api/v1/health)

---

## Pruebas Automatizadas

Para validar la suite de pruebas unitarias y de integracion:

``powershell
./mvnw test
```