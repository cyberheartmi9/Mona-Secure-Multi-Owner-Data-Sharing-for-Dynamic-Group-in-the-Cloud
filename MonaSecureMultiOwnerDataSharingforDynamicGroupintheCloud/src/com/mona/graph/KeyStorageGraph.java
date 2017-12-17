package com.mona.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.mona.user.Users;

public class KeyStorageGraph extends ApplicationFrame {

	public KeyStorageGraph(String title, int one, int two) {
			
		super(title);
		XYDataset dataset = null;
		try {
			dataset = createDataset();
		} catch (Exception e) {
			System.out.println("MultiLineChart -- Constructor" + e);
		}
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 370));
		setContentPane(chartPanel);
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	}

	private XYDataset createDataset() {
		XYSeriesCollection dataset = null;
		try {
			int auditsize = 0;
			

			int ran = new Random().nextInt();
			int x = (int) ran / Integer.MAX_VALUE * 700;

			final XYSeries series3 = new XYSeries("Existing System");
			int k = 0;
			for (int i = 0; i < 10; i++) {
				k = k + 10;
				series3.add(i, (auditsize * 10) + k);
			}

			final XYSeries series4 = new XYSeries("Praposed System");

			int z = 0;
			for (int i = 0; i < 3; i++) {
				z = z + 3;
				series4.add(i, (auditsize * 10) + z);
			}

			dataset = new XYSeriesCollection();

			dataset.addSeries(series3);
			dataset.addSeries(series4);
		} catch (Exception e) {
			System.out.println(e);
		}
		return dataset;
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createXYLineChart(
				"MONA STORAGE GRAPH", "KeyGeneration per users ",
				"KeyGenerating size in Bytes", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.white);
		final XYPlot plot1 = chart.getXYPlot();
		plot1.setBackgroundPaint(Color.lightGray);
		plot1.setDomainGridlinePaint(Color.white);
		plot1.setRangeGridlinePaint(Color.white);

		final XYPlot plot2 = chart.getXYPlot();
		plot2.setBackgroundPaint(Color.lightGray);
		plot2.setDomainGridlinePaint(Color.white);
		plot2.setRangeGridlinePaint(Color.white);

		final XYPlot plot3 = chart.getXYPlot();
		plot3.setBackgroundPaint(Color.lightGray);
		plot3.setDomainGridlinePaint(Color.white);
		plot3.setRangeGridlinePaint(Color.white);

		return chart;
	}

	public static void main(String[] args) {
		/*final KeyStorageGraph demo = new KeyStorageGraph("Auditing Chart",
				new ArrayList<Users>(3));
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);*/
	}

}