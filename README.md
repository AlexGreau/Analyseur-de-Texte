# Analyseur-de-Texte

BONDON Thibault
GREAU Alexandre

______________TD - Information Extraction from Wikipedia pages______________


EXERCIE 1 :
  - nom & prenom
Comme dans l'exemple de catherine deneuve dans l'énoncé nous avons annoté toutes les occurences de l'entité dans son article.
Pour cela nous considérons le titre comme le nom et prénom de l'entité, puis nous recherchons les occurences a l'aide d'un scanner qui lira le texte mot a mot. L'analyseur reconnait ainsi bien les noms et prenoms de l'entité, cependant pour q'une séquence "catherine deneuve" ne soit pas coupée en deux nous ajoutons une close lors de la détection du prénom afin de savoir si le prochain mot est le nom de l'entité ou pas. 
  - pronoms
Le premier probleme est de savoir le sexe de l'entité, doit on chercher un "he" un "she ? nous avons remarqué que le premier he/she
rencontré fait référence de facon correcte a l'entité. A partir de cette hypothese il nous fallait detecter le premier he/she et 
chercher toute ses occurences dans le texte. Dans le cas des ville il n'y a pas de pronoms.

EXERCICE 2 :
  - date
la date de naissance des entité, si elle existe, est la première date suivant la première parenthèse ouvrante. Celle ci se trouvant toujours dans la premiere phrase nous avons split le texte au niveau des parenthese ouvrantes et scannons les String obtenues, la premiere date trouvée interrompt la recherche. Dans notre programme, un jour est un [0-9]?[0-9], une annee [0-9]{4}, pour le mois, l'expression [A-Za-z]{3,9} n'étant pas assez restrictive, nous avons énumeré les différents mois.
Nous avons ensuite construit la balise à l'aide d'un StringBuilder.
