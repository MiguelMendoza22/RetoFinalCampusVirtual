package com.sofkau.questions.rutasdeaprendizaje;

import com.sofkau.models.rutasdeaprendizaje.ResponseCrearRutaExitosa;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ReturnCrearRutaExitosaResponse implements Question <ResponseCrearRutaExitosa> {
    @Override
    public ResponseCrearRutaExitosa answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(ResponseCrearRutaExitosa.class);
    }

    public static ReturnCrearRutaExitosaResponse returnCrearRutaExitosaResponse(){
        return new ReturnCrearRutaExitosaResponse();
    }
}
