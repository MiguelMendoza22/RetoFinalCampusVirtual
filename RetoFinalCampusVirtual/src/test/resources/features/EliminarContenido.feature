Feature: Eliminar Contenido
  Yo como administrador del campus
  Quiero poder eliminar los contenidos
  Para tener en la plataforma solo los contenidos adecuados

  Scenario Outline: eliminar contendio
    Given que el administrador esta en la pagina para eliminar contenidos
    When el adminitrador envia una peticion on el <id> del contenido
    Then el adminitrador debera recibir un codigo  <statusCode>
    Examples:
      | id                                     | statusCode |
      | "f6a502fd-4663-47ea-9483-048256ae9803" | 200        |
      | "50bf51a0-bf36-40ba-9605-112e49f6be9c" | 200        |
      | "e182a108-8cfc-48ce-bc18-384513ad6b22" | 200        |