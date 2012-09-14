package net.coljac.pirates.gui;

import static com.googlecode.charts4j.Color.BLACK;
import static com.googlecode.charts4j.Color.LIGHTGREY;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.coljac.pirates.Card;
import net.coljac.pirates.Ship;
import net.miginfocom.swing.MigLayout;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GChart;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Plots;

/**
 * The Class StatisticsPanel.
 */
public class StatisticsPanel extends JPanel {

    /** serialVersionUID. */
    private static final long serialVersionUID = -5842541989674042866L;

    /**
     * Instantiates a new statistics panel.
     */
    public StatisticsPanel() {
        super();
        setLayout(new MigLayout());

        final GChart chartFactions = buildDataFactions();
        final GChart chartExpansions = buildDataExpansions();

        try {
            final JLabel labelFactions = new JLabel("Factions repartition", JLabel.CENTER);
            final JLabel labelExpansions = new JLabel("Expansions repartition", JLabel.CENTER);
            final JLabel graphFactions = new JLabel(new ImageIcon(ImageIO.read(new URL(chartFactions.toURLString()))));
            add(graphFactions, "wrap, center");
            add(labelFactions, "wrap, center");
            final JLabel graphExpansions = new JLabel(new ImageIcon(ImageIO.read(new URL(chartExpansions.toURLString()))));
            add(graphExpansions, "wrap, center");
            add(labelExpansions, "wrap, center");
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private GChart buildData(final Map<String, Number> counts, final long max) {
        final List<Number> values = new ArrayList<Number>();
        final List<String> labels = new ArrayList<String>();
        for (final Entry<String, Number> entry : counts.entrySet()) {
            labels.add(entry.getKey());
            values.add(entry.getValue());
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        Collections.reverse(labels);

        // final Plot plot = Plots.newPlot(Data.newData(0, 66.6, 33.3, 100));
        final AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 14, AxisTextAlignment.RIGHT);
        final AxisLabels axisValues = AxisLabelsFactory.newNumericRangeAxisLabels(0, max);
        axisValues.setAxisStyle(axisStyle);
        final AxisLabels axisLabels = AxisLabelsFactory.newAxisLabels(labels);
        axisLabels.setAxisStyle(axisStyle);

        final BarChartPlot plot = Plots.newBarChartPlot(DataUtil.scaleWithinRange(0, max, values));
        final BarChart chart = GCharts.newBarChart(plot);
        // chart.setTitle("Stats", BLACK, 16);
        // chart.addHorizontalRangeMarker(40, 60, Color.newColor(RED, 30));
        // chart.setDataStacked(true);
        chart.addXAxisLabels(axisValues);
        chart.addYAxisLabels(axisLabels);
        chart.setHorizontal(true);
        chart.setBarWidth(19);
        chart.setSpaceWithinGroupsOfBars(2);
        chart.setGrid((50.0 / max) * 20, 600, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(LIGHTGREY));
        // final LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("E37600"), 100);
        // fill.addColorAndOffset(Color.newColor("DC4800"), 0);
        // chart.setAreaFill(fill);
        chart.setSize(700, (values.size() + 1) * 28);
        return chart;
    }

    private GChart buildDataExpansions() {
        final Map<String, Number> counts = new HashMap<String, Number>();
        long max = 0;
        for (final Card card : ManagerMain.instance.db.getCards()) {
            final String expansion = card.getExpansion();
            final int count = card.getOwned();
            Number n;
            if (counts.containsKey(expansion)) {
                n = new Long(counts.get(expansion).longValue() + count);
            } else {
                n = new Long(count);
            }
            if (n.longValue() > max) {
                max = n.longValue();
            }
            counts.put(expansion, n);
        }

        return buildData(counts, max);
    }

    /**
     * Builds the data.
     * 
     * @return the line chart
     */
    private GChart buildDataFactions() {
        final Map<String, Number> counts = new HashMap<String, Number>();
        long max = 0;
        for (final Ship ship : ManagerMain.instance.db.getShips()) {
            final String faction = ship.getFaction();
            final int count = ship.getOwned();
            Number n;
            if (counts.containsKey(faction)) {
                n = new Long(counts.get(faction).longValue() + count);
            } else {
                n = new Long(count);
            }
            if (n.longValue() > max) {
                max = n.longValue();
            }
            counts.put(faction, n);
        }

        return buildData(counts, max);
    }

}
