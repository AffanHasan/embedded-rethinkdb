/*
 * #region
 * embedded-rethinkdb-database
 * %%
 * Copyright (C) 2020 Etilize
 * %%
 * NOTICE: All information contained herein is, and remains the property of ETILIZE.
 * The intellectual and technical concepts contained herein are proprietary to
 * ETILIZE and may be covered by U.S. and Foreign Patents, patents in process, and
 * are protected by trade secret or copyright law. Dissemination of this information
 * or reproduction of this material is strictly forbidden unless prior written
 * permission is obtained from ETILIZE. Access to the source code contained herein
 * is hereby forbidden to anyone except current ETILIZE employees, managers or
 * contractors who have executed Confidentiality and Non-disclosure agreements
 * explicitly covering such access.
 *
 * The copyright notice above does not evidence any actual or intended publication
 * or disclosure of this source code, which includes information that is confidential
 * and/or proprietary, and is a trade secret, of ETILIZE. ANY REPRODUCTION, MODIFICATION,
 * DISTRIBUTION, PUBLIC PERFORMANCE, OR PUBLIC DISPLAY OF OR THROUGH USE OF THIS
 * SOURCE CODE WITHOUT THE EXPRESS WRITTEN CONSENT OF ETILIZE IS STRICTLY PROHIBITED,
 * AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL TREATIES. THE RECEIPT
 * OR POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR
 * IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO
 * MANUFACTURE, USE, OR SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
 * #endregion
 */

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
    public static String getVersionSpecificRethinkDbArchiveFileName(
            final String version) {
        return String.format("rethinkdb-%s.tgz", version);
    }

    /**
     * Returns version specific download URL for rethinkdb.
     *
     * @param version {@link String}
     * @return {@link URL} version specific download URL for rethinkdb
     * @throws MalformedURLException {@link MalformedURLException}
     */
    public static URL getVersionSpecificDownloadArchiveURL(final String version)
            throws MalformedURLException {
        return new URL(String.format(
                "https://download.rethinkdb.com/dist/rethinkdb-%s.tgz", version));
    }
}
