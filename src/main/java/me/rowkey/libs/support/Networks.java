package me.rowkey.libs.support;

import com.google.common.collect.Lists;
import me.rowkey.libs.cache.LRUCache;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Bryant.Hang on 2017/5/9.
 */
public final class Networks {
    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");

    private static final int RND_PORT_START = 30000;

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final int RND_PORT_RANGE = 10000;

    private static final int MAX_PORT = 65535;

    private Networks(){

    }

    public static boolean isInvalidLocalHost(String host) {
        return host == null
                || host.length() == 0
                || host.equalsIgnoreCase("localhost")
                || host.equals("0.0.0.0")
                || (LOCAL_IP_PATTERN.matcher(host).matches());
    }

    public static int getAvailablePort() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket();
            ss.bind(null);
            return ss.getLocalPort();
        } catch (IOException e) {
            return getRandomPort();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static int getAvailablePort(int port) {
        if (port <= 0) {
            return getAvailablePort();
        }
        for (int i = port; i < MAX_PORT; i++) {
            ServerSocket ss = null;
            try {
                ss = new ServerSocket(i);
                return i;
            } catch (IOException e) {
                // continue
            } finally {
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return port;
    }

    public static int getRandomPort() {
        return RND_PORT_START + RANDOM.nextInt(RND_PORT_RANGE);
    }

    public static InetSocketAddress toAddress(String address) {
        int i = address.indexOf(':');
        String host;
        int port;
        if (i > -1) {
            host = address.substring(0, i);
            port = Integer.parseInt(address.substring(i + 1));
        } else {
            host = address;
            port = 0;
        }
        return new InetSocketAddress(host, port);
    }

    public static String toAddressString(InetSocketAddress address) {
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }

    public static String toURL(String protocol, String host, int port, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(protocol).append("://");
        sb.append(host).append(':').append(port);
        if (path.charAt(0) != '/')
            sb.append('/');
        sb.append(path);
        return sb.toString();
    }

    /**
     * @param hostName
     * @return ip address or hostName if UnknownHostException
     */
    public static String getIpByHost(String hostName) {
        try {
            return InetAddress.getByName(hostName).getHostAddress();
        } catch (UnknownHostException e) {
            return hostName;
        }
    }

    private static final Map<String, String> hostNameCache = new LRUCache<String, String>(1000);

    public static String getHostName(String address) {
        try {
            int i = address.indexOf(':');
            if (i > -1) {
                address = address.substring(0, i);
            }
            String hostname = hostNameCache.get(address);
            if (hostname != null && hostname.length() > 0) {
                return hostname;
            }
            InetAddress inetAddress = InetAddress.getByName(address);
            if (inetAddress != null) {
                hostname = inetAddress.getHostName();
                hostNameCache.put(address, hostname);
                return hostname;
            }
        } catch (Throwable e) {
            // ignore
        }
        return address;
    }

    private static final String HEADER_USER_AGENT_KEY = "user-agent";

    /**
     * 从http请求中获取user agent
     *
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        Enumeration<String> names = request.getHeaderNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            if (HEADER_USER_AGENT_KEY.equalsIgnoreCase(name)) {
                return request.getHeader(name);
            }
        }
        return null;
    }

    /**
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        return getClientIP(request, false);
    }

    /**
     * @param request
     * @param transLocalIp whether to transfer 127.0.0.1 to real ip
     * @return
     */
    public static String getClientIP(HttpServletRequest request, boolean transLocalIp) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(ip) && transLocalIp) {
            // 根据网卡取本机配置的IP
            try {
                ip = Networks.getLocalIp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getLocalIps() {
        List<String> ipList = Lists.newArrayList();

        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ipList.add(ips.nextElement().getHostAddress());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ipList;
    }

    /**
     * 获取内网IP
     *
     * @return 内网IP
     */
    public static String getSiteIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface network = interfaces.nextElement();
                Enumeration<InetAddress> addresses = network.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.isSiteLocalAddress()) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    public static String getHostname() {
        String hostname = "localhost";
        try {
            FileReader fr = new FileReader("/etc/hostname");
            BufferedReader br = new BufferedReader(fr);
            String myreadline;
            while (br.ready()) {
                myreadline = br.readLine();
                hostname = myreadline;
            }
            fr.close();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hostname;
    }

    /**
     * 把IP按点号分4段，每段一整型就一个字节来表示，通过左移位来实现。
     * 第一段放到最高的8位，需要左移24位，依此类推即可
     *
     * @param ip ip地址
     * @return 整形
     */
    public static long ip2Num(String ip) {
        if (ip == null || "".equals(ip)) {
            return -1;
        }

        if (ip.contains(":")) {
            //ipv6的地址，不解析，返回127.0.0.1
            ip = "127.0.0.1";
        }

        byte[] bytes;
        try {
            bytes = InetAddress.getByName(ip).getAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return 0;
        }
        long resp = ((bytes[0] << 24) & 0xff000000) | ((bytes[1] << 16) & 0x00ff0000)
                | ((bytes[2] << 8) & 0x0000ff00) | (bytes[3] & 0x000000ff);

        if (resp < 0) {
            resp += 0x100000000L;
        }

        return resp;
    }

    /**
     * 把整数分为4个字节，通过右移位得到IP地址中4个点分段的值
     *
     * @param ipNum ip int value
     * @return ip str
     */
    public static String num2Ip(long ipNum) {
        return ((ipNum >> 24) & 0xFF) + "." + ((ipNum >> 16) & 0xFF) + "." + ((ipNum >> 8) & 0xFF) + "." + (ipNum & 0xFF);
    }
}