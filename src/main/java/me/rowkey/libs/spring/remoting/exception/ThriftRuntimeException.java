package me.rowkey.libs.spring.remoting.exception;

/**
 * Author: Bryant Hang
 * Date: 15/5/22
 * Time: 09:27
 */
public class ThriftRuntimeException extends RuntimeException {
    public ThriftRuntimeException() {
    }

    public ThriftRuntimeException(String message) {
        super(message);
    }

    public ThriftRuntimeException(Throwable cause) {
        super(cause);
    }

    public ThriftRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
