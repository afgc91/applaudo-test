package models.api;

import io.restassured.response.Response;

import static utils.JsonUtils.getObjectFromJsonAsString;

public class CharacterModel extends ApplaudoResponse {

    public int id;
    public String name;
    public String birthday;
    public String occupation;
    public String img;
    public String status;
    public String nickname;
    public String appearance;
    public String portrayed;
    public String category;
    public String betterCallSaulAppearance;

    public CharacterModel(Response response) {
        super(response);
        String responseStr = response.asString();
        loadCharacterProperties(responseStr, 0);
    }

    public CharacterModel(Response response, int index) {
        super(response);
        String responseStr = response.asString();
        loadCharacterProperties(responseStr, index);
    }

    private void loadCharacterProperties(String responseStr, int index) {
        this.id = Integer.parseInt(getObjectFromJsonAsString(responseStr, "$[" + index + "].char_id"));
        this.name = getObjectFromJsonAsString(responseStr, "$[" + index + "].name");
        this.birthday = getObjectFromJsonAsString(responseStr, "$[" + index + "].birthday");
        this.occupation = getObjectFromJsonAsString(responseStr, "$[" + index + "].occupation");
        this.img = getObjectFromJsonAsString(responseStr, "$[" + index + "].img");
        this.status = getObjectFromJsonAsString(responseStr, "$[" + index + "].status");
        this.nickname = getObjectFromJsonAsString(responseStr, "$[" + index + "].nickname");
        this.appearance = getObjectFromJsonAsString(responseStr, "$[" + index + "].appearance");
        this.portrayed = getObjectFromJsonAsString(responseStr, "$[" + index + "].portrayed");
        this.category = getObjectFromJsonAsString(responseStr, "$[" + index + "].category");
        this.betterCallSaulAppearance = getObjectFromJsonAsString(responseStr, "$[" + index + "].better_call_saul_appearance");
    }
}
