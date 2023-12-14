import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class imageEditor {

    //function 1 rotate clockwise
    static BufferedImage rotateClockwise(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color pixel = new Color(inputImage.getRGB(i,j));
                outputImage.setRGB(j, i, pixel.getRGB());
            }
        }

        outputImage = mirror(outputImage);

        return outputImage;
    }

    //function 2 rotate anticlockwise
    static BufferedImage rotateAntiClockwise(BufferedImage inputImage){
        BufferedImage outputImage = rotateClockwise(inputImage);
        outputImage = rotateClockwise(outputImage);
        outputImage = rotateClockwise(outputImage);
        return outputImage;
    }

    //function 3 mirror
    static BufferedImage mirror(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width/2; j++){
                Color pixel = new Color(inputImage.getRGB(j, i));
                outputImage.setRGB(j,i,inputImage.getRGB(inputImage.getWidth()-1-j , i));
                outputImage.setRGB(inputImage.getWidth()-1-j , i , pixel.getRGB());
                
            }
        }
        return outputImage;
    }

    //function 4 greyscale
    static BufferedImage convertToGreyScale(BufferedImage inputImage){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_BYTE_GRAY);

        for(int i = 0; i < height; i++){

            for(int j =0; j < width; j++){

                outputImage.setRGB(j,i, inputImage.getRGB(j,i));

            }
        }
        return outputImage;
    }

    //function 5 change brightness
    static BufferedImage changeBrightness(BufferedImage inputImage , int increase){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_3BYTE_BGR);
        
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width ; j++){

                Color pixel = new Color(inputImage.getRGB(j,i));

                int red = pixel.getRed();
                int blue = pixel.getBlue();
                int green = pixel.getGreen(); 
                
                red = red + (increase*red/100);
                blue = blue + (increase*blue/100);
                green = green + (increase*green/100);

                if(red > 255){
                    red = 255;
                }
                if(blue > 255){
                    blue = 255;
                }
                if(green > 255){
                    green = 255;
                }
                if(red < 0){
                    red = 0;
                }
                if(blue < 0){
                    blue = 0;
                }
                if(green < 0){
                    green = 0;
                }

                Color newPixel = new Color(red , green , blue);
                outputImage.setRGB(j,i,newPixel.getRGB());

            }
        }

        return outputImage;
    }

    //function 6 invert colours
    static BufferedImage inversion(BufferedImage inputImage){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < height; i++){

            for(int j = 0; j < width; j++){

                Color pixel = new Color(inputImage.getRGB(j,i));

                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();

                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;

                Color newPixel = new Color(red, green, blue);

                outputImage.setRGB(j, i, newPixel.getRGB());

            }
        }
        return outputImage;
    }

    //function 7 blur
    static BufferedImage blurr(BufferedImage inputImage , int pixelCount){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        int rowStart = 0;
        int rowEnd = pixelCount-1;

        while(rowEnd < height){

            int columnStart = 0;
            int columnEnd = pixelCount-1 ;

            while(columnEnd < width){

                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                for(int i = rowStart; i <= rowEnd; i++){
                    for(int j = columnStart; j <= columnEnd; j++){

                        Color pixel = new Color(inputImage.getRGB(j,i));

                        sumRed += pixel.getRed();
                        sumBlue += pixel.getBlue();
                        sumGreen += pixel.getGreen();

                    }
                }

                int avgRed = sumRed/(pixelCount*pixelCount);
                int avgBlue = sumBlue/(pixelCount*pixelCount);
                int avgGreen = sumGreen/(pixelCount*pixelCount);

                Color newPixel = new Color(avgRed , avgGreen , avgBlue);

                for(int i = rowStart ; i <= rowEnd ; i++){
                    for(int j = columnStart; j <= columnEnd; j++){
                        outputImage.setRGB(j, i, newPixel.getRGB() );
                    }
                }

                columnStart+=pixelCount;
                columnEnd+=pixelCount;
            }

            rowStart+=pixelCount;
            rowEnd+=pixelCount;
        }

        return outputImage;
    }

    public static void main (String agrs[]){

        try (Scanner sc = new Scanner(System.in)) {
            while (true){

                System.out.println("********** JAVA IMAGE EDITOR **********");
                System.out.println("");
                System.out.println("Please enter the path of the image you want to edit");
                System.out.print("(You can enter just the name and extension of the file if it is in the same directory): ");

                String location = sc.next();

                File inputFile = new File(location);            

                System.out.println("");
                System.out.println("1. Rotate clockwise");
                System.out.println("2. Rotate anticlockwise");
                System.out.println("3. Mirror");
                System.out.println("4. Convert to greyscale");
                System.out.println("5. Change brightness");
                System.out.println("6. Invert colours");
                System.out.println("7. Blur");
                System.out.println("0. EXIT");
                System.out.println("");
                System.out.print("Please choose the operation you want to perform on the image: ");

                int choice = sc.nextInt();
                System.out.println("");

                if (choice == 0){
                    System.out.println("Exit successful.");
                    break;
                }

                try{

                    BufferedImage inputImage = ImageIO.read(inputFile);

                    switch(choice){

                        case 1: BufferedImage rotatedClockwise = rotateClockwise(inputImage);
                                System.out.print("Please enter the name of the output file: ");
                                String outputname1 = sc.next();
                                File rotatedClockwiseImage = new File(outputname1 + ".jpeg");
                                ImageIO.write(rotatedClockwise , "jpeg" , rotatedClockwiseImage);
                                System.out.println("\nYour image has been successfully edited.\n");
                                break;

                        case 2: BufferedImage rotatedAntiClockwise = rotateAntiClockwise(inputImage);
                                System.out.print("Please enter the name of the output file: ");
                                String outputname2 = sc.next();
                                File rotatedAntiClockwiseImage = new File(outputname2 + ".jpeg");
                                ImageIO.write(rotatedAntiClockwise , "jpeg" , rotatedAntiClockwiseImage);
                                System.out.println("\nYour image has been successfully edited.\n");
                                break;

                        case 3: BufferedImage mirrored = mirror(inputImage);
                                System.out.print("Please enter the name of the output file: ");
                                String outputname3 = sc.next();
                                File mirroredImage = new File(outputname3 + ".jpeg");
                                ImageIO.write(mirrored , "jpeg" , mirroredImage);
                                System.out.println("\nYour image has been successfully edited.\n");
                                break;

                        case 4: BufferedImage grayScale = convertToGreyScale(inputImage);
                                System.out.print("Please enter the name of the output file: ");
                                String outputname4 = sc.next();
                                File grayScaleImage = new File(outputname4 + ".jpeg");
                                ImageIO.write(grayScale , "jpeg" , grayScaleImage);
                                System.out.println("\nYour image has been successfully edited.\n");
                                break;

                        case 5: System.out.print("Please enter the percentage by which you want to change the brightness: ");
                                int factor = sc.nextInt();
                                BufferedImage changedBrightness = changeBrightness(inputImage, factor);
                                System.out.print("Please enter the name of the output file: ");
                                String outputname5 = sc.next();
                                File changedBrightnessImage = new File(outputname5 + ".jpeg");
                                ImageIO.write(changedBrightness , "jpeg" , changedBrightnessImage);
                                System.out.println("\nYour image has been successfully edited.\n");
                                break;

                        case 6: BufferedImage inverted = inversion(inputImage);
                                System.out.print("Please enter the name of the output file: ");
                                String outputname6 = sc.next();
                                File invertedImage = new File(outputname6 + ".jpeg");
                                ImageIO.write(inverted , "jpeg" , invertedImage);
                                System.out.println("\nYour image has been successfully edited.\n");
                                break;

                        case 7: System.out.print("Please enter length of the suare you want to use for blurring: ");
                                int sideLength = sc.nextInt();
                                BufferedImage blurred = blurr(inputImage , sideLength);
                                System.out.print("Please enter the name of the output file: ");
                                String outputname7 = sc.next();
                                File blurredImage = new File(outputname7 + ".jpeg");
                                ImageIO.write(blurred , "jpeg" , blurredImage);
                                System.out.println("\nYour image has been successfully edited.\n");
                                break;

                        default: System.out.println("\nPlease enter a valid option.\n");
                    }
                    
                }
                catch(IOException e){
                    e.printStackTrace();
                }

            }

        }

    }

}
