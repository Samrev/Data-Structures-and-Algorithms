import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Driver {
    public static void main(String args[]) throws IOException{
        File myObj = new File("./test3.txt");
	PrintWriter out = new PrintWriter("./output1.txt"); 
        Scanner sc = new Scanner(myObj);
        int numTestCases;
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            int size;
            size = sc.nextInt();
            A1DynamicMem obj = new A1DynamicMem(size);
            int numCommands = sc.nextInt();
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument;
                argument = sc.nextInt();
                int result = -5;
                switch (command) {
                    case "Allocate":
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        result = obj.Free(argument);
                        break;
                    default:
                        break;
                }
		out.println(result);
            }
		out.close();

        }
    }
}