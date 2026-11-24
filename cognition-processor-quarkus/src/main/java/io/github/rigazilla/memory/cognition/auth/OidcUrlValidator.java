package io.github.rigazilla.memory.cognition.auth;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Guards the configured OIDC URLs. {@code https} is always allowed; plaintext {@code http} is
 * allowed only for {@code localhost} or a loopback IP literal unless the caller opts in with
 * {@code allow-insecure}. Only the configured URLs can be checked, not a discovered
 * {@code token_endpoint}.
 */
public final class OidcUrlValidator {

    private static final Pattern IPV4_LITERAL = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3}");

    private OidcUrlValidator() {
    }

    /**
     * Validates one URL.
     *
     * @param property      property name used in the error message
     * @param raw           the configured URL
     * @param allowInsecure whether a non-loopback {@code http://} URL is allowed
     * @throws AuthConfigurationException if the URL is not acceptable; the message never contains
     *                                    the URL (it may carry credentials)
     */
    public static void validate(String property, String raw, boolean allowInsecure) {
        URI uri = parse(property, raw);
        String scheme = uri.getScheme() == null ? "" : uri.getScheme().toLowerCase(Locale.ROOT);
        String host = uri.getHost();
        if ((!"http".equals(scheme) && !"https".equals(scheme))
                || host == null || host.isEmpty()
                || uri.getRawUserInfo() != null
                || uri.getRawFragment() != null) {
            throw new AuthConfigurationException(
                    property + " must be an absolute http or https URL without user info or fragment");
        }
        if ("http".equals(scheme) && !allowInsecure && !isLoopback(host)) {
            throw new AuthConfigurationException(property
                    + " uses plaintext http for a non-loopback host; use https or set "
                    + "memory-service.auth.oidc.allow-insecure=true (development only)");
        }
    }

    private static URI parse(String property, String raw) {
        try {
            return new URI(raw.trim());
        } catch (URISyntaxException e) {
            throw new AuthConfigurationException(property + " is not a valid URL");
        }
    }

    private static boolean isLoopback(String host) {
        String bare = host.startsWith("[") && host.endsWith("]")
                ? host.substring(1, host.length() - 1) : host;
        if ("localhost".equalsIgnoreCase(bare)) {
            return true;
        }
        // Only literals are resolved here, so no DNS lookup can happen.
        if (!IPV4_LITERAL.matcher(bare).matches() && !bare.contains(":")) {
            return false;
        }
        try {
            return InetAddress.getByName(bare).isLoopbackAddress();
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
