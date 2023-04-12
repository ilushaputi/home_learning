package ru.liga.exchangerateforecast.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.entity.GraphResult;
import ru.liga.exchangerateforecast.enums.RateType;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class JFreeFormatterService  {
    private  final Logger logger = LoggerFactory.getLogger(JFreeFormatterService.class);

    private final int width = 1920;
    private final int height = 1080;

    public GraphResult buildPredication(Map<RateType, List<Float>> inputMap) {
        logger.debug("Начинаю расчитывать график..");
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (RateType type : inputMap.keySet()) {
            XYSeries xySeries = new XYSeries(type.toString());

            int x = 0;
            for (float count : inputMap.get(type)) {
                x++;
                xySeries.add(x, count);
            }

            dataset.addSeries(xySeries);
        }

        JFreeChart jFreeChart = ChartFactory.createXYLineChart("Курс валют", "Число", "Курс", dataset);

//        XYPlot plot = jFreeChart.getXYPlot();
//
//        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//
//        renderer.setSeriesPaint(0, Color.RED);
//        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
//        renderer.setSeriesPaint(1, Color.BLUE);
//        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
//        renderer.setSeriesPaint(2, Color.GREEN);
//        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
//        renderer.setSeriesPaint(3, Color.CYAN);
//        renderer.setSeriesStroke(3, new BasicStroke(2.0f));
//        renderer.setSeriesPaint(4, Color.MAGENTA);
//        renderer.setSeriesStroke(4, new BasicStroke(2.0f));
//
//        plot.setRenderer(renderer);
//        plot.setBackgroundPaint(Color.white);
//        plot.setRangeGridlinesVisible(false);
//        plot.setDomainGridlinesVisible(false);
//
//        jFreeChart.getLegend().setFrame(BlockBorder.NONE);

        BufferedImage bufferedImage = jFreeChart.createBufferedImage(width, height);

        logger.debug("График готов...");

        return new GraphResult("Граф предсказания", bufferedImage);
    }
}