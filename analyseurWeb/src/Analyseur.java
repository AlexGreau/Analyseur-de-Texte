import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Analyseur {
    public static  void main(String [] args) throws IOException
    {
        int nomsRep = 0;
        int prenomsRep = 0;
        int fullRep = 0;
        String title = "Alessandro_Papetti.txt";
        // deduire du titre le nom de l'entite
        String []entities = title.split("_"); // deduire du titre le nom de l'entite
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(entities[0]);
        //prenom & nom separes
        String prenom = stringBuilder.toString();
        stringBuilder.append(" ");
        String nom = entities[1].split("\\.")[0];
        stringBuilder.append(nom);

        Scanner sc = new Scanner(new FileReader(title));

        while (sc.hasNext())
        {

            String s=sc.next();
            if(s.matches(prenom)){
                String next = sc.next();
                if (next.matches(nom+ ".?")){
                    //System.out.println(s + " " + next + " repere");
                    fullRep ++;
                }
                else {
                   // System.out.println(s + " repere");
                    prenomsRep ++;
                }
            }
            else if (s.matches(nom + ".?")) {
               // System.out.println(s + " repere");
                nomsRep ++;
         }
        }
        //print stats
        System.out.println(" nomsRep = " + nomsRep + "\n prenomsRep = " + prenomsRep + "\n full Rep = " + fullRep);
    }
}
