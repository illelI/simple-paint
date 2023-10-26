package state;

import components.MainPanel;
import components.Frame;
import model.Canvas;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

public abstract class CanvasState extends JPanel implements MouseListener, MouseMotionListener {
    protected MainPanel mainPanel;
    private final Canvas canvas;
    protected final Frame frame;
    protected int howMany;
    protected int current;
    protected List<BezierPoint> controlPoints;
    protected List<BezierPoint> points;

    CanvasState(MainPanel panel, Frame frame) {
        this.mainPanel = panel;
        this.frame = frame;
        canvas = Canvas.getInstance();
        controlPoints = convertShapeToBezierPoint(canvas.getControlPoints());
        points = convertShapeToBezierPoint(canvas.getShapesList());
        current = 0;
        howMany = 0;
    }

    public abstract void draw();

    public Canvas getCanvas() {
        return canvas;
    }

    private int newton(int n, int k) {
        int licznik = 1;
        int mianownik = 1;
        for (int i = n - k + 1; i < n + 1; i++) licznik *= i;
        for (int i = 1; i < k + 1; i++) mianownik *= i;
        return licznik/mianownik;
    }

    private double bernstein(int n, int i, double t) {
        return newton(n, i) * Math.pow(t, i) * Math.pow(1.0 - t, n - i);
    }

    protected void calculatePoint(int n, double t) {
        double x = 0.0;
        double y = 0.0;
        double bernstein;
        BezierPoint bp;
        for (int i = 0; i < n + 1; i++) {
            bernstein = bernstein(n, i, t);
            x += controlPoints.get(i).getX() * bernstein;
            y += controlPoints.get(i).getY() * bernstein;
        }
        bp = new BezierPoint(x, y);
        List<BezierPoint> list = points;
        for (BezierPoint point : list) {
            if (point.getX() == bp.getX() && point.getY() == bp.getY()) {
                return;
            }
        }
        points.add(bp);
    }

    protected void bezierPoints(int k) {
        int n = controlPoints.size() - 1;
        for (int i = 0; i < k + 1; i++) {
            double t = (double) i / k;
            calculatePoint(n, t);
        }
    }

    protected void makeCurve(BezierPoint p1, BezierPoint p2) {
        int x1 = (int)p1.getX();
        int y1 = (int)p1.getY();
        int x2 = (int)p2.getX();
        int y2 = (int)p2.getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;
        while (true) {
            getCanvas().addShape(new Ellipse2D.Double(x1, y1, 2, 2));
            if(x1 == x2 && y1 == y2) {
                break;
            }
            int e2 = 2*err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if(e2 < dx) {
                err = err +dx;
                y1 = y1 + sy;
            }
        }

    }

    protected List<BezierPoint> convertShapeToBezierPoint(List<Shape> shapesList) {
        List<BezierPoint> list = new ArrayList<>();
        for (Shape s : shapesList) {
            if (s == null) continue;
            list.add(new BezierPoint(s.getBounds().getX() + 2, s.getBounds().getY() + 2));
        }
        return list;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }
    public int getHowMany() {
        return howMany;
    }
    public List<BezierPoint> getControlPoints() {
        return controlPoints;
    }

    public void addPoint(int x, int y) {
        if (current < howMany) {
            getCanvas().addControlPoint(new Rectangle2D.Double(x - 2, y - 2, 4, 4));
            controlPoints.add(new BezierPoint(x, y));
            current++;
            draw();
        }
    }

    public void changePoints(List<Integer> values) {
        int x = 0;
        int y = 0;
        List<BezierPoint> updatedCp = new ArrayList<>();
        for (int value : values) {
            if (x == 0) {
                x = value;
            } else {
                y = value;
                updatedCp.add(new BezierPoint(x, y));
                x = 0;
            }
        }
        this.controlPoints = updatedCp;
        draw();
    }

    public class BezierPoint {
        double x;
        double y;
        BezierPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        void setX(double x) {
            this.x = x;
        }
        void setY(double y) {
            this.y = y;
        }
        java.awt.Point getPoint() {
            return new java.awt.Point((int)x, (int)y);
        }
    }
    
    protected class Point {
        private int x;
        private int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

    }

}
