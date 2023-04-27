Feature: Editar Contenido
  Yo como administrador del campus
  Quiero poder editar los contenidos
  Para poder actualizar la información de los contenidos

  Scenario Outline:Editar contenido exitosamente
    Given que el administrador se encuentra en la pagina de actualizar contenido
    When el adminitrador envia una peticion a al contenido <id> con el cuerpo que contiene <courseID>, <title>, <description>, <type> y <duration>
    Then el adminitrador debera recibir un codigo 200
    Examples:
      | id                                     | courseID                               | title                         | description                    | type | duration |
      | "ecab12da-71ad-4a77-93ac-009b92128e8d" | "ce6ed37f-101e-4c26-b99b-0265f9c40c4a" | "reto de programacion basica" | "reto de aprobación del curso" | 3    | 3        |
      | "52d8bbe4-2264-4544-b337-01972b17383a" | "dffde725-b89f-4dd4-b08d-8f9e7489b99c" | "lectura de base de datos"    | "libro de base de datos"       | 2    | 1        |
      | "52d8bbe4-2264-4544-b337-01972b17383a" | "dffde725-b89f-4dd4-b08d-8f9e7489b99c" | "lectura de base de datos"    | "libro de base de datos"       | 2    | 1        |

  Scenario Outline:Editar contenido fallido
    Given que el administrador se encuentra en la pagina de actualizar contenido
    When el adminitrador envia una peticion a al contenido <id> con el cuerpo que contiene <courseID>, <title>, <description>, <type> y <duration>
    Then el adminitrador debera recibir un codigo de status <statusCode>
    Examples:
      | id                                     | courseID                               | title                         | description                    | type | duration | statusCode |
      | "ecab12da-71ad-4a77-93ac-009b92128e8d" | ""                                     | "reto de programacion basica" | "reto de aprobación del curso" | 3    | 3        | 400        |
      | "52d8bbe4-2264-4544-b337-01972b17383a" | "dffde725-b89f-4dd4-b08d-8f9e7489b99c" | ""                            | "libro de base de datos"       | 2    | 1        | 500        |
      | "52d8bbe4-2264-4544-b337-01972b17383a" | "dffde725-b89f-4dd4-b08d-8f9e7489b99c" | "lectura de base de datos"    | ""                             | 2    | 1        | 500        |