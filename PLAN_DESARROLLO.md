# Plan de Desarrollo Completo — Sistema de Registro de Empleados

**Proyecto:** Sistema de empleados con control de asistencia y reportes  
**Equipo:** 6 integrantes  
**Integrante identificado:** Carlos  
**Tecnología sugerida:** Java Swing + MySQL + DAO + MVC  
**Estado actual:** Login con autenticación por rol implementado. Existen vistas base: LoginFrame, MenuAdminFrame, MenuEmpleadoFrame, EmpleadoFrame, AsistenciaFrame, ReporteFrame y CrearUsuarioFrame. El flujo visual principal ya existe, pero falta implementar la lógica completa, validaciones, DAOs, controladores e integración con base de datos.

---

## 1. Objetivo General

Desarrollar e integrar completamente el sistema de registro de empleados, permitiendo que el administrador gestione empleados, usuarios, asistencias y reportes, mientras que el empleado pueda registrar su asistencia y consultar sus reportes diarios o por fecha.

---

## 2. Alcance del Sistema

El sistema debe permitir:

- Iniciar sesión con usuario y contraseña.
- Redirigir según rol: administrador o empleado.
- Gestionar empleados: registrar, modificar, eliminar, listar y buscar por ID.
- Crear usuarios vinculados a empleados.
- Registrar asistencia diaria sin duplicados.
- Controlar jornada de mañana y tarde.
- Calcular atrasos, horas trabajadas, descuentos y sueldos.
- Generar reportes para empleados de tiempo completo y tiempo parcial.
- Consultar asistencias por fecha.
- Mantener una estructura DAO, modelo, vista y controlador.

---

## 3. Reglas de Negocio Principales

### Roles

| Rol | Permisos |
|---|---|
| Administrador | Acceso completo: empleados, usuarios, asistencia y reportes. |
| Empleado | Registrar asistencia propia y consultar reportes por día o fecha. |

### Jornada Laboral

| Jornada | Hora de entrada | Hora de salida |
|---|---:|---:|
| Mañana | 08:00 | 13:00 |
| Almuerzo | 13:00 | 14:00 |
| Tarde | 14:00 | 17:00 |

### Reglas de cálculo

- La jornada completa equivale a 8 horas diarias.
- El empleado de tiempo completo gana sueldo fijo mensual de **$1500**.
- El empleado de tiempo parcial gana **$5 por hora trabajada**.
- No se permite registrar más de 8 horas diarias.
- Por cada minuto de atraso se descuenta **$0.20**.
- No debe existir duplicación de asistencia para un mismo empleado en la misma fecha.

---

## 4. Estructura del Proyecto

```text
src/main/java
│
├── conexion
│   └── Conexion.java
│
├── modelo
│   ├── Usuario.java
│   ├── Empleado.java
│   └── Asistencia.java
│
├── dao
│   ├── UsuarioDAO.java
│   ├── EmpleadoDAO.java
│   └── AsistenciaDAO.java
│
├── vista
│   ├── LoginFrame.java
│   ├── MenuAdminFrame.java
│   ├── MenuEmpleadoFrame.java
│   ├── EmpleadoFrame.java
│   ├── AsistenciaFrame.java
│   ├── ReporteFrame.java
│   └── CrearUsuarioFrame.java
│
└── controlador
    ├── EmpleadoController.java
    ├── AsistenciaController.java
    ├── UsuarioController.java
    └── ReporteController.java
```

---

# 5. Distribución General del Trabajo

## Integrante 1: Carlos — Módulo de Gestión de Empleados

**Responsabilidad principal:** implementar la funcionalidad completa de gestión de empleados desde la vista hasta la base de datos.

### Archivos principales asignados

- `modelo/Empleado.java`
- `dao/EmpleadoDAO.java`
- `controlador/EmpleadoController.java`
- `vista/EmpleadoFrame.java`

### Funcionalidades que debe implementar

1. Registrar empleados.
2. Modificar información de empleados.
3. Eliminar empleados de forma lógica usando el campo `estado`.
4. Listar empleados activos en una tabla.
5. Buscar empleados por cédula, nombres o apellidos.
6. Validar que la cédula sea única.
7. Validar campos obligatorios.
8. Integrar botones de la vista con el controlador.

