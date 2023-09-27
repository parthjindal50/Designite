package designite.ui.ReportDialog;

import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import designite.models.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import designite.ui.Charts.MetricBarChart;
import designite.ui.Charts.MetricPieChart;
import designite.ui.Tables.SmellTable;
import org.knowm.xchart.*;

public class ReportDialog extends JDialog {

    private JPanel leftPanel, rightPanel, chartPanel, topPanel, bottomPanel, bottomLeftPanel, bottomRightPanel, bottomMostPanel;
    private JButton implementationSmellsBtn, designSmellsBtn, testSmellsBtn, architectureSmellsBtn, testabilitySmellsBtn, projectLevelSmellsBtn;
    private JLabel titleLabel;
    ProjectSmellsInfo smellsInfoProvider;
    SmellTable smellTable;
    Color editorBackgroundColor;
    Project project;

    ReportDialog(Project project) {
        this.project = project;
        smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        smellTable = new SmellTable();

        setModal(true);
        setResizable(true);
        setTitle("Code Smell analysis Report");
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        editorBackgroundColor = UIManager.getColor("EditorPane.background");
        contentPanel.setBackground(editorBackgroundColor);
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        createUiPanels();

        projectLevelSmellsBtn.addActionListener(e -> projectLevelSmellsBtnActionListener());
        implementationSmellsBtn.addActionListener(e -> implementationSmellBtnActionListener());
        designSmellsBtn.addActionListener(e -> designSmellBtnActionListener());
        architectureSmellsBtn.addActionListener(e -> archiSmellBtnActionListener());
        testSmellsBtn.addActionListener(e -> testSmellBtnActionListener());
        testabilitySmellsBtn.addActionListener(e -> testabilitySmellBtnActionListener());

        bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(bottomLeftPanel,BorderLayout.WEST);
        bottomPanel.add(bottomRightPanel,BorderLayout.EAST);
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(bottomPanel, BorderLayout.CENTER);
        rightPanel.add(bottomMostPanel,BorderLayout.SOUTH);
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.CENTER);
    }

    public void projectLevelSmellsBtnActionListener() {
        titleLabel.setText("Project Level Smells");
        createDonutChart();
        createBarChart();
        createMetricsTable();
    }

    public void implementationSmellBtnActionListener() {
        titleLabel.setText("Implementation Smells Analysis");
        List<ImplementationSmell> implementationSmellsList = smellsInfoProvider.getImplementationSmellsList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<implementationSmellsList.size();i++) {
            String smell = implementationSmellsList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        createPieChart(pieChartData,pieChartLabels);
        showSmellsWithFrequency(frequencyMap);
        bottomMostPanel = smellTable.showImplementationSmells(implementationSmellsList, bottomMostPanel);
    }

    public void designSmellBtnActionListener(){
        titleLabel.setText("Design Smells Analysis");
        List<DesignSmell> designSmellList = smellsInfoProvider.getDesignSmellList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<designSmellList.size();i++) {
            String smell = designSmellList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        createPieChart(pieChartData,pieChartLabels);
        showSmellsWithFrequency(frequencyMap);
        bottomMostPanel = smellTable.showDesignSmells(designSmellList, bottomMostPanel);
    }

    public void archiSmellBtnActionListener() {
        titleLabel.setText("Architecture Smells Analysis");
        List<ArchitectureSmell> architectureSmellList = smellsInfoProvider.getArchitectureSmellList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<architectureSmellList.size();i++) {
            String smell = architectureSmellList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        createPieChart(pieChartData,pieChartLabels);
        showSmellsWithFrequency(frequencyMap);
        bottomMostPanel = smellTable.showArchitectureSmells(architectureSmellList, bottomMostPanel);
    }

    public void testSmellBtnActionListener() {
        titleLabel.setText("Test Smells Analysis");
        List<TestSmell> testSmellsList = smellsInfoProvider.getTestSmellsList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<testSmellsList.size();i++) {
            String smell = testSmellsList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        createPieChart(pieChartData,pieChartLabels);
        showSmellsWithFrequency(frequencyMap);
        bottomMostPanel = smellTable.showTestSmells(testSmellsList, bottomMostPanel);
    }

    public void testabilitySmellBtnActionListener() {
        titleLabel.setText("Testability Smells Analysis");
        List<TestabilitySmell> testabilitySmellsList = smellsInfoProvider.getTestabilitySmellList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<testabilitySmellsList.size();i++) {
            String smell = testabilitySmellsList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        createPieChart(pieChartData,pieChartLabels);
        showSmellsWithFrequency(frequencyMap);
        bottomMostPanel = smellTable.showTestabilitySmells(testabilitySmellsList, bottomMostPanel);
    }

    public void createUiPanels() {
        createLeftPanel();
        createRightPanel();
        createTopPanel();
        createBottomLeftPanel();
        createBottomRightPanel();
        createChartPanel();
        createBottomMostPanel();
    }

    public void createLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.removeAll();
        leftPanel.setLayout(new GridLayout(6, 0));
        projectLevelSmellsBtn = new JButton("Project Level Smells");
        implementationSmellsBtn = new JButton("Implementation Smells");
        designSmellsBtn = new JButton("Design Smells");
        architectureSmellsBtn = new JButton("Architecture Smells");
        testSmellsBtn = new JButton("Test Smells");
        testabilitySmellsBtn = new JButton("Testability Smells");

        leftPanel.setBackground(editorBackgroundColor);
        leftPanel.add(projectLevelSmellsBtn);
        leftPanel.add(implementationSmellsBtn);
        leftPanel.add(designSmellsBtn);
        leftPanel.add(architectureSmellsBtn);
        leftPanel.add(testSmellsBtn);
        leftPanel.add(testabilitySmellsBtn);
    }

    public void createRightPanel() {
        rightPanel = new JPanel();
        rightPanel.removeAll();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(editorBackgroundColor);
    }

    public void createTopPanel() {
        topPanel = new JPanel();
        topPanel.removeAll();
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        topPanel.setBackground(UIUtil.getPanelBackground());
        titleLabel = new JLabel();
        Font labelFont = titleLabel.getFont();
        titleLabel.setText("Project Level Smells");
        titleLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);
    }

    public void createBottomLeftPanel() {
        bottomLeftPanel = new JPanel();
        bottomLeftPanel.removeAll();
        bottomLeftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bottomLeftPanel.setBackground(editorBackgroundColor);
    }

    public void createBottomRightPanel() {
        bottomRightPanel = new JPanel();
        createBarChart();
    }

    public void createBarChart() {
        bottomRightPanel.removeAll();
        bottomRightPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 10));
        MetricBarChart barChart = new MetricBarChart();
        CategoryChart chart = barChart.getBarChart(project);
        XChartPanel xChartPanel = new XChartPanel(chart);
        bottomRightPanel.add(xChartPanel, BorderLayout.CENTER);
    }

    public void createChartPanel() {
        chartPanel = new JPanel();
        createDonutChart();
    }

    public void createDonutChart() {
        chartPanel.removeAll();
        chartPanel.setLayout(new BorderLayout());
        MetricPieChart pieChart = new MetricPieChart();
        PieChart chart = pieChart.getDonutPieChart(project);
        XChartPanel xChartPanel = new XChartPanel(chart);
        chartPanel.add(xChartPanel);
        bottomLeftPanel.setLayout(new BorderLayout());
        bottomLeftPanel.add(chartPanel, BorderLayout.CENTER);
    }

    public void createPieChart(List<Number> chartData, List<String> chartLabels) {
        chartPanel.removeAll();
        MetricPieChart pieChart = new MetricPieChart();
        PieChart chart = pieChart.getPieChart(chartData,chartLabels);
        XChartPanel xChartPanel = new XChartPanel(chart);
        chartPanel.add(xChartPanel);
    }

    public void createBottomMostPanel() {
        bottomMostPanel = new JPanel();
        createMetricsTable();
    }

    public void createMetricsTable() {
        bottomMostPanel.removeAll();
        bottomMostPanel.setLayout(new BorderLayout());
        bottomMostPanel.setBackground(editorBackgroundColor);
        bottomRightPanel.setBorder(BorderFactory.createEmptyBorder(-10, 0, 0, 10));
        List<TypeMetrics> typeMetricsList = smellsInfoProvider.getTypeMetrics();
        bottomMostPanel = smellTable.showTypeMetrics(typeMetricsList, bottomMostPanel);
    }

    public void showSmellsWithFrequency(HashMap<String, Integer> frequencyMap) {
        JTable table = new JTable();
        table.setBackground(UIUtil.getPanelBackground());
        table.setCellSelectionEnabled(false);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        bottomRightPanel.removeAll();
        bottomRightPanel.setBorder(BorderFactory.createEmptyBorder(100, 20, 0, 10));
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Smell Type");
        model.addColumn("Frequency");
        if (frequencyMap.keySet().isEmpty()) {
            Object[] row = new Object[1];
            row[0] = "No smells detected for this type";
            model.addRow(row);
        }
        for (String value : frequencyMap.keySet()) {
            Object[] row = new Object[2];
            row[0] = value;
            row[1] = frequencyMap.get(value);
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(UIUtil.getPanelBackground());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        bottomRightPanel.setBackground(UIUtil.getPanelBackground());
        bottomRightPanel.setLayout(new BorderLayout());
        bottomRightPanel.add(scrollPane, BorderLayout.CENTER);
    }
}
