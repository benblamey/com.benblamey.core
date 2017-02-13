package com.benblamey.core;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * URI-related utility methods.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class UriUtility {

    /**
     * Gets a URI up to an including the path, but not the # or the fragment.
     *
     * @param uri
     * @return
     * @throws URISyntaxException
     */
    public static String getURIUpToPath(String uri) throws URISyntaxException {
        URI url2 = new URI(uri);
        return url2.getScheme() + "://" + url2.getAuthority() + url2.getPath();
    }

    /**
     * Convert a windows path to a "file://" style URL.
     *
     * @param path
     * @return
     */
    public static String windowsPathToURI(String path) {
        path = path.replace("\\", "/");
        path = "file:///" + path;
        return path;
    }

    private UriUtility() {
    }
}
