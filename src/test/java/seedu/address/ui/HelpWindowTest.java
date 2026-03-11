package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelpWindowTest {

    @BeforeAll
    public static void setUpFxToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void constructor_setsCommandListMessage() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        final HelpWindow[] helpWindowHolder = new HelpWindow[1];

        Platform.runLater(() -> {
            helpWindowHolder[0] = new HelpWindow(new Stage());
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);

        HelpWindow helpWindow = helpWindowHolder[0];
        Label helpMessage = (Label) helpWindow.getRoot().getScene().lookup("#helpMessage");

        assertEquals(HelpWindow.COMMAND_LIST_MESSAGE, helpMessage.getText());
    }

    @Test
    public void focus_helpWindow_focusesStage() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            HelpWindow helpWindow = new HelpWindow(new Stage());
            helpWindow.focus();
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }
}
