package com.ojodev.cookinghero.recipes.infrastructure.repository;

import com.ojodev.cookinghero.recipes.RecipesApplication;
import com.ojodev.cookinghero.recipes.data.MeasuresExamples;
import com.ojodev.cookinghero.recipes.infrastructure.po.DescriptiveNamePO;
import com.ojodev.cookinghero.recipes.infrastructure.po.MeasurePO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class })
@SpringBootTest(classes = RecipesApplication.class)
@Slf4j
public class MeasuresRepositoryTest {

    @SpringBootApplication
    static class ExampleConfig {}

    @Autowired
    private MeasuresRepository measuresRepository;

    @Test
    @Ignore
    public void saveMeasure ()  {

        MeasurePO measurePO = new MeasurePO(MeasuresExamples.MEASURE_01_ID, Arrays.asList(
                new DescriptiveNamePO(MeasuresExamples.MEASURE_01_NAME_ENGLISH_SINGULAR, MeasuresExamples.MEASURE_01_NAME_ENGLISH_PLURAL, MeasuresExamples.LANGUAGE_EN),
                new DescriptiveNamePO(MeasuresExamples.MEASURE_01_NAME_SPANISH_SINGULAR, MeasuresExamples.MEASURE_01_NAME_SPANISH_PLURAL, MeasuresExamples.LANGUAGE_ES)));

        measuresRepository.save(measurePO);
    }

    @Test
    @Ignore
    public void findAll()  {
       List<MeasurePO> measureList = measuresRepository.findAll();
        assertNotNull(measureList);
    }

    @Test
    @Ignore
    public void findById()  {
        List<MeasurePO> measureList = measuresRepository.findByObjectId(MeasuresExamples.MEASURE_01_ID);
        assertNotNull(measureList);
        assertEquals(1, measureList.size());
    }

    @Test
    @Ignore
    public void findByIdNotFound()  {
        List<MeasurePO> measureList = measuresRepository.findByObjectId("xxx");
        assertNotNull(measureList);
        assertEquals(0, measureList.size());
    }

}
