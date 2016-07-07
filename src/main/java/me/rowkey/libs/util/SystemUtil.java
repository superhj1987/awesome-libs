package me.rowkey.libs.util;

import java.io.IOException;

/**
 * Author: Bryant Hang
 * Date: 16/7/7
 * Time: 18:09
 */
public class SystemUtil {
    public static void killByPid(String str) {
        final String[] Array = {"ntsd.exe", "-c", "q", "-p", str};
        int i = 0;
        try {
            Process process = Runtime.getRuntime().exec(Array);
            process.waitFor();
        } catch (InterruptedException e) {
            System.out.println("run err!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (i != 0) {
            try {
                Process process = Runtime.getRuntime().exec(Array);
                process.waitFor();
            } catch (InterruptedException e) {
                System.out.println("err!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getMacBytes(String macStrsrc) {
        byte bytes[] = new byte[6];
        String hex[] = macStrsrc.split("-");
        if (hex.length != 6)
            throw new IllegalArgumentException("Invalid MAC address");
        try {
            for (int i = 0; i < 6; i++)
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid hex digit in MAC address");
        }
        return bytes;
    }
}
