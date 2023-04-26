Feature: Registrar Usuarios
  Como administrador del Campus Virtual
  Quiero registrar usuarios al Campus Virtual
  Para que los usuarios tengan credenciales de acceso al Campus Virutal

  Scenario Outline: Registro
    Given que el administrador se encuentra en la pagina de registro de usuarios
    When el adminitrador ingresa la informacion de <uidUser>, <email>, <password>  y <rol>
    Then el administrador debera ver el mensaje <mensaje> y el codigo de estado <statusCode>
    Examples:
      | uidUser | email                           | password         | rol | mensaje                  | statusCode |
      | "ID1"   | "jesusmiguelmm@ufps.edu.co"     | "Passmiguel111"  | 0   | "User Created"           | 200        |
      | "ID2"   | "melimenesesacevedo@gmail.com"  | "Passmelissa222" | 0   | "User Created"           | 200        |
      | "ID3"   | "lupitamimascota2023@gmail.com" | "Passlupita333"  | 1   | "User Created"           | 200        |
      | "ID4"   | "ospina_88@hotmail.com"         | "Passnevardo444" | 1   | "User Created"           | 200        |
      | "ID5"   | "jesusmiguelmm@ufps.edu.co"     | "Passmiguel111"  | 0   | "uidUser already exists" | 200        |
      | "ID6"   | "juanmanuelgofu@gmail.com"      | "Mipasss"        | 0   | "Incorrect format"       | 200        |
      | "ID7"   | "nevardospina8820@gmail.com"    | ""               | 1   | "Incorrect format"       | 200        |
      | "ID7"   | ""                              | "Passprueba666"  | 1   | "Incorrect format"       | 200        |
      | "ID8"   | ""                              | ""               | 1   | "Incorrect format"       | 200        |


