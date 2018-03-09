import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    public static String baliseCreator(String linkName, String corpse ){
        // <entity name=”http://en.wikipedia.org/wiki/Catherine_Deneuve”>Catherine Deneuve</entity>
        StringBuffer buff = new StringBuffer();
        buff.append("<entity name=”");
        buff.append(linkName);
        buff.append("”>");
        buff.append(corpse);
        buff.append("</entity>");

        return buff.toString();
    }

    public static boolean deterSexe(String nomTexte){
        Scanner sc  = new Scanner(nomTexte);
        // she = true; he = false
        while (sc.hasNext()){
            String next = sc.next();
            if (next.matches("[Ss]he")){
                return true;
            }else if (next.matches("[Hh]e")){
                return false;
            }
        }
        return false;
    }

    public static String dateExtractor(String doc){
        // TODO : detection Date etc et modification de la balise en consequence

        //DD mois annee || mois annee ||annee
        // prendre premiere date avant ")"
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(doc));
        } catch (FileNotFoundException e) {
            System.out.println("doc not found !!! \n");
        }
        boolean found = false;
        boolean end = false;
        Pattern anneePatern = Pattern.compile("[0-9]{4}"); // annee
        String moisPattern = "[A-Za-z]{3,9}"; // mois
        Pattern jourPattern = Pattern.compile("[0-9]?[0-9]"); // jourd
        while (sc.hasNext() && found == false && end == false){
            if (sc.next().matches("\\)")){
                // on veut trouver uniquement la date au debut du doc, entre parentheses
                end = true;
            }
            else if (sc.next().matches(moisPattern)){
                String next = sc.next();
                if (next.matches(anneePatern.toString())){
                    found = true;
                    System.out.println("mois + annee");
                }
            }
            else if (sc.next().matches(anneePatern.toString())){
                // aaaa
                found = true;
                System.out.println("annee");
                // ajouter au format de date et declarer " hasdate" dans la balise
            }
            else if (sc.next().matches(jourPattern.toString())){
                // ddmmaaa
                String next = sc.next();
                if (next.matches(moisPattern.toString())){
                    String nextnext = sc.next();
                    if (nextnext.matches(anneePatern.toString())){
                        found = true;
                        System.out.println("full date");
                    }
                }
            }
        }
        return "no date found";
    }

    public static  void main(String [] args) throws IOException
    {
        int nomsRep = 0;
        int prenomsRep = 0;
        int fullRep = 0;
        int pronomsRep = 0;
        String nom,prenom, completeTitle;
        String title = "Alessandro_Papetti.txt";
        String link;
        dateExtractor(title);
        // deduire du titre le nom de l'entite
        String [] entities = title.split("_"); // deduire du titre le nom de l'entite
        //prenom & nom separes
        prenom = entities[0];
        nom = entities[entities.length - 1].split("\\.")[0];
        boolean sexe = deterSexe(title);

        completeTitle = title.split("\\.")[0];

        // recherche du lien dans les donnees
        System.out.println(linkFinder(completeTitle));

        // si lien existe :  creer l'entete de la balise et la fin, pour ensuite les ajouter lors de l'ecriture
        link = linkFinder(completeTitle);


        Scanner sc = new Scanner(new FileReader(title)).useDelimiter("\n");
        File ff= new File("resultat.txt");
        ff.createNewFile();
        FileWriter fileWriter = new FileWriter(ff);
        // TODO :  exception du premier grand nom ex : david jean bailey


        while (sc.hasNext()) {
            // scanner de lignes
            String s = sc.next();
            Scanner sc2 = new Scanner(s);
            while (sc2.hasNext()) {
                String s2 = sc2.next();
                // scanner mot à mot
                if (s2.matches(prenom)) {
                    // si match vec prenom :  deux solutions
                    String next = sc2.next();
                    if (next.matches(".?" + nom + ".?")) {
                        // prenom + nom
                        fullRep++;
                        fileWriter.write(baliseCreator(link,s2 + " " + next));
                    } else {
                        // prenom simple
                        prenomsRep++;
                        fileWriter.write(baliseCreator(link, s2));

                    }
                } else if (s2.matches(".?" + nom + ".?")) {
                    // nom repere
                    nomsRep++;
                    fileWriter.write(baliseCreator(link, s2));
                }else if (sexe == true && s2.matches(".?She") ||s2.matches("she")) {
                    fileWriter.write(baliseCreator(link, s2));
                    pronomsRep++;
                }else if (sexe == false && s2.matches(".?He") ||s2.matches("he")){
                    fileWriter.write(baliseCreator(link, s2));
                    pronomsRep++;
                } else {
                    fileWriter.write(s2 + " ");
                }
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
        //print stats
        int totalSpotted = nomsRep + prenomsRep + fullRep;

        System.out.println(" nomsRep = " + nomsRep + "\n prenoms = " + prenomsRep + "\n fullname = " + fullRep + "\npronoms = "+ pronomsRep+"\n \n TOTAL SPOTTED : " + totalSpotted);
    }
}