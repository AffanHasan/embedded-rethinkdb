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

import java.io.IOException;

import io.apisense.embed.influx.ServerAlreadyRunningException;
import io.apisense.embed.influx.ServerNotRunningException;

/**
 * Describe the available operations on an embedded database.
 *
 * @author Affan Hasan
 * @since 1.0
 */
public interface EmbeddedDB {

    /**
     * Check if the server binaries are present locally
     * and install them if missing.
     *
     * @throws ServerAlreadyRunningException If the server is already started.
     */
    void init() throws ServerAlreadyRunningException, IOException, InterruptedException;

    /**
     * Start the server if not already running.
     *
     * @throws ServerAlreadyRunningException If the server is already started.
     */
    void start() throws ServerAlreadyRunningException;

    /**
     * Stop the started instance.
     *
     * @throws ServerNotRunningException If the server as not yet been started, or already been stopped.
     */
    void stop() throws ServerNotRunningException;

    /**
     * Stop the server if still running,
     * then remove the server data.
     *
     * @throws ServerNotRunningException If the server as not yet been started, or already been stopped.
     */
    void cleanup() throws ServerNotRunningException;
}
