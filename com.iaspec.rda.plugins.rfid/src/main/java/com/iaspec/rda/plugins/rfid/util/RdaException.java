/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.util;


import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-15
 */
public class RdaException extends RuntimeException
{
    private String message;
    private Throwable cause;

    private List<String> messages = new ArrayList<String>();

    /**
     * Default constructor. Takes no arguments.
     * Constructs a new runtime exception with null as its detail message.
     */
    public RdaException()
    {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message
     */
    public RdaException(String message)
    {
        super(message);

        this.message = message;
    }

    /**
     * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause
     */
    public RdaException(Throwable cause)
    {
        super(cause);

        this.cause = cause;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     *
     * @param message
     * @param cause
     */
    public RdaException(String message, Throwable cause)
    {
        super(message, cause);

        this.message = message;
        this.cause = cause;
    }

    /**
     * Constructs a list of runtime exceptions with the specified detail messages.
     *
     * @param messages
     */
    public RdaException(List<String> messages)
    {
        super();

        this.messages.addAll(messages);
    }


    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @return the cause
     */
    public Throwable getCause()
    {
        return cause;
    }

    /**
     * @return the messages
     */
    public List<String> getMessages()
    {
        return messages;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * @param cause the cause to set
     */
    public void setCause(Throwable cause)
    {
        this.cause = cause;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List<String> messages)
    {
        this.messages = messages;
    }
}
