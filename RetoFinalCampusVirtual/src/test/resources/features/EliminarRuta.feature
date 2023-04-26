Feature: Eliminar ruta de aprendizaje
  Como administrador del campus virtual
  quiero poder eliminar las rutas de aprendizaje del campus
  para poder actualizar la información

  Scenario Outline: eliminar ruta
    Given que el administrador esta en la pagina para eliminar rutas de aprendizaje
    When el adminitrador envia una peticion al ruta con el <id>
    Then el adminitrador debera recibir un codigo de estado <statusCode>
    Examples:
      | id                                     | statusCode |
      | "9e190903-7ed3-450c-a76b-ef7b1f971b92" | 200        |
      | "24e484b7-1856-4479-a5fc-ef657d51a645" | 200        |
      | "4471e7dc-fb9f-4f72-8688-d08eeaeb8482" | 200        |
