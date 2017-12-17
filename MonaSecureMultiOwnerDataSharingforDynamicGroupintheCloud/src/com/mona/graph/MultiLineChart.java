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



public class MultiLineChart extends ApplicationFrame {
	Users tpaverficationUser;
	List<Users> tpaverficationUserList;

	public MultiLineChart(final String title, List<Users> tpaverficationUserList) {
		super(title);
		this.tpaverficationUserList = tpaverficationUserList;
		XYDataset dataset = null;
		try {
			dataset = createDataset();
		} catch (Exception e) {
			System.out.println("MultiLineChart -- Constructor" + e);
		}
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(700, 470));
		setContentPane(chartPanel);
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);

	}

	private XYDataset createDataset() {
		final XYSeries series1 = new XYSeries("Individual Auding (c=460)");
		XYSeriesCollection dataset = null;
		try {
			int auditsize = tpaverficationUserList.size();
			Iterator iterator = tpaverficationUserList.iterator();
			int audit = 0, j = 1;
			while (iterator.hasNext()) {
				tpaverficationUser=(Users)iterator.next();
				audit = audit + (100 + j);
			}
			int auditvalue = 1;
			try {
				auditvalue = audit / auditsize;
			} catch (Exception e) {
				System.out.println(e);
			}
			int ran = new Random().nextInt();
			int x = (int) ran / Integer.MAX_VALUE * 700;

			series1.add(0, (int) x + 900);
			series1.add(10, (int) x + 900);
			series1.add(20, (int) x + 900);
			series1.add(30, (int) x + 900);
			series1.add(40, (int) x + 900);
			series1.add(50, (int) x + 900);
			series1.add(60, (int) x + 900);
			series1.add(70, (int) x + 900);
			series1.add(80, (int) x + 900);
			series1.add(90, (int) x + 900);

			final XYSeries series2 = new XYSeries("Individual Auding (c=300)");
			int ran1 = new Random().nextInt();
			int x1 = (int) ran1 / Integer.MAX_VALUE * auditvalue;

			series2.add(0, (int) x1 + 300);
			series2.add(10, (int) x1 + 300);
			series2.add(20, (int) x1 + 300);
			series2.add(30, (int) x1 + 300);
			series2.add(40, (int) x1 + 300);
			series2.add(50, (int) x1 + 300);
			series2.add(60, (int) x1 + 300);
			series2.add(70, (int) x1 + 300);
			series2.add(10, (int) x1 + 300);
			series2.add(90, (int) x1 + 300);

			final XYSeries series3 = new XYSeries("Batch Auding (c=460)");

			series3.add(0, (auditsize * 10) + 300);
			series3.add(10, (auditsize * 10) + 350);
			series3.add(20, (auditsize * 10) + 400);
			series3.add(30, (auditsize * 10) + 450);
			series3.add(40, (auditsize * 10) + 500);
			series3.add(50, (auditsize * 10) + 550);
			series3.add(60, (auditsize * 10) + 600);
			series3.add(70, (auditsize * 10) + 650);
			series3.add(80, (auditsize * 10) + 700);
			series3.add(90, (auditsize * 10) + 750);

			final XYSeries series4 = new XYSeries("Batch Auding (c=300)");
			series4.add(0, (auditsize * 10) + 300);
			series4.add(10, (auditsize * 10) + 400);
			series4.add(20, (auditsize * 10) + 500);
			series4.add(30, (auditsize * 10) + 600);
			series4.add(40, (auditsize * 10) + 700);
			series4.add(50, (auditsize * 10) + 800);
			series4.add(60, (auditsize * 10) + 900);
			series4.add(70, (auditsize * 10) + 1000);
			series4.add(80, (auditsize * 10) + 1100);
			series4.add(90, (auditsize * 10) + 1200);

			dataset = new XYSeriesCollection();
			dataset.addSeries(series1);
			dataset.addSeries(series2);
			dataset.addSeries(series3);
			dataset.addSeries(series4);
		} catch (Exception e) {
			System.out.println(e);
		}
		return dataset;
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createXYLineChart("Auditing",
				"Fraction of Invalid Responses ", "Auding time for task(ms)",
				dataset, PlotOrientation.VERTICAL, true, true, false);
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
		final MultiLineChart demo = new MultiLineChart("Auditing Chart",
				new ArrayList<Users>(3));
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}