### Backend

En `EmpleadoDAO.java`, Carlos debe implementar métodos como:

```java
public boolean registrar(Empleado empleado);
public boolean actualizar(Empleado empleado);
public boolean eliminarLogico(int idEmpleado);
public List<Empleado> listarActivos();
public Empleado buscarPorCedula(String cedula);
public List<Empleado> buscar(String texto);
```

### Frontend Swing

En `EmpleadoFrame.java`, Carlos debe trabajar:

- Formulario con campos:
  - Cédula
  - Nombres
  - Apellidos
  - Teléfono
  - Correo
  - Tipo de empleado
  - Sueldo fijo
  - Valor por hora
- Tabla para listar empleados.
- Botones:
  - Nuevo
  - Guardar
  - Editar
  - Eliminar
  - Buscar
  - Limpiar

### Validaciones

- La cédula debe tener máximo 10 caracteres.
- Nombres y apellidos no pueden estar vacíos.
- El tipo de empleado debe ser `TIEMPO_COMPLETO` o `TIEMPO_PARCIAL`.
- Si es tiempo completo, el sueldo fijo debe ser 1500 por defecto.
- Si es tiempo parcial, el valor hora debe ser 5 por defecto.

### Resultado esperado

Al finalizar, el administrador podrá registrar, editar, eliminar, buscar y listar empleados reales conectados a MySQL.

---

## Integrante 2: Evelyn Jhoana Cardenas — Módulo de Usuarios y Roles

**Responsabilidad principal:** implementar la creación de usuarios, autenticación mejorada y control de roles.

### Archivos principales asignados

- `modelo/Usuario.java`
- `dao/UsuarioDAO.java`
- `controlador/UsuarioController.java`
- `vista/LoginFrame.java`
- `vista/CrearUsuarioFrame.java`

### Funcionalidades que debe implementar

1. Crear usuarios vinculados a empleados existentes.
2. Validar que un empleado tenga máximo un usuario activo.
3. Validar usuario único.
4. Asignar rol: administrador o empleado.
5. Mejorar login para traer también el `id_empleado`.
6. Redirigir al menú correcto según rol.
7. Bloquear usuarios inactivos.

### Backend

En `UsuarioDAO.java`, debe implementar:

```java
public Usuario login(String usuario, String password);
public boolean crearUsuario(Usuario usuario);
public boolean existeUsuario(String usuario);
public boolean empleadoTieneUsuario(int idEmpleado);
public List<Usuario> listarUsuariosActivos();
public boolean desactivarUsuario(int idUsuario);
```

### Frontend Swing

En `CrearUsuarioFrame.java`, debe incluir:

- ComboBox de empleados activos.
- Campo usuario.
- Campo contraseña.
- ComboBox de rol.
- Botón guardar.
- Botón limpiar.

### Validaciones

- El usuario no puede repetirse.
- La contraseña no puede estar vacía.
- El empleado debe existir y estar activo.
- El rol debe ser obligatorio.

### Resultado esperado

El administrador podrá crear usuarios para empleados y el login podrá autenticar correctamente según el rol.

---

## Integrante 3: JuanFull1 — Módulo de Registro de Asistencia

**Responsabilidad principal:** implementar el registro de asistencia diaria para empleados.

### Archivos principales asignados

- `modelo/Asistencia.java`
- `dao/AsistenciaDAO.java`
- `controlador/AsistenciaController.java`
- `vista/AsistenciaFrame.java`

### Funcionalidades que debe implementar

1. Registrar entrada de la mañana.
2. Registrar salida de la mañana.
3. Registrar entrada de la tarde.
4. Registrar salida de la tarde.
5. Validar que no exista duplicación diaria.
6. Actualizar la misma asistencia durante el día.
7. Calcular minutos de atraso.
8. Calcular horas trabajadas.
9. Calcular descuento por atraso.

### Backend

En `AsistenciaDAO.java`, debe implementar:

