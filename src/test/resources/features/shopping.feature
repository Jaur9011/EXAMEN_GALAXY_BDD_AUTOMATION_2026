#language: es
@cesta
Característica: Cesta de compras
  Como cliente autenticado con direcciones y metodos de pago registrados
  Quiero agregar productos a mi cesta y completar el pedido
  Para poder recibir mis compras

  Antecedentes:
    Dado que un usuario registrado ha iniciado sesion y cuenta con 2 direcciones y 2 metodos de pago guardados

  @regression @cesta
  Escenario: Pedido 1 - Compra usando la busqueda de productos
    Cuando el usuario busca y agrega a la cesta el producto "manzana"
    Y el usuario busca y agrega a la cesta el producto "banana"
    Y el usuario busca y agrega a la cesta el producto "camiseta"
    Y el usuario completa el pedido usando la segunda direccion y el primer metodo de pago
    Entonces el pedido se confirma exitosamente

  @regression @cesta
  Escenario: Pedido 2 - Compra de productos aleatorios del catalogo
    Cuando el usuario agrega 2 productos aleatorios del catalogo a la cesta
    Y el usuario completa el pedido usando la segunda direccion y el primer metodo de pago
    Entonces el pedido se confirma exitosamente

