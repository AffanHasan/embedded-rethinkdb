package com.etilize.embedded.rethinkdb.utilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Common application-specific utility functions
 * 
 * @author Affan Hasan
 * @since 1.0
 */
public class CommonUtils {

	/**
	 * Constructor to hide the implicit public constructor
	 */
	private CommonUtils() {
	}
	
    /**
     * Returns version specific rethinkdb folder name.
     * 
     * @param version {@link String} rethinkdb version number
     * @return {@link String} version specific rethink-db folder name
     */
    public static String getRethinkDbFolderName(final String version) {
        return "rethinkdb-" + version;
    }
    
    /**
     * Returns rethink db version specific archive file name.
     * 
     * @param version {@link String} version
     * @return {@link String} rethinkdb archive file name
     */
    public static String getVersionSpecificRethinkDbArchiveFileName(final String version) {
    	return String.format("rethinkdb-%s.tgz", version);
    }
    
    /**
     * Returns version specific download URL for rethinkdb.
     * 
     * @param version {@link String}
     * @return {@link URL} version specific download URL for rethinkdb
     * @throws MalformedURLException {@link MalformedURLException}
     */
    public static URL getVersionSpecificDownloadArchiveURL(final String version) throws MalformedURLException {
    	return new URL(String.format("https://download.rethinkdb.com/dist/rethinkdb-%s.tgz", version));
    }
}
