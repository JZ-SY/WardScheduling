package services;
import hospital.MedicalNotification;
import java.util.ArrayList;

// 管理类(仓库):拥有所有 Service,对外只开几个方法
public class Scheduler {
    // Polymorphism:一个列表装四种子类
    private final ArrayList<Service> services = new ArrayList<>();
    // 条件:题目说"服务忙时要等" → 需要队列
    private final ArrayList<MedicalNotification> pending = new ArrayList<>();

    public Scheduler(){
        // 条件:题目给了初始位置
        services.add(new UltrasoundScanner("Nightingale"));
        services.add(new ECGRecorder("Seacole"));
        services.add(new Masseur("Seacole"));
        services.add(new DialysisMachine("Henderson"));
    }

    // 数据获取:按名字找对象
    private Service find(String serviceName) {
        for (Service s : services) {
            if (s.getName().equals(serviceName)) {
                return s;
            }
        }
        return null;
    }

    /**
     * 每小时做的事:
     * 1 时间流逝 2 收新通知 3 尽量满足队列
     */
    public void hourlyUpdate(MedicalNotification n) {
        for (Service s : services) {
            s.tick();
        }
        if (!n.getService().equals("None")) {
            pending.add(n);
        }
        // 循环里要删 → 下标循环
        for (int i = 0; i < pending.size(); i++) {
            MedicalNotification m = pending.get(i);
            Service s = find(m.getService());
            if (s != null && !s.isActive()) {
                try {
                    s.deployTo(m.getBay());
                    pending.remove(i);
                    i--;
                } catch (ServiceBusyException e) {
                    // 理论上到不了，防御
                    System.out.println("Skipped: " + e.getMessage());
                }
            }
        }
    }

    /** 给窗口用:某个 bay 里有哪些服务 */
    public String describeBay(String bay) {
        StringBuilder sb = new StringBuilder("<html><b>" + bay + "</b>");
        for (Service s : services) {
            if (s.getLocation().equals(bay)) {
                sb.append("<br>").append(s.getStatusText());
            }
        }
        sb.append("</html>");
        return sb.toString();
    }

    public boolean bayBusy(String bay) {
        for (Service s : services) {
            if (s.getLocation().equals(bay) && s.isActive()) {
                return true;
            }
        }
        return false;
    }

    public void printReport(int hour) {
        System.out.println("=== Report at hour " + hour + " ===");
        for (Service s : services) {
            System.out.println(" " + s);
        }
        System.out.println(" Pending notifications: " + pending.size());
    }
}