```java
public Asistencia buscarPorEmpleadoYFecha(int idEmpleado, LocalDate fecha);
public boolean crearAsistencia(Asistencia asistencia);
public boolean actualizarEntradaManana(int idEmpleado, LocalDate fecha, LocalTime hora);
public boolean actualizarSalidaManana(int idEmpleado, LocalDate fecha, LocalTime hora);
public boolean actualizarEntradaTarde(int idEmpleado, LocalDate fecha, LocalTime hora);
public boolean actualizarSalidaTarde(int idEmpleado, LocalDate fecha, LocalTime hora);
public boolean actualizarCalculos(Asistencia asistencia);
```

### Lógica de asistencia

- Si no existe asistencia del día, se crea el registro.
- Si ya existe, se actualiza el campo correspondiente.
- No se debe crear otro registro para el mismo empleado y fecha.
- La entrada de la mañana se compara con 08:00.
- La entrada de la tarde se compara con 14:00.
- Cada minuto de atraso equivale a $0.20.

### Frontend Swing

En `AsistenciaFrame.java`, debe incluir:

- Nombre del empleado logueado.
- Fecha actual.
- Botones:
  - Registrar entrada mañana
  - Registrar salida mañana
  - Registrar entrada tarde
  - Registrar salida tarde
- Tabla o panel resumen con las horas registradas.

### Resultado esperado

El empleado podrá registrar correctamente su asistencia diaria y el sistema calculará atrasos, horas trabajadas y descuentos.

---

## Integrante 4: Anthony — Reportes de Tiempo Completo

**Responsabilidad principal:** implementar los reportes mensuales de empleados de tiempo completo.

### Archivos principales asignados

- `dao/AsistenciaDAO.java`
- `dao/EmpleadoDAO.java`
- `controlador/ReporteController.java`
- `vista/ReporteFrame.java`

### Funcionalidades que debe implementar

1. Buscar empleado por cédula.
2. Validar que sea de tipo `TIEMPO_COMPLETO`.
3. Consultar asistencias del mes.
4. Mostrar entradas, salidas, atrasos y descuentos.
5. Calcular descuento total mensual.
6. Calcular sueldo final mensual.

### Backend

Debe implementar consultas como:

```java
public List<Asistencia> listarAsistenciasPorMes(int idEmpleado, int mes, int anio);
public BigDecimal calcularDescuentoMensual(int idEmpleado, int mes, int anio);
public BigDecimal calcularSueldoFinalTiempoCompleto(int idEmpleado, int mes, int anio);
```

### Fórmula

```text
Descuento total = minutos_atraso_total * 0.20
Sueldo final = 1500 - descuento total
```

### Frontend Swing

En `ReporteFrame.java`, debe crear una sección para:

- Ingresar cédula.
- Seleccionar mes y año.
- Botón consultar.
- Tabla con:
  - Fecha
  - Entrada mañana
  - Salida mañana
  - Entrada tarde
  - Salida tarde
  - Minutos atraso
  - Horas trabajadas
  - Descuento
- Labels finales:
  - Sueldo fijo
  - Descuento total
  - Sueldo a pagar

### Resultado esperado

El administrador podrá consultar el reporte mensual de un empleado de tiempo completo y obtener sueldo final con descuentos.

---

## Integrante 5: Samu2505-afk — Reportes de Tiempo Parcial

**Responsabilidad principal:** implementar reportes y cálculo de sueldo para empleados de tiempo parcial.

### Archivos principales asignados

- `dao/AsistenciaDAO.java`
- `controlador/ReporteController.java`
- `vista/ReporteFrame.java`

### Funcionalidades que debe implementar

1. Buscar empleado por cédula.
2. Validar que sea de tipo `TIEMPO_PARCIAL`.
3. Consultar asistencias del mes.
4. Sumar horas trabajadas del mes.
5. Ajustar minutos trabajados a horas decimales.
6. Calcular sueldo mensual por horas.
7. Validar que no supere 8 horas diarias.

### Backend

Debe implementar:

```java
public BigDecimal calcularHorasMensuales(int idEmpleado, int mes, int anio);
public BigDecimal calcularSueldoTiempoParcial(int idEmpleado, int mes, int anio);
```

### Fórmula

```text
Sueldo tiempo parcial = horas trabajadas del mes * 5.00
```

### Frontend Swing

En `ReporteFrame.java`, debe crear una sección para:

