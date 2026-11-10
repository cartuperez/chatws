# Actividad WebSocket

## Ejecutar

Requiere Java 21. Desde la raíz:

```powershell
.\mvnw.cmd spring-boot:run
```

Abrir `http://localhost:8080` en dos pestañas. Conectar con dos nombres distintos. Cada conexión publica el ingreso y cada mensaje aparece en ambas pestañas. Desconectar una pestaña publica su salida. El cliente bloquea el envío mientras no está conectado y permite volver a conectar.

## Recorrido

1. SockJS conecta a `/ws`; STOMP establece la sesión.
2. El cliente se suscribe a `/topic/public`.
3. Envía el nombre a `/app/chat.addUser`.
4. Envía mensajes a `/app/chat.sendMessage`.
5. El controlador utiliza el nombre registrado en la sesión y difunde el mensaje a `/topic/public`.
6. `ChatEvents` anuncia la desconexión.

`WebSocketConfig` utiliza `enableSimpleBroker` para esta actividad. El proyecto RabbitMQ reemplaza ese componente por un relay externo. Los mensajes recibidos se insertan como texto para evitar ejecutar HTML enviado por otro usuario.

Se mantiene el formato JSON `usuario`, `contenido`, `tipo` (`JOIN`, `CHAT`, `LEAVE`).

Pruebas: `.\mvnw.cmd test`. La prueba manual con dos pestañas verifica el recorrido completo del cliente SockJS/STOMP. Las bibliotecas del navegador se descargan desde jsDelivr y requieren acceso a Internet.

Referencia: [WebSocket/STOMP en Spring](https://docs.spring.io/spring-framework/reference/web/websocket/stomp.html).

Con el servidor ejecutándose, Node.js 22 o superior permite comprobar dos clientes STOMP, los ingresos, los mensajes y la salida:

```powershell
node scripts/verify-chat.mjs 8080
```
