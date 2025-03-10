
# API REST para Gestión de Clientes
## Endpoints de la API REST

| Método  | Endpoint                      | Descripción                         | Código de Respuesta                         |
|---------|--------------------------------|-------------------------------------|---------------------------------------------|
| GET     | `/api/customers/{customerId}` | Obtener información del cliente    | `200 OK` / `404 Not Found`                  |
| PUT     | `/api/customers/{customerId}` | Actualizar información del cliente | `200 OK` / `400 Bad Request` / `401 Unauthorized` |
| DELETE  | `/api/customers/{customerId}` | Eliminar cliente y su información  | `204 No Content` / `401 Unauthorized` / `404 Not Found` |


## 1. Obtener Información del Cliente

**Descripción:**  
Devuelve los datos del cliente, excepto información sensible como la tarjeta de crédito.

**Ejemplo de Solicitud (GET /api/customers/12345)**  

```http
GET /api/customers/12345 HTTP/1.1
Host: api.example.com
Authorization: Bearer <JWT-TOKEN>

```

**Ejemplo de Respuesta (200 OK)**

```json
{
  "customerId": "12345",
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan.perez@example.com",
  "address": "Calle 123, Ciudad, País"
}

```

**Errores Posibles:**

```http
404 Not Found  // Cliente no encontrado
401 Unauthorized  // Token inválido o sesión expirada

```

----------

## 2. Actualizar Información del Cliente

**Descripción:**  
Permite actualizar nombre, apellido, dirección y correo.

**Ejemplo de Solicitud (PUT /api/customers/12345)**

```http
PUT /api/customers/12345 HTTP/1.1
Host: api.example.com
Authorization: Bearer <JWT-TOKEN>
Content-Type: application/json

```

**Cuerpo de la Solicitud**

```json
{
  "firstName": "Carlos",
  "lastName": "Rodríguez",
  "email": "carlos.rodriguez@example.com",
  "address": "Avenida Siempre Viva 742, Springfield"
}

```

**Ejemplo de Respuesta (200 OK)**

```json
{
  "message": "Información actualizada correctamente"
}

```

**Errores Posibles:**

```http
400 Bad Request  // Datos inválidos en la solicitud
401 Unauthorized  // Usuario no autenticado
404 Not Found  // Cliente no encontrado

```

----------

## 3. Eliminar Cliente

**Descripción:**  
Borra los datos del cliente, pero no su historial de transacciones.

**Ejemplo de Solicitud (DELETE /api/customers/12345)**

```http
DELETE /api/customers/12345 HTTP/1.1
Host: api.example.com
Authorization: Bearer <JWT-TOKEN>

```

**Ejemplo de Respuesta (204 No Content)**  
No se devuelve contenido, solo la confirmación del borrado.

**Errores Posibles:**

```http
404 Not Found  // Cliente no encontrado
401 Unauthorized  // No autorizado para eliminar

```

----------

## Consideraciones Finales

### Seguridad:

-   No almacenar tarjetas de crédito ni ningun dato de pago  en la base de datos.
-   Solo guardar tokens de pago en un procesador seguro (Stripe, PayPal, etc.).

### Optimización:

-   Usar Redis para caché de datos de clientes y mejorar rendimiento.




# Sección 2 - Conocimientos

## 1. En Java, ¿qué hace la palabra "synchronized"?
Permite que un solo hilo acceda a un bloque de código o método a la vez, evitando condiciones de carrera en entornos concurrentes.

## 2. ¿Qué es la inversión de control (IoC) y cómo se implementa en Spring?
Es un principio donde el control de la creación y gestión de dependencias se delega a un contenedor. En Spring se implementa con **Spring IoC Container**, usando anotaciones como `@Component`, `@Service` y `@Autowired`.

## 3. Describa el propósito de las siguientes anotaciones:
- `@SpringBootApplication`: Marca la clase principal de una aplicación Spring Boot.
- `@RestController`: Define un controlador REST que devuelve JSON o XML.
- `@GetMapping`: Mapea solicitudes HTTP GET a un método en un controlador.
- `@Autowired`: Inyecta dependencias automáticamente en Spring.

## 4. En HTML, ¿cómo empieza un documento de HTML5?
```html
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi Página</title>
</head>
<body>
</body>
</html>

```

## 5. Explica la diferencia entre var, let y const. Da un ejemplo de cuándo usarías cada uno.

-   `var`: Tiene alcance de función, se usa poco debido a problemas de ámbito.
-   `let`: Tiene alcance de bloque, recomendado para variables que cambian de valor.
-   `const`: También tiene alcance de bloque, pero no permite reasignación.




## 6. Explica qué es JSX y por qué se utiliza en React.

JSX es una extensión de JavaScript que permite escribir código similar a HTML dentro de archivos JS. Se usa en React para definir interfaces de usuario de manera declarativa.




## 7. ¿Qué evalúa la siguiente expresión regular? `^[0-9]+$`

Verifica que una cadena contenga solo números enteros positivos.

Ejemplo:

```js
console.log(/^[0-9]+$/.test("12345")); // true
console.log(/^[0-9]+$/.test("123a"));  // false

```

## 8. ¿Cuál es la diferencia entre POST/GET y PUT/PATCH?

-   **GET**: Recupera datos.
-   **POST**: Envía datos para crear un recurso.
-   **PUT**: Actualiza completamente un recurso.
-   **PATCH**: Actualiza parcialmente un recurso.






## 9. En Node.js ¿qué hace la instrucción `async`?

Convierte una función en una función asíncrona, permitiendo usar `await` dentro de ella.

Ejemplo:

```js
async function obtenerDatos() {
  let respuesta = await fetch("https://api.example.com/datos");
  let datos = await respuesta.json();
  console.log(datos);
}

```

## 10. ¿Qué es el event loop de Node.js y en qué ayuda?

Es un mecanismo que permite a Node.js manejar operaciones asíncronas sin bloquear el hilo principal. Facilita la ejecución de múltiples tareas concurrentemente.

## 11. ¿Es recomendable implementar bases de datos en contenedores? ¿Por qué?

Depende del caso. En desarrollo es útil por portabilidad, pero en producción es mejor usar bases de datos gestionadas debido a problemas de rendimiento y persistencia de datos.

## 12. Indique a qué se refiere OWASP.

El **Open Web Application Security Project (OWASP)** es una organización que proporciona estándares y buenas prácticas para mejorar la seguridad en aplicaciones web. Su lista **OWASP Top 10** destaca las principales vulnerabilidades.

Aquí tienes las respuestas en **Markdown** de manera simple y clara:

# Sección 3: Conocimiento de Frameworks

## 1. Controlador REST en Spring Boot para /greeting

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "Mundo") String name) {
        return "Hola, " + name + "!";
    }
}
```

