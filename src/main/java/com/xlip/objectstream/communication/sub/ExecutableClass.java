package com.xlip.objectstream.communication.sub;

import java.io.Serializable;

public class ExecutableClass implements Serializable {
    private static final long serialVersionUID = -5711983245213357526L;

    public void execute() {
        System.out.println("Basic execution.");
    }
}
