package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import no.lamatech.abb.freeathome.ProjectManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ProjectTest {

    static ProjectManager projectManager;

    @BeforeClass
    public static void init() {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.registerModules(
                new JacksonXmlModule(),
                new JaxbAnnotationModule().setPriority(JaxbAnnotationModule.Priority.SECONDARY)
        );
        projectManager = new ProjectManager(mapper);
    }

    @Test
    public void testProject() throws IOException {
        InputStream stream = ProjectTest.class.getClassLoader().getResourceAsStream("getall.xml");
        Project project = projectManager.createWithGetAll(StreamUtils.copyToString(stream, Charset.defaultCharset()));
        project.getDevice("ABB700C92AB4").toString();
        Assert.assertNotNull(project);
    }

    @Test
    public void testUpdate() throws IOException {
        InputStream updateStream = ProjectTest.class.getClassLoader().getResourceAsStream("update.xml");
        Project updatedProject = projectManager.update(
                StreamUtils.copyToString(updateStream, Charset.defaultCharset())
        );
        Assert.assertNotNull(updatedProject);
    }
}