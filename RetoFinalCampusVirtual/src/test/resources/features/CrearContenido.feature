Feature: Crear contenido
  Yo como administrador del campus
  Quiero poder crear contenidos nuevos
  Para poder añadirlos a los cursos

  Scenario Outline: Crear un contenido
    Given que el administrador se encuentra en la pagina de contenidos
    When  el adminitrador ingresa la informacion del id del curso <courseID>, titulo <title>, descripcion <description>, tipo <type> y duracion <duration>
    Then  el administrador debera recibir un codigo de estado <statusCode>

    Examples:
      | courseID                               | title                           | description                                 | type | duration | statusCode |
      | "CE6ED37F-101E-4C26-B99B-0265F9C40C4A" | "Taller programacion basica"    | "Taller de ciclos "                         | 1    | 8.0      | 200        |
      | "CE6ED37F-101E-4C26-B99B-0265F9C40C4A" | "Leccion programacion basica"   | "Leccion de arrays"                         | 2    | 2.0      | 200        |
      | "CE6ED37F-101E-4C26-B99B-0265F9C40C4A" | "reto programacion basica"      | "reto final programacion basica"            | 3    | 2.0      | 200        |
      | ""                                     | "Desarrollo web con JavaScript" | "Fundamentos de la programación en Python " | 1    | 8.0      | 400        |
      | "CE6ED37F-101E-4C26-B99B-0265F9C40C4A" | ""                              | "Fundamentos de la programación en Python"  | 1    | 2.0      | 500        |
      | "CE6ED37F-101E-4C26-B99B-0265F9C40C4A" | "Desarrollo web con JavaScript" | ""                                          | 1    | 2.0      | 500        |



