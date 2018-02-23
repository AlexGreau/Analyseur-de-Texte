import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Analyseur {
    public static  void main(String [] args) throws IOException
    {
        int nomsRep = 0;
        int prenomsRep = 0;
        int fullRep = 0;
        int totalSpotted = 0;
        String nom,prenom;
        int zeub = 0;

        String title = "Kyoto.txt";

        // deduire du titre le nom de l'entite
        String [] entities = title.split("_"); // deduire du titre le nom de l'entite
        //prenom & nom separes
        prenom = entities[0];
        nom = entities[entities.length - 1].split("\\.")[0];

        Scanner sc = new Scanner(new FileReader(title));
        while (sc.hasNext())
        {

            String s=sc.next();
            if(s.matches(prenom)){
                String next = sc.next();
                if (next.matches(".?" + nom + ".?")){
                    //System.out.println(s + " " + next + " repere");
                    fullRep ++;
                }
                else {
                   // System.out.println(s + " repere");
                    prenomsRep ++;
                }
            }
            else if (s.matches(".?" + nom + ".?")) {
               // System.out.println(s + " repere");
                nomsRep ++;
                zeub ++;

         }
        }
        //print stats
        totalSpotted = nomsRep + prenomsRep + fullRep;
        System.out.println(" nomsRep = " + nomsRep + "\n prenoms = " + prenomsRep + "\n fullname = " + fullRep + "\n \n TOTAL SPOTTED : " + totalSpotted);
    }
}
