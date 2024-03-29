package a8;
import java.awt.event.MouseEvent;

public interface GameViewListener {
	void handleGameViewEvent(String command);
	void handleGameViewMouseEvent(int x, int y);
	void handleGameViewChangesEvent(
			String X, String Y,
			String LowB, String HighB,
			String LowD, String HighD);
}
