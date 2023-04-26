package com.sofkau.stepdefinitions;


import com.sofkau.models.rutasdeaprendizaje.CuerpoCrearRuta;
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
import static com.sofkau.tasks.DoPost.doPost;
import static com.sofkau.utils.CampusVirtual.CAMPUS_VIRTUAL_BASE_URL;

import static com.sofkau.utils.CampusVirtual.CREATE_PATH_RESOURCE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;


public class CrearRutaStepDefinition extends ApiSetUp {
    public static Logger LOGGER = Logger.getLogger(CrearRutaStepDefinition.class);
    private CuerpoCrearRuta cuerpoCrearRuta = new CuerpoCrearRuta();

    @Given("que el administrador se encuentra en la pagina de crear cursos")
    public void queElAdministradorSeEncuentraEnLaPaginaDeCrearCursos() {

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

    @When("el administrador envia una peticion con el id del coach {string}, el titulo de la ruta {string} y la descripcion de la ruta {string}")
    public void elAdministradorEnviaUnaPeticionConElIdDelCoachElTituloDeLaRutaYLaDescripcionDeLaRuta(String coachID, String title, String description) {

        try {
            cuerpoCrearRuta.setCoachID(coachID);
            cuerpoCrearRuta.setTitle(title);
            cuerpoCrearRuta.setDescription(description);
            actor.attemptsTo(
                    doPost()
                            .withTheResource(CREATE_PATH_RESOURCE.getValue())
                            .andTheRequestBody(cuerpoCrearRuta)
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al enviar la solicitud POST: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Then("debera recibir un body con el id del coach {string}, el titulo de la ruta {string} y la descripcion de la ruta {string} y un codigo de estado {int}")
    public void deberaRecibirUnBodyConElIdDelCoachElTituloDeLaRutaYLaDescripcionDeLaRutaYUnCodigoDeEstado(String coachID, String title, String description, Integer statusCode) {

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

    @Then("debera recibir un mensaje {string}  y un codigo de estado {int}")
    public void deberaRecibirUnMensajeYUnCodigoDeEstado(String Message, Integer statusCode) {
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
