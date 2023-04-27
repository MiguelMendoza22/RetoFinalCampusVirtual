Feature: Obtener
  Yo como usuario del campus
  Quiero poder obtener los contenidos del curso de la plataforma
  Para  poder visualizar el contenido de los cursos disponibles


  @ObtenerContenidosDeLosCursos
  Scenario: Obtener todos los contenidos de los cursos
    Given que el usuario se encuentra en la pagina de obtener contenido
    When el usuario envia una peticion para obtener todos los contenidos
    Then el usuario debera recibir un body de respuesta y un codigo de estado 200


  @ObtenerContenidosDeUnCurso
  Scenario Outline: Obtener contenidos de un curso
    Given que el usuario se encuentra en la pagina de obtener contenido
    When el usuario envia una peticion para obtener el contenido del curso por <id>
    Then el usuario debera ver un body de respuesta con el <title> del contenido y un codigo de estado <statusCode>
    Examples:
      | id                                     | title                         | statusCode |
      | "ecab12da-71ad-4a77-93ac-009b92128e8d" | "reto programacion basica"    | 200        |
      | "52d8bbe4-2264-4544-b337-01972b17383a" | "Leccion programacion basica" | 200        |
      | "affee3d0-e9f3-4b9f-8111-822d9d22402b" | "Taller programacion basica"  | 200        |
