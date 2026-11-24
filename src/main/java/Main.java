import hospital.HospitalInformation;
import hospital.MedicalNotification;
import services.Scheduler;
import ui.WardFrame;

public class Main {
    public static void main(String[] args) {
        // 1 建管理类
        Scheduler scheduler = new Scheduler();
        // 2 建窗口
        WardFrame frame = new WardFrame(scheduler);

        // 3 while 循环(题目给了 waitAnHour → 用while)
        while (true){
            MedicalNotification n = HospitalInformation.getMedicalNotificationFromDoctor();
            scheduler.hourlyUpdate(n);
            frame.refresh();

            int hour = HospitalInformation.getHourCount();
            if (hour > 0 && hour % 24 == 0) {
                scheduler.printReport(hour);
            }
            HospitalInformation.waitAnHour();
        }
    }
}
