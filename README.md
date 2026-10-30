# Pila y Cola con Spring Boot

Práctica de Estructuras de Datos: usar la pila (`Stack`) y la cola (`Queue`) que ya vienen en Java dentro de una API sencilla hecha con Spring Boot.

La API ya está armada y corre, pero todavía no guarda nada. Tu trabajo es conectar los endpoints con las estructuras de datos.

## Requisitos

- JDK 17 o más nuevo
- IntelliJ IDEA (Community está bien)
- Internet la primera vez que abras el proyecto (para descargar Gradle y las librerías)

No hace falta instalar Gradle ni Spring. El proyecto trae todo lo necesario.

## Cómo abrir el proyecto

1. Clona el repositorio.
2. En IntelliJ: **File > Open** y selecciona la carpeta `stackSpring`.
3. Si pregunta, elige **Trust Project**.
4. Espera a que termine de cargar (abajo a la derecha se ve el progreso). La primera vez tarda unos minutos.
5. Si IntelliJ te pide un SDK, selecciona tu JDK en **File > Project Structure > Project > SDK**.

## Estructura

```
src/main/java/com/example/stackspring/
├── StackSpringApplication.java     <- arranca la aplicación (no se toca)
├── controller/
│   ├── PilaController.java         <- EJERCICIO 1
│   └── TurnoController.java        <- EJERCICIO 2
└── model/
    ├── Turno.java                  <- clase Turno (no se toca)
    └── TurnoRequest.java           <- body del POST de turnos (no se toca)
```

## Cómo correr la aplicación

Abre `StackSpringApplication.java` y dale clic al botón verde ▶ junto a `main`.

Cuando en la consola aparezca algo como `Tomcat started on port 8080`, la API ya está lista en `http://localhost:8080`.

Para detenerla, usa el botón rojo ■. Cada vez que cambies código, detén y vuelve a correr.

Los GET se pueden abrir directo en el navegador. Los POST no; para esos usa Postman, `curl` o el archivo `peticiones.http` (en IntelliJ, si tu versión lo soporta). El POST de la pila manda el dato en la URL. El POST de turnos lo manda en el body como JSON.

> Los datos viven en memoria. Si detienes la aplicación, todo vuelve a empezar vacío.

---

## Ejercicio 1: Pila

Archivo: `controller/PilaController.java`

| Endpoint | Método en Java | Qué debe hacer |
|---|---|---|
| `POST /pila?valor=hola` | `agregar` | Meter el valor a la pila (`push`) |
| `GET /pila/pop` | `sacar` | Sacar el elemento de arriba (`pop`) y regresarlo |
| `GET /pila` | `verPila` | Regresar toda la pila |

Lo que tienes que hacer:

1. Crear una pila de `String` como atributo de la clase usando `java.util.Stack` (el import ya está).
2. En `agregar`, hacer `push` del valor.
3. En `sacar`, hacer `pop` y regresar el valor que salió. Ojo: si la pila está vacía, `pop()` lanza una excepción. Revisa primero con `isEmpty()`.
4. En `verPila`, regresar la pila completa.

Métodos de `Stack` que te sirven:

| Método | Qué hace |
|---|---|
| `push(x)` | Mete `x` arriba |
| `pop()` | Saca y regresa el de arriba |
| `peek()` | Regresa el de arriba sin sacarlo |
| `isEmpty()` | `true` si no hay elementos |
| `size()` | Cuántos elementos hay |

### Resultado esperado

| Petición | Respuesta |
|---|---|
| `GET /pila` | `[]` |
| `POST /pila?valor=uno` | `Se agrego: uno` |
| `POST /pila?valor=dos` | `Se agrego: dos` |
| `POST /pila?valor=tres` | `Se agrego: tres` |
| `GET /pila` | `["uno","dos","tres"]` |
| `GET /pila/pop` | `Se saco: tres` |
| `GET /pila` | `["uno","dos"]` |

La lista se muestra de abajo hacia arriba: el último elemento es el tope.

---

## Ejercicio 2: Cola de turnos

Archivo: `controller/TurnoController.java`

Un servicio de turnos, como el de un banco: cada persona pide turno con su nombre, recibe un número y espera a que la llamen. El primero que llega es el primero que pasa.

