/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.client.cli.fs.command;

import static org.junit.Assert.assertEquals;

import alluxio.AlluxioURI;
import alluxio.Constants;
import alluxio.annotation.dora.DoraTestTodoItem;
import alluxio.client.cli.fs.AbstractFileSystemShellTest;
import alluxio.client.file.FileSystemTestUtils;
import alluxio.grpc.WritePType;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for unsetTtl command.
 */
@DoraTestTodoItem(action = DoraTestTodoItem.Action.FIX, owner = "hua",
    comment = "fix unsetTtl command")
@Ignore
public final class UnsetTtlTest extends AbstractFileSystemShellTest {
  @Test
  public void unsetTtl() throws Exception {
    String filePath = "/testFile";
    AlluxioURI uri = new AlluxioURI("/testFile");
    FileSystemTestUtils.createByteFile(sFileSystem, filePath, WritePType.CACHE_THROUGH, 1);
    assertEquals(Constants.NO_TTL, sFileSystem.getStatus(uri).getTtl());

    // unsetTTL on a file originally with no TTL will leave the TTL unchanged.
    assertEquals(0, sFsShell.run("unsetTtl", filePath));
    assertEquals(Constants.NO_TTL, sFileSystem.getStatus(uri).getTtl());

    long ttl = 1000L;
    assertEquals(0, sFsShell.run("setTtl", filePath, String.valueOf(ttl)));
    assertEquals(ttl, sFileSystem.getStatus(uri).getTtl());
    assertEquals(0, sFsShell.run("unsetTtl", filePath));
    assertEquals(Constants.NO_TTL, sFileSystem.getStatus(uri).getTtl());
  }
}
