package com.ip_position.ipposition.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpValidator {

    private static final String OCTET_REGEX = "(25[0-5]|2[0-4]\\d|[0-1]?\\d\\d?)";
    private static final String IPV4_REGEX = "^" + OCTET_REGEX + "\\." + OCTET_REGEX + "\\." + OCTET_REGEX + "\\."
            + OCTET_REGEX + "$";

    private static final String GROUP_REGEX = "[0-9a-fA-F]{1,4}";
    private static final String IPV6_REGEX = "^" + GROUP_REGEX + ":(?:" + GROUP_REGEX + ":){6}" + GROUP_REGEX + "$";

    private final Pattern ipv4Pattern;
    private final Pattern ipv6Pattern;

    public IpValidator() {
        ipv4Pattern = Pattern.compile(IPV4_REGEX);
        ipv6Pattern = Pattern.compile(IPV6_REGEX);
    }

    public boolean validateIPv4(String ipAddress) {
        Matcher matcher = ipv4Pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public boolean validateIPv6(String ipAddress) {
        Matcher matcher = ipv6Pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public boolean isValidIPAddress(String ipAddress) {
        return validateIPv4(ipAddress) || validateIPv6(ipAddress);
    }
}