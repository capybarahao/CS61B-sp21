package byow.Core;

public class Utils {

    public static long getSeedFromInput(String uprInput) {

        int i = 1;
        String seedString = new String();
        while (uprInput.charAt(i) != 'S') {
            char nextNum = uprInput.charAt(i);
            seedString = seedString + nextNum;
            i++;
            if (i == uprInput.length()) {
                System.out.println("input format: N#######SWWWWAASSS\nSeed end with S");
                System.exit(0);
            }
        }

        // parse seed to long
        long seed = Long.parseLong(seedString);
        return seed;
    }

    public static String getMoveFromInput(String uprInput) {
        String movements = "";

        if (uprInput.charAt(0) == 'L') {
            movements = uprInput.substring(1); // if only "L", get an empty string
        }
        else if (uprInput.charAt(0) == 'N') {
            for (int i = 0; i < uprInput.length(); i++) {
                if (uprInput.charAt(i) == 'S') {
                    movements = uprInput.substring(i+1);// if only "N141S", get an empty string
                    break;
                }
            }
        }
        return movements;

    }
}