Cada turno es un objeto `Turno` con `numero` y `nombre`. La clase ya está hecha en `model/Turno.java`, y el número ya se genera solo en `pedirTurno`.

El nombre llega en el body del POST. Spring lo convierte automáticamente en un objeto `TurnoRequest` (`@RequestBody`) y de ahí se saca con `request.getNombre()`.

| Endpoint | Método en Java | Qué debe hacer |
|---|---|---|
| `POST /turnos` | `pedirTurno` | Formar el turno en la cola (`offer`) |
| `GET /turnos/siguiente` | `siguiente` | Sacar al primero de la cola (`poll`) y regresarlo |
| `GET /turnos` | `verCola` | Regresar toda la cola |

Lo que tienes que hacer:

1. Crear una cola de `Turno` como atributo de la clase. `Queue` es una interfaz, así que necesitas crearla con una clase que la implemente, por ejemplo `LinkedList` (los imports ya están).
2. En `pedirTurno`, agregar el turno a la cola.
3. En `siguiente`, sacar el primer turno de la cola y regresarlo.
4. En `verCola`, regresar la cola completa.

Métodos de `Queue` que te sirven:

| Método | Qué hace |
|---|---|
| `offer(x)` | Forma `x` al final de la cola |
| `poll()` | Saca y regresa el primero. Si está vacía regresa `null` |
| `peek()` | Regresa el primero sin sacarlo. Si está vacía regresa `null` |
| `isEmpty()` | `true` si no hay elementos |
| `size()` | Cuántos elementos hay |

A diferencia de `pop()` en la pila, `poll()` no lanza excepción si la cola está vacía, solo regresa `null`.

### Cómo pedir turno en Postman

- Método: **POST**
- URL: `http://localhost:8080/turnos`
- Pestaña **Body** > **raw** > en el menú de la derecha elige **JSON**
- Body:

```json
{
  "nombre": "Ana"
}
```

Desde la terminal:

```bash
curl -X POST http://localhost:8080/turnos -H "Content-Type: application/json" -d '{"nombre":"Ana"}'
```

### Resultado esperado

| Petición | Respuesta |
|---|---|
| `GET /turnos` | `[]` |
| `POST /turnos` con `{"nombre":"Ana"}` | `{"numero":1,"nombre":"Ana"}` |
| `POST /turnos` con `{"nombre":"Luis"}` | `{"numero":2,"nombre":"Luis"}` |
| `POST /turnos` con `{"nombre":"Sofia"}` | `{"numero":3,"nombre":"Sofia"}` |
| `GET /turnos` | `[{"numero":1,"nombre":"Ana"},{"numero":2,"nombre":"Luis"},{"numero":3,"nombre":"Sofia"}]` |
| `GET /turnos/siguiente` | `{"numero":1,"nombre":"Ana"}` |
| `GET /turnos` | `[{"numero":2,"nombre":"Luis"},{"numero":3,"nombre":"Sofia"}]` |

Si la cola está vacía, `GET /turnos/siguiente` regresa una respuesta en blanco.

---

## Retos extra

- **Pila:** agrega `GET /pila/peek` que regrese el elemento de arriba sin sacarlo.
- **Cola:** agrega `GET /turnos/actual` que regrese quién es el siguiente sin sacarlo de la cola.

## Errores comunes

- **`GET /pila` o `GET /turnos` siempre regresan `[]`**: todavía no estás guardando nada en la estructura.
- **`EmptyStackException`**: hiciste `pop()` con la pila vacía. Revisa con `isEmpty()` antes.
- **`Queue` marca error al hacer `new Queue<>()`**: `Queue` es una interfaz y no se puede crear directo. Usa `new LinkedList<>()`.
- **`400 Bad Request` al pedir turno**: el body no es JSON válido, o en Postman no elegiste **JSON** en el menú de **raw**.
- **`Port 8080 was already in use`**: ya tienes la aplicación corriendo en otra ventana. Detenla primero.
- **IntelliJ marca todo en rojo**: espera a que termine de descargar las dependencias, o da clic en el ícono del elefante (Gradle) y luego en **Reload**.
