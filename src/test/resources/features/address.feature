#language: es
@direccion
Característica: Agregar direccion de envio
  Como cliente autenticado
  Quiero registrar direcciones de envio en mi cuenta
  Para poder usarlas al completar mis pedidos

  Antecedentes:
    Dado que un usuario registrado ha iniciado sesion en la aplicacion

  @regression @direccion
  Escenario: Agregar dos direcciones de envio
    Cuando el usuario agrega la direccion de envio "Direccion Uno"
    Y el usuario agrega la direccion de envio "Direccion Dos"
    Entonces el usuario cuenta con 2 direcciones guardadas en su cuenta
