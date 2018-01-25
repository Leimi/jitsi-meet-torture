/*
 * Copyright @ 2015 Atlassian Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jitsi.meet.test;

import org.jitsi.meet.test.base.*;
import org.jitsi.meet.test.web.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Checks that the chat panel can be opened and closed with a shortcut and
 * with the toolbar button.
 *
 * TODO: Add tests for sending/receiving messages.
 *
 * @author Boris Grozev
 */
public class ChatPanelTest
    extends AbstractBaseTest
{
    /**
     * The single participant which will be used in this test.
     */
    private WebParticipant participant;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup()
    {
        super.setup();

        ensureOneParticipant();

        Participant p = getParticipant1();
        if (!(p instanceof WebParticipant))
        {
            throw new IllegalStateException(
                "This test only supports web and shouldn't be run on other "
                    + "platforms.");
        }

        participant = (WebParticipant) p;
    }

    /**
     * Opens and closes the chat panel with the toolbar button and with the
     * keyboard shortcut, and checks that the open/closed state is correct.
     */
    @Test
    public void testChatPanel()
    {
        // The chat panel requires a display name to be set.
        participant.setDisplayName("bla");

        ChatPanel chatPanel = participant.getChatPanel();

        assertFalse(
            chatPanel.isOpen(),
            "The chat panel should be initially closed");

        chatPanel.clickToolbarButton();

        // The chat panel should be open after clicking the button
        WebDriverWait wait = new WebDriverWait(participant.getDriver(), 2);
        wait.until((ExpectedCondition<Boolean>) d -> chatPanel.isOpen());

        chatPanel.pressShortcut();
        // The chat panel should be closed after pressing the shortcut
        wait.until((ExpectedCondition<Boolean>) d -> !chatPanel.isOpen());

        chatPanel.pressShortcut();
        // The chat panel should be open after pressing the shortcut
        wait.until((ExpectedCondition<Boolean>) d -> chatPanel.isOpen());

        chatPanel.clickToolbarButton();
        // The chat panel should be closed after clicking the button
        wait.until((ExpectedCondition<Boolean>) d -> !chatPanel.isOpen());
    }
}