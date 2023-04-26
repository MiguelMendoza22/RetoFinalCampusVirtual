Feature:Obtener cursos
  Yo como usuario del campus
  Quiero poder obtener los cursos de la plataforma
  Para  poder visualizar los cursos disponibles

  @ObtenerUnCurso
  Scenario Outline: Obtener un curso
    Given    que el usuario se encuentra en la pagina de cursos
    When    el usuario busca un curso por un id <id> en especifico
    Then    el usuario debera ver la informacion correspondiente al curso buscado

    Examples:
      | id                                     |
      | "CE6ED37F-101E-4C26-B99B-0265F9C40C4A" |
      | "1E27ABD0-1999-4362-B230-E5991B8D37EF" |
      | "F237A695-14B2-42CC-98A9-B0516B350841" |


  @ObtenerUnCursoQueNoExiste
  Scenario Outline: Obtener curso que no existe
    Given    que el usuario se encuentra en la pagina de cursos
    When    el usuario busca un curso con un <id> que no ha sido creado
    Then    el usuario debera obtener un codigo de estado <statusCode> y un json de respuesta

    Examples:
      | statusCode | id                                     |
      | "500"      | "CE6ED37F-101E-4C26-B59B-0265F9C40C7A" |
      | "500"      | "CE6ED37F-101E-4C26-B99B-0265F9C40C3B" |