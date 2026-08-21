# Deploy con Docker

Este proyecto ya viene con `Dockerfile` para el backend, `Dockerfile` para
el frontend, y un `docker-compose.yml` en la raíz que levanta todo junto
(MySQL + backend + frontend).

## 1. Requisitos

- Docker y Docker Compose instalados.
- Una cuenta de email para mandar los mails de verificación/reset de
  password (Gmail con "contraseña de aplicación", o un SMTP de pruebas
  como Mailtrap si solo querés probar).

## 2. Configurar variables de entorno

```bash
cp .env.example .env
```

Editá `.env` y completá al menos:

- `DB_PASSWORD` — contraseña de MySQL.
- `JWT_SECRET` — un secreto largo y random (`openssl rand -base64 48`).
- `MAIL_USERNAME` / `MAIL_PASSWORD` — credenciales SMTP.

Para correr todo **localmente** los valores por defecto de
`APP_BACKEND_URL` / `APP_FRONTEND_URL` (`localhost`) ya sirven tal cual.

Para desplegar en un **servidor real**, cambiá esas dos variables por tus
dominios reales antes de buildear, por ejemplo:

```env
APP_BACKEND_URL=https://api.tudominio.com
APP_FRONTEND_URL=https://tudominio.com
```

(`APP_BACKEND_URL` se usa tanto para el link de verificación de email como
para que el frontend sepa a qué URL pegarle — se "hornea" dentro del build
del frontend, así que si la cambiás tenés que volver a buildear la imagen
del frontend, no alcanza con reiniciar el contenedor).

## 3. Levantar todo

```bash
docker compose up --build
```

Esto levanta:

- MySQL en `localhost:3306`
- Backend en `localhost:8080`
- Frontend en `localhost:3000`

El backend espera a que MySQL esté healthy antes de arrancar.

## 4. Verificar que levantó bien

```bash
docker compose logs -f backend
```

Si ves errores de conexión a MySQL, esperá unos segundos más — la primera
vez que levanta MySQL puede tardar en inicializar.

Entrá a `http://localhost:3000` y probá registrar un usuario. Vas a
necesitar revisar el email configurado en `MAIL_USERNAME` para el link de
verificación (o mirar los logs del contenedor si usás un SMTP de pruebas
tipo Mailtrap, que te muestra los emails capturados en su panel web en vez
de mandarlos de verdad).

## 5. Producción: cosas a tener en cuenta

- **`DDL_AUTO`**: por defecto es `update` (cómodo para desarrollo, Hibernate
  ajusta el schema solo). Una vez que el schema esté estable en producción,
  cambialo a `validate` en `.env` para que Hibernate nunca modifique la
  base de datos sola — solo valide que coincide con las entidades.
- **HTTPS**: ni el backend ni el frontend traen HTTPS propio. En
  producción normalmente vas a poner esto detrás de un reverse proxy
  (nginx, Caddy, Traefik, o el load balancer del hosting que uses) que
  termine TLS y le pase el tráfico a los contenedores.
- **Backups de MySQL**: el volumen `mysql_data` persiste los datos entre
  reinicios de los contenedores, pero no es un backup. Para producción
  real conviene un backup automático aparte (dump periódico, snapshot del
  volumen, o usar una base de datos gestionada en vez de MySQL en un
  contenedor).
- **Página de reset de password**: el email de "olvidé mi contraseña"
  linkea a `{APP_FRONTEND_URL}/reset-password?token=...`, pero esa página
  del frontend todavía no existe — es la única pieza de la app que quedó
  pendiente. Si te interesa, la armamos en otra iteración.
