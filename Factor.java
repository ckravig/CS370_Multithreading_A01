import java.io.*;
import java.lang.Process;
import java.lang.ProcessBuilder;
import java.lang.ProcessBuilder.Redirect;
import java.util.*;

public class Factor
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        int numChild = 1;
        int total = 600000;
        // Spawn children processes
        for (int i = 0; i < numChild; i++)
        {
            ProcessBuilder pb = new ProcessBuilder("java", "FactorChild", String.valueOf(i));
            pb.redirectOutput(Redirect.INHERIT);
            pb.redirectError(Redirect.INHERIT);
            Process process = pb.start();
        }
        // Wait for children to finish
        for (int i = 0; i < numChild; i++)
        {
            Process process = Runtime.getRuntime().exec("wait");
            process.waitFor();
        }
        System.out.println("All Children Done: " + numChild);
    }
}

class FactorChild
{
    public static void main(String[] args)
    {
        int begin = 0;
        int end = 0;
        int id = Integer.parseInt(args[0]);
        int range = 600000 / 1;
        begin = id * range;
        end = begin + range;
        int start_s = (int) System.currentTimeMillis();
        int val, i;
        for (val = begin; val < end; val++)
        {
            for (i = 2; i <= val / 2; i++)
            {
                if (val % i == 0) break;
            }
            if (i > val / 2)
            {
                System.out.println("F:" + val);
            }
        }
        int stop_s = (int) System.currentTimeMillis();
        System.err.println("time: " + (stop_s - start_s));
        System.exit(0);
    }
}

