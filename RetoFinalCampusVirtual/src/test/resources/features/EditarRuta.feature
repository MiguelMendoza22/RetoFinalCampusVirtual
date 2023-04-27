Feature: Actualizar ruta de aprendizaje
  Como administrador del campus virtual
  Quiero poder editar las rutas de aprendizaje
  Para poder actualizar la información de las rutas de aprendizaje

  @ActualizarRutaExitoso
  Scenario Outline: Editar exitosamente
    Given que el administrador se encuentra en la pagina de actualizar rutas
    When el adminitrador envia una peticion a la ruta <id> con el cuerpo que contiene el id del coach <coachID>, el titulo de la ruta <title> y la descripcion de la ruta <description>
    Then el adminitrador debera recibir un body con el id del coach <coachID>, el titulo de la ruta <title> y la descripcion de la ruta <description> y un codigo de estado <statusCode>
    Examples:
      | id                                     | coachID   | title                              | description                        | statusCode |
      | "3D9D49A4-D649-4D2B-B8A8-09AB99E47050" | "Coach10" | "La mejor ruta de Python"          | "Ruta para ser el mejor en phyton" | 200        |
      | "CF77EF30-9B82-4F6B-AA17-107C38F8DB09" | "Coach11" | "La mejor ruta para aprender Java" | "Ruta para ser el mejor en phyton" | 200        |


  @ActualizarRutaFallida
  Scenario Outline: Editar Fallido
    Given que el administrador se encuentra en la pagina de actualizar rutas
    When el adminitrador envia una peticion a la ruta <id> con el cuerpo que contiene el id del coach <coachID>, el titulo de la ruta <title> y la descripcion de la ruta <description>
    Then debera recibir un mensaje de error y un codigo de estado 500
    Examples:
      | id                                     | coachID   | title                     | description                        |
      | "3D9D49A4-D649-4D2B-B8A8-09AB99E47050" | ""        | "La mejor ruta de Python" | "Ruta para ser el mejor en phyton" |
      | "3D9D49A4-D649-4D2B-B8A8-09AB99E47050" | "Coach10" | ""                        | "Ruta para ser el mejor en phyton" |
      | "3D9D49A4-D649-4D2B-B8A8-09AB99E47050" | "Coach10" | "La mejor ruta de Python" | ""                                 |