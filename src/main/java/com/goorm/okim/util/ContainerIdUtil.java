package com.goorm.okim.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ContainerIdUtil {
    public static String getContainerId() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "container id";
        }
    }

}