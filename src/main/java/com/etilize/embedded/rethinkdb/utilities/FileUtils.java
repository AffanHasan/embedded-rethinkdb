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

import static com.etilize.embedded.rethinkdb.utilities.CommonUtils.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * General application specific file manipulation utility.
 *
 * @author Affan Hasan
 * @since 1.0
 */
public class FileUtils {

    public static final String USER_HOME = org.apache.commons.io.FileUtils.getUserDirectoryPath();

    public static final String RETHINK_DB_BINARY_FILE_NAME = "rethinkdb";

    public static final String RELEASE_FOLDER_NAME = "release";

    private final String rootFolderPath;

    private final String databaseVersion;

    /**
     * Default constructor.
     *
     * @param rootFolderPath {@link String} directory containing rethinkdb folder
     * @param dbVersion {@link String} database version
     */
    public FileUtils(final String rootFolderPath, final String dbVersion) {
        this.rootFolderPath = rootFolderPath;
        databaseVersion = dbVersion;
    }

    /**
     * Checks if rethinkdb binary file exists or not.
     *
     * @return {@link Boolean}
     * @throws IOException IOException
     */
    public boolean isDbBinaryFileExists() throws IOException {
        final String rethinkDbFolderPath = FilenameUtils.concat(rootFolderPath,
                getRethinkDbFolderName(databaseVersion));
        final String releaseFolderPath = FilenameUtils.concat(rethinkDbFolderPath,
                RELEASE_FOLDER_NAME);
        final String rethinkDbBinaryFilePath = FilenameUtils.concat(releaseFolderPath,
                RETHINK_DB_BINARY_FILE_NAME);
        return org.apache.commons.io.FileUtils.directoryContains(
                new File(releaseFolderPath), new File(rethinkDbBinaryFilePath));
    }
    
    /**
     * Download rethinkdb archive file, it rewrites the archive file if already exists.
     * 
     * @throws IOException @{@link IOException}
     */
    public void downloadDbArchive() throws IOException {
    	org.apache.commons.io.FileUtils.copyURLToFile(getVersionSpecificDownloadArchiveURL(databaseVersion), 
    			new File(FilenameUtils.concat(rootFolderPath, getVersionSpecificRethinkDbArchiveFileName(databaseVersion))), 10000, 10000);
    }
    
    /**
     * Expands ".tgz" rethinkdb archive file
     * 
     * @throws IOException IOException 
     */
    public void expandDbArchive() throws IOException {
    	final File targetDir = new File(rootFolderPath);
		try (final InputStream fi = Files.newInputStream(Paths.get(FilenameUtils.concat(rootFolderPath, 
				getVersionSpecificRethinkDbArchiveFileName(databaseVersion))));
			     final InputStream bi = new BufferedInputStream(fi);
			     final InputStream gzi = new GzipCompressorInputStream(bi);
			     final ArchiveInputStream i = new TarArchiveInputStream(gzi)) {
		    ArchiveEntry entry = null;
		    while ((entry = i.getNextEntry()) != null) {
		        if (!i.canReadEntryData(entry)) {
		            // log something?
		            continue;
		        }
		        final String name = FilenameUtils.concat(targetDir.getCanonicalPath(), entry.getName());
		        final File f = new File(name);
		        if (entry.isDirectory()) {
		            if (!f.isDirectory() && !f.mkdirs()) {
		                throw new IOException("failed to create directory " + f);
		            }
		        } else {
		            final File parent = f.getParentFile();
		            if (!parent.isDirectory() && !parent.mkdirs()) {
		                throw new IOException("failed to create directory " + parent);
		            }
		            try (final OutputStream o = Files.newOutputStream(f.toPath())) {
		                IOUtils.copy(i, o);
		            }
		        }
		    }
		}
    }
}
