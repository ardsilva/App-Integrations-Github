/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.webhook.github.parser.v1;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.model.message.Message;
import org.symphonyoss.integration.webhook.github.CommonGithubTest;
import org.symphonyoss.integration.webhook.github.parser.GithubParserException;
import org.symphonyoss.integration.webhook.github.parser.GithubParserUtils;

import java.io.IOException;
import java.util.Collections;

/**
 * Unit tests for {@link ReleaseGithubParser}
 *
 * Created by ecarrenho on 23/09/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReleaseGithubParserTest extends CommonGithubTest {

  @Mock
  private GithubParserUtils utils;

  @InjectMocks
  private ReleaseGithubParser releaseGithubParser = new ReleaseGithubParser();

  @Before
  public void setup() throws IOException {
    // mocks
    JsonNode publicUserInfo = getJsonFile("parser/payload_github_public_info_baxterthehacker.json");
    doReturn(publicUserInfo).when(utils).doGetJsonApi(anyString());
  }

  @Test
  public void testRelease() throws IOException, GithubParserException {

    // files
    JsonNode releaseNode = getJsonFile("parser/release/payload_xgithubevent_release.json");
    String expectedMessage = getExpectedMessageML(
        "parser/release/v1/payload_xgithubevent_release_expected_message.xml");

    // call
    Message resultML =
        releaseGithubParser.parse(Collections.<String, String>emptyMap(), releaseNode);
    String result = resultML == null ? StringUtils.EMPTY : resultML.getMessage();
    assertEquals(expectedMessage, result);
  }

  @Test
  public void testReleaseWithName() throws IOException, GithubParserException {

    // files
    JsonNode releaseNode = getJsonFile(
        "parser/release/payload_xgithubevent_release_with_release_name.json");
    String expectedMessage = getExpectedMessageML(
        "parser/release/v1/payload_xgithubevent_release_expected_message_with_release_name"
            + ".xml");

    // call
    Message resultML =
        releaseGithubParser.parse(Collections.<String, String>emptyMap(), releaseNode);
    String result = resultML == null ? StringUtils.EMPTY : resultML.getMessage();
    assertEquals(expectedMessage, result);
  }
}
