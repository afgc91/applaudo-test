package api;

import io.restassured.RestAssured;
import models.api.CharacterModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static steps.api.CharacterSteps.*;
import static models.api.ApplaudoResponse.AssertionType.*;
import static support.Variables.*;

public class BreakingBadTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BreakingBadTest.class);

    @BeforeEach
    void setLogger() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Get Walter White birthday")
    void getWalterWhiteBirthday() {
        CharacterModel testCharacter = getCharacter(TEST_CHARACTER_ID);
        testCharacter.assertThat(HAS_KEY, "$[0].birthday", null);
        LOGGER.info("Mr. White's birthday: " + testCharacter.birthday);
    }

    @Test
    @DisplayName("Get characters portrayed")
    void getCharactersPortrayed() {
        ArrayList<CharacterModel> characters = getCharacters();
        characters.forEach(character -> LOGGER.info("Character Name: " + character.name + "\nPortrayed: " +
                character.portrayed + "\n" + LOGGER_LINE_DIVIDER));
    }
}
