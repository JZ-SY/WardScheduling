package services;

public class ECGRecorder extends Service {
    public ECGRecorder(String bay) {
        super("ECG", 1, bay);
    }
}

