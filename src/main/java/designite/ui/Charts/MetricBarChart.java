package designite.ui.Charts;

import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import designite.models.TypeMetrics;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetricBarChart {
    public CategoryChart getBarChart(Project project) {

        CategoryChart chart = new CategoryChartBuilder().width(300).height(300).title("Smell Density").xAxisTitle("").yAxisTitle("").build();

        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        Color annotationColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
        Color backgroundColor = UIUtil.getPanelBackground();
        Color[] sliceColors = new Color[] {
                new Color(236, 143, 110),
                new Color(224, 68, 14),
                new Color(230, 105, 62),
                new Color(243, 180, 159),
                new Color(246, 199, 182)
        };

        java.util.List<TypeMetrics> typeMetrics = smellsInfoProvider.getTypeMetrics();
        java.util.List<String> xData = Arrays.asList("Implementation", "Design", "Architecture", "Testability", "Test");
        java.util.List<Integer> yData = new ArrayList<Integer>();

        int totalLOC = getTotalLOC(typeMetrics);
        yData.add(calculateSmellDensity(smellsInfoProvider.getTotalImplementationSmells(),totalLOC));
        yData.add(calculateSmellDensity(smellsInfoProvider.getTotalDesignSmells(),totalLOC));
        yData.add(calculateSmellDensity(smellsInfoProvider.getTotalArchitectureSmells(),totalLOC));
        yData.add(calculateSmellDensity(smellsInfoProvider.getTotalTestabilitySmells(),totalLOC));
        yData.add(calculateSmellDensity(smellsInfoProvider.getTotalTestSmells(),totalLOC));

        chart.addSeries("Smell Density", xData, yData);

        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setPlotBackgroundColor(backgroundColor);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setToolTipBackgroundColor(backgroundColor);
        chart.getStyler().setToolTipBorderColor(annotationColor);
        chart.getStyler().setToolTipHighlightColor(backgroundColor);
        chart.getStyler().setPlotBorderColor(backgroundColor);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setAnnotationLineColor(annotationColor);
        chart.getStyler().setAnnotationTextPanelBackgroundColor(backgroundColor);
        chart.getStyler().setChartBackgroundColor(backgroundColor);
        chart.getStyler().setXAxisLabelRotation(35);
        chart.getStyler().setAnnotationTextFontColor(annotationColor);
        chart.getStyler().setLabelsFontColor(annotationColor);
        chart.getStyler().setAxisTickLabelsColor(annotationColor);
        chart.getStyler().setAxisTickMarksColor(annotationColor);
        chart.getStyler().setMarkerSize(8);
        chart.getStyler().setChartFontColor(annotationColor);
        chart.getStyler().setPlotGridVerticalLinesVisible(false);
        chart.getStyler().setPlotGridHorizontalLinesVisible(false);

        return chart;
    }

    private int calculateSmellDensity(int totalSmell, int totalLOC) {
        return ((totalSmell * 1000) / totalLOC);
    }

    private int getTotalLOC(List<TypeMetrics> typeMetrics) {
        int totalLOC =0;
        for (int i =0; i< typeMetrics.size(); i++) {
            totalLOC += Integer.parseInt(typeMetrics.get(i).LOC);
        }
        return totalLOC;
    }
}
