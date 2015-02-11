package fluximpl;

import flux.FlowContext;
import flux.ResourceMonitorAction;
import flux.notification.MailContentType;
import fluximpl.notification.MailActionImpl;
import fluximpl.rest.console.SystemResourceFormatter;
import org.hyperic.sigar.*;

import java.util.*;

/**
 * Created by arul on 11/11/14.
 */
public class ResourceMonitorActionImpl extends MailActionImpl implements ResourceMonitorAction {
  public ResourceMonitorActionImpl(FlowChartImpl flowChart, String name) {
    super(flowChart, name);
  }

  public ResourceMonitorActionImpl() {
    super(new FlowChartImpl(), "Resource Monitor Action");
  }

  private Sigar sigar = new Sigar();

  private String buildHtmlBody() throws SigarException {
    String memTableHeader = "    <table style='border-collapse:collapse;border-spacing:0;empty-cells:show;border:1px solid #cbcbcb'>\n" +
        "      <thead style='background:#e0e0e0;color:#000;text-align:left;vertical-align:bottom'>\n" +
        "        <tr>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'></th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>total</th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>used</th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>free</th>\n" +
        "        </tr>\n" +
        "    \n" +
        "      </thead>\n" +
        "      <tbody>\n";

    String memTableBody = "";
    Map<String, List<Long>> map = getMemoryInfo();

    List<Long> memRow = map.get("memRow");
    List<Long> actualRow = map.get("actualRow");
    List<Long> swapRow = map.get("swapRow");

    String tdStyle = "border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;";
    memTableBody += "<tr";
    tdStyle += "background-color:#f2f2f2;";
    memTableBody += ">";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += "Mem:";
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += SystemResourceFormatter.formatKiloBytes(memRow.get(0));
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += SystemResourceFormatter.formatKiloBytes(memRow.get(1));
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += SystemResourceFormatter.formatKiloBytes(memRow.get(2));
    memTableBody += "</td>";
    memTableBody += "</tr>\n";

    tdStyle = "border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;";
    memTableBody += "<tr";
    tdStyle += "background-color:transparent;";
    memTableBody += ">";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += "-/+ buffers/cache:";
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += SystemResourceFormatter.formatKiloBytes(actualRow.get(0));
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += SystemResourceFormatter.formatKiloBytes(actualRow.get(1));
    memTableBody += "</td>";
    memTableBody += "</tr>\n";

    tdStyle = "border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;";
    memTableBody += "<tr";
    tdStyle += "background-color:#f2f2f2;";
    memTableBody += ">";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += "Swap:";
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += SystemResourceFormatter.formatKiloBytes(swapRow.get(0));
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += SystemResourceFormatter.formatKiloBytes(swapRow.get(1));
    memTableBody += "</td>";
    memTableBody += "<td style='" + tdStyle + "'>";
    memTableBody += SystemResourceFormatter.formatKiloBytes(swapRow.get(2));
    memTableBody += "</td>";
    memTableBody += "</tr>\n";

    String memTableFooter = "      </tbody>\n" +
        "    </table>\n";

    String memTable = memTableHeader + memTableBody + memTableFooter;

    String diskTableHeader = "    <table style='border-collapse:collapse;border-spacing:0;empty-cells:show;border:1px solid #cbcbcb'>\n" + // <table class='pure-table'> border-collapse:collapse;border-spacing:0}[hidden]{display:none!important
        "      <thead style='background:#e0e0e0;color:#000;text-align:left;vertical-align:bottom'>\n" +
        "        <tr>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>Filesystem</th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>Size</th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>Used</th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>Avail</th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>Use%</th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>Mounted on</th>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>Type</th>\n" +
        "        </tr>\n" +
        "    \n" +
        "      </thead>\n" +
        "      <tbody>\n";

    List<FileSystem> sys = new ArrayList<>();
    FileSystem[] fslist = sigar.getFileSystemList();
    Collections.addAll(sys, fslist);

    Map<String, List<String>> diskMap = new HashMap<>();
    for (FileSystem sy : sys) {
      if (sy.getDevName().startsWith("/dev/")) {
        List<String> diskInfo = getDiskInfo(sy);
        diskMap.put(sy.getDevName(), diskInfo);
      }
    }

    String diskTableBody = "";

    int i = 0;
    for (Map.Entry<String, List<String>> entry : diskMap.entrySet()) {
      diskTableBody += "<tr";
      String diskTdStyle = "border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;";
      if (i % 2 == 0) {
        diskTdStyle += "background-color:#f2f2f2;";
      } else {
        diskTdStyle += "background-color:transparent;";
      }
      diskTableBody += ">";

      List<String> diskInfo = entry.getValue();
      for (String info : diskInfo) {
        diskTableBody += "<td style='" + diskTdStyle + "'>";
        diskTableBody += info;
        diskTableBody += "</td>";
      }
      diskTableBody += "</tr>\n";
      ++i;
    }

    String diskTableFooter = "      </tbody>\n" +
        "    </table>\n";

    String diskTable = diskTableHeader + diskTableBody + diskTableFooter;


    String cpuTableHeader = "    <table style='border-collapse:collapse;border-spacing:0;empty-cells:show;border:1px solid #cbcbcb'>\n" +
        "      <thead style='background:#e0e0e0;color:#000;text-align:left;vertical-align:bottom'>\n" +
        "        <tr>\n" +
        "          <th style='border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;'>Cpu</th>\n" +
        "        </tr>\n" +
        "    \n" +
        "      </thead>\n" +
        "      <tbody>\n";

    String cpuTableBody = "";

    cpuTableBody += "<tr";
    String cpuTdStyle = "border-left:1px solid #cbcbcb;border-width:0 0 0 1px;font-size:inherit;margin:0;overflow:visible;padding:6px 12px;background-color:#f2f2f2;";
    cpuTableBody += ">";

    String cpuUsage = String.format("%.2f", getCpuUsage());
    cpuTableBody += "<td style='" + cpuTdStyle + "'>";
    cpuTableBody += cpuUsage + "%";
    cpuTableBody += "</td>";
    cpuTableBody += "</tr>\n";

    String cpuTableFooter = "      </tbody>\n" +
        "    </table>\n";


    String cpuTable = cpuTableHeader + cpuTableBody + cpuTableFooter;

    String body = "<h2>" + sigar.getFQDN() + "</h2>";

    body += cpuTable + "<br>" + memTable + "<br>" + diskTable;

    return body;
  }

