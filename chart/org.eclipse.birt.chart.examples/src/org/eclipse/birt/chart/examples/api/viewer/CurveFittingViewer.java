/***********************************************************************
 * Copyright (c) 2004, 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.chart.examples.api.viewer;

import org.eclipse.birt.chart.api.ChartEngine;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.examples.api.script.JavaScriptViewer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.log.ILogger;
import org.eclipse.birt.chart.log.Logger;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.PlatformConfig;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CurveFittingViewer extends Composite implements PaintListener, SelectionListener {

	private IDeviceRenderer idr = null;

	private Chart cm = null;

	private static Combo cbType = null;

	private static Button btn = null;

	private GeneratedChartState gcs = null;

	private boolean bNeedsGeneration = true;

	private static ILogger logger = Logger.getLogger(JavaScriptViewer.class.getName());

	/**
	 * execute application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setSize(600, 400);
		shell.setLayout(new GridLayout());

		CurveFittingViewer cfViewer = new CurveFittingViewer(shell, SWT.NO_BACKGROUND);
		cfViewer.setLayoutData(new GridData(GridData.FILL_BOTH));
		cfViewer.addPaintListener(cfViewer);

		Composite cBottom = new Composite(shell, SWT.NONE);
		cBottom.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cBottom.setLayout(new RowLayout());

		Label la = new Label(cBottom, SWT.NONE);

		la.setText("&Choose: ");//$NON-NLS-1$
		cbType = new Combo(cBottom, SWT.DROP_DOWN | SWT.READ_ONLY);
		cbType.add("Bar Chart");//$NON-NLS-1$
		cbType.add("Line Chart");//$NON-NLS-1$
		cbType.add("Stock Chart");//$NON-NLS-1$
		cbType.add("Area Chart");//$NON-NLS-1$
		cbType.select(0);

		btn = new Button(cBottom, SWT.NONE);
		btn.setText("&Update");//$NON-NLS-1$
		btn.addSelectionListener(cfViewer);
		btn.setToolTipText("Update");//$NON-NLS-1$

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * Constructor
	 */
	CurveFittingViewer(Composite parent, int style) {
		super(parent, style);

		try {
			PlatformConfig config = new PlatformConfig();
			config.setProperty("STANDALONE", "true"); //$NON-NLS-1$ //$NON-NLS-2$
			idr = ChartEngine.instance(config).getRenderer("dv.SWT");//$NON-NLS-1$
		} catch (ChartException pex) {
			logger.log(pex);
		}
		cm = PrimitiveCharts.createCFBarChart();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.
	 * PaintEvent)
	 */
	public final void paintControl(PaintEvent e) {
		Rectangle d = this.getClientArea();
		Image imgChart = new Image(this.getDisplay(), d);
		GC gcImage = new GC(imgChart);
		idr.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, gcImage);

		Bounds bo = BoundsImpl.create(0, 0, d.width, d.height);
		bo.scale(72d / idr.getDisplayServer().getDpiResolution());

		Generator gr = Generator.instance();
		if (bNeedsGeneration) {
			bNeedsGeneration = false;
			try {
				gcs = gr.build(idr.getDisplayServer(), cm, bo, null, null, null);
			} catch (ChartException ce) {
				ce.printStackTrace();
			}
		}

		try {
			gr.render(idr, gcs);
			GC gc = e.gc;
			gc.drawImage(imgChart, d.x, d.y);
		} catch (ChartException gex) {
			showException(e.gc, gex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.
	 * events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if (e.widget.equals(btn)) {
			int iSelection = cbType.getSelectionIndex();
			switch (iSelection) {
			case 0:
				cm = PrimitiveCharts.createCFBarChart();
				break;
			case 1:
				cm = PrimitiveCharts.createCFLineChart();
				break;
			case 2:
				cm = PrimitiveCharts.createCFStockChart();
				break;
			case 3:
				cm = PrimitiveCharts.createCFAreaChart();
				break;
			}
			bNeedsGeneration = true;
			this.redraw();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.
	 * swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	private final void showException(GC g2d, Exception ex) {
		String sWrappedException = ex.getClass().getName();
		Throwable th = ex;
		while (ex.getCause() != null) {
			ex = (Exception) ex.getCause();
		}
		String sException = ex.getClass().getName();
		if (sWrappedException.equals(sException)) {
			sWrappedException = null;
		}

		String sMessage = null;
		if (th instanceof BirtException) {
			sMessage = ((BirtException) th).getLocalizedMessage();
		} else {
			sMessage = ex.getMessage();
		}

		if (sMessage == null) {
			sMessage = "<null>";//$NON-NLS-1$
		}
		StackTraceElement[] stea = ex.getStackTrace();
		Point d = this.getSize();

		Device dv = Display.getCurrent();
		Font fo = new Font(dv, "Courier", SWT.BOLD, 16);//$NON-NLS-1$
		g2d.setFont(fo);
		FontMetrics fm = g2d.getFontMetrics();
		g2d.setBackground(dv.getSystemColor(SWT.COLOR_WHITE));
		g2d.fillRectangle(20, 20, d.x - 40, d.y - 40);
		g2d.setForeground(dv.getSystemColor(SWT.COLOR_BLACK));
		g2d.drawRectangle(20, 20, d.x - 40, d.y - 40);
		g2d.setClipping(20, 20, d.x - 40, d.y - 40);
		int x = 25, y = 20 + fm.getHeight();
		g2d.drawString("Exception:", x, y);//$NON-NLS-1$
		x += g2d.textExtent("Exception:").x + 5;//$NON-NLS-1$
		g2d.setForeground(dv.getSystemColor(SWT.COLOR_RED));
		g2d.drawString(sException, x, y);
		x = 25;
		y += fm.getHeight();
		if (sWrappedException != null) {
			g2d.setForeground(dv.getSystemColor(SWT.COLOR_BLACK));
			g2d.drawString("Wrapped In:", x, y);//$NON-NLS-1$
			x += g2d.textExtent("Wrapped In:").x + 5;//$NON-NLS-1$
			g2d.setForeground(dv.getSystemColor(SWT.COLOR_RED));
			g2d.drawString(sWrappedException, x, y);
			x = 25;
			y += fm.getHeight();
		}
		g2d.setForeground(dv.getSystemColor(SWT.COLOR_BLACK));
		y += 10;
		g2d.drawString("Message:", x, y);//$NON-NLS-1$
		x += g2d.textExtent("Message:").x + 5;//$NON-NLS-1$
		g2d.setForeground(dv.getSystemColor(SWT.COLOR_BLUE));
		g2d.drawString(sMessage, x, y);
		x = 25;
		y += fm.getHeight();
		g2d.setForeground(dv.getSystemColor(SWT.COLOR_BLACK));
		y += 10;
		g2d.drawString("Trace:", x, y);//$NON-NLS-1$
		x = 40;
		y += fm.getHeight();
		g2d.setForeground(dv.getSystemColor(SWT.COLOR_DARK_GREEN));
		for (int i = 0; i < stea.length; i++) {
			g2d.drawString(stea[i].getClassName() + ":"//$NON-NLS-1$
					+ stea[i].getMethodName() + "(...):"//$NON-NLS-1$
					+ stea[i].getLineNumber(), x, y);
			x = 40;
			y += fm.getHeight();
		}
		fo.dispose();
	}
}
