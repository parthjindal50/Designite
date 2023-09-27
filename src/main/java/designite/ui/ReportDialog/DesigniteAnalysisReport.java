package designite.ui.ReportDialog;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class DesigniteAnalysisReport extends AnAction {
    public DesigniteAnalysisReport() {
        super("Analysis Report", "Code Smell analysis report", null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        EventQueue.invokeLater(() -> {
            try {
                ReportDialog dialog = new ReportDialog(project);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
