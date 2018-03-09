import java.io.*;
import java.util.Scanner;

public class Analyseur {

    public static String linkFinder(String titre) throws FileNotFoundException {
        //prends titre.txt et effectue une recherche sur les liens pour voir lesquels sont concernÃ©s
        // si lien trouve, renvoie le nom du link
        String correcTitle = titre.split("\\.")[0];
        System.out.println(correcTitle);
        // ajouter "http://en.wikipedia.org/wiki/" devant le titee pour obtenir le lien
        String linkName = "http://en.wikipedia.org/wiki/" + correcTitle;
        String ressourcesPath = "analyseurWeb/entity_list.txt";
        FileReader fileReader = new FileReader(ressourcesPath);
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNext()){
            String s = scanner.next();
            if (s.matches(linkName)){
                return linkName;
            }
        }
        return "LinkNotFound";
    }

    public static String baliseCreator(String linkName, String corpse ){
        // <entity name="http://en.wikipedia.org/wiki/Catherine_Deneuveâ€�>Catherine Deneuve</entity>
        StringBuffer buff = new StringBuffer();
        buff.append("<entity name=\"");
        buff.append(linkName);
        buff.append("\">");
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
        // prendre premiere date avant ")"
        Scanner sc = null;
        StringBuilder resultat = new StringBuilder();
        String cheminFichier="analyseurWeb/" + doc;
        try {
            sc = new Scanner(new FileReader(cheminFichier)).useDelimiter("\\)");
        } catch (FileNotFoundException e) {
            System.out.println("doc not found !!! \n");
        }
        boolean found = false;
        String anneePatern = "[0-9]{4}"; // annee
        String jourPattern = "[0-9]?[0-9]"; // jourd
        String firstSentence = sc.next();
        Scanner scanner = new Scanner(firstSentence);
        StringBuilder datestr = new StringBuilder();
        while (scanner.hasNext() && found == false){
            String actual = scanner.next();
            if (actual.matches(anneePatern)){
                datestr.insert(0,actual);
                found = true;
            }else if (actual.matches("January")){
                datestr.insert(0,"/01");
            }else if (actual.matches("February")){
                datestr.insert(0,"/02");
            }else if (actual.matches("March")){
                datestr.insert(0,"/03");
            }else if (actual.matches("April")){
                datestr.insert(0,"/04");
            }else if (actual.matches("May")){
                datestr.insert(0,"/05");
            }else if (actual.matches("June")){
                datestr.insert(0,"/06");
            }else if (actual.matches("July")){
                datestr.insert(0,"/07");
            }else if (actual.matches("August")){
                datestr.insert(0,"/08");
            }else if (actual.matches("September")){
                datestr.insert(0,"/09");
            }else if (actual.matches("October")){
                datestr.insert(0,"/10");
            }else if (actual.matches("November")){
                datestr.insert(0,"/11");
            }else if (actual.matches("December")){
                datestr.insert(0,"/12");
            }
            else if (actual.matches(jourPattern)){
                datestr.append("/" + actual);
            }
        }
        resultat.append("<");
        try {
            resultat.append(linkFinder(doc));
        } catch (FileNotFoundException e) {
            return "doc not found !!!";
        }
        resultat.append(",");
        if (found == true){
            resultat.append("hasDate,");
        }else {
            resultat.append("noDate,");
        }
        resultat.append(datestr);
        resultat.append(">");

        return resultat.toString();
    }
    
    public static  String extracteurType(String title) throws IOException
    {
	  String cheminFichier="analyseurWeb/" + title;
	  //scanner pour avoir eviter les eventuelles premier . en prononciation
	  Scanner sc = new Scanner(new FileReader(cheminFichier)).useDelimiter("\\)");
	  sc.next();
	  String first=sc.next();
	  //scanner pour recuperer la fin de la premiere phrase
	  Scanner sc2 = new Scanner(first).useDelimiter("\\.");
	  first=sc2.next();
	  String resultat="<\"http://en.wikipedia.org/wiki/";
	  resultat+=title.split("\\.")[0];
	  resultat+="\", \"type\",";
	  //scanner pour trouver le type
	  Scanner sc3 = new Scanner(first);
	  while(sc3.hasNext()){
		  String next=sc3.next();
		  if(next.matches("is")||next.matches("was")){
			  sc3.next();
			  while(sc3.hasNext()){
				  resultat+=sc3.next()+" ";
			  }
			  resultat+=">";
		  }
	  }
	  sc.close();
	  sc2.close();
	  sc3.close();
	  return resultat;
	  
    }

    public static  void main(String [] args) throws IOException
    {
        String nom,prenom, completeTitle;
        String title = "Benjamin_Biolay.txt";
        String link;
        System.out.println(dateExtractor(title));
        System.out.println(extracteurType(title));
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
        String cheminFichier="analyseurWeb/" + title;
        Scanner sc = new Scanner(new FileReader(cheminFichier)).useDelimiter("\n");
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
                // scanner mot Ã  mot
                if (s2.matches(prenom)) {
                    // si match vec prenom :  deux solutions
                    String next = sc2.next();
                    if (next.matches(".?" + nom + ".?")) {
                        // prenom + nom
                        fileWriter.write(baliseCreator(link,s2 + " " + next));
                    } else {
                        // prenom simple
                        fileWriter.write(baliseCreator(link, s2));

                    }
                } else if (s2.matches(".?" + nom + ".?")) {
                    // nom repere
                    fileWriter.write(baliseCreator(link, s2));
                }else if (sexe == true && s2.matches(".?She") ||s2.matches("she")) {
                    fileWriter.write(baliseCreator(link, s2));
                }else if (sexe == false && s2.matches(".?He") ||s2.matches("he")){
                    fileWriter.write(baliseCreator(link, s2));
                } else {
                    fileWriter.write(s2 + " ");
                }
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
}