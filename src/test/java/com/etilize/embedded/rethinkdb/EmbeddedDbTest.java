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

package com.etilize.embedded.rethinkdb;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.etilize.embedded.rethinkdb.utilities.FileUtils;
import com.etilize.test.AbstractTest;

import io.apisense.embed.influx.ServerAlreadyRunningException;

/**
 * Contains functional tests for {@link EmbeddedDB}
 *
 * @author Affan Hasan
 */
public class EmbeddedDbTest extends AbstractTest {
	
	private EmbeddedRethinkDbServer embeddedDb;
	
	@Mock
	private FileUtils fileUtils;
	
	@Before
	public void init() {
		embeddedDb = new EmbeddedRethinkDbServer(fileUtils);
	}
	
    @Test
    public void shouldCheckIfHomeDirectoryContainsCorrectVersionBinaries() throws IOException, ServerAlreadyRunningException {
    	when(fileUtils.isDbBinaryFileExists()).thenReturn(Boolean.TRUE);
    	embeddedDb.init();
    	verify(fileUtils, times(1)).isDbBinaryFileExists();
    }
    
    @Test
    public void shouldDownloadBinariesForCorrectDbVersionWhenDoesNotExists() throws IOException, ServerAlreadyRunningException {
    	when(fileUtils.isDbBinaryFileExists()).thenReturn(Boolean.FALSE);
    	embeddedDb.init();
    	verify(fileUtils, times(1)).isDbBinaryFileExists();
    	verify(fileUtils, times(1)).downloadDbArchive();
    	verify(fileUtils, times(1)).expandDbArchive();
//    	verify(fileUtils, times(1)).buildRethinkDb();
    }
    
    @Test
    public void shouldNotDownloadBinariesWhenExistsAlready() throws IOException, ServerAlreadyRunningException {
    	when(fileUtils.isDbBinaryFileExists()).thenReturn(Boolean.TRUE);
    	embeddedDb.init();
    	verify(fileUtils, times(1)).isDbBinaryFileExists();
    	verify(fileUtils, times(1)).isDbBinaryFileExists();
    	verify(fileUtils, times(1)).downloadDbArchive();
    	verify(fileUtils, times(1)).expandDbArchive();
    }
}
