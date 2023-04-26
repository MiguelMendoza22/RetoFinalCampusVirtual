package com.sofkau.stepdefinitions;


import com.sofkau.models.rutasdeaprendizaje.CuerpoEditarRuta;
import com.sofkau.models.rutasdeaprendizaje.ResponseCrearRutaExitosa;
import com.sofkau.models.rutasdeaprendizaje.ResponseCrearRutaFallida;
import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import static com.sofkau.questions.rutasdeaprendizaje.ReturnCrearRutaExitosaResponse.returnCrearRutaExitosaResponse;
import static com.sofkau.questions.rutasdeaprendizaje.ReturnCrearRutaFallidaResponse.returnCrearRutaFallidaResponse;
import static com.sofkau.tasks.DoPut.doPut;
import static com.sofkau.utils.CampusVirtual.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class EditarRutaStepDefinition extends ApiSetUp {
    public static Logger LOGGER = Logger.getLogger(EditarRutaStepDefinition.class);
    private CuerpoEditarRuta cuerpoEditarRuta = new CuerpoEditarRuta();

    @Given("que el administrador se encuentra en la pagina de actualizar rutas")
    public void queElAdministradorSeEncuentraEnLaPaginaDeActualizarRutas() {
        try {
            setUp(CAMPUS_VIRTUAL_BASE_URL.getValue());
            LOGGER.info("Se inicio la automatizacion en la URL: " + CAMPUS_VIRTUAL_BASE_URL.getValue());
        } catch (Exception e) {
            LOGGER.error("Error al iniciar la automatizacion : Detalles: " + e.getMessage());
            actor.should(
                    seeThatResponse("El servidor no está disponible",
                            response -> response.statusCode(HttpStatus.SC_OK))
            );
        }
    }

    @When("el adminitrador envia una peticion a la ruta {string} con el cuerpo que contiene el id del coach {string}, el titulo de la ruta {string} y la descripcion de la ruta {string}")
    public void elAdminitradorEnviaUnaPeticionALaRutaConElCuerpoQueContieneElIdDelCoachElTituloDeLaRutaYLaDescripcionDeLaRuta(String id, String coachID, String title, String description) {
        try {
            cuerpoEditarRuta.setCoachID(coachID);
            cuerpoEditarRuta.setTitle(title);
            cuerpoEditarRuta.setDescription(description);
            cuerpoEditarRuta.setDuration(0);
            cuerpoEditarRuta.setStatePath(1);
            actor.attemptsTo(
                    doPut().withResource(EDIT_PATH_ID_RESOURCE.getValue() + id)
                            .andTheRequestBody(cuerpoEditarRuta)
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());
        }   catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud PUT: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("el adminitrador debera recibir un body con el id del coach {string}, el titulo de la ruta {string} y la descripcion de la ruta {string} y un codigo de estado {int}")
    public void elAdminitradorDeberaRecibirUnBodyConElIdDelCoachElTituloDeLaRutaYLaDescripcionDeLaRutaYUnCodigoDeEstado(String coachID, String title, String description, Integer statusCode) {
        try {
            ResponseCrearRutaExitosa responseCrearRutaExitosa = returnCrearRutaExitosaResponse().answeredBy(actor);
            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_OK,
                            response -> response.statusCode(statusCode)),
                    seeThat("Retorna coach id",
                            act -> responseCrearRutaExitosa.getCoachID(), equalTo(coachID)),
                    seeThat("Retorna titulo",
                            act -> responseCrearRutaExitosa.getTitle(), equalTo(title)),
                    seeThat("Retorna descripción",
                            act -> responseCrearRutaExitosa.getDescription(), equalTo(description))
            );
        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }
    }

    @Then("debera recibir un mensaje de error y un codigo de estado {int}")
    public void deberaRecibirUnMensajeDeErrorYUnCodigoDeEstado(Integer statusCode) {
        String responseBody = SerenityRest.lastResponse().getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        try {
            ResponseCrearRutaFallida responseCrearRutaFallida = returnCrearRutaFallidaResponse().answeredBy(actor);
            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_INTERNAL_SERVER_ERROR,
                            response -> response.statusCode(statusCode)),
                    seeThat("El campo 'Message' esta presente", value -> jsonPath.getString("Message"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'Message' no esta presente o es nulo")
            );

        } catch (Exception e) {
            LOGGER.info("Error al realizar la comparacion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }
    }


}
