package uk.gov.homeoffice.borders.bpmspike;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;


@Deployment(resources = {"test-process.bpmn"})
public class BpmnProcessTest {
    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();

    @Test
    public void shouldStartProcess() {
        final var runtimeService = processEngineRule.getRuntimeService();

        final var processInstance = runtimeService.startProcessInstanceByKey("TestProcess");

        assertThat(processInstance).isStarted()
                                   .isWaitingAt("FrobWidget");
    }

    @Test
    public void shouldCompleteUserTask() {
        final var runtimeService = processEngineRule.getRuntimeService();
        final var taskService = processEngineRule.getTaskService();
        final var processInstance = runtimeService.startProcessInstanceByKey("TestProcess");

        final var task = taskService.createTaskQuery().taskName("Frob Widget").singleResult();
        taskService.complete(task.getId());

        assertThat(processInstance).isEnded();
    }
}
