import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class FileChooser {

    public static String choose(File selectedFile ) {

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog (null);

        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            selectedFile = jfc.getSelectedFile();
        }
        return selectedFile.getAbsolutePath();
    }

    public static String chooseDirectory() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        return f.getCurrentDirectory().getAbsolutePath() + File.separator + "text.txt";
    }
}
