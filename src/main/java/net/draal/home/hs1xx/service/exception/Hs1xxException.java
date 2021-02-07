package net.draal.home.hs1xx.service.exception;

public class Hs1xxException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public Hs1xxException(String message, Throwable cause) {
        super(message, cause);
    }
}