- Buscar por cédula.
- Seleccionar mes y año.
- Consultar reporte tiempo parcial.
- Mostrar tabla con asistencias.
- Mostrar:
  - Total de horas trabajadas
  - Valor por hora
  - Sueldo a pagar

### Resultado esperado

El administrador podrá consultar el sueldo mensual de empleados de tiempo parcial con base en sus horas reales trabajadas.

---

## Integrante 6: Robert Montesdeoca — Integración General, Menús, Validaciones Finales y Pruebas

**Responsabilidad principal:** unir todos los módulos, controlar navegación, probar el sistema y corregir errores de integración.

### Archivos principales asignados

- `vista/MenuAdminFrame.java`
- `vista/MenuEmpleadoFrame.java`
- `vista/LoginFrame.java`
- Todos los controladores para pruebas
- Script SQL final

### Funcionalidades que debe implementar

1. Conectar todos los botones del menú administrador.
2. Conectar todos los botones del menú empleado.
3. Validar permisos por rol.
4. Controlar cierre de sesión.
5. Probar navegación completa.
6. Revisar consistencia visual.
7. Preparar datos de prueba.
8. Documentar errores encontrados y corregidos.

### Menú Administrador

Debe permitir ir a:

- Gestión de empleados.
- Crear usuario.
- Registrar asistencia.
- Consultar asistencia por fecha.
- Reporte tiempo completo.
- Reporte tiempo parcial.
- Cerrar sesión.

### Menú Empleado

Debe permitir:

- Registrar asistencia propia.
- Consultar asistencia por fecha.
- Ver reporte diario.
- Cerrar sesión.

### Pruebas mínimas

Robert debe probar:

- Login correcto como administrador.
- Login correcto como empleado.
- Usuario incorrecto.
- Empleado creado correctamente.
- Usuario creado correctamente.
- Asistencia sin duplicados.
- Atraso calculado correctamente.
- Reporte tiempo completo correcto.
- Reporte tiempo parcial correcto.
- Cierre de sesión funcional.

### Resultado esperado

El sistema debe quedar completamente integrado, navegable y listo para presentación.

---

# 6. Cronograma de Trabajo Sugerido

| Día | Actividad | Responsable |
|---|---|---|
| Día 1 | Revisión de base de datos, modelos y conexión | Todo el equipo |
| Día 2 | Gestión de empleados | Carlos |
| Día 2 | Creación de usuarios y login | Evelyn |
| Día 3 | Registro de asistencia | JuanFull1 |
| Día 3 | Reporte tiempo completo | Anthony-web |
| Día 4 | Reporte tiempo parcial | Samu2505-afk |
| Día 4 | Menús, navegación e integración | Robert |
| Día 5 | Pruebas generales, correcciones y documentación | Todo el equipo |

---

# 7. Orden de Desarrollo Recomendado

## Fase 1: Base técnica

1. Verificar `Conexion.java`.
2. Confirmar conexión a MySQL.
3. Revisar modelos.
4. Crear los controladores faltantes.
5. Confirmar que el login funciona.

## Fase 2: Módulos principales

1. Gestión de empleados.
2. Creación de usuarios.
3. Registro de asistencia.
4. Reportes.
5. Integración de menús.

## Fase 3: Pruebas

1. Pruebas individuales por módulo.
2. Pruebas de integración.
3. Pruebas con datos reales.
4. Corrección de errores.
5. Preparación para exposición.

---

# 8. Funcionalidades por Vista

## LoginFrame

Debe permitir:

- Ingresar usuario.
- Ingresar contraseña.
- Validar credenciales.
- Redirigir según rol.
- Mostrar mensajes de error claros.

## MenuAdminFrame

Debe permitir:

- Ir a gestión de empleados.
- Ir a crear usuario.
- Ir a registrar asistencia.
- Ir a reportes.
- Cerrar sesión.

## MenuEmpleadoFrame

Debe permitir:

- Registrar asistencia del empleado logueado.
- Consultar asistencia por fecha.
- Ver reporte diario.
- Cerrar sesión.

## EmpleadoFrame

Debe permitir:

- Registrar empleado.
- Editar empleado.
- Eliminar empleado.
- Buscar empleado.
- Listar empleados.

