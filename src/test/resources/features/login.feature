#language: es
@login
Característica: Inicio de sesion
  Como cliente registrado de OWASP Juice Shop
  Quiero iniciar sesion en la aplicacion
  Para poder acceder a mi cuenta y comprar con confianza

  Antecedentes:
    Dado que existe un nuevo usuario registrado con credenciales validas

  @regression @login
  Escenario: Inicio de sesion con credenciales correctas
    Cuando el usuario inicia sesion con sus credenciales correctas
    Entonces el usuario accede correctamente al catalogo de productos

  @regression @login
  Escenario: Inicio de sesion con credenciales incorrectas
    Cuando el usuario inicia sesion con credenciales incorrectas
    Entonces el sistema muestra un mensaje de error de acceso
