import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.*;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.xfeatures2d.*;

public class Servlet2 extends HttpServlet
{
    private String greeting="From servlet2";
    public Servlet2(){}
    public Servlet2(String greeting)
    {
        this.greeting=greeting;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {    
        String opencvpath = "C:\\opencv\\build\\java\\x64\\opencv_java451.dll";
        System.load(opencvpath); 
        
        //String location1 = "C:\\Users\\koshelevss\\mercury.jpeg";
        String location1 = "C:/Users/koshelevss/mercury.jpg";
        Mat srcBase = Imgcodecs.imread(location1); 
        //if (image1.data==null) 
        //    System.out.println("image not found");
        
        System.out.println("image1.rows =" + srcBase.rows());
        System.out.println("image1.cols =" + srcBase .cols());
        
        //String location2 = "C:\\Users\\koshelevss\\mercury_avito.jpeg";
        String location2 = "C:/Users/koshelevss/mercury_avito.jpg";
        Mat srcTest1 = Imgcodecs.imread(location2); 
        System.out.println("image2.rows =" + srcTest1.rows());
        System.out.println("image2.cols =" + srcTest1 .cols());
        
        String location3 = "C:/Users/koshelevss/tiguan.jpg";
        Mat srcTest2 = Imgcodecs.imread(location3); 
        System.out.println("image3.rows =" + srcTest2.rows());
        System.out.println("image3.cols =" + srcTest2.cols());
        
        //hishistogram_comparison - start.
        // https://docs.opencv.org/master/d8/dc8/tutorial_histogram_comparison.html
        //--------------------------------------------------------------------------------------------------
        Mat hsvBase = new Mat(), hsvTest1 = new Mat(), hsvTest2 = new Mat();
        Imgproc.cvtColor( srcBase,  hsvBase,  Imgproc.COLOR_BGR2HSV );
        Imgproc.cvtColor( srcTest1, hsvTest1, Imgproc.COLOR_BGR2HSV );
        Imgproc.cvtColor( srcTest2, hsvTest2, Imgproc.COLOR_BGR2HSV );
        
        Mat hsvHalfDown = hsvBase.submat( new Range( hsvBase.rows()/2, hsvBase.rows() - 1 ), new Range( 0, hsvBase.cols() - 1 ) );
        int hBins = 50, sBins = 60;
        int[] histSize = { hBins, sBins };
        // hue varies from 0 to 179, saturation from 0 to 255
        float[] ranges = { 0, 180, 0, 256 };
        // Use the 0-th and 1-st channels
        int[] channels = { 0, 1 };
        
        Mat histBase = new Mat(), histHalfDown = new Mat(), histTest1 = new Mat(), histTest2 = new Mat();
        
        List<Mat> hsvBaseList = Arrays.asList(hsvBase);
        Imgproc.calcHist(hsvBaseList, new MatOfInt(channels), new Mat(), histBase, new MatOfInt(histSize), new MatOfFloat(ranges), false);   //вычисли гистограмму ?
        Core.normalize(histBase, histBase, 0, 1, Core.NORM_MINMAX);
        
        List<Mat> hsvHalfDownList = Arrays.asList(hsvHalfDown);
        Imgproc.calcHist(hsvHalfDownList, new MatOfInt(channels), new Mat(), histHalfDown, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histHalfDown, histHalfDown, 0, 1, Core.NORM_MINMAX);
        
        List<Mat> hsvTest1List = Arrays.asList(hsvTest1);
        Imgproc.calcHist(hsvTest1List, new MatOfInt(channels), new Mat(), histTest1, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histTest1, histTest1, 0, 1, Core.NORM_MINMAX);
        
        List<Mat> hsvTest2List = Arrays.asList(hsvTest2);
        Imgproc.calcHist(hsvTest2List, new MatOfInt(channels), new Mat(), histTest2, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histTest2, histTest2, 0, 1, Core.NORM_MINMAX);
        
        //0=Correlation ( CV_COMP_CORREL ), 1=Chi-Square ( CV_COMP_CHISQR ), 2=Intersection ( method=CV_COMP_INTERSECT ), 3=Bhattacharyya distance ( CV_COMP_BHATTACHARYYA ),  
        //4=Alternative Chi-Square (HISTCMP_CHISQR_ALT), 5= Kullback-Leibler divergence (HISTCMP_KL_DIV)
         for( int compareMethod = 0; compareMethod < 6; compareMethod++ ) {
            double baseBase = Imgproc.compareHist( histBase, histBase, compareMethod );        
            double baseHalf = Imgproc.compareHist( histBase, histHalfDown, compareMethod );     
            double baseTest1 = Imgproc.compareHist( histBase, histTest1, compareMethod );       
            double baseTest2 = Imgproc.compareHist( histBase, histTest2, compareMethod );                   
            System.out.println("Method " + compareMethod + " Perfect | Base-Half | Base-Test(1) | Base-Test(2) : " + baseBase + " | " + baseHalf + " | " + baseTest1 + " | " + baseTest2);
         };
         
        //hishistogram_comparison-end.
              
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>"+greeting+"</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
}
