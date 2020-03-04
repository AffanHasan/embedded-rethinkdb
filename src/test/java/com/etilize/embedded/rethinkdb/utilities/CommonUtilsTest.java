package com.etilize.embedded.rethinkdb.utilities;

import static com.etilize.embedded.rethinkdb.EmbeddedRethinkDbServer.*;
import static org.hamcrest.CoreMatchers.*;

import java.net.MalformedURLException;

import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

import com.etilize.test.AbstractTest;

/**
 * Contains tests for {@link CommonUtils}
 * 
 * @author Affan Hasan
 */
public class CommonUtilsTest extends AbstractTest {

	@Test
	public void shouldReturnVersionSpecificRethinkDbFolderName() {
		assertThat(CommonUtils.getRethinkDbFolderName(RETHINK_DB_VERSION), is("rethinkdb-" + RETHINK_DB_VERSION));
	}
	
	@Test
	public void shouldReturnVersionSpecificRethinkDbArchiveName() {
		assertThat(CommonUtils.getVersionSpecificRethinkDbArchiveFileName(RETHINK_DB_VERSION), is(String.format("rethinkdb-%s.tgz", RETHINK_DB_VERSION)));
	}
	
	@Test
	public void shouldReturnVersionSpecificRethinkDbArchiveDownloadURL() throws MalformedURLException {
		assertThat(CommonUtils.getVersionSpecificDownloadArchiveURL("2.4.0") //
				.toString(), is("https://download.rethinkdb.com/dist/rethinkdb-2.4.0.tgz"));
	}
}
