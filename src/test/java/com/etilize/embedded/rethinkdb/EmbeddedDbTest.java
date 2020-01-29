package com.etilize.embedded.rethinkdb;

import org.junit.Test;

import com.etilize.test.AbstractTest;

import io.apisense.embed.influx.ServerAlreadyRunningException;
/**
 * Contains functional tests for {@link EmbeddedDB}
 * 
 * @author Affan Hasan
 */
public class EmbeddedDbTest extends AbstractTest {
	
	private EmbeddedRethinkDbServer embeddedRethinkDbServer;

	@Test
	public void shouldInitializeDatabase() throws ServerAlreadyRunningException {
		embeddedRethinkDbServer.init();
	}
}
