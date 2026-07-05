#language: es
@historial
Característica: Historial de pedidos
  Como cliente que ya realizo compras
  Quiero revisar mi historial de pedidos
  Para verificar el estado de mis ordenes completadas

  Antecedentes:
    Dado que un usuario registrado completo 2 pedidos usando la segunda direccion y el primer metodo de pago

  @regression @historial
  Escenario: Revisar el historial y capturar evidencia de los pedidos completados
    Cuando el usuario abre su historial de pedidos
    Entonces el historial muestra al menos 2 pedidos completados
    Y se captura evidencia screenshot de cada uno de los 2 pedidos
