package services;

public class DialysisMachine extends Service {
    public DialysisMachine(String bay) {
        super("Dialysis", 4, bay);
    }

    // Polymorphism: 重写，自定义显示
    @Override
    public String getStatusText() {
        return "Dialysis" + (isActive() ? " [RUNNING]" : " (idle)");
    }
}
