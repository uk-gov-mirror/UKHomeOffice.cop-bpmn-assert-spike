package uk.gov.homeoffice.borders.bpmspike;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static java.util.Collections.singletonMap;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;


@Deployment(resources = {"test-process.bpmn"})
public class BpmnProcessTest {
    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();

    @Test
    public void shouldStartProcess() {
        final var processInstance = runtimeService().startProcessInstanceByKey("TestProcess", singletonMap("action", "frob"));

        assertThat(processInstance).isStarted()
                                   .isWaitingAt("FrobWidget");
    }

    @Test
    public void shouldCompleteUserTask() {
        final var processInstance = runtimeService().startProcessInstanceByKey("TestProcess", singletonMap("action", "frob"));

        assertThat(processInstance).isStarted();
        complete(task());
        assertThat(processInstance).isEnded();
    }

    @Test
    public void shouldCompleteExternalTask() {
        final var processInstance = runtimeService().startProcessInstanceByKey("TestProcess", singletonMap("action", "worfle"));

        assertThat(processInstance).isStarted();
        assertThat(processInstance).isWaitingAt("WorfleWidget");
        complete(externalTask());

        assertThat(processInstance).isEnded();
    }
}
