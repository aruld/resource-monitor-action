package flux;

import flux.dev.AdapterFactory;
import fluximpl.FlowChartImpl;
import fluximpl.ResourceMonitorActionImpl;

/**
 * Created by arul on 11/11/14.
 */
public class CustomFactory implements AdapterFactory {

  private FlowChartImpl flowChart;

  @Override
  public void init(FlowChartImpl flowChart) {
    this.flowChart = flowChart;
  }

  /**
   * Factory for resource monitor custom action
   * @param name name of the action
   * @return ResourceMonitorAction
   */
  public ResourceMonitorAction makeResourceMonitorAction(String name) {
    return new ResourceMonitorActionImpl(flowChart, name);
  }
}
