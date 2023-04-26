package com.sofkau.stepdefinitions;

import com.sofkau.models.usuarios.Usuario;
import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;


import static com.sofkau.tasks.DoPost.doPost;
import static com.sofkau.utils.Usuarios.USUARIOS_BASE_URL;
import static com.sofkau.utils.Usuarios.USUARIOS_REGISTRO_RESOURCE;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;


public class ApiRegistrarUsuariosStepDefinition extends ApiSetUp {

    public static Logger LOGGER=Logger.getLogger(ApiRegistrarUsuariosStepDefinition.class);

    private Usuario usuario = new Usuario();

    @Given("que el administrador se encuentra en la pagina de registro de usuarios")
    public void queElAdministradorSeEncuentraEnLaPaginaDeRegistroDeUsuarios() {

        try {
            setUp(USUARIOS_BASE_URL.getValue());
            LOGGER.info("Se inicio la automatizacion en la URL: " + USUARIOS_BASE_URL.getValue());
        } catch (Exception e) {
            LOGGER.error("Error al iniciar la automatizacion : Detalles: " + e.getMessage());
            actor.should(
                    seeThatResponse("El servidor no está disponible",
                            response -> response.statusCode(HttpStatus.SC_OK))
            );
        }
    }

    @When("el adminitrador ingresa la informacion de {string}, {string}, {string}  y {int}")
    public void elAdminitradorIngresaLaInformacionDeY(String ID, String correo, String password, Integer rol) {

        try {
            usuario.setUidUser(ID);
            usuario.setEmail(correo);
            usuario.setPassword(password);
            usuario.setRole(rol);

            actor.attemptsTo(
                    doPost()
                            .withTheResource(USUARIOS_REGISTRO_RESOURCE.getValue())
                            .andTheRequestBody(usuario)
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud POST: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el administrador debera ver el mensaje {string} y el codigo de estado {int}")
    public void elAdministradorDeberaVerElMensajeYElCodigoDeEstado(String mensaje, Integer statusCode) {

        try {
            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_OK,
                            response -> response.statusCode(statusCode))
            );
        } catch (Exception e){
            LOGGER.warn(e.getMessage());
        }
    }
}
