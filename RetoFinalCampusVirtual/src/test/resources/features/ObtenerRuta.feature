Feature: Obtener rutas de aprendizaje
  Como Usuario del campus virtual
  Quiero poder obtener las rutas de aprendizaje
  Para poder visualizar las rutas de aprendizaje que existen en el campus

  @ObtenerTodasRutasAprendizaje
  Scenario: Obtener todas las rutas de aprendizaje
    Given Que el usuario se encuentra en la pagina de obtener rutas
    When el adminitrador envia una peticion para obtener todos los rutas
    Then el administrador debera recibir un body de respuesta y un codigo de estado 200


  @ObtenerUnaRutaAprendizaje
  Scenario Outline: Obtener una ruta de aprendizaje
    Given Que el usuario se encuentra en la pagina de obtener rutas
    When el adminitrador envia una peticion con el <id> de la ruta de aprendizaje
    Then el administrador debera recibir un body con el titulo <title> y un codigo de estado <statusCode>
    Examples:
      | id                                     | title         | statusCode |
      | "06a151cd-0ba2-495c-b973-04a22ee457d3" | "Ruta Java"   | 200        |
      | "3d9d49a4-d649-4d2b-b8a8-09ab99e47050" | "Ruta Python" | 200        |
      | "60b182de-6392-4c57-af05-159c9181e001" | "Ruta SQL"    | 200        |
