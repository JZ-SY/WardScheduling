package services;

public class ServiceBusyException extends Exception {
    public ServiceBusyException(String msg) {
        super(msg);
    }
}
