package satellite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Satellite {

    static int[][] oldImage;
    static int[][] newImage;
    static int noOfRows, noOfCols;

    public static void main(String[] args) {
        String file = args.length == 0 ? "input.txt" : args[0];
        try {
            run(file);
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }
    }

    static void run(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            readSize(reader);
            createImages();
            readImage(reader, oldImage);
            readImage(reader, newImage);
        }
        printResult();
    }

    static void readSize(BufferedReader reader) throws IOException {
        noOfRows = Integer.parseInt(reader.readLine().trim());
        noOfCols = Integer.parseInt(reader.readLine().trim());
    }

    static void createImages() {
        oldImage = new int[noOfRows][noOfCols];
        newImage = new int[noOfRows][noOfCols];
    }

    static void readImage(BufferedReader reader, int[][] image) throws IOException {
        for (int row = 0; row < noOfRows; row++) {
            String[] parts = reader.readLine().trim().split("\\s+");
            for (int col = 0; col < noOfCols; col++)
                image[row][col] = Integer.parseInt(parts[col]);
        }
    }

    static int findBorder(boolean row, boolean fromStart) {
        int limit = row ? noOfRows : noOfCols;
        int position = fromStart ? 0 : limit - 1;
        int step = fromStart ? 1 : -1;
        while (position >= 0 && position < limit && equalLine(position, row))
            position += step;
        return position;
    }

    static boolean equalLine(int index, boolean row) {
        int limit = row ? noOfCols : noOfRows;
        for (int i = 0; i < limit; i++)
            if (value(oldImage, index, i, row) != value(newImage, index, i, row))
                return false;
        return true;
    }

    static int value(int[][] image, int index, int i, boolean row) {
        return row ? image[index][i] : image[i][index];
    }

    static void printResult() {
        int x1 = findBorder(true, true);
        int x2 = findBorder(true, false);
        int y1 = findBorder(false, true);
        int y2 = findBorder(false, false);
        if (x1 > x2 || y1 > y2) System.out.println("The two images are the same");
        else System.out.println((x1 + 1) + " " + (y1 + 1) + " " + (x2 + 1) + " " + (y2 + 1));
    }
}