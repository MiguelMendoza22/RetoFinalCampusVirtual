Feature: Crear ruta de aprendizaje
  Yo como administrador del campus
  Quiero poder crear rutas de aprendizaje
  Para poder asignarlas a estudiantes

  Scenario Outline: Crear ruta de aprendizaje exitoso
    Given que el administrador se encuentra en la pagina de crear rutas
    When el administrador envia una peticion con el id del coach <coachID>, el titulo de la ruta <title> y la descripcion de la ruta <description>
    Then debera recibir un body con el id del coach <coachID>, el titulo de la ruta <title> y la descripcion de la ruta <description> y un codigo de estado <statusCode>
    Examples:
      | coachID  | title         | description                           | statusCode |
      | "Coach1" | "Ruta Python" | "Ruta para apreder Python desde cero" | 200        |
      | "Coach2" | "Ruta SQL"    | "Ruta para apreder SQL desde cero"    | 200        |
      | "Coach3" | "Ruta Java"   | "Ruta para aprender Java desde cero"  | 200        |


  Scenario Outline: Crear ruta de aprendizaje fallida
    Given que el administrador se encuentra en la pagina de crear rutas
    When el administrador envia una peticion con el id del coach <coachID>, el titulo de la ruta <title> y la descripcion de la ruta <description>
    Then debera recibir un mensaje <Message>  y un codigo de estado <statusCode>
    Examples:
      | coachID  | title         | description                           | Message                                                                                                           | statusCode |
      | ""       | "Ruta Python" | "Ruta para apreder Python desde cero" | "Ingresa por favor el id del coach, no puede ser vacio o nulo (Parameter 'CoachID')"                              | 500        |
      | "Coach2" | ""            | "Ruta para apreder SQL desde cero"    | "Ingresa un  titulo por favor, no puedes dejar el campo como nulo o vacio (Parameter 'Title')"                    | 500        |
      | "Coach3" | "Ruta SQL"    | ""                                    | "No puedes ingresar una descripcion vacia o nula, por favor ingresa alguna descripcion (Parameter 'Description')" | 500        |

