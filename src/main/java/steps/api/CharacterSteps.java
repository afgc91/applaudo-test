package steps.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import controllers.api.ApiConsumer;
import io.restassured.response.Response;
import models.api.CharacterModel;

import java.util.ArrayList;
import java.util.Objects;

import static support.Variables.*;
import static controllers.api.ApiConsumer.HttpMethod.GET;
import static models.api.ApplaudoResponse.ResponseCode.OK;
import static utils.JsonUtils.getObjectFromJson;

public class CharacterSteps {
    private static ApiConsumer apiConsumer;
    private static CharacterModel characterModel;

    public static CharacterModel getCharacter(String characterId) {
        apiConsumer = new ApiConsumer();
        apiConsumer.setServicePath(CHARACTER_PATH);
        apiConsumer.setHttpMethod(GET);
        apiConsumer.addPathParameter("characterId", characterId);
        apiConsumer.sendRequest();

        characterModel = new CharacterModel(apiConsumer.getResponse());
        characterModel.assertThat(OK);
        return characterModel;
    }

    public static ArrayList<CharacterModel> getCharacters() {
        apiConsumer = new ApiConsumer();
        apiConsumer.setServicePath(CHARACTERS_PATH);
        apiConsumer.setHttpMethod(GET);
        apiConsumer.sendRequest();

        Response response = apiConsumer.getResponse();
        int numberOfCharacters = ((ArrayNode) Objects.requireNonNull(getObjectFromJson(response.asString(), "$"))).size();

        ArrayList<CharacterModel> characters = new ArrayList<>();

        for (int i = 0; i < numberOfCharacters; i++) {
            characterModel = new CharacterModel(response, i);
            if (i == 0) {
                characterModel.assertThat(OK);
            }
            characters.add(characterModel);
        }

        return characters;
    }
}
