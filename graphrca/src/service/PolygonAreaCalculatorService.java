package service;

import java.awt.geom.Point2D;

public class PolygonAreaCalculatorService {

    /**
     * Calculates the area of a polygon, having only the distance of vertexes to the center.
     *
     * @param triangleSides
     * @return
     */
    public double calculatePolygonArea(double[] triangleSides) {
        double angle = 360/triangleSides.length;
        double sideA, sideB;
        double area = 0.;
        for (int i = 0; i < triangleSides.length; ++i) {
            sideA = triangleSides[i];
            if (i < triangleSides.length - 1) {
                sideB = triangleSides[i + 1];
            }
            else {
                sideB = triangleSides[0];
            }

            /**
             * CASE: NO TRIANGLE, JUST LINE
             */
            if (sideB == 0.) {
                area += sideA;
            }
            else {
                Point2D.Double pointA = new Point2D.Double(sideA, 0.);
                Point2D.Double pointB = new Point2D.Double(sideB * Math.cos(angle), sideB * Math.sin(angle));
                area += calculateAngleTriangle(pointA, pointB);
            }
        }

        return area;
    }

    /**
     * Calculates the common area of two polygons, having only the distance of vertexes to the center of polygon A and B.
     * Both polygones are sliced into triangles to calculate their area.
     *
     * @param triangleSidesA
     * @param triangleSidesB
     * @return
     */
    public double calculateCommonPolygonArea(double[] triangleSidesA, double[] triangleSidesB) {
        double angle = 360/triangleSidesA.length;
        double sideA1, sideA2, sideB1, sideB2;
        double area = 0.;
        for (int i = 0; i < triangleSidesA.length; ++i) {
            sideA1 = triangleSidesA[i];
            sideB1 = triangleSidesB[i];
            if (i < triangleSidesA.length - 1) {
                sideA2 = triangleSidesA[i + 1];
                sideB2 = triangleSidesB[i + 1];
            }
            else {
                sideA2 = triangleSidesA[0];
                sideB2 = triangleSidesB[0];
            }
            area += calculateCommonTriangleArea(sideA1, sideA2, sideB1, sideB2, angle);
        }

        return area;
    }

    /**
     * Calculates the area of a triangle with one of the vertexes in the coordinate origin and coordinates
     * A and B
     *
     * @param pointA
     * @param pointB
     * @return
     */
    private double calculateAngleTriangle(Point2D.Double pointA, Point2D.Double pointB) {
        return Math.abs((pointA.getX()*(pointB.getY()))/2);
    }

    /**
     * Calculates the common area of two triangles with one of the vertexes in the coordinate origin and known angle
     * and sides A1, B1 and A2, B2
     *
     * @param sideA1
     * @param sideA2
     * @param sideB1
     * @param sideB2
     * @param angle
     * @return
     */
    private double calculateCommonTriangleArea(double sideA1, double sideA2, double sideB1, double sideB2, double angle) {
        Point2D.Double pointA = new Point2D.Double();
        Point2D.Double pointB = new Point2D.Double();
        Point2D.Double pointC = new Point2D.Double();
        Point2D.Double pointD = new Point2D.Double();
        Point2D.Double pointE = new Point2D.Double();

        /**
         * Calculate coordinates for points A, B, C and D
         */
        if (sideA1 <= sideB1) {
            pointA.setLocation(sideA1, 0.);
            pointC.setLocation(sideB1, 0.);
        }
        else {
            pointA.setLocation(sideB1, 0.);
            pointC.setLocation(sideA1, 0.);
        }

        if (sideA2 <= sideB2) {
            pointB.setLocation(sideA2*Math.cos(angle), sideA2*Math.sin(angle));
            pointD.setLocation(sideB2*Math.cos(angle), sideB2*Math.sin(angle));
        }
        else {
            pointB.setLocation(sideB2*Math.cos(angle), sideB2*Math.sin(angle));
            pointD.setLocation(sideA2*Math.cos(angle), sideA2*Math.sin(angle));
        }

        /**
         * CASE: NO TRIANGLE, JUST LINE
         */
        if ( (sideA1 <= sideB1 && sideA2 == 0. && sideB2 == 0.) ) {
            return sideA1;
        }
        else if ( (sideB1 <= sideA1 && sideB2 == 0. && sideA2 == 0.) ) {
            return sideB1;
        }

        /**
         * CASE: TRIANGLE INNER TRIANGLE
         */
        if ( (sideA1 <= sideB1 && sideA2 <= sideB2) || (sideB1 <= sideA1 && sideB2 <= sideA2) ) {
            return calculateAngleTriangle(pointA, pointB);
        }

        /**
         * CASE: INTERSECTION
         * Calculate coordinates of intersection point E
         */
        double pointEXDividend = (pointA.getX()*pointD.getY() - pointA.getY()*pointD.getX())*(pointC.getX() - pointB.getX()) -
                (pointA.getX() - pointD.getX())*(pointC.getX()*pointB.getY() - pointC.getY()*pointB.getX());
        double pointEYDividend = (pointA.getX()*pointD.getY() - pointA.getY()*pointD.getX())*(pointC.getY() - pointB.getY()) -
                (pointA.getY() - pointD.getY())*(pointC.getX()*pointB.getY() - pointC.getY()*pointB.getX());
        double pointEDivisor = (pointA.getX() - pointD.getX())*(pointC.getY() - pointB.getY()) -
                (pointA.getY() - pointD.getY())*(pointC.getX() - pointB.getX());

        double pointEX = pointEXDividend / pointEDivisor;
        double pointEY = pointEYDividend / pointEDivisor;

        pointE.setLocation(pointEX, pointEY);

        /**
         * Calculate area for polygon with coordinates (0, 0), A, B and E (common area of both triangles)
         */
        return Math.abs(((0. - pointA.getX()*pointE.getY()) + (pointB.getX()*pointE.getY() - pointE.getX()*pointB.getY()))/2);
    }
}
