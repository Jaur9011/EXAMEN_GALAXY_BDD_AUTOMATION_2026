#language: es
@pago
Característica: Agregar metodos de pago
  Como cliente autenticado
  Quiero registrar tarjetas de credito o debito en mi cuenta
  Para poder usarlas al completar mis pedidos

  Antecedentes:
    Dado que un usuario registrado ha iniciado sesion en la aplicacion

  @regression @pago
  Escenario: Agregar dos metodos de pago
    Cuando el usuario agrega una tarjeta de pago valida
    Y el usuario agrega una segunda tarjeta de pago valida
    Entonces el usuario cuenta con 2 metodos de pago guardados en su cuenta
