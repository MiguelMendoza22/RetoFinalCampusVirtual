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
      | "a620a51d-ace4-4877-b480-35cd6c7f8c70" | 200        |
      | "9ed416fb-5b91-40d6-a30f-3871351ed9f8" | 200        |
      | "6bcfd14f-51bd-4a74-838e-48f4726a6e2d" | 200        |