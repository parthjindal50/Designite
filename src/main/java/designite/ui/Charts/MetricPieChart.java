package designite.ui.Charts;

import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;

import java.awt.*;
import java.util.List;

public class MetricPieChart {
    public PieChart getPieChart(List<Number> chartData, List<String> chartLabels) {
        PieChart chart = new PieChartBuilder().width(700).height(700).title("").build();

        Color annotationColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
        Color backgroundColor = UIUtil.getPanelBackground();

        for (int i = 0; i < chartData.size(); i++) {
            chart.addSeries(chartLabels.get(i), chartData.get(i));
        }
        Color[] sliceColors = new Color[] {
                new Color(194, 55, 40),
                new Color(225, 75, 49),
                new Color(222, 110, 86),
                new Color(225, 166, 146),
                new Color(226, 226, 226),
                new Color(25, 132, 197),
                new Color(34, 167, 240),
                new Color(99, 191, 240),
                new Color(167, 213, 237)
        };

        PieStyler styler = chart.getStyler();

        styler.setSeriesColors(sliceColors);
        styler.setToolTipsEnabled(true);
        styler.setToolTipBackgroundColor(backgroundColor);
        styler.setToolTipBorderColor(annotationColor);
        styler.setToolTipHighlightColor(backgroundColor);
        styler.setLegendBackgroundColor(backgroundColor);
        styler.setChartBackgroundColor(backgroundColor);
        styler.setPlotBackgroundColor(backgroundColor);
        styler.setPlotBorderColor(backgroundColor);
        styler.setPlotContentSize(.9);
        styler.setLegendBorderColor(backgroundColor);
        styler.setChartFontColor(annotationColor);
        styler.setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);

        return chart;
    }

    public PieChart getDonutPieChart(Project project) {
        PieChart chart = new PieChartBuilder().width(300).height(300).title("").build();

        Color annotationColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
        Color backgroundColor = UIUtil.getPanelBackground();

        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);

        chart.addSeries("Implementation Smells", smellsInfoProvider.getTotalImplementationSmells());
        chart.addSeries("Design Smells", smellsInfoProvider.getTotalDesignSmells());
        chart.addSeries("Architecture Smells", smellsInfoProvider.getTotalArchitectureSmells());
        chart.addSeries("Test Smells", smellsInfoProvider.getTotalTestSmells());
        chart.addSeries("Testability Smells", smellsInfoProvider.getTotalTestabilitySmells());

        Color[] sliceColors = new Color[] {
                new Color(224, 68, 14),
                new Color(230, 105, 62),
                new Color(236, 143, 110),
                new Color(243, 180, 159),
                new Color(246, 199, 182)
        };

        PieStyler styler = chart.getStyler();

        styler.setSeriesColors(sliceColors);
        styler.setToolTipsEnabled(true);
        styler.setToolTipBackgroundColor(backgroundColor);
        styler.setToolTipBorderColor(annotationColor);
        styler.setToolTipHighlightColor(backgroundColor);
        styler.setLegendBackgroundColor(backgroundColor);
        styler.setChartBackgroundColor(backgroundColor);
        styler.setPlotBackgroundColor(backgroundColor);
        styler.setPlotBorderColor(backgroundColor);
        styler.setPlotContentSize(.9);
        styler.setLegendVisible(true);
        styler.setLabelsFontColor(Color.RED);
        styler.setLegendBorderColor(backgroundColor);
        styler.setDonutThickness(.5);
        styler.setChartFontColor(annotationColor);
        styler.setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);

        return chart;
    }
}
