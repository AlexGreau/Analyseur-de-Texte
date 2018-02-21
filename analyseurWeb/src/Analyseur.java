import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Analyseur {
    public static  void main(String [] args) throws IOException
    {
        String title = "Alessandro_Papetti.txt";
        // deduire du titre le nom de l'entite
        String []entities = title.split("_"); // deduire du titre le nom de l'entite
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(entities[0]);
        String entity1 = stringBuilder.toString();
        stringBuilder.append(" ");

        String entity2= entities[1].split("\\.")[0];
        stringBuilder.append(entity2);

        Scanner sc = new Scanner(new FileReader(title));

        while (sc.hasNext())
        {
            String s=sc.next();
            if(s.matches(entity1)){
                String next = sc.next();
                if (next.matches(entity2)){
                    System.out.println(s + " " + next + " repere");
                }
                else {
                    System.out.println(s + " repere");
                }
            }
            else if (s.matches(entity2)) {
                System.out.println(s + " repere");
         }
        }

    }
}
