package services;
// Inheritance: 只需告诉父类"我叫什么､要几小时､一开始在哪"
public class UltrasoundScanner extends Service {
    public UltrasoundScanner(String bay) {
        super("Ultrasound", 3, bay);
    }
}
