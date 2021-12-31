package com.xlip.objectstream.communication;

import com.xlip.objectstream.communication.sub.WrapType;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
public class Wrap implements Serializable {
    private static final long serialVersionUID = 4680319446505624100L;

    public static Wrap createRequest() {
        return new WrapBuilder().wrapType(WrapType.REQUEST).build();
    }

    public static Wrap createResponse() {
        return new WrapBuilder().wrapType(WrapType.RESPONSE).build();
    }

    private WrapType wrapType;

    private int code;
    private boolean success;
    private String message;

    private String cmd;
    private Object payload;

}
