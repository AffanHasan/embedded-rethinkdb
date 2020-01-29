package com.etilize.embedded.rethinkdb;

import io.apisense.embed.influx.ServerAlreadyRunningException;
import io.apisense.embed.influx.ServerNotRunningException;

/**
 * Default implementation of {@link EmbeddedDB}
 * 
 * @author Affan Hasan
 */
public class EmbeddedRethinkDbServer implements EmbeddedDB {

	@Override
	public void init() throws ServerAlreadyRunningException {
		// TODO check /home/user/ already contains the required binaries with correct version
			// file system check for binaries
		    // 
		// TODO If not then download to /home/user the sources
		// TODO extract the sources
	}

	@Override
	public void start() throws ServerAlreadyRunningException {
		// TODO start the database server on a free port
	}

	@Override
	public void stop() throws ServerNotRunningException {
		// TODO stop the server
		
	}

	@Override
	public void cleanup() throws ServerNotRunningException {
		// TODO clean up any datafiles
		
	}

}
