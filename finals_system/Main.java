
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }
            POSMainFrame frame = new POSMainFrame();
            frame.setVisible(true);
        });
    }
}
