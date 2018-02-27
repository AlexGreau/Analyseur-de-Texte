import java.io.*;
import java.util.Scanner;

public class Analyseur {

    public static String linkFinder(String titre) throws FileNotFoundException {
        //prends titre.txt et effectue une recherche sur les liens pour voir lesquels sont concernés
        // si lien trouvé, renvoie le nom du link
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
                return linkName;
            }
        }
        return "null";
    }

    public static String[] baliseCreator(String linkName){
        // <entity name=”http://en.wikipedia.org/wiki/Catherine_Deneuve”>Catherine Deneuve</entity>
        String [] stringTab = new String[2];
        stringTab[0] = "<entity name=”" + linkName + "”>";
        stringTab[1] = "</entity>";
        return stringTab;
    }

    public static  void main(String [] args) throws IOException
    {
        int nomsRep = 0;
        int prenomsRep = 0;
        int fullRep = 0;
        String nom,prenom, completeTitle;
        String title = "Alessandro_Papetti.txt";
        String []balise;
        String debutBalise,finBalise;

        // deduire du titre le nom de l'entite
        String [] entities = title.split("_"); // deduire du titre le nom de l'entite
        //prenom & nom separes
        prenom = entities[0];
        nom = entities[entities.length - 1].split("\\.")[0];
        completeTitle = title.split("\\.")[0];

        // recherche du lien dans les donnees
        System.out.println(linkFinder(completeTitle));

        // si lien existe :  creer l'entete de la balise et la fin, pour ensuite les ajouter lors de l'ecriture
        balise = baliseCreator(linkFinder(completeTitle));
        debutBalise = balise[0];
        finBalise = balise [1];

        Scanner sc = new Scanner(new FileReader(title)).useDelimiter("\n");
        File ff= new File("resultat.txt");
        ff.createNewFile();
        FileWriter fileWriter=new FileWriter(ff);
        // TODO : detectiondes pronoms personnels, Date etc et modification de la balise en consequence

        while (sc.hasNext()) {
            String s = sc.next();
            Scanner sc2 = new Scanner(s);
            while (sc2.hasNext()) {
                String s2 = sc2.next();
                if (s2.matches(prenom)) {
                    String next = sc2.next();
                    if (next.matches(".?" + nom + ".?")) {
                        //System.out.println(s + " " + next + " repere");
                        fullRep++;
                        fileWriter.write(debutBalise);
                        fileWriter.write(s2 + " " + next);
                        fileWriter.write(finBalise + " ");
                    } else {
                        // System.out.println(s + " repere");
                        prenomsRep++;
                        fileWriter.write(debutBalise);
                        fileWriter.write(s2);
                        fileWriter.write(finBalise + " ");
                    }
                } else if (s2.matches(".?" + nom + ".?")) {
                    // System.out.println(s + " repere");
                    nomsRep++;
                    fileWriter.write(debutBalise);
                    fileWriter.write(s2);
                    fileWriter.write(finBalise + " ");
                } else {
                    fileWriter.write(s2 + " ");
                }
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
        //print stats
        int totalSpotted = nomsRep + prenomsRep + fullRep;

        System.out.println(" nomsRep = " + nomsRep + "\n prenoms = " + prenomsRep + "\n fullname = " + fullRep + "\n \n TOTAL SPOTTED : " + totalSpotted);
    }
}
