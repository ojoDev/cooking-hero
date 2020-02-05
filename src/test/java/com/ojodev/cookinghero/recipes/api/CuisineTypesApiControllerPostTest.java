package com.ojodev.cookinghero.recipes.api;

import com.google.common.net.HttpHeaders;
import com.ojodev.cookinghero.recipes.business.CuisineTypesBusiness;
import com.ojodev.cookinghero.recipes.config.Messages;
import com.ojodev.cookinghero.recipes.data.CuisineTypesExamples;
import com.ojodev.cookinghero.recipes.domain.model.LanguageEnumBO;
import com.ojodev.cookinghero.recipes.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CuisineTypesApiControllerPostTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Messages messages;

    @MockBean
    private CuisineTypesBusiness cuisineTypesBusiness;

    private static final String LOCALE_ENGLISH = "en";
    private static final String LOCALE_SPANISH = "es";

    private static final String INVALID_LANGUAGE = "xx";
    private static final String INVALID_NAME = "xxxxx";

    @Test
    public void postCuisineType() throws Exception {

        this.mvc.perform(post("/cuisine-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LOCALE_ENGLISH)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(CuisineTypesExamples.CUISINE_TYPE_NEW)))
                .andExpect(status().isCreated());
        }

    @Test
    public void postNotDefaultCuisineType() throws Exception {

        this.mvc.perform(post("/cuisine-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LOCALE_ENGLISH)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(CuisineTypesExamples.CUISINE_TYPE_NEW_NO_DEFAULT_LANGUAGE)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(messages.get("error.badrequest.mustcontaindefault.code"))))
                .andExpect(jsonPath("$.description", is(messages.get("error.badrequest.mustcontaindefault.desc", LOCALE_ENGLISH))));
    }

    @Test
    public void postInvalidLanguageInCuisineTypeName() throws Exception {

        String content = "{\n" +
                "  \"names\": [\n" +
                "    {\n" +
                "      \"name\": \"veggie\",\n" +
                "      \"language\": \"en\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"invalid\",\n" +
                "      \"language\": \"xx\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        this.mvc.perform(post("/cuisine-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LOCALE_ENGLISH)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(messages.get("error.badrequest.invalidlanguage.code"))))
                .andExpect(jsonPath("$.description", is(messages.get("error.badrequest.invalidlanguage.desc", LanguageEnumBO.getValueList()))));
    }
}