  private List<String> getDiskInfo(FileSystem fs) throws SigarException {
    long used, avail, total, pct;
    try {
      FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
      used = usage.getTotal() - usage.getFree();
      avail = usage.getAvail();
      total = usage.getTotal();
      pct = (long) (usage.getUsePercent() * 100);
    } catch (SigarException e) {
      used = avail = total = pct = 0;
    }
    String usePct;
    if (pct == 0) {
      usePct = "-";
    } else {
      usePct = pct + "%";
    }
    List<String> items = new ArrayList<>();
    items.add(fs.getDevName());
    items.add(formatSize(total));
    items.add(formatSize(used));
    items.add(formatSize(avail));
    items.add(usePct);
    items.add(fs.getDirName());
    items.add(fs.getSysTypeName() + "/" + fs.getTypeName());
    return items;
  }

  private String formatSize(long size) {
    return Sigar.formatSize(size * 1024);
  }

  private Map<String, List<Long>> getMemoryInfo() throws SigarException {
    Map<String, List<Long>> map = new HashMap<>();
    Mem mem = sigar.getMem();
    Swap swap = sigar.getSwap();
    map.put("memRow", Arrays.asList(format(mem.getTotal()),
        format(mem.getUsed()),
        format(mem.getFree())));
    if ((mem.getUsed() != mem.getActualUsed()) ||
        (mem.getFree() != mem.getActualFree())) {
      map.put("actualRow", Arrays.asList(format(mem.getActualUsed()),
          format(mem.getActualFree())));
    }
    map.put("swapRow", Arrays.asList(format(swap.getTotal()),
        format(swap.getUsed()),
        format(swap.getFree())));

    return map;
  }

  private Long format(long value) {
    return value / 1024;
  }

  private float getCpuUsage() throws SigarException {
    CpuPerc cpuPerc = sigar.getCpuPerc();
    return (float) cpuPerc.getCombined() * 100;
  }

  @Override
  public Object execute(FlowContext a) throws Exception {
    setContentType(MailContentType.TEXT_HTML);
    try {
      setBody(buildHtmlBody());
    } finally {
      if (sigar != null)
        sigar.close();
    }
    return super.execute(a);
  }
}
