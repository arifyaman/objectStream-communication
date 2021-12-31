package com.xlip.objectstream.communication;

import lombok.Data;

import java.io.Serializable;

@Data
public class LockedWrap implements Serializable {
    private static final long serialVersionUID = 8774778335787358404L;

    private byte[] bytes;

    public LockedWrap(byte[] bytes) {
        this.bytes = bytes;
    }
}
