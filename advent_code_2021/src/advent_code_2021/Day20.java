package advent_code_2021;

import java.util.List;
import java.util.Map;

/*
 * Image enhancement program that operates similar to game of life
 * after each round of enhancement size of picture can change
 * 
 * image is infinite, initially with background that is DARK
 * but if background DARK && (rule[0] = LIGHT) then background becomes LIGHT
 * if background is LIGHT && (rule[511] = DARK) then background becomes DARK
 * 
 * image is stored where true = !BACKGROND
 */
public class Day20 {
	private static final String DAY20_INPUT_TXT = "src/resources/day20_input.txt";
	private static final boolean LIGHT = true;
	private static final boolean DARK = false;
	private static final int DARK_RULE = 0;
	private static final int LIGHT_RULE = 511;
	private static boolean[] rules;
	private static boolean background = DARK;

	public static void main(String[] args) {
		boolean[][] image; // not sure if this is a wise choice for part 2, but makes many aspects easier.  
		int minX = 0;
		int minY = 0;
		image = initialize();
	
		// part 1 enhance image twice
		image = enhanceImage(image);
		image = enhanceImage(image);
		System.out.println("Day 20 part 1 " + countLit(image, background));

		// part 2 enhance a total of 50 times (48 more)
		for (int i=0; i<48; i++) {
			image = enhanceImage(image);
		}
		System.out.println("Day 20 part 2 " + countLit(image, background));
		
	}
	
	/*
	 *  expand map by +2 in every direction
	 *  determine new background
	 *  
	 *  process old image && old background to populate
	 *  new image
	 */
	private static boolean[][] enhanceImage(boolean[][] input) {
		boolean[][] output = new boolean[input.length+4][input[0].length+4];
		boolean oldBackground = background;
		background = (background == DARK ? rules[DARK_RULE] : rules[LIGHT_RULE]);
		for (int r=0; r<output.length; r++) {
			for (int c=0; c<output[0].length; c++) {
				output[r][c] = calculatePixelIsLit(r-2, c-2, input, oldBackground) != background; 
			}
		}
		return output;
	}
	
	/*
	 * examine pixels in 9x9 grid centered on pixel
	 * convert to integer 0-511
	 * look up rule
	 * return whether pixel matches new background
	 */
	
	private static boolean calculatePixelIsLit(int row, int col, boolean[][] image, boolean oldBackground) {
		String rule = "";
		for (int r=row-1; r<=row+1; r++) {
			for(int c=col-1; c<=col+1; c++) {
				rule += isLit(image, r, c, oldBackground) ? "1" : "0"; 
			}
		}
		return rules[Integer.parseInt(rule,2)];			
	}

	private static int countLit(boolean[][] image, boolean background) {
		int count = 0;
		for (int r=0; r<image.length; r++) {
			for (int c=0; c<image[0].length; c++) {
				count += isLit(image,r,c,background) ? 1 : 0;
			}
		}
		return count;
	}
	
	private static boolean isLit(boolean[][] image, int r, int c, boolean background) {
		if (r<0 || r>= image.length) return background;
		if (c<0 || c>= image[0].length) return background;
		if (background == DARK) return image[r][c];
		return !image[r][c];
	}

	private static boolean[][]  initialize() {
		List<String> records = FileUtility.readRecords(DAY20_INPUT_TXT,"\\r?\\n\\r?\\n");
		rules = getRules(records.get(0));
		return getImage(records.get(1));
	}

	private static boolean[] getRules(String s) {
		s = s.replaceAll("\\s","");
		boolean[] rules = new boolean[s.length()];
		for (int i=0; i<s.length(); i++) {
			rules[i] = (s.charAt(i) == '#');
		}
		return rules;
	}
	
	private static boolean[][] getImage(String s) {
				String[] sa = s.split("\\r?\\n");
		boolean[][] image = new boolean[sa.length][sa[0].length()];
		for (int r=0; r<sa.length; r++) {
			for (int c=0; c<sa[0].length(); c++) {
				image[r][c] = (sa[r].charAt(c) == '#');
			}
		}
		return image;
	}

	private static void displayImage (boolean[][] image, boolean background) {
		System.out.println("\nbackground is " + background);
		for (int r=0; r<image.length; r++) {
			for (int c=0; c<image[0].length; c++) {
				System.out.print(image[r][c] == background ? "." : "#");
			}
			System.out.println();
		}
	}
	
}
