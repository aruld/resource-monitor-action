import flux.*;

import java.util.Arrays;

/**
 * Created by arul on 11/11/14.
 */
public class ResourceMonitorActionTest {
  public static void main(String[] args) throws Exception {

    // First, make a Flux engine.
    Factory factory = Factory.makeInstance();
    Configuration configuration = new Configuration();
    configuration.setDatabaseType(DatabaseType.H2);
    configuration.setSecurityEnabled(false);
    Engine engine = factory.makeEngine(configuration);

    // start the engine
    engine.start();

    // Flux models jobs using workflows. Create a workflow.
    FlowChart workflow = EngineHelper.makeFlowChart("System Resource Monitor Workflow");

    // Our flow chart will consist of a custom action. Here is how we
    // load custom actions and triggers.

    // Create a resource monitor action to monitor system that is running Flux engine.
    CustomFactory actionFactory = (CustomFactory) workflow.makeFactory("CustomFactory");
    ResourceMonitorAction action = actionFactory.makeResourceMonitorAction("Resource Monitor Action");
    action.setSubject("System Resource Info");
    action.setFromAddress("arul@flux.ly");
    action.setToAddresses(Arrays.asList("flux.arul@outlook.com"));
    action.setMailServer("smtp-mail.outlook.com");
    action.setUsername("user");
    action.setPassword("pass");

    // schedule the workflow
    String name = engine.put(workflow);

    engine.join(name, "+60s", "+5s");

    // dispose the engine
    engine.dispose();

  }
}
