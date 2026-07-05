#language: es
@registro
Característica: Registro de usuario
  Como visitante nuevo de OWASP Juice Shop
  Quiero poder crear una cuenta
  Para poder comprar y gestionar mis pedidos

  Antecedentes:
    Dado que el usuario esta en la pagina de registro de la aplicacion

  @regression @registro
  Esquema del escenario: Registro exitoso de nuevos usuarios
    Cuando el usuario completa el formulario de registro con datos validos para "<usuario>"
    Entonces la cuenta del usuario "<usuario>" se crea correctamente
    Y el sistema redirige al usuario a la pagina de inicio de sesion

    Ejemplos:
      | usuario   |
      | usuario1  |
      | usuario2  |
