package designite.ui.toolWindow;

import com.intellij.codeInspection.ex.InspectionManagerEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;
import designite.lineMarkerProvider.ProjectSmellsInfo;

import javax.swing.*;
import java.util.Hashtable;

public class ToolWindowFactory implements com.intellij.openapi.wm.ToolWindowFactory {
    public ToolWindow toolWindow;
    //public static ClassInfoToolWindow classInfoToolWindow = null;
    private static Hashtable<Project, ClassInfoToolWindow> classInfoToolWindowDict = new Hashtable<>();

    private ClassInfoToolWindow myToolWindow;
    private Content content;
    public ToolWindowFactory()
    {
        myToolWindow = new ClassInfoToolWindow(this);
    }
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        //ClassInfoToolWindow myToolWindow = new ClassInfoToolWindow(this);
        //classInfoToolWindow = myToolWindow;
        addThisInstance(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        content = contentFactory.createContent(myToolWindow.getContent(project), "Code quality information", true);
        toolWindow.getContentManager().addContent(content);
        this.toolWindow = toolWindow;
    }

    private void addThisInstance(Project project) {
        classInfoToolWindowDict.computeIfAbsent(project, k -> myToolWindow);
    }

    public static ClassInfoToolWindow getInstance(Project project) {
        if (project !=null){
            return classInfoToolWindowDict.get(project);
        }
        return null;
    }
    public static void resetToolWindow(Project project){
        if (project !=null){
            classInfoToolWindowDict.remove(project);
        }
    }
    void setContent(JPanel panel) {
        if (toolWindow == null)
            return;
        toolWindow.getContentManager().removeAllContents(false);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        content = contentFactory.createContent(panel, "Code quality information", true);
        toolWindow.getContentManager().addContent(content);
    }
}