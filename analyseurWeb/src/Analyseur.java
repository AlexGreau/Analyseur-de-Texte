import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class Analyseur {
    public static boolean searchLink(String titre) throws FileNotFoundException {
        //prends titre.txt et effectue une recherche sur les liens pour voir lesquels sont concernés
        String correcTitle = titre.split("\\.")[0];
        System.out.println(correcTitle);
        // ajouter "http://en.wikipedia.org/wiki/" devant le titee pour obtenir le lien
        String linkName = "http://en.wikipedia.org/wiki/" + correcTitle;
        String ressourcesPath = "entity_list.txt";
        FileReader fileReader = new FileReader(ressourcesPath);
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNext()){
            String s = scanner.next();
            if (s.matches(linkName)){
                return true;
            }
        }
        return false;
    }
    public static  void main(String [] args) throws IOException
    {
        int nomsRep = 0;
        int prenomsRep = 0;
        int fullRep = 0;
        String nom,prenom, completeTitle;
        String title = "Kyoto.txt";

        // deduire du titre le nom de l'entite
        String [] entities = title.split("_"); // deduire du titre le nom de l'entite
        //prenom & nom separes
        prenom = entities[0];
        nom = entities[entities.length - 1].split("\\.")[0];
        completeTitle = title.split("\\.")[0];
        System.out.println(searchLink(completeTitle));

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
            }
        }
        //print stats
        int totalSpotted = nomsRep + prenomsRep + fullRep;

        System.out.println(" nomsRep = " + nomsRep + "\n prenoms = " + prenomsRep + "\n fullname = " + fullRep + "\n \n TOTAL SPOTTED : " + totalSpotted);
    }
}
