import java.io.*;
public class Check {
	public static void main(String[] args) throws IOException {

        String line = "";  
        String splitBy = (",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");  
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] vert = line.split(splitBy);
				if(vert[0].charAt(0)=='\"')
				   vert[0] =vert[0].substring(1, vert[0].length()-1);
				if(vert[1].charAt(0)=='\"')
				   vert[1] =vert[1].substring(1, vert[1].length()-1);
                System.out.println(vert[0] + " ; " + vert[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }
}