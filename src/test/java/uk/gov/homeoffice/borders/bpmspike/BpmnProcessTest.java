package uk.gov.homeoffice.borders.bpmspike;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;


public class BpmnProcessTest {
    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();

    @Test
    @Deployment(resources = {"test-process.bpmn"})
    public void shouldStartProcess() {
        final var runtimeService = processEngineRule.getRuntimeService();

        final var processInstance = runtimeService.startProcessInstanceByKey("TestProcess");

        assertThat(processInstance).isStarted();

    }
}
