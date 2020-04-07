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

import static org.apache.commons.exec.ExecuteWatchdog.INFINITE_TIMEOUT;
import static com.etilize.embedded.rethinkdb.utilities.CommonUtils.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
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

    private static final String CONFIGURE_COMMAND = "./configure";

    private static final String CONFIGURE_COMMAND_ARGUMENT = "--allow-fetch";

    private static final String MAKE_COMMAND = "make";

    private static final String TAR_COMMAND = "tar";

    private static final String TAR_COMMAND_ARGUMENT = "xf";

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
        org.apache.commons.io.FileUtils.copyURLToFile(
                getVersionSpecificDownloadArchiveURL(databaseVersion),
                new File(FilenameUtils.concat(rootFolderPath,
                        getVersionSpecificRethinkDbArchiveFileName(databaseVersion))),
                10000, 10000);
    }

    /**
     * Expands ".tgz" rethinkdb archive file
     *
     * @throws IOException IOException
     * @throws InterruptedException
     */
    public void expandDbArchive() throws IOException, InterruptedException {
        final CommandLine tarCommand = new CommandLine(TAR_COMMAND);
        tarCommand.addArgument(TAR_COMMAND_ARGUMENT);
        tarCommand.addArgument(
                getVersionSpecificRethinkDbArchiveFileName(databaseVersion));
        final DefaultExecuteResultHandler defaultResultHandler = new DefaultExecuteResultHandler();
        final Executor executor = getExecutor(rootFolderPath);
        executor.execute(tarCommand, defaultResultHandler);
        defaultResultHandler.waitFor();
    }

    /**
     * Make the rethinkdb binaries from source code
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void buildBinaries() throws IOException, InterruptedException {
        final String dbFolderPath = FilenameUtils.concat(rootFolderPath,
                getRethinkDbFolderName(databaseVersion));
        final CommandLine configureCommand = new CommandLine(CONFIGURE_COMMAND);
        configureCommand.addArgument(CONFIGURE_COMMAND_ARGUMENT);
        final DefaultExecuteResultHandler defaultResultHandler = new DefaultExecuteResultHandler();
        final Executor executor = getExecutor(dbFolderPath);
        executor.execute(configureCommand, defaultResultHandler);
        defaultResultHandler.waitFor();
        final CommandLine commandMake = new CommandLine(MAKE_COMMAND);
        executor.execute(commandMake, defaultResultHandler);
        defaultResultHandler.waitFor();
    }

    /**
     * Returns a new {@link Executor} instance.
     *
     * @param directoryPath {@link String} directory path
     * @return {@link Executor}
     */
    private Executor getExecutor(final String directoryPath) {
        final ExecuteWatchdog watchdog = new ExecuteWatchdog(INFINITE_TIMEOUT);
        final Executor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(directoryPath));
        executor.setExitValue(1);
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(new PumpStreamHandler());
        return executor;
    }
}
