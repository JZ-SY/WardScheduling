package services;
import hospital.HospitalInformation;

// Abstraction: 四种服务的共同骨架;时长由子类决定
public abstract class Service {
    private final String name; // "Ultrasound"
    private final int durationHours;
    private String location; // 现在在哪个 bay
    private int hoursRemaining = 0; // >0 表示正在工作

    // Encapsulation: 只给子类 super 使用
    protected Service(String name, int durationHours, String initialBay) {
        this.name = name;
        this.durationHours = durationHours;
        this.location = initialBay;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public boolean isActive() { return hoursRemaining > 0; }

    /**
     * 题目规则:active 时不能被重新部署 → 用异常表达合同外的调用
     */
    public void deployTo(String bay) throws ServiceBusyException {
        if (isActive()) {
            throw new ServiceBusyException(name + " is busy for " + hoursRemaining + "h");
        }
        // 不在同一个 bay 才叫 porter
        if (!bay.equals(location)) {
            HospitalInformation.requestPorter(bay, name);
            location = bay;
        }
        hoursRemaining = durationHours;
    }

    /** 每小时被管理类调用一次 */
    public void tick() {
        if (hoursRemaining > 0) {
            hoursRemaining--;
        }
    }

    /** 子类可重写(Polymorphism),默认实现够用 */
    public String getStatusText() {
        return name + (isActive() ? " (" + hoursRemaining + "h left)" : " (idle)");
    }

    @Override
    public String toString() {
        return getStatusText() + " @ " + location;
    }
}
