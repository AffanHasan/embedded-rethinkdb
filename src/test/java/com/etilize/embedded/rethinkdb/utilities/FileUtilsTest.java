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

import static com.etilize.embedded.rethinkdb.EmbeddedRethinkDbServer.*;
import static com.etilize.embedded.rethinkdb.utilities.CommonUtils.*;
import static com.etilize.embedded.rethinkdb.utilities.FileUtils.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.etilize.test.AbstractTest;

import static org.hamcrest.MatcherAssert.*;

/**
 * Functional tests for {@link FileUtils}
 *
 * @author Affan Hasan
 */
public class FileUtilsTest extends AbstractTest {

    private String releaseFolderPath;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder(new File(USER_HOME));

    private FileUtils fileUtils;
    private String userHomeDirectory;

    @Before
    public void init() throws IOException {
        userHomeDirectory = tempFolder.getRoot() //
                .getAbsolutePath();
        releaseFolderPath = tempFolder.newFolder(
                getRethinkDbFolderName(RETHINK_DB_VERSION), RELEASE_FOLDER_NAME) //
                .getAbsolutePath();
        fileUtils = new FileUtils(userHomeDirectory, RETHINK_DB_VERSION);
    }

    @Test
    public void shouldReturnTrueWhenDatabaseBinaryExists() throws IOException {
        org.apache.commons.io.FileUtils.touch(new File(
                FilenameUtils.concat(releaseFolderPath, RETHINK_DB_BINARY_FILE_NAME)));
        assertThat(fileUtils.isDbBinaryFileExists(), is(true));
    }

    @Test
    public void shouldReturnFalseWhenDatabaseBinaryDonotExists() throws IOException {
        assertThat(fileUtils.isDbBinaryFileExists(), is(false));
    }
    
    /**
     * This test is ignored since it is very time taking
     * 
     * @throws IOException
     */
    @Test
    @Ignore
    public void shouldDownloadDbArchive() throws IOException {
    	fileUtils.downloadDbArchive();
    	org.apache.commons.io.FileUtils.directoryContains(
                new File(userHomeDirectory), new File(getVersionSpecificRethinkDbArchiveFileName(RETHINK_DB_VERSION)));
    }
    
    /**
     * This test is ignored since it is very time taking
     * 
     * @throws IOException
     */
    @Test
    @Ignore
    public void shouldExtractRethinkDbArchive() throws IOException{
    	fileUtils.expandDbArchive();
    }
}