## CrearUsuarioFrame

Debe permitir:

- Seleccionar empleado.
- Crear usuario.
- Asignar rol.
- Validar duplicados.

## AsistenciaFrame

Debe permitir:

- Registrar entradas y salidas.
- Calcular atrasos.
- Calcular horas trabajadas.
- Evitar duplicados diarios.

## ReporteFrame

Debe permitir:

- Consultar por cédula.
- Consultar por fecha, mes y año.
- Mostrar reporte de tiempo completo.
- Mostrar reporte de tiempo parcial.
- Mostrar sueldo y descuentos.

---

# 9. Recomendaciones Técnicas

## DAO

Cada DAO debe manejar únicamente consultas SQL y conexión con base de datos. No debe contener código visual.

## Controlador

Cada controlador debe actuar como puente entre la vista y el DAO. Debe validar datos antes de enviarlos al DAO.

## Vista

Las vistas deben tener solo diseño, eventos de botones y llamadas al controlador. No deben tener SQL directo.

## Modelo

Las clases modelo deben representar las tablas de la base de datos.

---

# 10. Datos de Prueba Recomendados

```sql
INSERT INTO empleados 
(cedula, nombres, apellidos, telefono, correo, tipo_empleado, sueldo_fijo, valor_hora)
VALUES
('1800000002', 'Carlos', 'Perez', '0991111111', 'carlos@gmail.com', 'TIEMPO_COMPLETO', 1500.00, 5.00),
('1800000003', 'Evelyn', 'Cardenas', '0992222222', 'evelyn@gmail.com', 'TIEMPO_PARCIAL', 1500.00, 5.00),
('1800000004', 'Juan', 'Lopez', '0993333333', 'juan@gmail.com', 'TIEMPO_COMPLETO', 1500.00, 5.00),
('1800000005', 'Robert', 'Mendoza', '0994444444', 'robert@gmail.com', 'TIEMPO_PARCIAL', 1500.00, 5.00),
('1800000006', 'Samuel', 'Torres', '0995555555', 'samuel@gmail.com', 'TIEMPO_COMPLETO', 1500.00, 5.00),
('1800000007', 'Anthony', 'Vargas', '0996666666', 'anthony@gmail.com', 'TIEMPO_PARCIAL', 1500.00, 5.00);
```

---

# 11. Entregables por Integrante

| Integrante | Entregable |
|---|---|
| Carlos | Gestión completa de empleados funcional. |
| Evelyn | Login, usuarios y roles funcionales. |
| JuanFull1 | Registro de asistencia funcional. |
| Robert | Reporte mensual tiempo completo funcional. |
| Samu2505-afk | Reporte mensual tiempo parcial funcional. |
| RobertM21 | Integración general, menús, pruebas y documentación. |

---

# 12. Criterios de Aceptación

El proyecto se considera terminado cuando:

- El login autentica usuarios reales de la base de datos.
- El sistema redirige correctamente según el rol.
- El administrador puede gestionar empleados.
- El administrador puede crear usuarios.
- El empleado puede registrar asistencia.
- No existen registros duplicados de asistencia por fecha.
- El sistema calcula atrasos y descuentos.
- El reporte de tiempo completo muestra sueldo fijo, descuentos y sueldo final.
- El reporte de tiempo parcial muestra horas trabajadas y sueldo final.
- Los menús funcionan correctamente.
- La interfaz mantiene un estilo visual uniforme.

---

# 13. Prioridad de Desarrollo

| Prioridad | Módulo |
|---|---|
| Alta | Login y usuarios |
| Alta | Gestión de empleados |
| Alta | Registro de asistencia |
| Media | Reportes |
| Media | Validaciones visuales |
| Alta | Integración final |

---

# 14. Conclusión

Este plan divide el sistema en módulos independientes pero integrables. Cada integrante tiene una responsabilidad clara, archivos asignados, tareas específicas de frontend y backend, validaciones y resultados esperados. Si cada miembro completa su módulo siguiendo la estructura DAO, modelo, vista y controlador, al final el equipo podrá unir todas las partes y presentar una funcionalidad completa: gestión de empleados, control de asistencia, roles, reportes y cálculo de sueldos.
