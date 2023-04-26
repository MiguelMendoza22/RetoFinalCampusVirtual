Feature:Crear curso
  Yo como administrador del campus
  Quiero poder crear cursos nuevos
  Para poder añadirlos a las rutas de aprendizaje

  Scenario:    Crear un curso exitosamente
    Given    que el administrador se encuentra en la pagina de cursos
    When    el administrador crea un curso con la informacion requerida
    Then    el administrador debera ver un mensaje de confirmacion de creacion del curso y validar el codigo de estado 200

  Scenario Outline:    Crear curso sin ingresar alguno de los campos
    Given    que el administrador se encuentra en la pagina de cursos
    When     ingresa la informacion de <titulo> y <descripcion>
    Then     y validar el codigo de estado <statuscode>
    Examples:
      | titulo                        | descripcion                                  | statuscode |
      | ""                            | "Aprende los fundamentos de la programacion" | "500"      |
      | "Fundamentos de programacion" | ""                                           | "500"      |
