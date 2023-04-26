package com.sofkau.stepdefinitions;
import com.sofkau.models.cursos.CuerpoCrearCurso;
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
import static com.sofkau.utils.CampusVirtual.CREATE_COURSES;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.core.IsNull.notNullValue;



public class CrearCursoStepDefinition extends ApiSetUp {

    public static Logger LOGGER=Logger.getLogger(CrearCursoStepDefinition.class);

    private CuerpoCrearCurso cuerpoCrearCurso = new CuerpoCrearCurso();

    @Given("que el administrador se encuentra en la pagina de cursos")
    public void que_el_administrador_se_encuentra_en_la_pagina_de_cursos() {


        try {
            setUp(CAMPUS_VIRTUAL_BASE_URL.getValue());
            LOGGER.info("Se inicio la automatizacion en la URL: " + CAMPUS_VIRTUAL_BASE_URL.getValue());
        } catch (Exception e) {
            LOGGER.error("Error al iniciar la automatizacion : Detalles: " + e.getMessage());
            actor.should(
                    seeThatResponse("El servidor no esta disponible",
                            response -> response.statusCode(HttpStatus.SC_OK))
            );
        }
    }

    @When("el administrador crea un curso con la informacion requerida")
    public void el_administrador_crea_un_curso_con_la_informacion_requerida() {
        try {

            cuerpoCrearCurso.setTitle("Programacion Basica");
            cuerpoCrearCurso.setDescription("Aprenderas desde cero a programar para convertirte en un experto");

            actor.attemptsTo(
                    doPost()
                            .withTheResource(CREATE_COURSES.getValue())
                            .andTheRequestBody(cuerpoCrearCurso)
            );
            LOGGER.info(SerenityRest.lastResponse().body().asString());

        } catch (Exception e) {
            LOGGER.info("se ha producido un error" + e);
            LOGGER.error("Ocurrio un error al enviar la solicitud POST: " + e.getMessage());

        }

    }

    @Then("el administrador debera ver un mensaje de confirmacion de creacion del curso y validar el codigo de estado {int}")
    public void el_administrador_debera_ver_un_mensaje_de_confirmacion_de_creacion_del_curso_y_validar_el_codigo_de_estado(Integer codigo) {

        ResponseCrearRutaExitosa responseCrearRutaExitosa = returnCrearRutaExitosaResponse().answeredBy(actor);
        String responseBody = SerenityRest.lastResponse().getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        try {
            actor.should(
                    seeThatResponse("Se espera un código de respuesta: " + HttpStatus.SC_OK,
                            response -> response.statusCode(codigo)),
                    seeThat("El campo 'Title' esta presente", value -> jsonPath.getString("title"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'title' no esta presente o es nulo"),
                    seeThat("El campo 'Description' esta presente", value -> jsonPath.getString("description"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'description' no esta presente o es nulo")
            );
            LOGGER.info("Se ha validado correctamente la asercion");

        } catch (Exception e) {
            LOGGER.info("Error al realizar la asercion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }

    }


    @When("ingresa la informacion de {string} y {string}")
    public void ingresa_la_informacion_de_y(String titulo, String descripcion) {
        try {

            cuerpoCrearCurso.setTitle(titulo);
            cuerpoCrearCurso.setDescription(descripcion);

            actor.attemptsTo(
                    doPost()
                            .withTheResource(CREATE_COURSES.getValue())
                            .andTheRequestBody(cuerpoCrearCurso)
            );
            LOGGER.info("Se ha validado correctamente la asercion");

        } catch (Exception e) {
            LOGGER.info("se ha producido un error" + e);
            LOGGER.error("Ocurrio un error al enviar la solicitud POST: " + e.getMessage());

        }


    }

    @Then("y validar el codigo de estado {string}")
    public void y_validar_el_codigo_de_estado(String statuscode) {

        String responseBody = SerenityRest.lastResponse().getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        try {
            ResponseCrearRutaFallida responseCrearRutaFallida = returnCrearRutaFallidaResponse().answeredBy(actor);

            actor.should(
                    seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_INTERNAL_SERVER_ERROR,
                            response -> response.statusCode(Integer.parseInt(statuscode))),
                    seeThat("El campo 'Message' esta presente", value -> jsonPath.getString("Message"), notNullValue())
                            .orComplainWith(AssertionError.class, "El campo 'Message' no esta presente o es nulo")
            );

        } catch (Exception e) {
            LOGGER.info("Error al realizar la asercion");
            LOGGER.warn(e.getMessage());
            Assertions.fail();
        }


    }

}
